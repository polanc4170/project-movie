package org.project.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

	@InjectMocks
	private MovieController controller;

	@Mock
	private MovieService service;

	//
	// Create
	//

	@Test
	public void addMovie_Success () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.doAnswer(
			(Answer <Void>) inv -> null
		).when(service).addMovie(
			ArgumentMatchers.any(MovieDTO.class)
		);

		ResponseEntity<Object> response = controller.addMovie(dto);

		Assertions.assertEquals(201, response.getStatusCode().value());
		Assertions.assertNotNull(response.getHeaders().getLocation());
		Assertions.assertEquals("/0", response.getHeaders().getLocation().getPath());
	}

	@Test
	public void addMovie_WhenAlreadyExists () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.doThrow(
			new MovieAlreadyExistsException("Error")
		).when(service).addMovie(
			ArgumentMatchers.any(MovieDTO.class)
		);

		ResponseEntity<Object> response = controller.addMovie(dto);

		Assertions.assertEquals(409, response.getStatusCode().value());
		Assertions.assertNull(response.getHeaders().getLocation());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals("Error", response.getBody().toString());
	}

	//
	// Read
	//

	@Test
	public void getMovies_Success_WithParams () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.when(
			service.getMovies(
				ArgumentMatchers.anyMap()
			)
		).thenReturn(List.of(dto));

		ResponseEntity<Object> response = controller.getMovies(Collections.emptyMap());

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	public void getMovies_Success_NoParams () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.when(
			service.getMovies(
				ArgumentMatchers.anyMap()
			)
		).thenReturn(List.of(dto));

		ResponseEntity<Object> response = controller.getMovies(Map.of());

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	public void getMovies_Success_NullParams () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.when(
			service.getMovies(null)
		).thenReturn(List.of(dto));

		ResponseEntity<Object> response = controller.getMovies(null);

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	public void getMovies_SyntaxError () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		Mockito.doThrow(
			new NumberFormatException()
		).when(service).getMovies(
			ArgumentMatchers.anyMap()
		);

		ResponseEntity<Object> response = controller.getMovies(Map.of("page", "x"));

		Assertions.assertEquals(400, response.getStatusCode().value());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void getMovieById_Success () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.when(
			service.getMovieById(
				ArgumentMatchers.anyLong()
			)
		).thenReturn(dto);

		ResponseEntity<Object> response = controller.getMovieById(0L);

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	public void getMovieById_WhenNotFound () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		Mockito.doThrow(
			new MovieNotFoundException("Error")
		).when(service).getMovieById(
			ArgumentMatchers.anyLong()
		);

		ResponseEntity<Object> response = controller.getMovieById(1L);

		Assertions.assertEquals(400, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals("Error", response.getBody().toString());
	}

	//
	// Update
	//

	@Test
	public void updateMovieById_Success () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.when(
			service.updateMovieById(
				ArgumentMatchers.anyLong(),
				ArgumentMatchers.any(MovieDTO.class)
			)
		).thenReturn(dto);

		ResponseEntity<Object> response = controller.updateMovieById(0L, dto);

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	public void updateMovieById_WhenNotFound () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		MovieDTO dto = new MovieDTO(0L, "", 0, "", null);

		Mockito.doThrow(
			new MovieNotFoundException("Error")
		).when(service).updateMovieById(
			ArgumentMatchers.anyLong(),
			ArgumentMatchers.any(MovieDTO.class)
		);

		ResponseEntity<Object> response = controller.updateMovieById(1L, dto);

		Assertions.assertEquals(400, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals("Error", response.getBody().toString());
	}

	//
	// Delete
	//

	@Test
	public void deleteMovies_Success () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		Mockito.doAnswer(
			(Answer <Void>) inv -> null
		).when(service).deleteMovies();

		ResponseEntity<Object> response = controller.deleteMovies();

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void deleteMovieById_Success () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		Mockito.doAnswer(
			(Answer <Void>) inv -> null
		).when(service).deleteMovieById(
			ArgumentMatchers.anyLong()
		);

		ResponseEntity<Object> response = controller.deleteMovieById(1L);

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void deleteMovieById_WhenNotFound () {
		RequestContextHolder.setRequestAttributes(
			new ServletRequestAttributes(new MockHttpServletRequest())
		);

		Mockito.doThrow(
			new MovieNotFoundException("Error")
		).when(service).deleteMovieById(
			ArgumentMatchers.anyLong()
		);

		ResponseEntity<Object> response = controller.deleteMovieById(0L);

		Assertions.assertEquals(400, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals("Error", response.getBody().toString());
	}

}
