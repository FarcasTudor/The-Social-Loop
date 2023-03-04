package social_network.domain;


import java.io.Serializable;

/**
 * Clasa ce reprezinta un obiect abstract
 *
 * @param <ID>
 */
public class Entity<ID> implements Serializable {

    private static final long serialVersionUID = 7331115341259248461L;


    /**
     * ID-ul entitatii
     */
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public Entity() {
    }

    /**
     * Metoda ce returneaza ID-ul entitatii
     *
     * @return ID
     */
    public ID getId() {
        return id;
    }

    /**
     * Metoda ce seteaza ID-ul id la entitate
     *
     * @param id - id of entity
     */
    public void setId(ID id) {
        this.id = id;
    }
}
