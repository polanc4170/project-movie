package org.project.movie;

import org.project.image.Image;
import org.project.image.ImageAction;
import org.project.image.exception.ImageActionNotSupportedException;
import org.project.image.ImageDTO;
import org.project.image.ImageService;
import org.project.movie.exception.MovieAlreadyExistsException;
import org.project.movie.exception.MovieNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {

	private final MovieRepository movieRepository;
	private final MovieMapper     movieMapper;
	private final ImageService    imageService;

	//
	// Create
	//

	@Transactional
	public void addMovie (MovieDTO movie) {
		Optional<Movie> dbOptMovie = movieRepository.findByImdbId(movie.imdbId());

		if (dbOptMovie.isPresent()) {
			throw new MovieAlreadyExistsException(
				"Movie IMDB_ID '%d' already exists.".formatted(movie.imdbId())
			);
		}

		movie.images().forEach(
			imageService::addImage
		);

		movieRepository.save(movieMapper.toMovie(movie));
	}

	//
	// Read
	//

	private Page<MovieDTO> getMoviesByPage (Map<String, String> parameters) {
		return movieRepository.findAll(
			PageRequest.of(
				Integer.parseInt(parameters.getOrDefault("page", "0")),
				Integer.parseInt(parameters.getOrDefault("size", "10"))
			)
		).map(movieMapper::toDTO);
	}

	private List<MovieDTO> getMoviesByFilter (Map<String, String> parameters) {
		List<Movie> movies;
		String      pattern = null;

		if (parameters.containsKey("pattern")) {
			pattern = parameters.get("pattern");
		}

		if (parameters.containsKey("startYear") || parameters.containsKey("endYear")) {
			Integer startYear = Integer.parseInt(parameters.getOrDefault("startYear",    "0"));
			Integer endYear   = Integer.parseInt(parameters.getOrDefault(  "endYear", "9999"));

			if (pattern != null) movies = movieRepository.findByPatternAndYear(pattern, startYear, endYear);
			else                 movies = movieRepository.findByYear(startYear, endYear);
		}
		else {
			if (pattern != null) movies = movieRepository.findByPattern(pattern);
			else                 movies = movieRepository.findAll();
		}

		return movies.stream().map(movieMapper::toDTO).toList();
	}

	public Iterable<MovieDTO> getMovies (Map<String, String> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			if (parameters.containsKey("page")) {
				return getMoviesByPage(parameters);
			}

			return getMoviesByFilter(parameters);
		}

		return movieRepository.findAll().stream().map(movieMapper::toDTO).toList();
	}

	public MovieDTO getMovieByImdbId (Long imdbId) {
		Optional<Movie> dbOptMovie = movieRepository.findByImdbId(imdbId);

		if (dbOptMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(imdbId)
			);
		}

		return movieMapper.toDTO(dbOptMovie.get());
	}

	//
	// Update
	//

	private void updateMovieImagesWithReplace (Movie movie, List<ImageDTO> imageDTOs) {
		imageService.deleteImagesByImdbId(movie.getImdbId());

		List<Image> array = new ArrayList<>(imageDTOs.size());

		imageDTOs.forEach(
			(imageDTO) -> array.add(imageService.addImage(imageDTO))
		);

		movie.setImages(array);
	}

	private void updateMovieImagesWithAdd (Movie movie, List<ImageDTO> imageDTOs) {
		List<Image> images = new ArrayList<>(
			movie.getImages()
		);

		images.addAll(
			imageDTOs.stream().map(
				imageService::addImage
			).toList()
		);

		movie.setImages(images);
	}

	@Transactional
	public MovieDTO updateMovieByImdbId (Long imdbId, MovieDTO movie, ImageAction imageAction) {
		Optional<Movie> dbOptMovie = movieRepository.findByImdbId(imdbId);

		if (dbOptMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(imdbId)
			);
		}

		Movie dbMovie = dbOptMovie.get();

		if (movie.title() != null && !movie.title().equals(dbMovie.getTitle())) {
			dbMovie.setTitle(movie.title());
		}

		if (movie.year() != null && !movie.year().equals(dbMovie.getYear())) {
			dbMovie.setYear(movie.year());
		}

		if (movie.description() != null && !movie.description().equals(dbMovie.getDescription())) {
			dbMovie.setDescription(movie.description());
		}

		if (movie.images() != null && !movie.images().isEmpty()) {
			switch (imageAction) {
				case REPLACE -> updateMovieImagesWithReplace(dbMovie, movie.images());
				case ADD     -> updateMovieImagesWithAdd(dbMovie, movie.images());
				default      -> throw new ImageActionNotSupportedException(
					"Image ACTION '%s' is not supported".formatted(imageAction.name())
				);
			}
		}

		return movieMapper.toDTO(movieRepository.save(dbMovie));
	}

	@Transactional
	public MovieDTO updateMovieByImdbId (Long imdbId, MovieDTO movie) {
		return updateMovieByImdbId(imdbId, movie, ImageAction.ADD);
	}

	//
	// Delete
	//

	@Transactional
	public void deleteMovies () {
		imageService.deleteImages();
		movieRepository.deleteAll();
	}

	@Transactional
	public void deleteMovieByImdbId (Long imdbId) {
		Optional<Movie> dbOptMovie = movieRepository.findByImdbId(imdbId);

		if (dbOptMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(imdbId)
			);
		}

		Movie dbMovie = dbOptMovie.get();

		imageService.deleteImagesByImdbId(dbMovie.getImdbId());
		movieRepository.delete(dbMovie);
	}

}
