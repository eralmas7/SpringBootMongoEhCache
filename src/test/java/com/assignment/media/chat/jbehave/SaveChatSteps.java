package com.assignment.media.chat.jbehave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.assignment.media.dto.ChatDTO;

@Steps
public class SaveChatSteps {

    private RestTemplate restTemplate;
    private ChatDTO chatDto;

    @Given("a registered company tries to register chat with user as $user and password as $password")
    public void createNewChat(@Named("user") String user, @Named("password") String password) {
        restTemplate = new TestRestTemplate(user, password);
        chatDto = new ChatDTO();
    }

    @When("company tries to send in a new chat with title as $title parent as $parentId author as $authorId and content as $content")
    public void fillChat(@Named("title") String title, @Named("parentId") String parentId, @Named("authorId") String authorId, @Named("content") String content) {
        chatDto.setTitle(title);
        chatDto.setParentId(parentId);
        chatDto.setAuthorId(authorId);
        chatDto.setContent(content);
        chatDto.setPostedDateTime(new Date().getTime());
    }

    @Then("user should be allowed to push the data")
    public void registerChat() {
        final ResponseEntity<ChatDTO> entity = restTemplate.postForEntity("http://localhost:8080/api/chat", chatDto, ChatDTO.class);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
        assertNotNull(entity.getBody().getId());
        assertEquals(chatDto.getTitle(), entity.getBody().getTitle());
        assertEquals(chatDto.getAuthorId(), entity.getBody().getAuthorId());
        assertEquals(chatDto.getPostedDateTime(), entity.getBody().getPostedDateTime());
        assertEquals(chatDto.getContent(), entity.getBody().getContent());
    }
}
