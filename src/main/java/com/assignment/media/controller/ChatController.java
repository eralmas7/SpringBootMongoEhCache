package com.assignment.media.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.assignment.media.dto.ChatDTO;
import com.assignment.media.exception.ChatNotFoundException;
import com.assignment.media.service.ChatService;

/**
 * This controller provides the public API that is used to manage the information of Chat entries.
 * 
 */
@RestController
@RequestMapping("/api/chat")
public final class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);
    private final ChatService service;

    @Autowired
    public ChatController(final ChatService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ChatDTO create(@RequestBody @Valid final ChatDTO chatEntry) {
        LOGGER.info("Creating a new chat entry with information: {}", chatEntry);
        final ChatDTO createdChat = service.create(chatEntry);
        LOGGER.info("Created a new chat entry with information: {}", createdChat);
        return createdChat;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ChatDTO delete(@PathVariable("id") final String id) {
        LOGGER.info("Deleting chat entry with id: {}", id);
        final ChatDTO deletedChat = service.delete(id);
        LOGGER.info("Deleted chat entry with information: {}", deletedChat);
        return deletedChat;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ChatDTO> findAll() {
        LOGGER.info("Finding all chat entries");
        final List<ChatDTO> chatEntries = service.findAll();
        LOGGER.info("Found {} chat entries", chatEntries.size());
        return chatEntries;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ChatDTO findById(@PathVariable("id") final String id) {
        LOGGER.info("Finding chat entry with id: {}", id);
        final ChatDTO chatEntry = service.findById(id);
        LOGGER.info("Found chat entry with information: {}", chatEntry);
        return chatEntry;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ChatDTO update(@PathVariable("id") final String id, @RequestBody @Valid final ChatDTO chatEntry) {
        LOGGER.info("Updating chat entry with chatEntry: {}", chatEntry);
        final ChatDTO updatedChat = service.update(chatEntry);
        LOGGER.info("Updated chat entry with information: {}", updatedChat);
        return chatEntry;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleChatNotFound(final ChatNotFoundException ex) {
        LOGGER.error("Handling error with message: {}", ex.getMessage());
    }
}
