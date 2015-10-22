package com.assignment.media.chat;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.AbstractAssert;
import com.assignment.media.model.Chat;

class ChatAssert extends AbstractAssert<ChatAssert, Chat> {

    private ChatAssert(final Chat actual) {
        super(actual, ChatAssert.class);
    }

    static ChatAssert assertThatChat(final Chat actual) {
        return new ChatAssert(actual);
    }

    public ChatAssert hasDescription(final String expectedContent) {
        isNotNull();
        final String actualDescription = actual.getContent();
        assertThat(actualDescription).overridingErrorMessage("Expected description to be <%s> but was <%s>", expectedContent, actualDescription).isEqualTo(expectedContent);
        return this;
    }

    public ChatAssert hasId(final String expectedId) {
        isNotNull();
        final String actualId = actual.getId();
        assertThat(actualId).overridingErrorMessage("Expected id to be <%s> but was <%s>", expectedId, actualId).isEqualTo(expectedId);
        return this;
    }

    public ChatAssert hasNoDescription() {
        isNotNull();
        final String actualDescription = actual.getContent();
        assertThat(actualDescription).overridingErrorMessage("Expected description to be <null> but was <%s>", actualDescription).isNull();
        return this;
    }

    public ChatAssert hasNoId() {
        isNotNull();
        final String actualId = actual.getId();
        assertThat(actualId).overridingErrorMessage("Expected id to be <null> but was <%s>", actualId).isNull();
        return this;
    }

    public ChatAssert hasTitle(final String expectedTitle) {
        isNotNull();
        final String actualTitle = actual.getTitle();
        assertThat(actualTitle).overridingErrorMessage("Expected title to be <%s> but was <%s>", expectedTitle, actualTitle).isEqualTo(expectedTitle);
        return this;
    }
}
