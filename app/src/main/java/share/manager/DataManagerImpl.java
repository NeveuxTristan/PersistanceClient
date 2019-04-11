package share.manager;

import org.json.JSONException;

import java.util.ArrayList;

import share.dataObject.Magasin;
import share.dataObject.Manager;
import share.dataObject.Seller;
import share.dataObject.User;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;
import share.enumUtils.EnumUser;
import share.jsonLoader.JsonLoader;

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
    DataManagerImpl()
    {

    }

    @Override
    public void loadDatas() throws JSONException
    {
        JsonLoader.INSTANCE.loadAllDatas();
    }

    @Override
    public void setMagasins(ArrayList<Magasin> magasins)
    {
        this.magasins = magasins;
    }

    @Override
    public void setVisites(ArrayList<Visite> visites)
    {
        this.visites = visites;
    }

    @Override
    public void setUsers(ArrayList<User> users)
    {
        this.users = users;
    }

    @Override
    public ArrayList<User> getAllUsers()
    {
        return users;
    }

    @Override
    public ArrayList<Visite> getAllVisites()
    {
        return visites;
    }

    @Override
    public ArrayList<Magasin> getAllMagasins()
    {
        return magasins;
    }

    @Override
    public ArrayList<Manager> getAllManagers()
    {
        ArrayList<Manager> managers = new ArrayList<>();
        for (User u : users)
        {
            if (EnumUser.MANAGER.equals(u.getUserType()))
            {
                managers.add((Manager) u);
            }
        }
        return managers;
    }

    @Override
    public ArrayList<Seller> getAllSellers()
    {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (User u : users)
        {
            if (EnumUser.SELLER.equals(u.getUserType()))
            {
                sellers.add((Seller) u);
            }
        }
        return sellers;
    }

    @Override
    public ArrayList<Seller> getAllSellersByManager(int idManager)
    {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (User u : users)
        {
            if (EnumUser.SELLER.equals(u.getUserType()) && ((Seller) u).getIdManager() == idManager)
            {
                sellers.add((Seller) u);
            }
        }
        return sellers;
    }

    @Override
    public ArrayList<Visite> getAllVisiteByUser(int idUser)
    {
        ArrayList<Visite> visitesByUser = new ArrayList<>();
        for (Visite v : visites)
        {
            if (idUser == v.getIdVisitor())
            {
                visitesByUser.add(v);
            }
        }
        return visitesByUser;
    }

    @Override
    public ArrayList<Magasin> getAllMagasinByEnseigne(EnumEnseigne enumEnseigne)
    {
        ArrayList<Magasin> magasinsByEnseigne = new ArrayList<>();
        for (Magasin m : magasins)
        {
            if (enumEnseigne.equals(m.getEnseigne()))
            {
                magasinsByEnseigne.add(m);
            }
        }
        return magasinsByEnseigne;
    }

    @Override
    public Magasin getMagasinById(int id)
    {
        for (Magasin m : magasins)
        {
            if (m.getId() == id)
                return m;
        }
        return null;
    }

    @Override
    public void deleteAllVisiteForUserId(int idUser)
    {
        ArrayList visitesCopy = (ArrayList) visites.clone();
        for (Visite v : getAllVisites())
        {
            if (v.getIdVisitor() == idUser)
                visites.remove(v);
        }
        saveVisitesToJson();
    }

    @Override
    public void saveVisitesToJson()
    {
        JsonLoader.INSTANCE.saveAllVisites();
    }


}
