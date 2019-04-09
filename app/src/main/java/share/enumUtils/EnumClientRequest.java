package share.enumUtils;

public enum EnumClientRequest {

    //Première connexion du client -> On synchronise les données
    FIRST_CONNECTION(0, "FIRST CONNECTION"),
    //Client se reconnecte -> On check les datas
    CONNECTION(1, "CONNECTION"),
    // LE client fait une mise à jour et l'envoi au serveur
    SEND_UPDATE(2, "SEND UPDATE"),
    // Error
    ERROR(-1, "ERROR");

    int id;
    String displayEnum;

    EnumClientRequest(int id, String displayEnum) {
        this.id = id;
        this.displayEnum = displayEnum;
    }

    public int getId() {
        return id;
    }

    public String getDisplayEnum() {
        return displayEnum;
    }

    public static EnumClientRequest getEnumByID(int id) {
        switch (id) {
            case 0:
                return FIRST_CONNECTION;
            case 1:
                return CONNECTION;
            case 2:
                return SEND_UPDATE;
            default:
                return ERROR;
        }
    }
}
