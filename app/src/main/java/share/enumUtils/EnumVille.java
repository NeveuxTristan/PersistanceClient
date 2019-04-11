package share.enumUtils;

/**
 * @author Neveux_du_Geniebre on 11/04/2019.
 */
public enum EnumVille {

    LAVAL(0, "Laval"),
    ANGERS(1, "ANGERS"),
    NANTES(2, "Nantes"),
    PARIS(3, "Paris"),
    LYON(4, "Lyon"),
    ROUENS(5, "Rouens"),
    BREST(6, "Brest"),
    VANNES(7, "Vannes"),
    RENNES(8, "Rennes"),
    STRASBOURG(9, "Strasbourg");

    private int id;

    private String displayName;

    EnumVille(int id, String displayName)
    {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId()
    {
        return id;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public static EnumVille getVilleById(int id)
    {
        switch (id)
        {
            case 0:
                return LAVAL;
            case 1:
                return ANGERS;
            case 2:
                return NANTES;
            case 3:
                return PARIS;
            case 4:
                return LYON;
            case 5:
                return ROUENS;
            case 6:
                return BREST;
            case 7:
                return VANNES;
            case 8:
                return RENNES;
            case 9:
                return STRASBOURG;
            default:
                return null;
        }

    }

}
