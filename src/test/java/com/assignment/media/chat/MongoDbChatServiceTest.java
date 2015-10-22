package com.assignment.media.chat;

import static com.assignment.media.chat.ChatAssert.assertThatChat;
import static com.assignment.media.chat.ChatDTOAssert.assertThatChatDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import com.assignment.media.dto.ChatDTO;
import com.assignment.media.exception.ChatNotFoundException;
import com.assignment.media.model.Chat;
import com.assignment.media.repository.ChatRepository;
import com.assignment.media.service.MongoDBChatService;

@RunWith(MockitoJUnitRunner.class)
public class MongoDbChatServiceTest {

    private static final String DESCRIPTION = "description";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final long postedDateTime = new Date().getTime();
    private static final String parentId = "9876";
    private static final String authorId = "1234";
    @Mock
    private ChatRepository repository;
    private MongoDBChatService service;

    @Before
    public void setUp() {
        this.service = new MongoDBChatService(repository);
    }

    @Test
    public void create_ShouldSaveNewChatEntry() {
        final ChatDTO newChat = new ChatDTOBuilder().title(TITLE).content(DESCRIPTION).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(repository.save(isA(Chat.class))).thenAnswer(invocation -> (Chat) invocation.getArguments()[0]);
        service.create(newChat);
        final ArgumentCaptor<Chat> savedChatArgument = ArgumentCaptor.forClass(Chat.class);
        verify(repository, times(1)).save(savedChatArgument.capture());
        verifyNoMoreInteractions(repository);
        final Chat savedChat = savedChatArgument.getValue();
        assertThatChat(savedChat).hasTitle(TITLE).hasDescription(DESCRIPTION);
    }

    @Test
    public void create_ShouldReturnTheInformationOfCreatedChatEntry() {
        final ChatDTO newChat = new ChatDTOBuilder().title(TITLE).content(DESCRIPTION).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(repository.save(isA(Chat.class))).thenAnswer(invocation -> {
            final Chat persisted = (Chat) invocation.getArguments()[0];
            ReflectionTestUtils.setField(persisted, "id", ID);
            return persisted;
        });
        ChatDTO returned = service.create(newChat);
        assertThatChatDTO(returned).hasId(ID).hasTitle(TITLE).hasDescription(DESCRIPTION);
    }

    @Test(expected = ChatNotFoundException.class)
    public void delete_ChatEntryNotFound_ShouldThrowException() {
        when(repository.findOne(ID)).thenReturn(Optional.empty());
        service.findById(ID);
    }

    @Test
    public void delete_ChatEntryFound_ShouldDeleteTheFoundChatEntry() {
        final Chat deleted = new ChatBuilder().id(ID).build();
        when(repository.findOne(ID)).thenReturn(Optional.of(deleted));
        service.delete(ID);
        verify(repository, times(1)).delete(deleted);
    }

    @Test
    public void delete_ChatEntryFound_ShouldReturnTheDeletedChatEntry() {
        final Chat deleted = new ChatBuilder().id(ID).title(TITLE).content(DESCRIPTION).build();
        when(repository.findOne(ID)).thenReturn(Optional.of(deleted));
        final ChatDTO returned = service.delete(ID);
        assertThatChatDTO(returned).hasId(ID).hasTitle(TITLE).hasDescription(DESCRIPTION);
    }

    @Test
    public void findAll_OneChatEntryFound_ShouldReturnTheInformationOfFoundChatEntry() {
        final Chat expected = new ChatBuilder().id(ID).title(TITLE).content(DESCRIPTION).build();
        when(repository.findAll()).thenReturn(Arrays.asList(expected));
        final List<ChatDTO> todoEntries = service.findAll();
        assertThat(todoEntries).hasSize(1);
        final ChatDTO actual = todoEntries.iterator().next();
        assertThatChatDTO(actual).hasId(ID).hasTitle(TITLE).hasDescription(DESCRIPTION);
    }

    @Test(expected = ChatNotFoundException.class)
    public void findById_ChatEntryNotFound_ShouldThrowException() {
        when(repository.findOne(ID)).thenReturn(Optional.empty());
        service.findById(ID);
    }

    @Test
    public void findById_ChatEntryFound_ShouldReturnTheInformationOfFoundChatEntry() {
        final Chat found = new ChatBuilder().id(ID).title(TITLE).content(DESCRIPTION).build();
        when(repository.findOne(ID)).thenReturn(Optional.of(found));
        final ChatDTO returned = service.findById(ID);
        assertThatChatDTO(returned).hasId(ID).hasTitle(TITLE).hasDescription(DESCRIPTION);
    }

    @Test(expected = ChatNotFoundException.class)
    public void update_UpdatedChatEntryNotFound_ShouldThrowException() {
        when(repository.findOne(ID)).thenReturn(Optional.empty());
        final ChatDTO updated = new ChatDTOBuilder().id(ID).build();
        service.update(updated);
    }

    @Test
    public void update_UpdatedChatEntryFound_ShouldSaveUpdatedChatEntry() {
        final Chat existing = new ChatBuilder().id(ID).title(TITLE).content(DESCRIPTION).build();
        when(repository.findOne(ID)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        final ChatDTO updated = new ChatDTOBuilder().id(ID).title(TITLE).content(DESCRIPTION).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        service.update(updated);
        verify(repository, times(1)).save(Mockito.any(Chat.class));
        assertThatChat(existing).hasId(ID).hasTitle(TITLE).hasDescription(DESCRIPTION);
    }

    @Test
    public void update_UpdatedChatEntryFound_ShouldReturnTheInformationOfUpdatedChatEntry() {
        final Chat existing = new ChatBuilder().id(ID).title(TITLE).content(DESCRIPTION).build();
        when(repository.findOne(ID)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        final ChatDTO updated = new ChatDTOBuilder().id(ID).title(TITLE).content(DESCRIPTION).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        final ChatDTO returned = service.update(updated);
        assertThatChatDTO(returned).hasId(ID).hasTitle(TITLE).hasDescription(DESCRIPTION);
    }
}
