package com.assignment.media.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import com.assignment.media.model.Chat;

/**
 * This repository provides CRUD operations for {@link com.assignment.media.model.Chat} objects.
 * 
 */
public interface ChatRepository extends Repository<Chat, String> {

    /**
     * Deletes a Chat entry from the database.
     * 
     * @param deleted The deleted Chat entry.
     */
    public void delete(final Chat chatToDelete);

    /**
     * Finds all Chat entries from the database.
     * 
     * @return The information of all Chat entries that are found from the database.
     */
    public List<Chat> findAll();

    /**
     * Finds the information of a single Chat entry.
     * 
     * @param id The id of the requested Chat entry.
     * @return The information of the found Chat entry. If no Chat entry is found, this method
     *         returns an empty {@link java.util.Optional} object.
     */
    public Optional<Chat> findOne(final String id);

    /**
     * Saves a new Chat entry to the database.
     * 
     * @param saved The information of the saved Chat entry.
     * @return The information of the saved Chat entry.
     */
    public Chat save(final Chat chatToSave);
}
