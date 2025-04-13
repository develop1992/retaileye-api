package edu.ttu.retaileye.services;

public interface IAssignment<E, ID> {
    /**
     * Assigns an entity to a specific ID.
     *
     * @param assigneeId the ID of the assignee
     * @param assignmentId the ID of the assignment
     * @return the assigned entity
     */
    E assignTo(ID assigneeId, ID assignmentId);

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
