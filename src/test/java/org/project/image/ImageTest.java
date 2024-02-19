package org.project.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImageTest {

	@Test
	public void createImage_ArgsNone () {
		Image image = new Image();

		Assertions.assertNotNull(image);
		Assertions.assertNull(image.getId());
		Assertions.assertNull(image.getUuid());
		Assertions.assertNull(image.getImdbId());
		Assertions.assertNull(image.getBytes());
	}

	@Test
	public void createImage_ArgsAll () {
		Image image = new Image(null, 0L, 0L, new Byte[8]);

		Assertions.assertNotNull(image);
		Assertions.assertNull(image.getId());
		Assertions.assertNotNull(image.getUuid());
		Assertions.assertNotNull(image.getImdbId());
		Assertions.assertNotNull(image.getBytes());

		Assertions.assertEquals(0, image.getUuid());
		Assertions.assertEquals(0, image.getImdbId());
		Assertions.assertEquals(8, image.getBytes().length);
	}

	@Test
	public void createMovie_Lombok () {
		Image image = new Image();

		image.setId(null);
		image.setUuid(0L);
		image.setImdbId(0L);
		image.setBytes(new Byte[8]);

		Assertions.assertNotNull(image);
		Assertions.assertNull(image.getId());
		Assertions.assertNotNull(image.getUuid());
		Assertions.assertNotNull(image.getImdbId());
		Assertions.assertNotNull(image.getBytes());

		Assertions.assertEquals(0, image.getUuid());
		Assertions.assertEquals(0, image.getImdbId());
		Assertions.assertEquals(8, image.getBytes().length );
	}

}
