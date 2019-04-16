package share.jsonLoader;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonLoader {

    JsonLoaderImpl INSTANCE = new JsonLoaderImpl();

    void loadAllDatas() throws JSONException;

    void saveAllDatas();

    void saveAllVisites();

    void saveAllUsers();

    void saveAllMagasins();

    JSONObject createJsonFileFromVisite();

    void updateVisite(int idVisite);

}
