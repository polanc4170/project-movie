package org.project.image;

import org.project.image.exception.ImageAlreadyExistsException;
import org.project.image.exception.ImageNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@SuppressWarnings("unused")
public class ImageService {

	private final ImageRepository repository;
	private final ImageMapper     mapper;

	//
	// Create
	//

	public Image addImage (ImageDTO image) {
		Optional<Image> dbOptImage = repository.findByUuid(image.uuid());

		if (dbOptImage.isPresent()) {
			throw new ImageAlreadyExistsException(
				"Image UUID '%d' already exists.".formatted(image.uuid())
			);
		}

		return repository.save(mapper.toImage(image));
	}

	//
	// Read
	//

	public Image getImageByUuid (Long uuid, Long imdbId) {
		Optional<Image> dbOptImage = repository.findByUuid(uuid);

		if (dbOptImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%d' does not exist.".formatted(uuid)
			);
		}

		Image image = dbOptImage.get();

		if (imdbId != null) {
			if (!imdbId.equals(image.getImdbId())) {
				throw new ImageNotFoundException(
					"Image IMDB_ID '%d' does not match.".formatted(imdbId)
				);
			}
		}

		return image;
	}

	public Image getImageByUuid (Long uuid) {
		return getImageByUuid(uuid, null);
	}

	//
	// Update
	//

	public Image updateImageByUuid (Long uuid, Long imdbId, ImageDTO image) {
		Optional<Image> dbOptImage = repository.findByUuid(uuid);

		if (dbOptImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%d' does not exist.".formatted(uuid)
			);
		}

		Image dbImage = dbOptImage.get();

		if (imdbId != null) {
			if (!imdbId.equals(image.imdbId())) {
				throw new ImageNotFoundException(
					"Image IMDB_ID '%d' does not match.".formatted(imdbId)
				);
			}
		}

		if (image.bytes() != null && image.bytes().length > 0) {
			dbImage.setBytes(image.bytes());
		}

		return repository.save(dbImage);
	}

	public Image updateImageByUuid (Long uuid, ImageDTO image) {
		return updateImageByUuid(uuid, null, image);
	}

	//
	// Delete
	//

	public void deleteImages () {
		repository.deleteAll();
	}

	public void deleteImagesByImdbId (Long imdbId) {
		repository.deleteAllByImdb(imdbId);
	}

	public void deleteImageByUuid (Long uuid, Long imdbId) {
		Optional<Image> dbOptImage = repository.findByUuid(uuid);

		if (dbOptImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%s' does not exist.".formatted(uuid)
			);
		}

		Image dbImage = dbOptImage.get();

		if (imdbId != null) {
			if (!imdbId.equals(dbImage.getImdbId())) {
				throw new ImageNotFoundException(
					"Image IMDB_ID '%d' does not match.".formatted(imdbId)
				);
			}
		}

		repository.delete(dbOptImage.get());
	}

	public void deleteImageByUuid (Long uuid) {
		deleteImageByUuid(uuid, null);
	}

}
