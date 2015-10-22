package com.assignment.media.chat;

import java.util.Date;
import org.springframework.test.util.ReflectionTestUtils;
import com.assignment.media.model.Chat;

class ChatBuilder {

    private String content;
    private String id;
    private String title = "NOT_IMPORTANT";
    private long dateTime = new Date().getTime();

    public ChatBuilder() {}

    public ChatBuilder content(final String content) {
        this.content = content;
        return this;
    }

    public ChatBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public ChatBuilder title(final String title) {
        this.title = title;
        return this;
    }

    public Chat build() {
        final Chat chat = Chat.getBuilder().setTitle(title).setContent(content).setPostedDateTime(dateTime).build();
        ReflectionTestUtils.setField(chat, "id", id);
        return chat;
    }
}
