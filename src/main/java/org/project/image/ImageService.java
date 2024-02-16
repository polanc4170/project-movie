package org.project.image;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository repository;

	//
	// Create
	//

	public void addImage (Image image) {
		Optional<Image> dbImage = repository.findByUuid(image.getUuid());

		if (dbImage.isPresent()) {
			throw new ImageAlreadyExistsException(
				"Image UUID '%s' already exists.".formatted(image.getUuid())
			);
		}

		repository.save(image);
	}

	//
	// Read
	//

	public List<Image> getAllImages () {
		return repository.findAll();
	}

	public Image getImageByUuid (String id) {
		Optional<Image> dbImage = repository.findByUuid(id);

		if (dbImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%s' does not exist.".formatted(id)
			);
		}

		return dbImage.get();
	}

	//
	// Update
	//

	public Image updateImage (Image image) {
		Optional<Image> dbImage = repository.findByUuid(image.getUuid());

		if (dbImage.isEmpty()) {
			throw new ImageAlreadyExistsException(
				"Image UUID '%s' does not exist.".formatted(image.getUuid())
			);
		}

		dbImage.get().setBytes(image.getBytes());

		return repository.save(dbImage.get());
	}

	//
	// Delete
	//

	public void deleteImageByUuid (String id) {
		Optional<Image> dbImage = repository.findByUuid(id);

		if (dbImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%s' does not exist.".formatted(id)
			);
		}

		repository.delete(dbImage.get());
	}

}
