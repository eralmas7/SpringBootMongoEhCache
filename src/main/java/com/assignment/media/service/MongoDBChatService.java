package com.assignment.media.service;

import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.assignment.media.dto.ChatDTO;
import com.assignment.media.exception.ChatNotFoundException;
import com.assignment.media.model.Chat;
import com.assignment.media.repository.ChatRepository;

/**
 * This service class saves {@link com.assignment.media.model.Chat} objects to MongoDB database.
 * 
 */
@Service
public final class MongoDBChatService implements ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBChatService.class);
    private final ChatRepository repository;

    @Autowired
    public MongoDBChatService(final ChatRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChatDTO create(final ChatDTO chatDto) {
        LOGGER.info("Creating a new Chat entry with information: {}", chatDto);
        Chat persisted = Chat.getBuilder().setAuthorId(chatDto.getAuthorId()).setContent(chatDto.getContent()).setId(chatDto.getId()).setParentId(chatDto.getParentId()).setPostedDateTime(chatDto.getPostedDateTime()).setTitle(chatDto.getTitle()).build();
        persisted = repository.save(persisted);
        LOGGER.info("Created a new Chat entry with information: {}", persisted);
        return convertToDTO(persisted);
    }

    @Override
    public ChatDTO delete(final String id) {
        LOGGER.info("Deleting a Chat entry with id: {}", id);
        final Chat deleted = findChatById(id);
        repository.delete(deleted);
        LOGGER.info("Deleted Chat entry with informtation: {}", deleted);
        return convertToDTO(deleted);
    }

    @Override
    public List<ChatDTO> findAll() {
        LOGGER.info("Finding all Chat entries.");
        final List<Chat> ChatEntries = repository.findAll();
        LOGGER.info("Found {} Chat entries", ChatEntries.size());
        return convertToDTOs(ChatEntries);
    }

    private List<ChatDTO> convertToDTOs(List<Chat> models) {
        return models.stream().map(this::convertToDTO).collect(toList());
    }

    @Override
    public ChatDTO findById(final String id) {
        LOGGER.info("Finding Chat entry with id: {}", id);
        final Chat found = findChatById(id);
        LOGGER.info("Found Chat entry: {}", found);
        return convertToDTO(found);
    }

    @Override
    public ChatDTO update(final ChatDTO chatDto) {
        LOGGER.info("Updating Chat entry with information: {}", chatDto);
        Chat updated = findChatById(chatDto.getId());
        final Chat mergedChat = Chat.getBuilder().setAuthorId(updated.getAuthorId()).setContent(chatDto.getContent()).setId(chatDto.getId()).setParentId(chatDto.getParentId()).setPostedDateTime(chatDto.getPostedDateTime()).setTitle(chatDto.getTitle()).build();
        updated = repository.save(mergedChat);
        LOGGER.info("Updated Chat entry with information: {}", mergedChat);
        return convertToDTO(mergedChat);
    }

    private Chat findChatById(final String id) {
        final Optional<Chat> result = repository.findOne(id);
        return result.orElseThrow(() -> new ChatNotFoundException(id));
    }

    private ChatDTO convertToDTO(final Chat chat) {
        final ChatDTO dto = new ChatDTO(chat.getId(), chat.getTitle(), chat.getPostedDateTime(), chat.getParentId(), chat.getAuthorId(), chat.getContent());
        return dto;
    }
}
