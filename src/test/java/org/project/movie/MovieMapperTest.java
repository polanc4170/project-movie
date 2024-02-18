package org.project.movie;

import org.project.ObjectGenerator;
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

@ExtendWith(MockitoExtension.class)
public class MovieMapperTest {

	@InjectMocks
	private MovieMapper movieMapper;

	@Mock
	private ImageMapper imageMapper;

	@Test
	public void toMovie () {
		MovieDTO movieDTO = ObjectGenerator.randomMovieDTO();

		Mockito.when(
			imageMapper.toImage(
				ArgumentMatchers.any(ImageDTO.class)
			)
		).thenReturn(
			ObjectGenerator.randomImage()
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
		Movie movie = ObjectGenerator.randomMovie();

		Mockito.when(
			imageMapper.toDTO(
				ArgumentMatchers.any(Image.class)
			)
		).thenReturn(
			ObjectGenerator.randomImageDTO()
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
