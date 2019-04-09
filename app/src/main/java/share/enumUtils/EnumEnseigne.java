package share.enumUtils;

public enum EnumEnseigne {

    CARREFOUR(0, "CARREFOUR"), LECLERC(1, "E.Leclerc"), SUPER_U(2, "Super U");

    private int id;

    private String displayName;

    EnumEnseigne(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EnumEnseigne getEnumFromString(String s) {
        switch (s) {
            case "CARREFOUR":
                return CARREFOUR;
            case "LECLERC":
                return LECLERC;
            case "SUPER_U":
                return SUPER_U;
        }
        return null;
    }
}
