package org.project.movie;

import org.project.container.PostgresDocker;
import org.project.image.Image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class MovieTest extends PostgresDocker {

	@Test
	public void createMovieNoArgs () {
		Movie movie = new Movie();

		Assertions.assertNotNull(movie);
		Assertions.assertTrue(movie.getImages().isEmpty());
		Assertions.assertNull(movie.getId());
		Assertions.assertNull(movie.getImdbId());
		Assertions.assertNull(movie.getYear());
		Assertions.assertNull(movie.getTitle());
		Assertions.assertNull(movie.getDescription());
	}

	@Test
	public void createMovieAllArgs () {
		Movie movie = new Movie(null, 1L, "Title", 2000, "Description", Set.of(
			new Image(null, "uuid-0", new Byte[8]),
			new Image(null, "uuid-1", new Byte[8])
		));

		Assertions.assertNotNull(movie);
		Assertions.assertNull(movie.getId());
		Assertions.assertEquals(1L,            movie.getImdbId());
		Assertions.assertEquals("Title",       movie.getTitle());
		Assertions.assertEquals(2000,          movie.getYear());
		Assertions.assertEquals("Description", movie.getDescription());
		Assertions.assertFalse(movie.getImages().isEmpty());
		Assertions.assertEquals(2,             movie.getImages().size());
	}

	@Test
	public void checkSetterAndGetter () {
		Movie movie = new Movie();

		movie.setId(null);
		movie.setImdbId(1L);
		movie.setTitle("Title");
		movie.setYear(2000);
		movie.setDescription("Description");
		movie.setImages(Set.of(
			new Image(null, "uuid-0", new Byte[8]),
			new Image(null, "uuid-1", new Byte[8])
		));

		Assertions.assertNotNull(movie);
		Assertions.assertNull(movie.getId());
		Assertions.assertEquals(1L,            movie.getImdbId());
		Assertions.assertEquals("Title",       movie.getTitle());
		Assertions.assertEquals(2000,          movie.getYear());
		Assertions.assertEquals("Description", movie.getDescription());
		Assertions.assertFalse(movie.getImages().isEmpty());
		Assertions.assertEquals(2,             movie.getImages().size());
	}

}
