package share.dataObject;

import share.enumUtils.EnumEnseigne;

import java.io.Serializable;

/**
 * Classe représentant un magasin dans le système
 */
public class Magasin implements Serializable {

    /**
     * Identifiant unique du magasin
     */
    private int id;

    /**
     * Enseigne du magasin
     */
    private EnumEnseigne enseigne;

    /**
     * Ville du magasin
     */
    private String ville;

    public Magasin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnumEnseigne getEnseigne() {
        return enseigne;
    }

    public void setEnseigne(EnumEnseigne enseigne) {
        this.enseigne = enseigne;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDisplayName() {
        return this.enseigne + " " + this.ville;
    }

    @Override
    public String toString() {
        return "Magasin[" + this.id + "] " + getDisplayName();
    }
}
