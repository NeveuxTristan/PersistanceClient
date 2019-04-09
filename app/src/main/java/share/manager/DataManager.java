package share.manager;

import org.json.JSONException;

import share.dataObject.*;
import share.enumUtils.EnumEnseigne;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'exposer les données
 */
public interface DataManager {

    /**
     * INSTANCE UNIQUE de DataManager
     */
    DataManager INSTANCE = new DataManagerImpl();

    /**
     * -- SET DES DONNEES --
     **/

    void loadDatas() throws JSONException;

    void setMagasins(ArrayList<Magasin> magasins);

    void setVisites(ArrayList<Visite> visites);

    void setUsers(ArrayList<User> users);


    /**
     * --- OBTENTION DES DONNEES PAR GENRE ---
     **/

    List<User> getAllUsers();

    List<Visite> getAllVisites();

    List<Magasin> getAllMagasins();

    List<Manager> getAllManagers();

    List<Seller> getAllSellers();

    /**
     * --- OBTENTION DES DONNEES A PARTIR D'UN ID ---
     **/

    List<Seller> getAllSellersByManager(int idManager);

    List<Visite> getAllVisiteByUser(int idUser);

    List<Magasin> getAllMagasinByEnseigne(EnumEnseigne enumEnseigne);

}