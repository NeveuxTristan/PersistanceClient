package share.dataObject;

import share.enumUtils.EnumUser;

import java.io.Serializable;

/**
 * Classe de base d'un utilisateur
 */
public abstract class User implements Serializable {

    /**
     * Id de l'utilisateur
     */
    private int id;

    /**
     * Type d'utilisateur
     */
    private EnumUser userType;

    /**
     * Nom de l'utilisateur
     */
    private String name;

    /**
     * Prénom de l'utilisateur
     */
    private String firstName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnumUser getUserType() {
        return userType;
    }

    void setUserType(EnumUser userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userType=" + userType +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
