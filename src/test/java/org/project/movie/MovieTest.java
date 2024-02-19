package org.project.movie;

import org.project.utils.ObjectGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MovieTest {

	@Test
	public void createMovie_ArgsNone () {
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
	public void createMovie_ArgsAll () {
		Movie movie = new Movie(null, 0L, "T", 2000, "D", List.of(
			ObjectGenerator.randomImage(),
			ObjectGenerator.randomImage()
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
	public void createMovie_Lombok () {
		Movie movie = new Movie();

		movie.setId(null);
		movie.setImdbId(0L);
		movie.setTitle("T");
		movie.setYear(2000);
		movie.setDescription("D");
		movie.setImages(List.of(
			ObjectGenerator.randomImage(),
			ObjectGenerator.randomImage()
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
