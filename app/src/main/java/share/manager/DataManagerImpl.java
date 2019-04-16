package share.manager;

import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import client.connection.ConnectionManager;
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
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Magasin> magasins = new ArrayList<>();
    private ArrayList<Visite> visites = new ArrayList<>();

    private ArrayList<Visite> visitesDeleted = new ArrayList<>();

    /**
     * Chargement des données au démarrage
     */
    DataManagerImpl() {

    }

    private void createFakeData() {
        users = new ArrayList<>();
        magasins = new ArrayList<>();
        visites = new ArrayList<>();
        Manager m = new Manager();
        m.setFirstName("Jeremy");
        m.setId(1);
        m.setName("PERROUAULT");
        users.add(m);
        Seller s = new Seller();
        s.setFirstName("Tristan");
        s.setId(2);
        s.setName("NEVEUX");
        users.add(s);
        s = new Seller();
        s.setFirstName("kay");
        s.setId(3);
        s.setName("DOP");
        users.add(s);

        magasins.add(new Magasin(1, EnumEnseigne.SUPER_U, 0));
        magasins.add(new Magasin(2, EnumEnseigne.SUPER_U, 1));
        magasins.add(new Magasin(3, EnumEnseigne.SUPER_U, 2));
        magasins.add(new Magasin(4, EnumEnseigne.SUPER_U, 3));
        magasins.add(new Magasin(5, EnumEnseigne.SUPER_U, 4));
        magasins.add(new Magasin(6, EnumEnseigne.SUPER_U, 5));
        magasins.add(new Magasin(7, EnumEnseigne.SUPER_U, 6));
        magasins.add(new Magasin(8, EnumEnseigne.SUPER_U, 7));
        magasins.add(new Magasin(9, EnumEnseigne.SUPER_U, 8));
        magasins.add(new Magasin(10, EnumEnseigne.SUPER_U, 9));
        magasins.add(new Magasin(11, EnumEnseigne.LECLERC, 0));
        magasins.add(new Magasin(12, EnumEnseigne.LECLERC, 1));
        magasins.add(new Magasin(13, EnumEnseigne.LECLERC, 2));
        magasins.add(new Magasin(14, EnumEnseigne.LECLERC, 3));
        magasins.add(new Magasin(15, EnumEnseigne.LECLERC, 4));
        magasins.add(new Magasin(16, EnumEnseigne.LECLERC, 5));
        magasins.add(new Magasin(17, EnumEnseigne.LECLERC, 6));
        magasins.add(new Magasin(18, EnumEnseigne.LECLERC, 7));
        magasins.add(new Magasin(19, EnumEnseigne.LECLERC, 8));
        magasins.add(new Magasin(20, EnumEnseigne.LECLERC, 9));
        magasins.add(new Magasin(21, EnumEnseigne.CARREFOUR, 0));
        magasins.add(new Magasin(22, EnumEnseigne.CARREFOUR, 1));
        magasins.add(new Magasin(23, EnumEnseigne.CARREFOUR, 2));
        magasins.add(new Magasin(24, EnumEnseigne.CARREFOUR, 3));
        magasins.add(new Magasin(25, EnumEnseigne.CARREFOUR, 4));
        magasins.add(new Magasin(26, EnumEnseigne.CARREFOUR, 5));
        magasins.add(new Magasin(27, EnumEnseigne.CARREFOUR, 6));
        magasins.add(new Magasin(28, EnumEnseigne.CARREFOUR, 7));
        magasins.add(new Magasin(29, EnumEnseigne.CARREFOUR, 8));
        magasins.add(new Magasin(30, EnumEnseigne.CARREFOUR, 9));

        visites.add(new Visite(17, 3, "04/05/2019", true, "Etat parfait"));
        visites.add(new Visite(14, 2, "08/05/2019", true, "Bien, gestion des boissons fraiches à retravailler"));
        visites.add(new Visite(28, 2, "11/05/2019", true, "Désastreux. Conseil sanitaire saisi."));
        visites.add(new Visite(1, 2, "14/05/2019", false, ""));
        visites.add(new Visite(24, 3, "16/05/2019", false, ""));
        visites.add(new Visite(6, 2, "17/05/2019", false, ""));
        visites.add(new Visite(12, 2, "24/05/2019", false, ""));
        visites.add(new Visite(23, 3, "24/05/2019", false, ""));

    }

    @Override
    public void loadDatas() throws JSONException {
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
    public ArrayList<User> getAllUsers() {
        return users;
    }

    @Override
    public ArrayList<Visite> getAllVisites() {
        return visites;
    }

    @Override
    public ArrayList<Magasin> getAllMagasins() {
        return magasins;
    }

    @Override
    public ArrayList<Manager> getAllManagers() {
        ArrayList<Manager> managers = new ArrayList<>();
        for (User u : users) {
            if (EnumUser.MANAGER.equals(u.getUserType())) {
                managers.add((Manager) u);
            }
        }
        return managers;
    }

    @Override
    public ArrayList<Seller> getAllSellers() {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (User u : users) {
            if (EnumUser.SELLER.equals(u.getUserType())) {
                sellers.add((Seller) u);
            }
        }
        return sellers;
    }

    @Override
    public ArrayList<Seller> getAllSellersByManager(int idManager) {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (User u : users) {
            if (EnumUser.SELLER.equals(u.getUserType()) && ((Seller) u).getIdManager() == idManager) {
                sellers.add((Seller) u);
            }
        }
        return sellers;
    }

    @Override
    public ArrayList<Visite> getAllVisiteByUser(int idUser) {
        ArrayList<Visite> visitesByUser = new ArrayList<>();
        for (Visite v : visites) {
            if (idUser == v.getIdVisitor()) {
                visitesByUser.add(v);
            }
        }
        return visitesByUser;
    }

    @Override
    public ArrayList<Magasin> getAllMagasinByEnseigne(EnumEnseigne enumEnseigne) {
        ArrayList<Magasin> magasinsByEnseigne = new ArrayList<>();
        for (Magasin m : magasins) {
            if (enumEnseigne.equals(m.getEnseigne())) {
                magasinsByEnseigne.add(m);
            }
        }
        return magasinsByEnseigne;
    }

    @Override
    public Magasin getMagasinById(int id) {
        for (Magasin m : magasins) {
            if (m.getId() == id)
                return m;
        }
        return null;
    }

    @Override
    public Magasin getMagasinByEnseigneAndCity(EnumEnseigne enseigne, int idCity) {
        Log.d("enseigne = " + enseigne + " idCity = " + idCity, magasins.toString());
        for (Magasin m : magasins) {
            if (m.getEnseigne().equals(enseigne) && m.getVille() == idCity)
                return m;
        }
        return null;
    }

    @Override
    public void deleteAllVisiteForUserId(int idUser) {
        ArrayList<Visite> visitesCopy = (ArrayList<Visite>) visites.clone();
        for (Visite v : visitesCopy) {
            if (v.getIdVisitor() == idUser) {
                visitesDeleted.add(v);
                visites.remove(v);
            }
        }
        if (ConnectionManager.INSTANCE.isConnected())
            ConnectionManager.INSTANCE.connectAndSyncDatas();
        saveVisitesToJson();
    }

    @Override
    public void deleteVisiteById(int id) {
        ArrayList<Visite> visitesCopy = (ArrayList<Visite>) visites.clone();
        boolean deleteOk = false;
        int position = 0;
        for (Visite v : visitesCopy) {
            if (v.getId() == id) {
                visites.remove(position);
                visitesDeleted.add(v);
                deleteOk = true;
                break;
            }
            position++;
        }
        if (deleteOk) {
            if (ConnectionManager.INSTANCE.isConnected())
                ConnectionManager.INSTANCE.connectAndSyncDatas();
            saveVisitesToJson();
        }
    }

    @Override
    public void saveAll() {
        JsonLoader.INSTANCE.saveAllDatas();
    }

    @Override
    public void saveVisitesToJson() {
        JsonLoader.INSTANCE.saveAllVisites();
    }

    @Override
    public void saveVisite(Visite visiteToSave) {
        ArrayList<Visite> visitesCopy = (ArrayList<Visite>) visites.clone();
        boolean saveOk = false;
        int position = 0;
        for (Visite v : visitesCopy) {
            if (v.getId() == visiteToSave.getId()) {
                visites.set(position, visiteToSave);
                saveOk = true;
                break;
            }
            position++;
        }
        if (saveOk) {
            if (ConnectionManager.INSTANCE.isConnected())
                ConnectionManager.INSTANCE.connectAndSyncDatas();
            saveVisitesToJson();
        }
    }

    @Override
    public Visite addVisite(int idSeller, int idMagasin, String date) {
        Visite v = new Visite(idMagasin, idSeller, date, false, "", Calendar.getInstance().getTimeInMillis());
        visites.add(v);
        if (ConnectionManager.INSTANCE.isConnected())
            ConnectionManager.INSTANCE.connectAndSyncDatas();
        saveVisitesToJson();
        return v;
    }

    @Override
    public void init() {
        //  createFakeData();
        try {
            loadDatas();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean syncVisites(ArrayList<Visite> visitesFromServer) {
        ArrayList<Visite> visitesCopy = (ArrayList<Visite>) visites.clone();
        ArrayList<Visite> newVisites = new ArrayList<>();

        Visite tmp;
        for (Visite v : visitesFromServer) {
            tmp = getVisiteById(v.getId());
            if (tmp != null) {
                if (v.getTimestamp() > tmp.getTimestamp())
                    newVisites.add(v);
                else newVisites.add(tmp);
                visitesCopy.remove(tmp);
            } else {
                tmp = getVisiteDeletedById(v.getId());
                if (tmp == null)
                    newVisites.add(v);
            }
        }
        if (!visitesCopy.isEmpty()) {
            for (Visite vi : visitesCopy) {
                if (vi.isCreatedLocally())
                    newVisites.add(vi);
            }
        }
        visitesDeleted.clear();
        setVisites(newVisites);
        return true;
    }

    private Visite getVisiteDeletedById(int id) {
        for (Visite v : visitesDeleted) {
            if (v.getId() == id)
                return v;
        }
        return null;
    }

    private Visite getVisiteById(int id) {
        for (Visite v : visites) {
            if (v.getId() == id)
                return v;
        }
        return null;
    }


}
