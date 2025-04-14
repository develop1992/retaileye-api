package edu.ttu.retaileye.services;

public interface IAssignment<E, ID> {
    /**
     * Assigns an entity to another entity.
     *
     * @param entity the entity to assign
     * @return the assigned entity
     */
    E assignTo(E entity);

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
}
