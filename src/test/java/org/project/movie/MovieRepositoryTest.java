package org.project.movie;

import org.project.utils.ObjectGenerator;
import org.project.docker.DockerPostgres;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class MovieRepositoryTest extends DockerPostgres {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MovieRepository movieRepository;

	@BeforeEach
	public void clearDatabase () {
		movieRepository.deleteAll();
	}

	@Test
	public void findByImdbId_Empty () {
		Optional<Movie> dbOptMovie = movieRepository.findByImdbId(0L);

		Assertions.assertTrue(dbOptMovie.isEmpty());
	}

	@Test
	public void findByImdbId_Present () {
		Movie movie = ObjectGenerator.randomMovie();

		System.out.println(movie.getImdbId());
		movieRepository.save(movie);

		Optional<Movie> dbOptMovie = movieRepository.findByImdbId(movie.getImdbId());

		Assertions.assertTrue(dbOptMovie.isPresent());

		Movie dbMovie = dbOptMovie.get();

		Assertions.assertEquals(movie.getTitle(),         dbMovie.getTitle());
		Assertions.assertEquals(movie.getYear(),          dbMovie.getYear());
		Assertions.assertEquals(movie.getDescription(),   dbMovie.getDescription());
		Assertions.assertEquals(movie.getImages().size(), dbMovie.getImages().size());
	}

	@Test
	public void findByPattern_Empty () {
		List<Movie> dbMovies = movieRepository.findByPattern("");

		Assertions.assertTrue(dbMovies.isEmpty());
	}

	@Test
	public void findByPattern_Present () {
		movieRepository.saveAll(List.of(
			new Movie(null, 1L, "Star Wars I",   1999, "Description", List.of()),
			new Movie(null, 2L, "Star Wars II",  2002, "Description", List.of()),
			new Movie(null, 3L, "Star Wars III", 2005, "Description", List.of()),
			new Movie(null, 4L, "Star Wars IV",  1977, "Description", List.of()),
			new Movie(null, 5L, "Star Wars V",   1980, "Description", List.of()),
			new Movie(null, 6L, "Star Wars VI",  1983, "Description", List.of()),
			new Movie(null, 7L, "Terminator",    1984, "Description", List.of()),
			new Movie(null, 8L, "Terminator 2",  1991, "Description", List.of()),
			new Movie(null, 9L, "Terminator 3",  2003, "Description", List.of())
		));

		Assertions.assertEquals(6, movieRepository.findByPattern("Star Wars" ).size());
		Assertions.assertEquals(3, movieRepository.findByPattern("Terminator").size());
		Assertions.assertEquals(9, movieRepository.findByPattern("a"         ).size());
		Assertions.assertEquals(0, movieRepository.findByPattern("y"         ).size());
	}

	@Test
	public void findByYear_Empty () {
		Assertions.assertEquals(0, movieRepository.findByYear(1970, 1980).size());
		Assertions.assertEquals(0, movieRepository.findByYear(1970, 1990).size());
		Assertions.assertEquals(0, movieRepository.findByYear(1970, 2000).size());
		Assertions.assertEquals(0, movieRepository.findByYear(1970, 2010).size());
	}

	@Test
	public void findByYear_Present () {
		movieRepository.saveAll(List.of(
			new Movie(null, 1L, "Star Wars I",   1999, "Description", List.of()),
			new Movie(null, 2L, "Star Wars II",  2002, "Description", List.of()),
			new Movie(null, 3L, "Star Wars III", 2005, "Description", List.of()),
			new Movie(null, 4L, "Star Wars IV",  1977, "Description", List.of()),
			new Movie(null, 5L, "Star Wars V",   1980, "Description", List.of()),
			new Movie(null, 6L, "Star Wars VI",  1983, "Description", List.of()),
			new Movie(null, 7L, "Terminator",    1984, "Description", List.of()),
			new Movie(null, 8L, "Terminator 2",  1991, "Description", List.of()),
			new Movie(null, 9L, "Terminator 3",  2003, "Description", List.of())
		));

		Assertions.assertEquals(1, movieRepository.findByYear(1970, 1980).size());
		Assertions.assertEquals(4, movieRepository.findByYear(1970, 1990).size());
		Assertions.assertEquals(6, movieRepository.findByYear(1970, 2000).size());
		Assertions.assertEquals(9, movieRepository.findByYear(1970, 2010).size());
	}

}
