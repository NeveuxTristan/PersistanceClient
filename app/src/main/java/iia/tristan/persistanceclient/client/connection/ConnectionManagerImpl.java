package iia.tristan.persistanceclient.client.connection;

public class ConnectionManagerImpl implements ConnectionManager {

    private boolean isFirstConnection;

    private boolean isConnected;

    private boolean isManagerAccount;

    private int idAccount;

    ConnectionManagerImpl() {
        checkFirstConnection();
        this.isConnected = false;
    }

    /**
     * On vérifie l'état des Datas pour savoir si c'est la première connection
     */
    private void checkFirstConnection() {

    }


    @Override
    public boolean getStateFirstConnection() {
        return isFirstConnection;
    }

    @Override
    public boolean connect() {
        return isConnected;
    }

    @Override
    public void setUser(boolean isManager, int id) {
        this.isManagerAccount = isManager;
        this.idAccount = id;
    }
}
