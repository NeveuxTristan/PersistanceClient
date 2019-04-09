package client.connection;

public interface ConnectionManager {

    ConnectionManagerImpl INSTANCE = new ConnectionManagerImpl();

    boolean getStateFirstConnection();

    boolean connect();

    void setUser(boolean isManager, int id);

    int getUser();
}