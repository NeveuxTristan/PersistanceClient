package client.connection;

import java.util.ArrayList;

import share.dataObject.User;
import share.manager.DataManager;

public class ConnectionManagerImpl implements ConnectionManager {

    private boolean isFirstConnection;

    private boolean isConnected;

    private boolean isManagerAccount;

    private int idAccount;

    ConnectionManagerImpl()
    {
        checkFirstConnection();
        this.isConnected = false;
    }

    /**
     * On vérifie l'état des Datas pour savoir si c'est la première
     * Si les utilisateurs existent alors les données sont OK
     */
    private void checkFirstConnection()
    {
        ArrayList<User> users = DataManager.INSTANCE.getAllUsers();
        isFirstConnection = users != null && !users.isEmpty();
    }

    @Override
    public boolean getStateFirstConnection()
    {
        return isFirstConnection;
    }

    @Override
    public boolean connect()
    {
        return isConnected;
    }

    @Override
    public void setUser(boolean isManager, int id)
    {
        this.isManagerAccount = isManager;
        this.idAccount = id;
    }

    @Override
    public int getUser()
    {
        return idAccount;
    }
}
