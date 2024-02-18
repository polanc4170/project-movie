package org.project.movie;

import org.project.image.Image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MovieTest {

	@Test
	public void createMovieNoArgs () {
		Movie movie = new Movie();

		Assertions.assertNotNull(movie);
		Assertions.assertNull(movie.getId());
		Assertions.assertNull(movie.getImdbId());
		Assertions.assertNull(movie.getYear());
		Assertions.assertNull(movie.getTitle());
		Assertions.assertNull(movie.getDescription());
		Assertions.assertTrue(movie.getImages().isEmpty());
	}

	@Test
	public void createMovieAllArgs () {
		Movie movie = new Movie(null, 0L, "T", 2000, "D", List.of(
			new Image(null, 0L, 0L, new Byte[8]),
			new Image(null, 1L, 1L, new Byte[8])
		));

		Assertions.assertNotNull(movie);
		Assertions.assertNull(movie.getId());
		Assertions.assertNotNull(movie.getImdbId());
		Assertions.assertNotNull(movie.getYear());
		Assertions.assertNotNull(movie.getTitle());
		Assertions.assertNotNull(movie.getDescription());
		Assertions.assertFalse(movie.getImages().isEmpty());

		Assertions.assertEquals(0,    movie.getImdbId());
		Assertions.assertEquals("T",  movie.getTitle());
		Assertions.assertEquals(2000, movie.getYear());
		Assertions.assertEquals("D",  movie.getDescription());
		Assertions.assertEquals(2,    movie.getImages().size());
	}

	@Test
	public void checkSetterAndGetter () {
		Movie movie = new Movie();

		movie.setId(null);
		movie.setImdbId(0L);
		movie.setTitle("T");
		movie.setYear(2000);
		movie.setDescription("D");
		movie.setImages(List.of(
			new Image(null, 0L, 0L, new Byte[8]),
			new Image(null, 1L, 1L, new Byte[8])
		));

		Assertions.assertNotNull(movie);
		Assertions.assertNull(movie.getId());
		Assertions.assertNotNull(movie.getImdbId());
		Assertions.assertNotNull(movie.getYear());
		Assertions.assertNotNull(movie.getTitle());
		Assertions.assertNotNull(movie.getDescription());
		Assertions.assertFalse(movie.getImages().isEmpty());

		Assertions.assertEquals(0,    movie.getImdbId());
		Assertions.assertEquals("T",  movie.getTitle());
		Assertions.assertEquals(2000, movie.getYear());
		Assertions.assertEquals("D",  movie.getDescription());
		Assertions.assertEquals(2,    movie.getImages().size());
	}

}
