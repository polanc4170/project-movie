package org.project.movie;

import org.project.image.Image;
import org.project.image.ImageDTO;
import org.project.image.ImageMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

	@InjectMocks
	private MovieService service;

	@Mock
	private MovieMapper movieMapper;

	@Mock
	private ImageMapper imageMapper;

	@Mock
	private MovieRepository repository;

	//
	// Create
	//

	@Test
	public void addMovie_Success () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of());
		Movie    movie    = new Movie(null, 0L, "A", 0, "A", List.of());

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.empty());

		Mockito.when(
			movieMapper.toMovie(
				ArgumentMatchers.any(MovieDTO.class)
			)
		).thenReturn(movie);

		Mockito.when(
			repository.save(
				ArgumentMatchers.any(Movie.class)
			)
		).then(AdditionalAnswers.returnsFirstArg());

		service.addMovie(movieDTO);
	}

	@Test
	public void addMovie_WhenAlreadyExists () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of());
		Movie    movie    = new Movie(null, 0L, "A", 0, "A", List.of());

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.of(movie));

		Assertions.assertThrows(
			MovieAlreadyExistsException.class,
			() -> service.addMovie(movieDTO)
		);
	}

	//
	// Read
	//

	@Test
	public void getMovies_FilterParams_Success () {
		Mockito.when(
			repository.filterByPattern(
				ArgumentMatchers.anyString()
			)
		).thenReturn(Collections.emptyList());

		Mockito.when(
			repository.filterByPatternAndYear(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyInt()
			)
		).thenReturn(Collections.emptyList());

		Mockito.when(
			repository.filterByYear(
				ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyInt()
			)
		).thenReturn(Collections.emptyList());

		Mockito.when(
			repository.findAll()
		).thenReturn(Collections.emptyList());

		Assertions.assertNotNull(service.getMovies(Map.of("x", "0", "y", "0")));
		Assertions.assertNotNull(service.getMovies(Map.of("pattern", "x")));
		Assertions.assertNotNull(service.getMovies(Map.of("startYear", "0", "endYear", "0")));
		Assertions.assertNotNull(service.getMovies(Map.of("pattern", "x", "startYear", "0")));
		Assertions.assertNotNull(service.getMovies(Map.of("pattern", "x", "endYear", "0")));
		Assertions.assertNotNull(service.getMovies(Map.of("pattern", "x", "startYear", "0", "endYear", "0")));
	}

	@Test
	public void getMovies_FilterParams_WhenNumberFormat () {
		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("startYear", "x"))
		);

		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("endYear", "x"))
		);
	}

	@Test
	public void getMovies_PageParams_Success () {
		Mockito.when(
			repository.findAll(
				ArgumentMatchers.any(PageRequest.class)
			)
		).thenReturn(Page.empty());

		Assertions.assertNotNull(service.getMovies(Map.of("page", "1")));
		Assertions.assertNotNull(service.getMovies(Map.of("page", "1", "size", "10")));
	}

	@Test
	public void getMovies_PageParams_WhenNumberFormat () {
		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("page", "x"))
		);

		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("page", "1", "size", "x"))
		);
	}

	@Test
	public void getMovies_PageParams_WhenIllegalArgument () {
		Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> service.getMovies(Map.of("page", "-1"))
		);

		Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> service.getMovies(Map.of("page", "1", "size", "0"))
		);
	}

	@Test
	public void getMovies_Success_NullParams () {
		Mockito.when(
			repository.findAll()
		).thenReturn(Collections.emptyList());

		service.getMovies(null);
	}

	@Test
	public void getMovies_Success_EmptyParams () {
		Mockito.when(
			repository.findAll()
		).thenReturn(Collections.emptyList());

		service.getMovies(Map.of());
	}

	@Test
	public void getMovies_WhenNumberFormat () {
		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("page", "x"))
		);

		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("page", "1", "size", "x"))
		);
	}

	@Test
	public void getMovies_WhenIllegalArgument () {
		Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> service.getMovies(Map.of("page", "-1"))
		);

		Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> service.getMovies(Map.of("page", "1", "size", "0"))
		);
	}


	@Test
	public void getMoviesById_Success () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of());
		Movie    movie    = new Movie(null, 0L, "A", 0, "A", List.of());

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.of(movie));

		Mockito.when(
			movieMapper.toDTO(
				ArgumentMatchers.any(Movie.class)
			)
		).thenReturn(movieDTO);

		MovieDTO response = service.getMovieById(movieDTO.imdbId());

		Assertions.assertNotNull(response);
		Assertions.assertEquals(movie.getImdbId(),        response.imdbId());
		Assertions.assertEquals(movie.getTitle(),         response.title());
		Assertions.assertEquals(movie.getYear(),          response.year());
		Assertions.assertEquals(movie.getDescription(),   response.description());
		Assertions.assertEquals(movie.getImages().size(), response.images().size());
	}

	@Test
	public void getMoviesById_WhenNotFound () {
		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.empty());

		Assertions.assertThrows(
			MovieNotFoundException.class,
			() -> service.getMovieById(0L)
		);
	}

	//
	// Update
	//

	@Test
	public void updateMovieById_Success_NullParams () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of());
		Movie    movie    = new Movie(null, 0L, "A", 0, "A", List.of());

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.of(movie));

		Mockito.when(
			movieMapper.toDTO(
				ArgumentMatchers.any(Movie.class)
			)
		).thenReturn(movieDTO);

		Mockito.when(
			repository.save(
				ArgumentMatchers.any(Movie.class)
			)
		).then(AdditionalAnswers.returnsFirstArg());

		MovieDTO response = service.updateMovieById(
			movie.getImdbId(),
			new MovieDTO(movie.getImdbId(), null, null, null, null)
		);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(movie.getImdbId(),        response.imdbId());
		Assertions.assertEquals(movie.getTitle(),         response.title());
		Assertions.assertEquals(movie.getYear(),          response.year());
		Assertions.assertEquals(movie.getDescription(),   response.description());
		Assertions.assertEquals(movie.getImages().size(), response.images().size());
	}

	@Test
	public void updateMovieById_Success_SameParams () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of());
		Movie    movie    = new Movie(null, 0L, "A", 0, "A", List.of());

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.of(movie));

		Mockito.when(
			movieMapper.toDTO(
				ArgumentMatchers.any(Movie.class)
			)
		).thenReturn(movieDTO);

		Mockito.when(
			repository.save(
				ArgumentMatchers.any(Movie.class)
			)
		).then(AdditionalAnswers.returnsFirstArg());

		MovieDTO response = service.updateMovieById(
			movie.getImdbId(),
			new MovieDTO(
				movie.getImdbId(),
				movie.getTitle(),
				movie.getYear(),
				movie.getDescription(),
				List.of()
			)
		);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(movie.getImdbId(),        response.imdbId());
		Assertions.assertEquals(movie.getTitle(),         response.title());
		Assertions.assertEquals(movie.getYear(),          response.year());
		Assertions.assertEquals(movie.getDescription(),   response.description());
		Assertions.assertEquals(movie.getImages().size(), response.images().size());
	}

	@Test
	public void updateMovieById_Success_DiffParams () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of());
		ImageDTO imageDTO = new ImageDTO("0", new Byte[8]);
		Movie    movie    = new Movie(null, 0L, "A", 0, "A", List.of());
		Image    image    = new Image(null, "0", new Byte[8]);

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.of(movie));

		Mockito.when(
			movieMapper.toDTO(
				ArgumentMatchers.any(Movie.class)
			)
		).thenReturn(movieDTO);

		Mockito.when(
			imageMapper.toImage(
				ArgumentMatchers.any(ImageDTO.class)
			)
		).thenReturn(image);

		Mockito.when(
			repository.save(
				ArgumentMatchers.any(Movie.class)
			)
		).then(AdditionalAnswers.returnsFirstArg());

		MovieDTO response = service.updateMovieById(
			movie.getImdbId(),
			new MovieDTO(1L, "B", 1, "B", List.of(imageDTO))
		);

		Assertions.assertNotNull(response);
	}

	@Test
	public void updateMovieById_WhenNotFound () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of());

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.empty());

		Assertions.assertThrows(
			MovieNotFoundException.class,
			() -> service.updateMovieById(movieDTO.imdbId(), movieDTO)
		);
	}

	//
	// Delete
	//

	@Test
	public void deleteMovies_Success () {
		Mockito.doAnswer(
			(Answer <Void>) inv -> null
		).when(repository).deleteAll();

		service.deleteMovies();
	}

	@Test
	public void deleteMovieById_Success () {
		Movie movie = new Movie(null, 0L, "A", 0, "A", List.of());

		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.of(movie));

		Mockito.doAnswer(
			(Answer <Void>) inv -> null
		).when(repository).delete(
			ArgumentMatchers.any(Movie.class)
		);

		service.deleteMovieById(0L);
	}

	@Test
	public void deleteMovieById_WhenNotFound () {
		Mockito.when(
			repository.findByImdbId(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(Optional.empty());

		Assertions.assertThrows(
			MovieNotFoundException.class,
			() -> service.deleteMovieById(1L)
		);
	}

}
