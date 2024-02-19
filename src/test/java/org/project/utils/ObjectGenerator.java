package org.project.utils;

import org.project.image.Image;
import org.project.image.ImageDTO;
import org.project.movie.Movie;
import org.project.movie.MovieDTO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public final class ObjectGenerator {

	private static final UniformRandomProvider random = RandomSource.XO_RO_SHI_RO_128_PP.create();

	private ObjectGenerator () {}

	private static Long randomID () {
		return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
	}

	private static String randomTitle () {
		return RandomStringUtils.randomAlphabetic(8, 16);
	}

	private static String randomDescription () {
		return RandomStringUtils.randomAlphabetic(32, 64);
	}

	private static Integer randomYear () {
		return random.nextInt(1970, 2020);
	}

	private static Byte [] randomBytes () {
		byte [] bytes = new byte[8];

		random.nextBytes(bytes);

		return ArrayUtils.toObject(bytes);
	}

	private static List<Image> randomImages () {
		return Stream.generate(
			ObjectGenerator::randomImage
		).limit(
			random.nextInt(1, 5)
		).toList();
	}

	private static List<ImageDTO> randomImageDTOs () {
		return Stream.generate(
			ObjectGenerator::randomImageDTO
		).limit(
			random.nextInt(1, 5)
		).toList();
	}

	public static MovieDTO randomMovieDTO () {
		return new MovieDTO(
			randomID(),
			randomTitle(),
			randomYear(),
			randomDescription(),
			randomImageDTOs()
		);
	}

	public static Movie randomMovie () {
		return new Movie(
			null,
			randomID(),
			randomTitle(),
			randomYear(),
			randomDescription(),
			randomImages()
		);
	}

	public static Image randomImage () {
		return new Image(
			null,
			randomID(),
			randomID(),
			randomBytes()
		);
	}

	public static ImageDTO randomImageDTO () {
		return new ImageDTO(
			randomID(),
			randomID(),
			randomBytes()
		);
	}

	public static MovieDTO of (Movie movie) {
		return new MovieDTO(
			movie.getImdbId(),
			movie.getTitle(),
			movie.getYear(),
			movie.getDescription(),
			movie.getImages().stream().map(
				ObjectGenerator::of
			).toList()
		);
	}

	public static Movie of (MovieDTO movieDTO) {
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
	}

	public static ImageDTO of (Image image) {
		return new ImageDTO(
			image.getUuid(),
			image.getImdbId(),
			image.getBytes()
		);
	}

	public static Image of (ImageDTO imageDTO) {
		return new Image(
			null,
			imageDTO.uuid(),
			imageDTO.imdbId(),
			imageDTO.bytes()
		);
	}

}
