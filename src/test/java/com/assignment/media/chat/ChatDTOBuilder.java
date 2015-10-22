package com.assignment.media.chat;

import com.assignment.media.dto.ChatDTO;

class ChatDTOBuilder {

    private String id;
    private String title;
    private String content;
    private long postedDateTime;
    private String parentId;
    private String authorId;

    ChatDTOBuilder() {}

    ChatDTOBuilder content(final String content) {
        this.content = content;
        return this;
    }

    ChatDTOBuilder id(final String id) {
        this.id = id;
        return this;
    }

    ChatDTOBuilder title(final String title) {
        this.title = title;
        return this;
    }

    ChatDTOBuilder postedDateTime(final long postedDateTime) {
        this.postedDateTime = postedDateTime;
        return this;
    }

    ChatDTOBuilder parentId(final String parentId) {
        this.parentId = parentId;
        return this;
    }

    ChatDTOBuilder authorId(final String authorId) {
        this.authorId = authorId;
        return this;
    }

    ChatDTO build() {
        final ChatDTO dto = new ChatDTO(id, title, postedDateTime, parentId, authorId, content);
        return dto;
    }
}
