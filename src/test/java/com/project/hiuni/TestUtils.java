package com.project.hiuni;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.mock.web.MockMultipartFile;

public class TestUtils {

	public static MockMultipartFile getMockImageFile(String formatName, String fileName, String contentType)
		throws IOException {
		BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 100; x++) {
				image.setRGB(x, y, new Color(x * 2 % 256, y * 2 % 256, (x + y) % 256).getRGB());
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, formatName, baos);
		byte[] imageBytes = baos.toByteArray();

		return new MockMultipartFile(
			"file",
			fileName,
			contentType,
			imageBytes
		);
	}
}
