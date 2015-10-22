package com.assignment.media.model;

import javax.validation.constraints.NotNull;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public final class Blog {

    @Id
    private String id;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private long postedDateTime;
    @NotNull
    @NotEmpty
    private String authorId;
    @NotNull
    @NotEmpty
    private String content;

    public Blog() {}

    private Blog(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.postedDateTime = builder.postedDateTime;
        this.authorId = builder.authorId;
        this.content = builder.content;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String title;
        private long postedDateTime;
        private String authorId;
        private String content;

        private Builder() {}

        public Builder setId(final String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder setPostedDateTime(final long postedDateTime) {
            this.postedDateTime = postedDateTime;
            return this;
        }

        public Builder setAuthorId(final String authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder setContent(final String content) {
            this.content = content;
            return this;
        }

        public Blog build() {
            final Blog blog = new Blog(this);
            return blog;
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPostedDateTime() {
        return postedDateTime;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
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
