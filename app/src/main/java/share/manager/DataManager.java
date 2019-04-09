package share.manager;

import org.json.JSONException;

import java.util.ArrayList;

import share.dataObject.Magasin;
import share.dataObject.Manager;
import share.dataObject.Seller;
import share.dataObject.User;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;

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

    ArrayList<User> getAllUsers();

    ArrayList<Visite> getAllVisites();

    ArrayList<Magasin> getAllMagasins();

    ArrayList<Manager> getAllManagers();

    ArrayList<Seller> getAllSellers();

    /**
     * --- OBTENTION DES DONNEES A PARTIR D'UN ID ---
     **/

    ArrayList<Seller> getAllSellersByManager(int idManager);

    ArrayList<Visite> getAllVisiteByUser(int idUser);

    ArrayList<Magasin> getAllMagasinByEnseigne(EnumEnseigne enumEnseigne);

}