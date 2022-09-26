package client.ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageManager {

	private static int id = 0;

	private static final HashMap<Integer, BufferedImage> images;

	private static final HashMap<String, Integer> idCache;

	static {
		images = new HashMap<Integer, BufferedImage>();
		idCache = new HashMap<String, Integer>();
	}

	public static BufferedImage getImage(int id) {
		return images.get(id);
	}

	public static int readImage(String filename) {
		Integer cache = null;
		if ((cache = idCache.get(filename)) != null) {
			return cache.intValue();
		}

		BufferedImage image;
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

		int nextId = ImageManager.id++;

		ImageManager.images.put(nextId, image);
		ImageManager.idCache.put(filename, nextId);

		return nextId;
	}
}
