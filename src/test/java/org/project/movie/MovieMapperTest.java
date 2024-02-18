package org.project.movie;

import org.project.image.Image;
import org.project.image.ImageDTO;
import org.project.image.ImageMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MovieMapperTest {

	@InjectMocks
	private MovieMapper movieMapper;

	@Mock
	private ImageMapper imageMapper;

	@Test
	public void toMovie () {
		MovieDTO movieDTO = new MovieDTO(0L, "A", 0, "A", List.of(
			new ImageDTO("0", new Byte[8]),
			new ImageDTO("1", new Byte[8])
		));

		Mockito.when(
			imageMapper.toImage(
				ArgumentMatchers.any(ImageDTO.class)
			)
		).thenReturn(
			new Image(null, UUID.randomUUID().toString(), new Byte[8])
		);

		Movie movie = movieMapper.toMovie(movieDTO);

		Assertions.assertNotNull(movie);
		Assertions.assertEquals(movieDTO.imdbId(),        movie.getImdbId());
		Assertions.assertEquals(movieDTO.title(),         movie.getTitle());
		Assertions.assertEquals(movieDTO.year(),          movie.getYear());
		Assertions.assertEquals(movieDTO.description(),   movie.getDescription());
		Assertions.assertEquals(movieDTO.images().size(), movie.getImages().size());
	}

	@Test
	public void toDTO () {
		Movie movie = new Movie(null, 0L, "A", 0, "A", List.of(
			new Image(null, "0", new Byte[8]),
			new Image(null, "1", new Byte[8])
		));

		Mockito.when(
			imageMapper.toDTO(
				ArgumentMatchers.any(Image.class)
			)
		).thenReturn(
			new ImageDTO(UUID.randomUUID().toString(), new Byte[8])
		);

		MovieDTO movieDTO = movieMapper.toDTO(movie);

		Assertions.assertNotNull(movie);
		Assertions.assertEquals(movie.getImdbId(),        movieDTO.imdbId());
		Assertions.assertEquals(movie.getTitle(),         movieDTO.title());
		Assertions.assertEquals(movie.getYear(),          movieDTO.year());
		Assertions.assertEquals(movie.getDescription(),   movieDTO.description());
		Assertions.assertEquals(movie.getImages().size(), movieDTO.images().size());
	}

}
