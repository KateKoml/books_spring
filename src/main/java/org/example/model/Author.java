package org.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors", schema = "public")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;

    @Column(name = "year_of_birth", nullable = false)
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Book> books = new HashSet<>();


    public Author() {
    }

    public Author(String fullName, Integer yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public Author(Long id, String fullName, Integer yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
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
        if (!(o instanceof Author author)) return false;

        if (!getId().equals(author.getId())) return false;
        if (!getFullName().equals(author.getFullName())) return false;
        return getYearOfBirth().equals(author.getYearOfBirth());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFullName().hashCode();
        result = 31 * result + getYearOfBirth().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
