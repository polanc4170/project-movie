package org.project.movie;

import org.project.ObjectGenerator;
import org.project.image.ImageAction;
import org.project.image.ImageDTO;
import org.project.image.ImageService;
import org.project.image.exception.ImageActionNotSupportedException;
import org.project.movie.exception.MovieAlreadyExistsException;
import org.project.movie.exception.MovieNotFoundException;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	@Mock
	private ImageService imageService;

	@Mock
	private MovieMapper movieMapper;

	//
	// Private mockito callers
	//

	private void mockito_ImageService_AddImage () {
		Mockito.when(imageService.addImage(ArgumentMatchers.any(ImageDTO.class))
		).thenReturn(ObjectGenerator.randomImage());
	}

	private void mockito_ImageService_DeleteImages () {
		Mockito.doAnswer((Answer <Void>) inv -> null
		).when(imageService).deleteImages();
	}

	private void mockito_MovieMapper_ToMovie () {
		Mockito.when(
			movieMapper.toMovie(
				ArgumentMatchers.any(MovieDTO.class)
			)
		).then((Answer<Movie>) inv -> {
			MovieDTO movieDTO = inv.getArgument(0, MovieDTO.class);

			return new Movie(
				null,
				movieDTO.imdbId(),
				movieDTO.title(),
				movieDTO.year(),
				movieDTO.description(),
				movieDTO.images().stream().map(
					ObjectGenerator::of
				).toList()
			);
		});
	}

	private void mockito_MovieMapper_ToDTO () {
		Mockito.when(
			movieMapper.toDTO(
				ArgumentMatchers.any(Movie.class)
			)
		).then((Answer<MovieDTO>) inv -> {
			Movie movie = inv.getArgument(0, Movie.class);

			return new MovieDTO(
				movie.getImdbId(),
				movie.getTitle(),
				movie.getYear(),
				movie.getDescription(),
				movie.getImages().stream().map(
					ObjectGenerator::of
				).toList()
			);
		});
	}

	private void mockito_MovieRepository_FindByImdbId (Movie movie) {
		Mockito.when(repository.findByImdbId(ArgumentMatchers.anyLong())
		).thenReturn(movie == null ? Optional.empty() : Optional.of(movie));
	}

	private void mockito_MovieRepository_Save () {
		Mockito.when(repository.save(ArgumentMatchers.any(Movie.class))
		).then(AdditionalAnswers.returnsFirstArg());
	}

	@SuppressWarnings("SameParameterValue")
	private void mockito_MovieRepository_FindAllPageable (Page<Movie> page) {
		Mockito.when(repository.findAll(ArgumentMatchers.any(PageRequest.class))
		).thenReturn(page == null ? Page.empty() : page);
	}

	@SuppressWarnings("SameParameterValue")
	private void mockito_MovieRepository_FindAll (Movie [] movies) {
		List<Movie> list;

		if      (movies == null)     list = Collections.emptyList();
		else if (movies.length == 0) list = Collections.emptyList();
		else                         list = Arrays.asList(movies);

		Mockito.when(repository.findAll()
		).thenReturn(list);
	}

	@SuppressWarnings("SameParameterValue")
	private void mockito_MovieRepository_FindByPatternAndYear (Movie [] movies) {
		List<Movie> list;

		if      (movies == null)     list = Collections.emptyList();
		else if (movies.length == 0) list = Collections.emptyList();
		else                         list = Arrays.asList(movies);

		Mockito.when(repository.findByPatternAndYear(
			ArgumentMatchers.anyString(),
			ArgumentMatchers.anyInt(),
			ArgumentMatchers.anyInt()
		)).thenReturn(list);
	}

	@SuppressWarnings("SameParameterValue")
	private void mockito_MovieRepository_FindByYear (Movie [] movies) {
		List<Movie> list;

		if      (movies == null)     list = Collections.emptyList();
		else if (movies.length == 0) list = Collections.emptyList();
		else                         list = Arrays.asList(movies);

		Mockito.when(repository.findByYear(
			ArgumentMatchers.anyInt(),
			ArgumentMatchers.anyInt()
		)).thenReturn(list);
	}

	@SuppressWarnings("SameParameterValue")
	private void mockito_MovieRepository_FindByPattern (Movie [] movies) {
		List<Movie> list;

		if      (movies == null)     list = Collections.emptyList();
		else if (movies.length == 0) list = Collections.emptyList();
		else                         list = Arrays.asList(movies);

		Mockito.when(repository.findByPattern(
			ArgumentMatchers.anyString()
		)).thenReturn(list);
	}

	private void mockito_MovieRepository_DeleteAll () {
		Mockito.doAnswer((Answer <Void>) inv -> null
		).when(repository).deleteAll();
	}

	private void mockito_MovieRepository_Delete () {
		Mockito.doAnswer((Answer <Void>) inv -> null
		).when(repository).delete(ArgumentMatchers.any(Movie.class));
	}

	//
	// Create
	//

	@Test
	public void addMovie_Success () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		mockito_MovieMapper_ToMovie();
		mockito_MovieRepository_FindByImdbId(null);
		mockito_MovieRepository_Save();
		mockito_ImageService_AddImage();

		service.addMovie(movieDTO);
	}

	@Test
	public void addMovie_ExceptionExists () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();
		Movie    movie    = ObjectGenerator.of(movieDTO);

		mockito_MovieRepository_FindByImdbId(movie);

		Assertions.assertThrows(
			MovieAlreadyExistsException.class,
			() -> service.addMovie(movieDTO)
		);
	}

	//
	// Read
	//

	@Test
	public void getMovies_Success_ParamFind () {
		mockito_MovieRepository_FindByPatternAndYear(null);
		mockito_MovieRepository_FindByPattern(null);
		mockito_MovieRepository_FindByYear(null);
		mockito_MovieRepository_FindAll(null);

		Assertions.assertNotNull(service.getMovies(
			Map.of("nonsense", "0"))
		);

		Assertions.assertNotNull(service.getMovies(
			Map.of("pattern", "x")
		));

		Assertions.assertNotNull(service.getMovies(
			Map.of("startYear", "0", "endYear", "0")
		));

		Assertions.assertNotNull(service.getMovies(
			Map.of("pattern", "x", "startYear", "0")
		));

		Assertions.assertNotNull(service.getMovies(
			Map.of("pattern", "x", "endYear", "0")
		));

		Assertions.assertNotNull(service.getMovies(
			Map.of("pattern", "x", "startYear", "0", "endYear", "0")
		));
	}

	@Test
	public void getMovies_Success_ParamPage () {
		mockito_MovieRepository_FindAllPageable(null);

		Assertions.assertNotNull(service.getMovies(
			Map.of("page", "1")
		));

		Assertions.assertNotNull(service.getMovies(
			Map.of("page", "1", "size", "10")
		));
	}

	@Test
	public void getMovies_Success_ParamNull () {
		mockito_MovieRepository_FindAll(null);

		service.getMovies(null);
	}

	@Test
	public void getMovies_Success_ParamEmpty () {
		mockito_MovieRepository_FindAll(null);

		service.getMovies(Map.of());
	}

	@Test
	public void getMovies_Exception_ParamFind () {
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
	public void getMovies_Exception_ParamPage () {
		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("page", "x"))
		);

		Assertions.assertThrows(
			NumberFormatException.class,
			() -> service.getMovies(Map.of("page", "1", "size", "x"))
		);

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
	public void getMoviesByImdbId_Success () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();
		Movie    movie    = ObjectGenerator.of(movieDTO);

		mockito_MovieRepository_FindByImdbId(movie);
		mockito_MovieMapper_ToDTO();

		MovieDTO response = service.getMovieByImdbId(movieDTO.imdbId());

		Assertions.assertNotNull(response);
		Assertions.assertEquals(movie.getImdbId(),        response.imdbId());
		Assertions.assertEquals(movie.getTitle(),         response.title());
		Assertions.assertEquals(movie.getYear(),          response.year());
		Assertions.assertEquals(movie.getDescription(),   response.description());
		Assertions.assertEquals(movie.getImages().size(), response.images().size());
	}

	@Test
	public void getMoviesByImdbId_ExceptionNotFound () {
		mockito_MovieRepository_FindByImdbId(null);

		Assertions.assertThrows(
			MovieNotFoundException.class,
			() -> service.getMovieByImdbId(0L)
		);
	}

	//
	// Update
	//

	@Test
	public void updateMovieByImdbId_Success_ParamNull () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();
		Movie    movie    = ObjectGenerator.of(movieDTO);
		MovieDTO paramDTO = new MovieDTO(null, null, null, null, null);

		mockito_MovieRepository_FindByImdbId(movie);
		mockito_MovieMapper_ToDTO();
		mockito_MovieRepository_Save();

		MovieDTO response = service.updateMovieByImdbId(
			movie.getImdbId(),
			paramDTO
		);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(movie.getImdbId(),        response.imdbId());
		Assertions.assertEquals(movie.getTitle(),         response.title());
		Assertions.assertEquals(movie.getYear(),          response.year());
		Assertions.assertEquals(movie.getDescription(),   response.description());
		Assertions.assertEquals(movie.getImages().size(), response.images().size());
	}

	@Test
	public void updateMovieByImdbId_Success_ParamSame () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();
		Movie    movie    = ObjectGenerator.of(movieDTO);

		mockito_MovieRepository_FindByImdbId(movie);
		mockito_MovieRepository_Save();
		mockito_MovieMapper_ToDTO();
		mockito_ImageService_AddImage();

		MovieDTO response = service.updateMovieByImdbId(
			movie.getImdbId(),
			movieDTO
		);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(movie.getImdbId(),        response.imdbId());
		Assertions.assertEquals(movie.getTitle(),         response.title());
		Assertions.assertEquals(movie.getYear(),          response.year());
		Assertions.assertEquals(movie.getDescription(),   response.description());
		Assertions.assertEquals(movie.getImages().size(), response.images().size());
	}

	@Test
	public void updateMovieByImdbId_Success_ActionReplace () {
		MovieDTO paramDTO = ObjectGenerator.randomMovieDTO();
		Movie    movie    = ObjectGenerator.randomMovie();

		mockito_MovieRepository_FindByImdbId(movie);
		mockito_MovieRepository_Save();
		mockito_MovieMapper_ToDTO();
		mockito_ImageService_AddImage();

		MovieDTO response = service.updateMovieByImdbId(
			movie.getImdbId(),
			paramDTO,
			ImageAction.REPLACE
		);

		Assertions.assertNotNull(response);

		Assertions.assertEquals(paramDTO.title(),         response.title());
		Assertions.assertEquals(paramDTO.year(),          response.year());
		Assertions.assertEquals(paramDTO.description(),   response.description());
		Assertions.assertEquals(paramDTO.images().size(), response.images().size());
	}

	@Test
	public void updateMovieByImdbId_Success_ActionAdd () {
		MovieDTO paramDTO = ObjectGenerator.randomMovieDTO();
		Movie    movie    = ObjectGenerator.randomMovie();

		mockito_MovieRepository_FindByImdbId(movie);
		mockito_MovieRepository_Save();
		mockito_MovieMapper_ToDTO();
		mockito_ImageService_AddImage();

		int expectedImages =
			movie.getImages().size() +
			paramDTO.images().size();

		MovieDTO response = service.updateMovieByImdbId(
			movie.getImdbId(),
			paramDTO,
			ImageAction.ADD
		);

		Assertions.assertNotNull(response);

		Assertions.assertEquals(paramDTO.title(),       response.title());
		Assertions.assertEquals(paramDTO.year(),        response.year());
		Assertions.assertEquals(paramDTO.description(), response.description());
		Assertions.assertEquals(expectedImages,			response.images().size());
	}

	@Test
	public void updateMovieByImdbId_ExceptionNotSupported () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();
		Movie    movie    = ObjectGenerator.of(movieDTO);

		mockito_MovieRepository_FindByImdbId(movie);

		Assertions.assertThrows(
			ImageActionNotSupportedException.class,
			() -> service.updateMovieByImdbId(
				movieDTO.imdbId(),
				movieDTO,
				ImageAction.IGNORE
			)
		);
	}

	@Test
	public void updateMovieByImdbId_ExceptionNotFound () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		mockito_MovieRepository_FindByImdbId(null);

		Assertions.assertThrows(
			MovieNotFoundException.class,
			() -> service.updateMovieByImdbId(movieDTO.imdbId(), movieDTO)
		);
	}

	//
	// Delete
	//

	@Test
	public void deleteMovies_Success () {
		mockito_MovieRepository_DeleteAll();
		mockito_ImageService_DeleteImages();

		service.deleteMovies();
	}

	@Test
	public void deleteMovieByImdbId_Success () {
		Movie movie = ObjectGenerator.randomMovie();

		mockito_MovieRepository_FindByImdbId(movie);
		mockito_MovieRepository_Delete();

		service.deleteMovieByImdbId(movie.getImdbId());
	}

	@Test
	public void deleteMovieByImdbId_Exception () {
		mockito_MovieRepository_FindByImdbId(null);

		Assertions.assertThrows(
			MovieNotFoundException.class,
			() -> service.deleteMovieByImdbId(0L)
		);
	}

}
