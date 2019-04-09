package share.jsonLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

    /// -- Définition des chemins --- ///

    private final static String MAGASIN_FILE = "magasin.json";
    private final static String USER_FILE = "user.json";
    private final static String VISITE_FILE = "visite.json";

    JsonLoaderImpl()
    {
    }

    @Override
    public void loadAllDatas() throws JSONException
    {
        loadMagasinInfos(loadJSONFromAsset(MAGASIN_FILE));
        loadUserInfos(loadJSONFromAsset(USER_FILE));
        loadVisitesInfos(loadJSONFromAsset(VISITE_FILE));
    }


    @Override
    public void saveAllDatas()
    {
    }

    @Override
    public void saveAllVisites()
    {

    }

    @Override
    public void updateVisite(int idVisite)
    {

    }

    private void loadMagasinInfos(String file) throws JSONException
    {
        if (file.isEmpty())
            return;
        JSONObject obj = new JSONObject(file);
        JSONArray arr = obj.getJSONArray("magasins");
        JSONObject o;
        ArrayList<Magasin> magasins = new ArrayList<>();
        Magasin m;
        for (int i = 0; i < arr.length(); i++)
        {
            o = arr.getJSONObject(i);
            m = new Magasin();
            m.setId(o.getInt("id"));
            m.setVille(o.getString("ville"));
            m.setEnseigne(EnumEnseigne.getEnumFromString(o.getString("enseigne")));
            magasins.add(m);
        }
        DataManager.INSTANCE.setMagasins(magasins);
    }

    private void loadUserInfos(String file) throws JSONException
    {
        if (file.isEmpty())
            return;
        JSONObject obj = new JSONObject(file);
        JSONArray arr = obj.getJSONArray("users");
        JSONObject o;
        ArrayList<User> users = new ArrayList<>();
        User u;
        for (int i = 0; i < arr.length(); i++)
        {
            o = arr.getJSONObject(i);
            EnumUser enumUser = EnumUser.getEnumFromString(o.getString("function"));
            if (enumUser != null)
                switch (enumUser)
                {
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

    private void loadVisitesInfos(String file) throws JSONException
    {
        if (file.isEmpty())
            return;
        JSONObject obj = new JSONObject(file);
        JSONArray arr = obj.getJSONArray("visites");
        JSONObject o;
        ArrayList<Visite> visites = new ArrayList<>();
        Visite v;

        for (int i = 0; i < arr.length(); i++)
        {
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

//    private String loadFileFromPath(String filePath)
//    {
//        File file = new File(loadJSONFromAsset(filePath));
//        String output = "";
//        Scanner sc = null;
//        try
//        {
//            sc = new Scanner(file);
//        } catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        while (sc != null && sc.hasNextLine())
//            output = output.concat(sc.nextLine());
//        return output;
//    }

    public String loadJSONFromAsset(String filePath)
    {
        String json = null;
        try
        {
            InputStream is = MainActivity.INSTANCE.getAssetManager().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}