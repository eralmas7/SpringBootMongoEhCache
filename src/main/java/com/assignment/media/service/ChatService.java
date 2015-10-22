package com.assignment.media.service;

import java.util.List;
import com.assignment.media.dto.ChatDTO;

/**
 * This interface declares the methods that provides CRUD operations for
 * {@link com.assignment.media.model.Chat} objects.
 * 
 */
public interface ChatService {

    /**
     * Creates a new chat entry.
     * 
     * @param chat The information of the created chat entry.
     * @return The information of the created chat entry.
     */
    public ChatDTO create(final ChatDTO chat);

    /**
     * Deletes a chat entry.
     * 
     * @param id The id of the deleted chat entry.
     * @return THe information of the deleted chat entry.
     * @throws com.assignment.media.controller.ChatNotFoundException if no chat entry is found.
     */
    public ChatDTO delete(final String id);

    /**
     * Finds all chat entries.
     * 
     * @return The information of all chat entries.
     */
    public List<ChatDTO> findAll();

    /**
     * Finds a single chat entry.
     * 
     * @param id The id of the requested chat entry.
     * @return The information of the requested chat entry.
     * @throws com.assignment.media.controller.ChatNotFoundException if no chat entry is found.
     */
    public ChatDTO findById(final String id);

    /**
     * Updates the information of a chat entry.
     * 
     * @param chat The information of the updated chat entry.
     * @return The information of the updated chat entry.
     * @throws com.assignment.media.controller.ChatNotFoundException if no chat entry is found.
     */
    public ChatDTO update(final ChatDTO chat);
}
