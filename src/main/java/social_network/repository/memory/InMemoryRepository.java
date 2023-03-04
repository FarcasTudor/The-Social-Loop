package social_network.repository.memory;


import social_network.repository.Repository;
import social_network.domain.Entity;
import social_network.exceptions.DuplicateException;
import social_network.exceptions.LackException;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository class that contains entities
 *
 * @param <ID> - ID of entity
 * @param <E>  - type of entity
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {


    /**
     * A map that contains the id as keys and the users as the values
     */
    Map<ID, E> entities;


    /**
     * Constructor of the MemoryRepository
     */
    public InMemoryRepository() {
        this.entities = new HashMap<>();
    }

    public boolean exists(Long id) {
        for (E item : entities.values()) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public E findFriendById(Long id1, Long id2) {
        if (id1 == null || id2 == null) {
            throw new IllegalArgumentException("ID must not be null!\n");
        } else if (entities.get(id1) == null || entities.get(id2) == null) {
            throw new LackException("Entity with this id doesn't exist!\n");
        }
        //Trb sa fac if sa verific daca sunt prieteni, si daca da sa returnez prietenul cu id2
        return null;
    }

    /**
     * Method that returns an entity with the id given
     *
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return entity with the id given, or exception if that id doesn't exist
     */
    @Override
    public E findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null!\n");
        } else if (entities.get(id) == null) {
            throw new LackException("Entity with this id doesn't exist!\n");
        }
        return entities.get(id);
    }

    /**
     * Method that return all entities from container
     *
     * @return entities container if it exists, otherwise throws exception
     */
    @Override
    public Iterable<E> findAll() {
        if (entities.values().size() == 0) {
            throw new LackException("No entities in social network!\n");
        }
        return entities.values();
    }


    /*public List<E> getAll() {
        return this.friendlist;
    }*/


    /**
     * Method that adds an entity to the container
     *
     * @param entity - entity that will be added to container
     * @return null if the entity is added successfully, otherwise returns the entity that exists already
     */
    @Override
    public E save(E entity) throws FileNotFoundException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be not null\n");
        }


        for (E ent : entities.values()) {
            if (ent.getId() == entity.getId()) {
                throw new DuplicateException("ID already exists!\n");
            }
        }
        entities.put(entity.getId(), entity);

        return null;
    }


    /**
     * Method that deletes an entity from the container
     *
     * @param id - the ID of the entity we remove
     * @return null if the operation delete succeeds, otherwise throws exception
     */
    @Override
    public E delete(ID id) {

        if (entities.get(id) == null) {
            throw new IllegalArgumentException("Entity with this ID doesn't exist!\n");
        } else entities.remove(id);
        return null;
    }

    public E update(E entity1, E entity2) {
        entities.put(entity1.getId(), entity2);
        return null;
    }

    /**
     * Method that returns the size of the container
     *
     * @return size of container
     */
    public int containerSize() {
        return entities.size();
    }

}
