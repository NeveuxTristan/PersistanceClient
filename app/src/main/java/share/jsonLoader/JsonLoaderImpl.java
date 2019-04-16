package share.jsonLoader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import client.MainActivity;
import share.dataObject.Magasin;
import share.dataObject.Manager;
import share.dataObject.Seller;
import share.dataObject.User;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;
import share.enumUtils.EnumUser;
import share.manager.DataManager;

public class JsonLoaderImpl implements JsonLoader {

    //-- Définition des chemins ---//

    private final static String MAGASIN_FILE = "magasin.json";
    private final static String USER_FILE = "user.json";
    private final static String VISITE_FILE = "visite.json";

    JsonLoaderImpl() {
    }

    @Override
    public void loadAllDatas() throws JSONException {
        loadMagasinInfos(loadJsonFromLocalStorage(MAGASIN_FILE));
        loadUserInfos(loadJsonFromLocalStorage(USER_FILE));
        loadVisitesInfos(loadJsonFromLocalStorage(VISITE_FILE));
    }


    @Override
    public void saveAllDatas() {
        saveAllMagasins();
        saveAllUsers();
        saveAllVisites();
    }

    @Override
    public void saveAllVisites() {
        JSONObject json = createJsonFileFromVisite();
        try {
            Writer output = null;
            File file = new File(MainActivity.INSTANCE.getFilesDir(), VISITE_FILE);
            output = new BufferedWriter(new FileWriter(file));
            output.write(json.toString());
            output.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveAllUsers() {
        JSONObject json = createJsonFileFromUser();
        try {
            Writer output = null;
            File file = new File(MainActivity.INSTANCE.getFilesDir(), USER_FILE);
            output = new BufferedWriter(new FileWriter(file));
            output.write(json.toString());
            output.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void saveAllMagasins() {
        JSONObject json = createJsonFileFromMagasin();
        try {
            Writer output = null;
            File file = new File(MainActivity.INSTANCE.getFilesDir(), MAGASIN_FILE);
            output = new BufferedWriter(new FileWriter(file));
            output.write(json.toString());
            output.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public JSONObject createJsonFileFromVisite() {
        JSONObject finalJson = new JSONObject();
        try {
            JSONArray ja = new JSONArray();
            JSONObject jo;
            for (Visite v : DataManager.INSTANCE.getAllVisites()) {
                jo = new JSONObject();
                jo.put("id", v.getId());
                jo.put("idMagasin", v.getIdMagasin());
                jo.put("idVisitor", v.getIdVisitor());
                jo.put("dateVisite", v.getDateVisite());
                jo.put("isVisiteDone", v.isVisiteDone());
                jo.put("comment", v.getComment());
                jo.put("timestamp", v.getTimestamp());
                ja.put(jo);
            }
            finalJson.put("visites", ja);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalJson;
    }

    private JSONObject createJsonFileFromUser() {
        JSONObject finalJson = new JSONObject();
        try {
            JSONArray ja = new JSONArray();
            JSONObject jo;
            for (User u : DataManager.INSTANCE.getAllUsers()) {
                jo = new JSONObject();
                jo.put("id", u.getId());
                jo.put("name", u.getName());
                jo.put("firstname", u.getFirstName());
                jo.put("function", u.getUserType());
                if (u.getUserType().equals(EnumUser.SELLER))
                    jo.put("managerid", ((Seller) u).getIdManager());
                ja.put(jo);
            }
            finalJson.put("users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalJson;
    }

    private JSONObject createJsonFileFromMagasin() {
        JSONObject finalJson = new JSONObject();
        try {
            JSONArray ja = new JSONArray();
            JSONObject jo;
            for (Magasin m : DataManager.INSTANCE.getAllMagasins()) {
                jo = new JSONObject();
                jo.put("id", m.getId());
                jo.put("enseigne", m.getEnseigne());
                jo.put("villeId", m.getVille());
                ja.put(jo);
            }
            finalJson.put("magasins", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalJson;
    }


    @Override
    public void updateVisite(int idVisite) {

    }

    private void loadMagasinInfos(String file) throws JSONException {
        if (file == null || file.isEmpty())
            return;
        JSONObject obj = new JSONObject(file);
        JSONArray arr = obj.getJSONArray("magasins");
        JSONObject o;
        ArrayList<Magasin> magasins = new ArrayList<>();
        Magasin m;
        for (int i = 0; i < arr.length(); i++) {
            o = arr.getJSONObject(i);
            m = new Magasin();
            m.setId(o.getInt("id"));
            m.setVille(o.getInt("villeId"));
            m.setEnseigne(EnumEnseigne.getEnumFromString(o.getString("enseigne")));
            magasins.add(m);
        }
        DataManager.INSTANCE.setMagasins(magasins);
    }

    private void loadUserInfos(String file) throws JSONException {
        if (file == null || file.isEmpty())
            return;
        JSONObject obj = new JSONObject(file);
        JSONArray arr = obj.getJSONArray("users");
        JSONObject o;
        ArrayList<User> users = new ArrayList<>();
        User u;
        for (int i = 0; i < arr.length(); i++) {
            o = arr.getJSONObject(i);
            EnumUser enumUser = EnumUser.getEnumFromString(o.getString("function"));
            if (enumUser != null)
                switch (enumUser) {
                    case MANAGER:
                        u = new Manager();
                        u.setId(o.getInt("id"));
                        u.setFirstName(o.getString("firstname"));
                        u.setName(o.getString("name"));
                        users.add(u);
                        break;
                    case SELLER:
                        u = new Seller();
                        u.setId(o.getInt("id"));
                        u.setFirstName(o.getString("firstname"));
                        u.setName(o.getString("name"));
                        ((Seller) u).setIdManager(o.getInt("managerid"));
                        users.add(u);
                        break;
                }
        }
        DataManager.INSTANCE.setUsers(users);
    }

    private void loadVisitesInfos(String file) throws JSONException {
        if (file == null || file.isEmpty())
            return;
        JSONObject obj = new JSONObject(file);
        JSONArray arr = obj.getJSONArray("visites");
        JSONObject o;
        ArrayList<Visite> visites = new ArrayList<>();
        Visite v;

        for (int i = 0; i < arr.length(); i++) {
            o = arr.getJSONObject(i);
            v = new Visite();
            v.setId(o.getInt("id"));
            v.setTimestamp(o.getLong("timestamp"));
            v.setIdMagasin(o.getInt("idMagasin"));
            v.setIdVisitor(o.getInt("idVisitor"));
            v.setVisiteDone(o.getBoolean("isVisiteDone"));
            v.setDateVisite(o.getString("dateVisite"));
            v.setComment(o.getString("comment"));
            visites.add(v);
        }
        DataManager.INSTANCE.setVisites(visites);
    }

    String loadJsonFromLocalStorage(String filePath) {
        String json = null;
        String[] files = MainActivity.INSTANCE.fileList();
        boolean find = false;
        for (String file : files) {
            if (file.equals(filePath)) {
                try {
                    InputStream is = MainActivity.INSTANCE.openFileInput(filePath);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    json = new String(buffer, "UTF-8");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return null;
                }
                return json;
            }
        }
        if (!find)
            new File(MainActivity.INSTANCE.getFilesDir(), filePath);

        return null;
    }
}