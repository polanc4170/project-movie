package org.project.image;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "images")
public class ImageController {

	private final ImageService service;
	private final ImageMapper  mapper;

	//
	// Create
	//

	@PostMapping(path = "")
	public void addImage (@RequestBody ImageDTO image) {
		service.addImage(mapper.toImage(image));
	}

	//
	// Read
	//

	@GetMapping(path = "")
	public List<ImageDTO> getAllMovies () {
		return service.getAllImages()
			.stream()
			.map(mapper::toDTO)
			.toList();
	}

	@GetMapping(path = "view")
	public ImageDTO getImageByUuid (@RequestParam(name = "id") String id) {
		return mapper.toDTO(service.getImageByUuid(id));
	}

	//
	// Update
	//

	@PutMapping(path = "")
	public ImageDTO updateImage (@RequestBody ImageDTO image) {
		return mapper.toDTO(service.updateImage(mapper.toImage(image)));
	}

	//
	// Delete
	//

	@DeleteMapping(path = "")
	public void deleteImageByUuid (@RequestParam(name = "id") String id) {
		service.deleteImageByUuid(id);
	}

}
