package org.project.movie;

import org.project.ObjectGenerator;
import org.project.movie.exception.MovieAlreadyExistsException;
import org.project.movie.exception.MovieNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

	@InjectMocks
	private MovieController controller;

	@Mock
	private MovieService service;

	@BeforeAll
	public static void httpSetup () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);
	}

	//
	// Create
	//

	@Test
	public void addMovie_Success () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		Mockito.doAnswer(
			(Answer<Void>) inv -> null
		).when(service).addMovie(
			ArgumentMatchers.any(MovieDTO.class)
		);

		ResponseEntity<Object> response = controller.addMovie(movieDTO);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getHeaders());
		Assertions.assertNotNull(response.getHeaders().getLocation());

		Assertions.assertEquals(
			HttpStatus.CREATED.value(),
			response.getStatusCode().value()
		);

		Assertions.assertEquals(
			"/" + movieDTO.imdbId(),
			response.getHeaders().getLocation().getPath()
		);
	}

	@Test
	public void addMovie_Exception () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		Mockito.doThrow(
			new MovieAlreadyExistsException("")
		).when(service).addMovie(
			ArgumentMatchers.any(MovieDTO.class)
		);

		ResponseEntity<Object> response = controller.addMovie(movieDTO);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getHeaders());

		Assertions.assertEquals(
			HttpStatus.CONFLICT.value(),
			response.getStatusCode().value()
		);
	}

	//
	// Read
	//

	@Test
	public void getMovies_Success_ParamEmpty () {
		Mockito.when(service.getMovies(ArgumentMatchers.anyMap()
		)).thenReturn(List.of());

		ResponseEntity<Object> response = controller.getMovies(Map.of());

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.OK.value(),
			response.getStatusCode().value()
		);
	}

	@Test
	public void getMovies_Success_ParamNull () {
		Mockito.when(service.getMovies(ArgumentMatchers.isNull()
		)).thenReturn(List.of());

		ResponseEntity<Object> response = controller.getMovies(null);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.OK.value(),
			response.getStatusCode().value()
		);
	}

	@Test
	public void getMovies_Exception() {
		Mockito.doThrow(
			new MovieNotFoundException("")
		).when(service).getMovies(
			ArgumentMatchers.anyMap()
		);

		ResponseEntity<Object> response = controller.getMovies(Map.of());

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.BAD_REQUEST.value(),
			response.getStatusCode().value()
		);
	}

	@Test
	public void getMovieByImdbId_Success () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		Mockito.when(service.getMovieByImdbId(
			ArgumentMatchers.anyLong()
		)).thenReturn(movieDTO);

		ResponseEntity<Object> response = controller.getMovieById(movieDTO.imdbId());

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.OK.value(),
			response.getStatusCode().value()
		);
	}

	@Test
	public void getMovieByImdbId_Exception () {
		Mockito.doThrow(
			new MovieNotFoundException("")
		).when(service).getMovieByImdbId(
			ArgumentMatchers.anyLong()
		);

		ResponseEntity<Object> response = controller.getMovieById(0L);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.BAD_REQUEST.value(),
			response.getStatusCode().value()
		);
	}

	//
	// Update
	//

	@Test
	public void updateMovieById_Success () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		Mockito.when(service.updateMovieByImdbId(
			ArgumentMatchers.anyLong(),
			ArgumentMatchers.any(MovieDTO.class)
		)).thenReturn(movieDTO);

		ResponseEntity<Object> response = controller.updateMovieById(0L, movieDTO);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.OK.value(),
			response.getStatusCode().value()
		);
	}

	@Test
	public void updateMovieById_Exception () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		Mockito.doThrow(
			new MovieNotFoundException("")
		).when(service).updateMovieByImdbId(
			ArgumentMatchers.anyLong(),
			ArgumentMatchers.any(MovieDTO.class)
		);

		ResponseEntity<Object> response = controller.updateMovieById(0L, movieDTO);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.BAD_REQUEST.value(),
			response.getStatusCode().value()
		);
	}

	//
	// Delete
	//

	@Test
	public void deleteMovies_Success () {
		Mockito.doAnswer(
			(Answer<Void>) inv -> null
		).when(service).deleteMovies();

		ResponseEntity<Object> response = controller.deleteMovies();

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.OK.value(),
			response.getStatusCode().value()
		);
	}

	@Test
	public void deleteMovieByImdbId_Success () {
		Mockito.doAnswer(
			(Answer<Void>) inv -> null
		).when(service).deleteMovieByImdbId(
			ArgumentMatchers.any(Long.class)
		);

		ResponseEntity<Object> response = controller.deleteMovieById(1L);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.OK.value(),
			response.getStatusCode().value()
		);
	}

	@Test
	public void deleteMovieByImdbId_Exception () {
		Mockito.doThrow(
			new MovieNotFoundException("")
		).when(service).deleteMovieByImdbId(
			ArgumentMatchers.anyLong()
		);

		ResponseEntity<Object> response = controller.deleteMovieById(0L);

		Assertions.assertNotNull(response.getStatusCode());
		Assertions.assertNotNull(response.getBody());

		Assertions.assertEquals(
			HttpStatus.BAD_REQUEST.value(),
			response.getStatusCode().value()
		);
	}

}
