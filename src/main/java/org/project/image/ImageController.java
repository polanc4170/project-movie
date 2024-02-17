package org.project.image;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "images")
public class ImageController {

	private final ImageService service;

	//
	// Create
	//

	@PostMapping(path = "")
	public void addImage (@RequestBody ImageDTO image) {
		service.addImage(image);
	}

	//
	// Read
	//

	@GetMapping(path = "")
	public ResponseEntity<?> getImages () {
		return ResponseEntity.ok(service.getImages());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getImageById (@PathVariable String id) {
		return ResponseEntity.ok(service.getImageById(id));
	}

	//
	// Update
	//

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateImageById (@PathVariable String id, @RequestBody ImageDTO image) {
		return ResponseEntity.ok(service.updateImageById(id, image));
	}

	//
	// Delete
	//

	@DeleteMapping(path = "")
	public void deleteImages () {
		service.deleteImages();
	}

	@DeleteMapping(path = "/{id}")
	public void deleteImageById (@PathVariable String id) {
		service.deleteImageById(id);
	}

}
