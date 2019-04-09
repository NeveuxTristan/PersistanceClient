package share.dataObject;

import share.enumUtils.EnumUser;

/**
 * Classe représentant un manager dans le système
 */
public class Manager extends User {

    public Manager() {
        this.setUserType(EnumUser.MANAGER);
    }

    @Override
    public String toString() {
        return super.toString() + " MANAGER ";
    }

}