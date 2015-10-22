package com.assignment.media.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import com.assignment.media.model.Blog;

/**
 * This repository provides CRUD operations for {@link com.assignment.media.model.Blog} objects.
 * 
 */
public interface BlogRepository extends Repository<Blog, String> {

    /**
     * Deletes a Blog entry from the database.
     * 
     * @param deleted The deleted Blog entry.
     */
    public void delete(final Blog blogToDelete);

    /**
     * Finds all Blog entries from the database.
     * 
     * @return The information of all Blog entries that are found from the database.
     */
    public List<Blog> findAll();

    /**
     * Finds the information of a single Blog entry.
     * 
     * @param id The id of the requested Blog entry.
     * @return The information of the found Blog entry. If no Blog entry is found, this method
     *         returns an empty {@link java.util.Optional} object.
     */
    public Optional<Blog> findOne(final String id);

    /**
     * Saves a new Blog entry to the database.
     * 
     * @param saved The information of the saved Blog entry.
     * @return The information of the saved Blog entry.
     */
    public Blog save(final Blog blogToSave);
}
