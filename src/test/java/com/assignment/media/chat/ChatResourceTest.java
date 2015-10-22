package com.assignment.media.chat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import com.assignment.media.Bootstrap;
import com.assignment.media.dto.ChatDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Bootstrap.class})
@WebAppConfiguration
@IntegrationTest("server.port=9000")
@DirtiesContext
public class ChatResourceTest {

    private static final RestTemplate restTemplate = new TestRestTemplate("admin", "admin");
    private static final String TITLE = "mytitle";
    private static final String PARENT_ID = "myParentId";
    private static final String AUTHOR_ID = "myAuthorId";
    private static final String CONTENT = "blah blah content";

    private ChatDTO postChat(final String id) {
        final ChatDTO chatDTO = new ChatDTO(id, TITLE, new Date().getTime(), PARENT_ID, AUTHOR_ID, CONTENT);
        final ResponseEntity<ChatDTO> entity = restTemplate.postForEntity("http://localhost:9000/api/chat", chatDTO, ChatDTO.class);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
        assertEquals(chatDTO, entity.getBody());
        return chatDTO;
    }

    private ChatDTO findChatById(final ChatDTO chatDto) {
        final Map<String, String> variablesMap = new HashMap<String, String>();
        variablesMap.put("id", chatDto.getId());
        final ResponseEntity<ChatDTO> entity = restTemplate.getForEntity("http://localhost:9000/api/chat/{id}", ChatDTO.class, variablesMap);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
        assertEquals(chatDto, entity.getBody());
        return entity.getBody();
    }

    @SuppressWarnings("rawtypes")
    private void findAllChats(final int expectedCount) {
        final ResponseEntity<List> entity = restTemplate.getForEntity("http://localhost:9000/api/chat", List.class);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
        assertEquals(expectedCount, entity.getBody().size());
    }

    private void deleteChatById(final String id) {
        final Map<String, String> variablesMap = new HashMap<String, String>();
        variablesMap.put("id", id);
        restTemplate.delete("http://localhost:9000/api/chat/{id}", variablesMap);
    }

    private ChatDTO updateChat(String id) {
        final Map<String, String> variablesMap = new HashMap<String, String>();
        variablesMap.put("id", id);
        final ChatDTO chatDTO = new ChatDTO(id, TITLE + id, new Date().getTime(), PARENT_ID, AUTHOR_ID, CONTENT);
        restTemplate.put("http://localhost:9000/api/chat/{id}", chatDTO, variablesMap);
        return chatDTO;
    }

    @Test
    public void testChatResource() {
        final ChatDTO chatDto = postChat("100");
        findChatById(chatDto);
        ChatDTO newChatDto = postChat("200");
        findChatById(newChatDto);
        findAllChats(2);
        deleteChatById(chatDto.getId());
        findAllChats(1);
        newChatDto = updateChat(newChatDto.getId());
        newChatDto = findChatById(newChatDto);
        assertEquals(TITLE + newChatDto.getId(), newChatDto.getTitle());
        deleteChatById(newChatDto.getId());
    }
}
