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
     * Retrieves an entity by its employee ID.
     * @param id the employee ID to search for
     * @return the entity associated with the specified employee ID
     */
    E getByEmployeeId(ID id);
}
