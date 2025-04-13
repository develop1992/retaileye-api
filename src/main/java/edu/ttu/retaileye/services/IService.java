package edu.ttu.retaileye.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IService<E, ID> {
    /**
     * Adds an entity to the database.
     *
     * @param entity the entity to add
     * @return the added entity
     */
    E add(E entity);

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    E update(E entity);

    /**
     * Removes an entity by its ID.
     *
     * @param id the ID of the entity to remove
     */
    void remove(ID id);

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity with the specified ID
     */
    E getById(ID id);

    /**
     * Retrieves all entities.
     *
     * @return a list of all entities
     */
    List<E> getAll();
}
