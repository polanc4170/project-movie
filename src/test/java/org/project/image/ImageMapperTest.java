package org.project.image;

import org.project.utils.ObjectGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImageMapperTest {

	@InjectMocks
	private ImageMapper imageMapper;

	@Test
	public void toImage () {
		ImageDTO imageDTO = ObjectGenerator.randomImageDTO();
		Image    image    = imageMapper.toImage(imageDTO);

		Assertions.assertNotNull(image);
		Assertions.assertEquals(imageDTO.uuid(),   image.getUuid());
		Assertions.assertEquals(imageDTO.imdbId(), image.getImdbId());
		Assertions.assertEquals(imageDTO.bytes(),  image.getBytes());
	}

	@Test
	public void toDTO () {
		Image    image    = ObjectGenerator.randomImage();
		ImageDTO imageDTO = imageMapper.toDTO(image);

		Assertions.assertNotNull(imageDTO);
		Assertions.assertEquals(image.getUuid(),   imageDTO.uuid());
		Assertions.assertEquals(image.getImdbId(), imageDTO.imdbId());
		Assertions.assertEquals(image.getBytes(),  imageDTO.bytes());
	}

}
