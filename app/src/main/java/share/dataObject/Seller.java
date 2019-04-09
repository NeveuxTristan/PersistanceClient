package share.dataObject;

import share.enumUtils.EnumUser;

/**
 * Classe représentant un vendeur dans le système
 */
public class Seller extends User {

    /**
     * Id du manager en charge
     */
    private int idManager;

    public Seller() {
        this.setUserType(EnumUser.SELLER);
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;
    }

    @Override
    public String toString() {
        return super.toString() + " Seller{" +
                "idManager=" + idManager +
                '}';
    }
}
