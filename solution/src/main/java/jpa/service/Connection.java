package jpa.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class Connection {

    protected EntityManagerFactory emf = null;
    protected EntityManager em = null;
    private String dbName = "SMS";

    public void connect() {
        this.emf = Persistence.createEntityManagerFactory(dbName);
        this.em = emf.createEntityManager();
    }

    public void disconnect() {
        if (this.em != null) {
            em.close();
        }
        if (this.emf != null) {
            emf.close();
        }
    }
}
