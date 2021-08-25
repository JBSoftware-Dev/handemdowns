package com.handemdowns.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IThumbnailService {
	BufferedImage crop(byte[] byteArr, int newW, int newH) throws IOException;
	BufferedImage resize(byte[] byteArr, int newW, int newH) throws IOException;
}