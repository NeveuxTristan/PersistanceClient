package client.connection;

public interface ConnectionManager {

    ConnectionManagerImpl INSTANCE = new ConnectionManagerImpl();

    boolean getStateFirstConnection();

    void connect();

    void connectAndSyncDatas();

    void disconnect();

    void setUser(boolean isManager, int id);

    int getUser();

    boolean isConnected();
}
