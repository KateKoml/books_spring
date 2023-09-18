package org.example.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.model.Book;

import java.util.Set;

public class AuthorDto {
    private Long id;
    private String fullName;
    private Integer yearOfBirth;
    private Set<Book> books;

    public AuthorDto(Long id, String fullName, Integer yearOfBirth, Set<Book> books) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
        this.books = books;
    }

    public AuthorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDto authorDto)) return false;

        if (getId() != null ? !getId().equals(authorDto.getId()) : authorDto.getId() != null) return false;
        if (getFullName() != null ? !getFullName().equals(authorDto.getFullName()) : authorDto.getFullName() != null)
            return false;
        if (getYearOfBirth() != null ? !getYearOfBirth().equals(authorDto.getYearOfBirth()) : authorDto.getYearOfBirth() != null)
            return false;
        return getBooks() != null ? getBooks().equals(authorDto.getBooks()) : authorDto.getBooks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (getYearOfBirth() != null ? getYearOfBirth().hashCode() : 0);
        result = 31 * result + (getBooks() != null ? getBooks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
