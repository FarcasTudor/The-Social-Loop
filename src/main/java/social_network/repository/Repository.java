package social_network.repository;

import social_network.domain.Entity;
import social_network.exceptions.ValidationException;

import java.io.FileNotFoundException;
import java.util.List;


public interface Repository<ID, E extends Entity<ID>> {


    /**
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException if id is null.
     */
    E findOne(ID id);

    /**
     * @return all entities
     */
    Iterable<E> findAll();


    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidationException      if the entity is not valid
     * @throws IllegalArgumentException if the given entity is null.     *
     */
    E save(E entity) throws FileNotFoundException;


    /**
     * removes the entity with the specified id
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException if the given id is null.
     */
    E delete(ID id);

    E update(E entity1, E entity2);


}