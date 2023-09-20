package org.example.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BookDto {
    private Long id;
    private String name;
    private Integer year;
    private Long authorId;

    public BookDto(Long id, String name, Integer year, Long authorId) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.authorId = authorId;
    }

    public BookDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDto bookDto)) return false;

        if (getId() != null ? !getId().equals(bookDto.getId()) : bookDto.getId() != null) return false;
        if (getName() != null ? !getName().equals(bookDto.getName()) : bookDto.getName() != null) return false;
        return getYear() != null ? getYear().equals(bookDto.getYear()) : bookDto.getYear() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getYear() != null ? getYear().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
