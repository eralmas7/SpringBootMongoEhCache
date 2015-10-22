package com.assignment.media.service;

import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.assignment.media.dto.BlogDTO;
import com.assignment.media.exception.BlogNotFoundException;
import com.assignment.media.model.Blog;
import com.assignment.media.repository.BlogRepository;

/**
 * This service class saves {@link com.assignment.media.model.Blog} objects to MongoDB database.
 * 
 */
@Service
public final class MongoDBBlogService implements BlogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBBlogService.class);
    private final BlogRepository repository;

    @Autowired
    public MongoDBBlogService(final BlogRepository repository) {
        this.repository = repository;
    }

    @Override
    public BlogDTO create(final BlogDTO blogDto) {
        LOGGER.info("Creating a new Blog entry with information: {}", blogDto);
        Blog persisted = Blog.getBuilder().setAuthorId(blogDto.getAuthorId()).setContent(blogDto.getContent()).setId(blogDto.getId()).setPostedDateTime(blogDto.getPostedDate()).setTitle(blogDto.getTitle()).build();
        persisted = repository.save(persisted);
        LOGGER.info("Created a new Blog entry with information: {}", persisted);
        return convertToDTO(persisted);
    }

    @Override
    public BlogDTO delete(final String id) {
        LOGGER.info("Deleting a Blog entry with id: {}", id);
        final Blog deleted = findBlogById(id);
        repository.delete(deleted);
        LOGGER.info("Deleted Blog entry with informtation: {}", deleted);
        return convertToDTO(deleted);
    }

    @Override
    public List<BlogDTO> findAll() {
        LOGGER.info("Finding all Blog entries.");
        List<Blog> blogEntries = repository.findAll();
        LOGGER.info("Found {} Blog entries", blogEntries.size());
        return convertToDTOs(blogEntries);
    }

    private List<BlogDTO> convertToDTOs(final List<Blog> models) {
        return models.stream().map(this::convertToDTO).collect(toList());
    }

    @Override
    public BlogDTO findById(final String id) {
        LOGGER.info("Finding Blog entry with id: {}", id);
        final Blog found = findBlogById(id);
        LOGGER.info("Found Blog entry: {}", found);
        return convertToDTO(found);
    }

    @Override
    public BlogDTO update(final BlogDTO blogDto) {
        LOGGER.info("Updating Blog entry with information: {}", blogDto);
        Blog updated = findBlogById(blogDto.getId());
        final Blog mergedBlog = Blog.getBuilder().setAuthorId(updated.getAuthorId()).setContent(blogDto.getContent()).setId(blogDto.getId()).setPostedDateTime(blogDto.getPostedDate()).setTitle(updated.getTitle()).build();
        updated = repository.save(mergedBlog);
        LOGGER.info("Updated Blog entry with information: {}", mergedBlog);
        return convertToDTO(mergedBlog);
    }

    private Blog findBlogById(final String id) {
        final Optional<Blog> result = repository.findOne(id);
        return result.orElseThrow(() -> new BlogNotFoundException(id));
    }

    private BlogDTO convertToDTO(final Blog blog) {
        final BlogDTO dto = new BlogDTO(blog.getId(), blog.getTitle(), blog.getPostedDateTime(), blog.getAuthorId(), blog.getContent());
        return dto;
    }
}
