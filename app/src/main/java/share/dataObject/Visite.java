package share.dataObject;

import java.io.Serializable;

/**
 * Classe représentant une visite dans le sytème
 */
public class Visite implements Serializable {

    private static int AUTO_ID = 1;

    /**
     * Date de création de la visite
     */
    private long timestamp;

    /**
     * Id unique de la visite
     */
    private int id;

    /**
     * Id du magasin visité
     */
    private int idMagasin;

    /**
     * Id du visiteur
     */
    private int idVisitor;

    /**
     * Date prévue de la visite
     */
    private String dateVisite;

    /**
     * La visite est-elle est effectuée
     */
    private boolean isVisiteDone;

    /**
     * commentaire facultatif
     */
    private String comment;

    public Visite() {
    }

    public Visite(int id, int idMagasin, int idVisitor, String dateVisite, boolean isVisiteDone, String comment) {
        this.id = AUTO_ID++;
        this.idMagasin = idMagasin;
        this.idVisitor = idVisitor;
        this.dateVisite = dateVisite;
        this.isVisiteDone = isVisiteDone;
        this.comment = comment;
    }

    public Visite(int idMagasin, int idVisitor, String dateVisite, boolean isVisiteDone, String comment) {
        this.idMagasin = idMagasin;
        this.idVisitor = idVisitor;
        this.dateVisite = dateVisite;
        this.isVisiteDone = isVisiteDone;
        this.comment = comment;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    public int getIdVisitor() {
        return idVisitor;
    }

    public void setIdVisitor(int idVisitor) {
        this.idVisitor = idVisitor;
    }

    public String getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(String dateVisite) {
        this.dateVisite = dateVisite;
    }

    public boolean isVisiteDone() {
        return isVisiteDone;
    }

    public void setVisiteDone(boolean visiteDone) {
        isVisiteDone = visiteDone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Visite{" +
                "timestamp=" + timestamp +
                ", id=" + id +
                ", idMagasin=" + idMagasin +
                ", idVisitor=" + idVisitor +
                ", dateVisite='" + dateVisite + '\'' +
                ", isVisiteDone=" + isVisiteDone +
                ", comment='" + comment + '\'' +
                '}';
    }
}
