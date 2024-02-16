package org.project.image;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping(path = "")
	public List<ImageDTO> getAllImages () {
		return service.getAllImages()
			.stream()
			.map(mapper::toDTO)
			.toList();
	}

	@GetMapping(path = "view")
	public ImageDTO getImageByUUID (@RequestParam(name = "id") String id) {
		return mapper.toDTO(service.getImageByUUID(id));
	}

	@PostMapping(path = "")
	public void addNewImage (@RequestBody ImageDTO image) {
		service.addNewImage(mapper.toImage(image));
	}

}
