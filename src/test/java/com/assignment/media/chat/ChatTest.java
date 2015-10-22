package com.assignment.media.chat;

import static com.assignment.media.chat.ChatAssert.assertThatChat;
import java.util.Date;
import org.junit.Test;
import com.assignment.media.model.Chat;

public class ChatTest {

    private static final String DESCRIPTION = "description";
    private static final String TITLE = "title";
    private static final int MAX_LENGTH_DESCRIPTION = 500;
    private static final int MAX_LENGTH_TITLE = 100;
    private static final long postedDateTime = new Date().getTime();

    @Test
    public void build_WithoutDescription_ShouldCreateNewChatEntryWithCorrectTitle() {
        final Chat build = Chat.getBuilder().setTitle(TITLE).build();
        assertThatChat(build).hasNoId().hasTitle(TITLE).hasNoDescription();
    }

    @Test
    public void build_WithTitleAndDescription_ShouldCreateNewChatEntryWithCorrectTitleAndDescription() {
        final Chat build = Chat.getBuilder().setTitle(TITLE).setContent(DESCRIPTION).setPostedDateTime(postedDateTime).build();
        assertThatChat(build).hasNoId().hasTitle(TITLE).hasDescription(DESCRIPTION);
    }

    @Test
    public void build_WithMaxLengthTitleAndDescription_ShouldCreateNewChatEntryWithCorrectTitleAndDescription() {
        final String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        final String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);
        final Chat build = Chat.getBuilder().setTitle(maxLengthTitle).setContent(maxLengthDescription).setPostedDateTime(postedDateTime).build();
        assertThatChat(build).hasNoId().hasTitle(maxLengthTitle).hasDescription(maxLengthDescription);
    }
}
