package com.handemdowns.service.impl;

import com.handemdowns.service.IThumbnailService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ThumbnailService implements IThumbnailService {
	@Override
	public BufferedImage crop(byte[] byteArr, int newW, int newH) throws IOException {
		InputStream in = new ByteArrayInputStream(byteArr);
		BufferedImage originalImage = ImageIO.read(in);
		double imageWidth = originalImage.getWidth();
		double imageHeight = originalImage.getHeight();


		double ratio_w = 1, ratio_h = 1;
		if(imageWidth > newW) {
			ratio_w = newW / imageWidth;
		}
		if(imageHeight > newH) {
			ratio_h = newH / imageHeight;
		}
		double ratio = Math.min(ratio_w, ratio_h);
		imageWidth = Math.floor(imageWidth * ratio);
		imageHeight = Math.floor(imageHeight * ratio);

		return originalImage.getSubimage(0, 0, (int)imageWidth, (int)imageHeight);
	}

	@Override
	public BufferedImage resize(byte[] byteArr, int newW, int newH) throws IOException {
		InputStream in = new ByteArrayInputStream(byteArr);
		BufferedImage originalImage = ImageIO.read(in);
		return Thumbnails.of(originalImage).size(newW, newH).asBufferedImage();
	}
}