package repository;

import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(TestConfig.class)
@ExtendWith(PostgresExtension.class)
@Transactional
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testSaveFlushAndFindByIdAuthor() {
        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.saveAndFlush(author);

        Long authorId = author.getId();
        Optional<Author> optAuthor = authorRepository.findById(authorId);
        assertTrue(optAuthor.isPresent());
        assertEquals("Allan Poe", optAuthor.get().getFullName());
    }

    @Test
    void testSaveAllAndFindAllAuthors() {
        Author author1 = new Author();
        author1.setFullName("Allan Poe");
        author1.setYearOfBirth(1809);

        Author author2 = new Author();
        author2.setFullName("Kraft");
        author2.setYearOfBirth(1850);

        authorRepository.saveAll(List.of(author1, author2));

        List<Author> authors = authorRepository.findAll();
        assertEquals(2, authors.size());
        assertEquals("Allan Poe", authors.get(0).getFullName());
        assertEquals("Kraft", authors.get(1).getFullName());
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.saveAndFlush(author);

        Long authorId = author.getId();
        assertTrue(authorRepository.existsById(authorId));

        authorRepository.deleteById(authorId);
        assertFalse(authorRepository.existsById(authorId));
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.saveAndFlush(author);

        Long authorId = author.getId();
        Author updatedAuthor = authorRepository.findById(authorId).orElse(null);
        assertNotNull(updatedAuthor);

        updatedAuthor.setFullName("Edgar Allan Poe");
        authorRepository.saveAndFlush(updatedAuthor);

        Author fetchedUpdatedAuthor = authorRepository.findById(authorId).orElse(null);
        assertNotNull(fetchedUpdatedAuthor);

        assertEquals("Edgar Allan Poe", fetchedUpdatedAuthor.getFullName());
    }
}
