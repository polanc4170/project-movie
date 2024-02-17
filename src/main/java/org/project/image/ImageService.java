package org.project.image;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository repository;
	private final ImageMapper     mapper;

	//
	// Create
	//

	public void addImage (ImageDTO image) {
		Optional<Image> dbOptImage = repository.findByUuid(image.uuid());

		if (dbOptImage.isPresent()) {
			throw new ImageAlreadyExistsException(
				"Image UUID '%s' already exists.".formatted(image.uuid())
			);
		}

		repository.save(mapper.toImage(image));
	}

	//
	// Read
	//

	public List<ImageDTO> getImages () {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public ImageDTO getImageById (String id) {
		Optional<Image> dbOptImage = repository.findByUuid(id);

		if (dbOptImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%s' does not exist.".formatted(id)
			);
		}

		return mapper.toDTO(dbOptImage.get());
	}

	//
	// Update
	//

	public ImageDTO updateImageById (String id, ImageDTO image) {
		Optional<Image> dbOptImage = repository.findByUuid(id);

		if (dbOptImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%s' does not exist.".formatted(id)
			);
		}

		Image dbImage = dbOptImage.get();

		if (image.bytes() != null && image.bytes().length > 0) {
			dbImage.setBytes(image.bytes());
		}

		return mapper.toDTO(repository.save(dbImage));
	}

	//
	// Delete
	//

	public void deleteImages () {
		repository.deleteAll();
	}

	public void deleteImageById (String id) {
		Optional<Image> dbOptImage = repository.findByUuid(id);

		if (dbOptImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%s' does not exist.".formatted(id)
			);
		}

		repository.delete(dbOptImage.get());
	}

}
