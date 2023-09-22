package repository;

import org.example.model.Genre;
import org.example.repository.GenreRepository;
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
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    void testSaveFlushAndFindByIdGenre() {
        Genre genre = new Genre();
        genre.setType("horror");
        genreRepository.saveAndFlush(genre);

        Long genreId = genre.getId();
        Optional<Genre> optGenre = genreRepository.findById(genreId);
        assertTrue(optGenre.isPresent());
        assertEquals("horror", optGenre.get().getType());
    }

    @Test
    void testSaveAllAndFindAllGenres() {
        Genre genre = new Genre();
        genre.setType("horror");

        Genre genre2 = new Genre();
        genre2.setType("romance");

        genreRepository.saveAll(List.of(genre, genre2));

        List<Genre> genres = genreRepository.findAll();
        assertEquals(2, genres.size());
        assertEquals("horror", genres.get(0).getType());
        assertEquals("romance", genres.get(1).getType());
    }

    @Test
    void testDeleteGenre() {
        Genre genre = new Genre();
        genre.setType("horror");

        genreRepository.saveAndFlush(genre);

        Long genreId = genre.getId();
        assertTrue(genreRepository.existsById(genreId));

        genreRepository.deleteById(genreId);
        assertFalse(genreRepository.existsById(genreId));
    }

    @Test
    void testUpdateGenre() {
        Genre genre = new Genre();
        genre.setType("horror");

        genreRepository.saveAndFlush(genre);

        Long genreId = genre.getId();
        Genre updatedGenre = genreRepository.findById(genreId).orElse(null);
        assertNotNull(updatedGenre);

        updatedGenre.setType("thriller");
        genreRepository.saveAndFlush(updatedGenre);

        Genre fetchedUpdatedGenre = genreRepository.findById(genreId).orElse(null);
        assertNotNull(fetchedUpdatedGenre);

        assertEquals("thriller", fetchedUpdatedGenre.getType());
    }
}
