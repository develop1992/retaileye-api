package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.services.IService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public abstract class BaseController<E, ID> {

    protected final IService<E, ID> service;

    public BaseController(IService<E, ID> service) {
        this.service = service;
    }

    /**
     * Adds an entity to the database.
     *
     * @param entity the entity to add
     * @return the added entity
     */
    @PostMapping
    public ResponseEntity<E> add(@RequestBody E entity) {
        var addedEntity = service.add(entity);
        return new ResponseEntity<>(addedEntity, HttpStatus.CREATED);
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param id     the ID of the entity to update
     * @param entity the entity with updated values
     * @return the updated entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<E> update(@PathVariable ID id, @RequestBody E entity) {
        try {
            var setIdMethod = entity.getClass().getMethod("setId", id.getClass());
            setIdMethod.setAccessible(true);
            setIdMethod.invoke(entity, id);
        } catch (Exception e) {
            throw new InternalException("Error setting ID", e);
        }

        var updatedEntity = service.update(entity);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    /**
     * Removes an entity by its ID.
     *
     * @param id the ID of the entity to remove
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable ID id) {
        service.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<E> getById(@PathVariable ID id) {
        var entity = service.getById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * Retrieves all entities.
     *
     * @return a list of all entities
     */
    @GetMapping
    public ResponseEntity<List<E>> getAll() {
        var entities = service.getAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }
}
