package com.assignment.media.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.cache.annotation.Cacheable;

/**
 * This data transfer object contains the information of a single chat entry and specifies
 * validation rules that are used to ensure that only valid information can be saved to the used
 * database.
 * 
 */
@Cacheable(value = "chatCache")
public final class ChatDTO {

    private String id;
    private String title;
    private long postedDateTime;
    private String parentId;
    private String authorId;
    private String content;

    public ChatDTO(final String id, final String title, final long postedDateTime, final String parentId, final String authorId, final String content) {
        this.id = id;
        this.title = title;
        this.postedDateTime = postedDateTime;
        this.parentId = parentId;
        this.authorId = authorId;
        this.content = content;
    }

    public ChatDTO() {}

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public long getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(final long postedDateTime) {
        this.postedDateTime = postedDateTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[] {"postedDate"});
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, new String[] {"postedDate"});
    }
}
