package share.jsonLoader;

import org.json.JSONException;

public interface JsonLoader {

    JsonLoaderImpl INSTANCE = new JsonLoaderImpl();

    void loadAllDatas() throws JSONException;

    void saveAllDatas();

    void saveAllVisites();

    void updateVisite(int idVisite);

}
