package com.assignment.media.service;

import java.util.List;
import com.assignment.media.dto.BlogDTO;

/**
 * This interface declares the methods that provides CRUD operations for
 * {@link com.assignment.media.model.Blog} objects.
 * 
 */
public interface BlogService {

    /**
     * Creates a new blog entry.
     * 
     * @param blog The information of the created blog entry.
     * @return The information of the created blog entry.
     */
    public BlogDTO create(final BlogDTO blog);

    /**
     * Deletes a blog entry.
     * 
     * @param id The id of the deleted blog entry.
     * @return THe information of the deleted blog entry.
     * @throws com.assignment.media.controller.BlogNotFoundException if no blog entry is found.
     */
    public BlogDTO delete(final String id);

    /**
     * Finds all blog entries.
     * 
     * @return The information of all blog entries.
     */
    public List<BlogDTO> findAll();

    /**
     * Finds a single blog entry.
     * 
     * @param id The id of the requested blog entry.
     * @return The information of the requested blog entry.
     * @throws com.assignment.media.controller.BlogNotFoundException if no blog entry is found.
     */
    public BlogDTO findById(final String id);

    /**
     * Updates the information of a blog entry.
     * 
     * @param blog The information of the updated blog entry.
     * @return The information of the updated blog entry.
     * @throws com.assignment.media.controller.BlogNotFoundException if no blog entry is found.
     */
    public BlogDTO update(final BlogDTO blog);
}
