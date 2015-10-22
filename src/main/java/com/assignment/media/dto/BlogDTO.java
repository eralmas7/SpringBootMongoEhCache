package com.assignment.media.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;

/**
 * This data transfer object contains the information of a single blog entry and specifies
 * validation rules that are used to ensure that only valid information can be saved to the used
 * database.
 * 
 */
@Cacheable(value = "blogCache")
public final class BlogDTO {

    @Id
    private String id;
    @NotNull
    @NotEmpty
    @Size(min = 10)
    private String title;
    @NotNull
    @NotEmpty
    private long postedDateTime;
    @NotNull
    @NotEmpty
    @Size(min = 10)
    private String authorId;
    @NotNull
    @NotEmpty
    @Size(min = 10)
    private String content;

    public BlogDTO(final String id, final String title, final long postedDateTime, final String authorId, final String content) {
        this.id = id;
        this.title = title;
        this.postedDateTime = postedDateTime;
        this.authorId = authorId;
        this.content = content;
    }

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

    public long getPostedDate() {
        return postedDateTime;
    }

    public void setPostedDate(final long postedDateTime) {
        this.postedDateTime = postedDateTime;
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
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
