package org.project.image;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository repository;

	public List<Image> getAllImages () {
		return repository.findAll();
	}

	public Image getImageByUUID (String id) {
		Optional<Image> dbImage = repository.findByUUID(id);

		if (dbImage.isEmpty()) {
			throw new ImageNotFoundException(
				"Image UUID '%s' does not exist.".formatted(id)
			);
		}

		return dbImage.get();
	}

	public void addNewImage (Image image) {
		Optional<Image> dbImage = repository.findByUUID(image.getUuid());

		if (dbImage.isPresent()) {
			throw new ImageAlreadyExistsException(
				"Image UUID '%s' already exists.".formatted(image.getUuid())
			);
		}

		repository.save(image);
	}

}
