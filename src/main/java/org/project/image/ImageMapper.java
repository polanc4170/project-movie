package org.project.image;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class ImageMapper {

	public ImageDTO toDTO (Image image) {
		return new ImageDTO(
			image.getUuid(),
			image.getBytes()
		);
	}

	public Image toImage (ImageDTO dto) {
		Image image = new Image();

		image.setUuid(dto.uuid());
		image.setBytes(dto.bytes());

		return image;
	}

}
