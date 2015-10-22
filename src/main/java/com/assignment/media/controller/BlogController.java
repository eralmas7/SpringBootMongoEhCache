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
import com.assignment.media.dto.BlogDTO;
import com.assignment.media.exception.BlogNotFoundException;
import com.assignment.media.service.BlogService;

/**
 * This controller provides the public API that is used to manage the information of blog entries.
 * 
 */
@RestController
@RequestMapping("/api/blogs")
public final class BlogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);
    private final BlogService service;

    @Autowired
    public BlogController(final BlogService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BlogDTO create(@RequestBody @Valid final BlogDTO blogEntry) {
        LOGGER.info("Creating a new blog entry with information: {}", blogEntry);
        final BlogDTO createdBlog = service.create(blogEntry);
        LOGGER.info("Created a new blog entry with information: {}", createdBlog);
        return createdBlog;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public BlogDTO delete(@PathVariable("id") final String id) {
        LOGGER.info("Deleting blog entry with id: {}", id);
        final BlogDTO deletedBlog = service.delete(id);
        LOGGER.info("Deleted blog entry with information: {}", deletedBlog);
        return deletedBlog;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BlogDTO> findAll() {
        LOGGER.info("Finding all blog entries");
        final List<BlogDTO> blogEntries = service.findAll();
        LOGGER.info("Found {} blog entries", blogEntries.size());
        return blogEntries;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public BlogDTO findById(@PathVariable("id") final String id) {
        LOGGER.info("Finding blog entry with id: {}", id);
        final BlogDTO blogEntry = service.findById(id);
        LOGGER.info("Found blog entry with information: {}", blogEntry);
        return blogEntry;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public BlogDTO update(@RequestBody @Valid final BlogDTO blogEntry) {
        LOGGER.info("Updating blog entry with blogEntry: {}", blogEntry);
        final BlogDTO updatedBlog = service.update(blogEntry);
        LOGGER.info("Updated blog entry with information: {}", updatedBlog);
        return updatedBlog;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleBlogNotFound(final BlogNotFoundException ex) {
        LOGGER.error("Handling error with message: {}", ex.getMessage());
    }
}
