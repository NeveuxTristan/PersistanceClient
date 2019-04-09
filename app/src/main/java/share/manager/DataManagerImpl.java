package share.manager;

import org.json.JSONException;

import share.dataObject.*;
import share.enumUtils.EnumEnseigne;
import share.enumUtils.EnumUser;
import share.jsonLoader.JsonLoader;

import java.util.ArrayList;
import java.util.List;

class DataManagerImpl implements DataManager {

    /**
     * Liste des utilisateurs
     */
    private ArrayList<User> users;
    private ArrayList<Magasin> magasins;
    private ArrayList<Visite> visites;

    /**
     * Chargement des données au démarrage
     */
    DataManagerImpl() {

    }

    @Override
    public void loadDatas() throws JSONException
    {
        JsonLoader.INSTANCE.loadAllDatas();
    }

    @Override
    public void setMagasins(ArrayList<Magasin> magasins) {
        this.magasins = magasins;
    }

    @Override
    public void setVisites(ArrayList<Visite> visites) {
        this.visites = visites;
    }

    @Override
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public List<Visite> getAllVisites() {
        return visites;
    }

    @Override
    public List<Magasin> getAllMagasins() {
        return magasins;
    }

    @Override
    public List<Manager> getAllManagers() {
        ArrayList<Manager> managers = new ArrayList<>();
        for (User u : users) {
            if (EnumUser.MANAGER.equals(u.getUserType())) {
                managers.add((Manager) u);
            }
        }
        return managers;
    }

    @Override
    public List<Seller> getAllSellers() {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (User u : users) {
            if (EnumUser.SELLER.equals(u.getUserType())) {
                sellers.add((Seller) u);
            }
        }
        return sellers;
    }

    @Override
    public List<Seller> getAllSellersByManager(int idManager) {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (User u : users) {
            if (EnumUser.SELLER.equals(u.getUserType()) && ((Seller) u).getIdManager() == idManager) {
                sellers.add((Seller) u);
            }
        }
        return sellers;
    }

    @Override
    public List<Visite> getAllVisiteByUser(int idUser) {
        ArrayList<Visite> visitesByUser = new ArrayList<>();
        for (Visite v : visites) {
            if (idUser == v.getIdVisitor()) {
                visitesByUser.add(v);
            }
        }
        return visitesByUser;
    }

    @Override
    public List<Magasin> getAllMagasinByEnseigne(EnumEnseigne enumEnseigne) {
        ArrayList<Magasin> magasinsByEnseigne = new ArrayList<>();
        for (Magasin m : magasins) {
            if (enumEnseigne.equals(m.getEnseigne())) {
                magasinsByEnseigne.add(m);
            }
        }
        return magasinsByEnseigne;
    }


}
