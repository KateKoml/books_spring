package org.example.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BookCreateDto {
    private String name;
    private Integer year;
    private Long authorId;

    public BookCreateDto(String name, Integer year, Long authorId) {
        this.name = name;
        this.year = year;
        this.authorId = authorId;
    }

    public BookCreateDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthor(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCreateDto that)) return false;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getYear() != null ? getYear().equals(that.getYear()) : that.getYear() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getYear() != null ? getYear().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
