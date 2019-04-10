package client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import share.dataObject.User;
import share.enumUtils.EnumClientRequest;
import share.manager.DataManager;

public class ConnectionManagerImpl implements ConnectionManager {

    private boolean isFirstConnection;

    private boolean isConnected;

    private boolean isManagerAccount;

    private int idAccount;

    private Socket socket;

    private static final String HOST = "10.0.2.2 ";
    private static final int PORT = 85421;

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
    public void connect()
    {
        createConnectionClient();
        isConnected = true;
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

    private void createConnectionClient()
    {
        try
        {
            this.socket = new Socket(InetAddress.getByName(HOST), PORT);
            connectToServer();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void connectToServer() throws IOException
    {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        if (isFirstConnection)
            out.println(EnumClientRequest.FIRST_CONNECTION.getId());
        else
        {
            out.println(EnumClientRequest.CONNECTION.getId());
            out.println(DataManager.INSTANCE.getAllVisites().toString());
        }
        out.flush();
    }

}
