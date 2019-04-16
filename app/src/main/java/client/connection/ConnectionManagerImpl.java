package client.connection;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import share.dataObject.Magasin;
import share.dataObject.Manager;
import share.dataObject.Seller;
import share.dataObject.User;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;
import share.enumUtils.EnumUser;
import share.jsonLoader.JsonLoader;
import share.manager.DataManager;

public class ConnectionManagerImpl implements ConnectionManager {

    private static final String IP_ADRESS = "192.168.43.43:80";

    private boolean isFirstConnection;

    private boolean isConnected;

    private boolean isManagerAccount;

    private int idAccount;

    ConnectionManagerImpl() {
        checkFirstConnection();
        this.isConnected = false;
    }

    /**
     * On vérifie l'état des Datas pour savoir si c'est la première
     * Si les utilisateurs existent alors les données sont OK
     */
    private void checkFirstConnection() {
        DataManager.INSTANCE.init();
        ArrayList<Magasin> magasins = DataManager.INSTANCE.getAllMagasins();
        isFirstConnection = magasins != null && !magasins.isEmpty();
    }

    @Override
    public boolean getStateFirstConnection() {
        return isFirstConnection;
    }

    @Override
    public void connect() {
        createConnectionClient();
        isConnected = true;
    }

    @Override
    public void setUser(boolean isManager, int id) {
        this.isManagerAccount = isManager;
        this.idAccount = id;
    }

    @Override
    public int getUser() {
        return idAccount;
    }

    @Override
    public boolean isConnected() {
        return this.isConnected;
    }

    private void createConnectionClient() {
        if (!isFirstConnection) {
            connectAndGetAllDatas();
            isFirstConnection = true;
        } else
            connectAndSyncDatas();
    }

    private void connectAndGetAllDatas() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + IP_ADRESS + "/persistance/sendAll.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    BufferedReader br = new BufferedReader(in);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    br.close();
                    JSONArray jsonObject = new JSONArray(sb.toString());

                    try {
                        parseFlyJson(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    private void parseFlyJson(JSONArray reader) throws JSONException, IOException {

        JSONArray users = reader.getJSONObject(0).getJSONArray("users");
        JSONArray magasins = reader.getJSONObject(1).getJSONArray("magasins");
        JSONArray visites = reader.getJSONObject(2).getJSONArray("visites");

        ArrayList<User> usersFromServer = new ArrayList<>();
        ArrayList<Magasin> magasinsFromServer = new ArrayList<>();
        ArrayList<Visite> visitesFromServer = new ArrayList<>();

        User u;
        for (int i = 0; i < users.length(); i++) {
            JSONObject userObj = users.getJSONObject(i);
            switch (EnumUser.getEnumFromString(userObj.getString("function"))) {
                case MANAGER:
                    u = new Manager();
                    break;
                default:
                    u = new Seller();
                    ((Seller) u).setIdManager(userObj.getInt("managerid"));
                    break;
            }
            u.setId(userObj.getInt("id"));
            u.setFirstName(userObj.getString("firstname"));
            u.setName(userObj.getString("name"));
            usersFromServer.add(u);
        }

        Magasin m;
        for (int i = 0; i < magasins.length(); i++) {
            JSONObject magasinObj = magasins.getJSONObject(i);
            m = new Magasin();
            m.setId(magasinObj.getInt("id"));
            m.setEnseigne(EnumEnseigne.getEnumFromString(magasinObj.getString("enseigne")));
            m.setVille(magasinObj.getInt("villeId"));
            magasinsFromServer.add(m);
        }

        Visite v;
        for (int i = 0; i < visites.length(); i++) {
            JSONObject visiteObj = visites.getJSONObject(i);
            v = new Visite();
            v.setId(visiteObj.getInt("id"));
            v.setIdMagasin(visiteObj.getInt("idMagasin"));
            v.setIdVisitor(visiteObj.getInt("idVisitor"));
            v.setComment(visiteObj.getString("comment"));
            v.setVisiteDone(visiteObj.getBoolean("isVisiteDone"));
            v.setDateVisite(visiteObj.getString("dateVisite"));
            v.setTimestamp(visiteObj.getLong("timestamp"));
            visitesFromServer.add(v);
        }

        DataManager.INSTANCE.setMagasins(magasinsFromServer);
        DataManager.INSTANCE.setUsers(usersFromServer);
        DataManager.INSTANCE.setVisites(visitesFromServer);
        DataManager.INSTANCE.saveAll();
    }

    @Override
    public void connectAndSyncDatas() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + IP_ADRESS + "/persistance/sendVisites.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    BufferedReader br = new BufferedReader(in);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    br.close();
                    JSONObject jsonObject = new JSONObject(sb.toString());

                    try {
                        parseAndSyncVisites(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void parseAndSyncVisites(JSONObject reader) throws JSONException, IOException {
        JSONArray visites = reader.getJSONArray("visites");
        ArrayList<Visite> visitesFromServer = new ArrayList<>();

        Visite v;
        for (int i = 0; i < visites.length(); i++) {
            JSONObject visiteObj = visites.getJSONObject(i);
            v = new Visite();
            v.setId(visiteObj.getInt("id"));
            v.setIdMagasin(visiteObj.getInt("idMagasin"));
            v.setIdVisitor(visiteObj.getInt("idVisitor"));
            v.setComment(visiteObj.getString("comment"));
            v.setVisiteDone(visiteObj.getBoolean("isVisiteDone"));
            v.setTimestamp(visiteObj.getLong("timestamp"));
            v.setDateVisite(visiteObj.getString("dateVisite"));
            visitesFromServer.add(v);
        }

        if (DataManager.INSTANCE.syncVisites(visitesFromServer)) {
            DataManager.INSTANCE.saveVisitesToJson();
            NotifyVisitesToServer();
        }
    }

    private void NotifyVisitesToServer() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + IP_ADRESS + "/persistance/saveVisite.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonToSend = JsonLoader.INSTANCE.createJsonFileFromVisite();

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonToSend.toString());

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    BufferedReader br = new BufferedReader(in);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println(sb.toString());

                    os.flush();
                    os.close();

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public void disconnect() {
        this.isConnected = false;
    }
}
