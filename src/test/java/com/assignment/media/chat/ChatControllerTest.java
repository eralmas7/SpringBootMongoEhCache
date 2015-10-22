package com.assignment.media.chat;

import static com.assignment.media.chat.ChatDTOAssert.assertThatChatDTO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import com.assignment.media.controller.ChatController;
import com.assignment.media.dto.ChatDTO;
import com.assignment.media.exception.ChatNotFoundException;
import com.assignment.media.exception.handler.RestErrorHandler;
import com.assignment.media.service.ChatService;

@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final String DESCRIPTION = "description";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final int MAX_LENGTH_DESCRIPTION = 500;
    private static final int MAX_LENGTH_TITLE = 100;
    private static final long postedDateTime = new Date().getTime();
    private static final String parentId = "9876";
    private static final String authorId = "1234";
    @Mock
    private ChatService service;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ChatController(service)).setHandlerExceptionResolvers(withExceptionControllerAdvice()).build();
    }

    private ExceptionHandlerExceptionResolver withExceptionControllerAdvice() {
        final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {

            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod, final Exception exception) {
                final Method method = new ExceptionHandlerMethodResolver(RestErrorHandler.class).resolveMethod(exception);
                if (method != null) {
                    final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
                    messageSource.setBasename("messages");
                    return new ServletInvocableHandlerMethod(new RestErrorHandler(messageSource), method);
                }
                return super.getExceptionHandlerMethod(handlerMethod, exception);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    @Test
    public void create_ChatEntryWithOnlyTitle_ShouldCreateNewChatEntryWithoutDescription() throws Exception {
        final ChatDTO newTodoEntry = new ChatDTOBuilder().title(TITLE).build();
        mockMvc.perform(post("/api/chat").contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(newTodoEntry)));
        final ArgumentCaptor<ChatDTO> createdArgument = ArgumentCaptor.forClass(ChatDTO.class);
        verify(service, times(1)).create(createdArgument.capture());
        verifyNoMoreInteractions(service);
        final ChatDTO created = createdArgument.getValue();
        assertThatChatDTO(created).hasNoId().hasTitle(TITLE).hasNoDescription();
    }

    @Test
    public void create_ChatEntryWithOnlyTitle_ShouldReturnResponseStatusCreated() throws Exception {
        final ChatDTO newTodoEntry = new ChatDTOBuilder().title(TITLE).build();
        when(service.create(isA(ChatDTO.class))).then(invocationOnMock -> {
            final ChatDTO saved = (ChatDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });
        mockMvc.perform(post("/api/chat").contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(newTodoEntry))).andExpect(status().isCreated());
    }

    @Test
    public void create_ChatEntryWithOnlyTitle_ShouldReturnTheInformationOfCreatedChatEntryAsJSon() throws Exception {
        final ChatDTO newTodoEntry = new ChatDTOBuilder().title(TITLE).build();
        when(service.create(isA(ChatDTO.class))).then(invocationOnMock -> {
            ChatDTO saved = (ChatDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });
        mockMvc.perform(post("/api/chat").contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(newTodoEntry))).andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.title", is(TITLE))).andExpect(
                        jsonPath("$.content", isEmptyOrNullString()));
    }

    @Test
    public void create_ChatEntryWithMaxLengthTitleAndDescription_ShouldCreateNewChatEntryWithCorrectInformation() throws Exception {
        final String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        final String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);
        final ChatDTO newTodoEntry = new ChatDTOBuilder().title(maxLengthTitle).content(maxLengthDescription).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        mockMvc.perform(post("/api/chat").contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(newTodoEntry)));
        final ArgumentCaptor<ChatDTO> createdArgument = ArgumentCaptor.forClass(ChatDTO.class);
        verify(service, times(1)).create(createdArgument.capture());
        verifyNoMoreInteractions(service);
        final ChatDTO created = createdArgument.getValue();
        assertThatChatDTO(created).hasNoId().hasTitle(maxLengthTitle).hasDescription(maxLengthDescription);
    }

    @Test
    public void create_ChatEntryWithMaxLengthTitleAndDescription_ShouldReturnResponseStatusCreated() throws Exception {
        final String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        final String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);
        final ChatDTO newTodoEntry = new ChatDTOBuilder().title(maxLengthTitle).content(maxLengthDescription).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.create(isA(ChatDTO.class))).then(invocationOnMock -> {
            final ChatDTO saved = (ChatDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });
        mockMvc.perform(post("/api/chat").contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(newTodoEntry))).andExpect(status().isCreated());
    }

    @Test
    public void create_ChatEntryWithMaxLengthTitleAndDescription_ShouldReturnTheInformationOfCreatedChatEntryAsJson() throws Exception {
        final String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        final String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);
        ChatDTO newTodoEntry = new ChatDTOBuilder().title(maxLengthTitle).content(maxLengthDescription).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.create(isA(ChatDTO.class))).then(invocationOnMock -> {
            final ChatDTO saved = (ChatDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });
        mockMvc.perform(post("/api/chat").contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(newTodoEntry))).andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.title", is(maxLengthTitle))).andExpect(
                        jsonPath("$.content", is(maxLengthDescription)));
    }

    @Test
    public void delete_ChatEntryNotFound_ShouldReturnResponseStatusNotFound() throws Exception {
        when(service.delete(ID)).thenThrow(new ChatNotFoundException(ID));
        mockMvc.perform(delete("/api/chat/{id}", ID)).andExpect(status().isNotFound());
    }

    @Test
    public void delete_ChatEntryFound_ShouldReturnResponseStatusOk() throws Exception {
        final ChatDTO deleted = new ChatDTOBuilder().id(ID).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.delete(ID)).thenReturn(deleted);
        mockMvc.perform(delete("/api/chat/{id}", ID)).andExpect(status().isOk());
    }

    @Test
    public void delete_ChatEntryFound_ShouldTheInformationOfDeletedChatEntryAsJson() throws Exception {
        final ChatDTO deleted = new ChatDTOBuilder().id(ID).title(TITLE).content(DESCRIPTION).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.delete(ID)).thenReturn(deleted);
        mockMvc.perform(delete("/api/chat/{id}", ID)).andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.title", is(TITLE))).andExpect(jsonPath("$.content", is(DESCRIPTION)));
    }

    @Test
    public void findAll_ShouldReturnResponseStatusOk() throws Exception {
        mockMvc.perform(get("/api/chat")).andExpect(status().isOk());
    }

    @Test
    public void findAll_OneChatEntryFound_ShouldReturnListThatContainsOneChatEntryAsJson() throws Exception {
        final ChatDTO found = new ChatDTOBuilder().id(ID).title(TITLE).content(DESCRIPTION).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.findAll()).thenReturn(Arrays.asList(found));
        mockMvc.perform(get("/api/chat")).andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(ID))).andExpect(jsonPath("$[0].title", is(TITLE))).andExpect(jsonPath("$[0].content", is(DESCRIPTION)));
    }

    @Test
    public void findById_ChatEntryFound_ShouldReturnResponseStatusOk() throws Exception {
        final ChatDTO found = new ChatDTOBuilder().build();
        when(service.findById(ID)).thenReturn(found);
        mockMvc.perform(get("/api/chat/{id}", ID)).andExpect(status().isOk());
    }

    @Test
    public void findById_ChatEntryFound_ShouldTheInformationOfFoundTodoEntryAsJson() throws Exception {
        final ChatDTO found = new ChatDTOBuilder().id(ID).title(TITLE).content(DESCRIPTION).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.findById(ID)).thenReturn(found);
        mockMvc.perform(get("/api/chat/{id}", ID)).andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.title", is(TITLE))).andExpect(jsonPath("$.content", is(DESCRIPTION)));
    }

    @Test
    public void findById_ChatEntryNotFound_ShouldReturnResponseStatusNotFound() throws Exception {
        when(service.findById(ID)).thenThrow(new ChatNotFoundException(ID));
        mockMvc.perform(get("/api/chat/{id}", ID)).andExpect(status().isNotFound());
    }

    @Test
    public void update_ChatEntryWithOnlyTitle_ShouldUpdateTheInformationOfChatEntry() throws Exception {
        final ChatDTO updatedTodoEntry = new ChatDTOBuilder().id(ID).title(TITLE).parentId(parentId).postedDateTime(postedDateTime).build();
        mockMvc.perform(put("/api/chat/{id}", ID).contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(updatedTodoEntry)));
        final ArgumentCaptor<ChatDTO> updatedArgument = ArgumentCaptor.forClass(ChatDTO.class);
        verify(service, times(1)).update(updatedArgument.capture());
        verifyNoMoreInteractions(service);
        final ChatDTO updated = updatedArgument.getValue();
        assertThatChatDTO(updated).hasId(ID).hasTitle(TITLE).hasNoDescription();
    }

    @Test
    public void update_ChatEntryWithOnlyTitle_ShouldReturnResponseStatusOk() throws Exception {
        final ChatDTO updatedTodoEntry = new ChatDTOBuilder().id(ID).title(TITLE).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.update(isA(ChatDTO.class))).then(invocationOnMock -> (ChatDTO) invocationOnMock.getArguments()[0]);
        mockMvc.perform(put("/api/chat/{id}", ID).contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(updatedTodoEntry))).andExpect(status().isOk());
    }

    @Test
    public void update_ChatEntryWithOnlyTitle_ShouldReturnTheInformationOfUpdatedChatEntryAsJSon() throws Exception {
        final ChatDTO updatedTodoEntry = new ChatDTOBuilder().id(ID).title(TITLE).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.update(isA(ChatDTO.class))).then(invocationOnMock -> (ChatDTO) invocationOnMock.getArguments()[0]);
        mockMvc.perform(put("/api/chat/{id}", ID).contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(updatedTodoEntry))).andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.title", is(TITLE))).andExpect(
                        jsonPath("$.content", isEmptyOrNullString()));
    }

    @Test
    public void update_ChatEntryWithMaxLengthTitleAndDescription_ShouldUpdateTheInformationOfChatEntry() throws Exception {
        final String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        final String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);
        final ChatDTO updatedTodoEntry = new ChatDTOBuilder().id(ID).title(maxLengthTitle).content(maxLengthDescription).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        mockMvc.perform(put("/api/chat/{id}", ID).contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(updatedTodoEntry)));
        final ArgumentCaptor<ChatDTO> updatedArgument = ArgumentCaptor.forClass(ChatDTO.class);
        verify(service, times(1)).update(updatedArgument.capture());
        verifyNoMoreInteractions(service);
        final ChatDTO updated = updatedArgument.getValue();
        assertThatChatDTO(updated).hasId(ID).hasTitle(maxLengthTitle).hasDescription(maxLengthDescription);
    }

    @Test
    public void update_ChatEntryWithMaxLengthTitleAndDescription_ShouldReturnResponseStatusOk() throws Exception {
        final String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        final String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);
        final ChatDTO updatedTodoEntry = new ChatDTOBuilder().id(ID).title(maxLengthTitle).content(maxLengthDescription).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.create(isA(ChatDTO.class))).then(invocationOnMock -> (ChatDTO) invocationOnMock.getArguments()[0]);
        mockMvc.perform(put("/api/chat/{id}", ID).contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(updatedTodoEntry))).andExpect(status().isOk());
    }

    @Test
    public void update_ChatEntryWithMaxLengthTitleAndDescription_ShouldReturnTheInformationOfCreatedUpdatedChatEntryAsJson() throws Exception {
        final String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        final String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);
        final ChatDTO updatedTodoEntry = new ChatDTOBuilder().id(ID).title(maxLengthTitle).content(maxLengthDescription).authorId(authorId).parentId(parentId).postedDateTime(postedDateTime).build();
        when(service.update(isA(ChatDTO.class))).then(invocationOnMock -> (ChatDTO) invocationOnMock.getArguments()[0]);
        mockMvc.perform(put("/api/chat/{id}", ID).contentType(APPLICATION_JSON_UTF8).content(WebTestUtil.convertObjectToJsonBytes(updatedTodoEntry))).andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.title", is(maxLengthTitle)))
                        .andExpect(jsonPath("$.content", is(maxLengthDescription)));
    }
}
