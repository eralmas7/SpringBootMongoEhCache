package com.assignment.media.chat;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.AbstractAssert;
import com.assignment.media.dto.ChatDTO;

/**
 */
class ChatDTOAssert extends AbstractAssert<ChatDTOAssert, ChatDTO> {

    private ChatDTOAssert(final ChatDTO actual) {
        super(actual, ChatDTOAssert.class);
    }

    static ChatDTOAssert assertThatChatDTO(final ChatDTO actual) {
        return new ChatDTOAssert(actual);
    }

    public ChatDTOAssert hasDescription(final String expectedDescription) {
        isNotNull();
        final String actualDescription = actual.getContent();
        assertThat(actualDescription).overridingErrorMessage("Expected description to be <%s> but was <%s>", expectedDescription, actualDescription).isEqualTo(expectedDescription);
        return this;
    }

    public ChatDTOAssert hasId(final String expectedId) {
        isNotNull();
        final String actualId = actual.getId();
        assertThat(actualId).overridingErrorMessage("Expected id to be <%s> but was <%s>", expectedId, actualId).isEqualTo(expectedId);
        return this;
    }

    public ChatDTOAssert hasNoDescription() {
        isNotNull();
        final String actualDescription = actual.getContent();
        assertThat(actualDescription).overridingErrorMessage("expected description to be <null> but was <%s>", actualDescription).isNull();
        return this;
    }

    public ChatDTOAssert hasNoId() {
        isNotNull();
        final String actualId = actual.getId();
        assertThat(actualId).overridingErrorMessage("Expected id to be <null> but was <%s>", actualId).isNull();
        return this;
    }

    public ChatDTOAssert hasTitle(final String expectedTitle) {
        isNotNull();
        final String actualTitle = actual.getTitle();
        assertThat(actualTitle).overridingErrorMessage("Expected title to be <%s> but was <%s>", expectedTitle, actualTitle).isEqualTo(expectedTitle);
        return this;
    }
}
