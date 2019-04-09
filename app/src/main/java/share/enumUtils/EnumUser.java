package share.enumUtils;

public enum EnumUser {

    MANAGER(0, "Manager"), SELLER(1, "Vendeur");

    private int id;

    private String displayName;

    EnumUser(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EnumUser getEnumFromString(String s) {
        switch (s) {
            case "MANAGER":
                return MANAGER;
            case "SELLER":
                return SELLER;
        }
        return null;
    }
}
