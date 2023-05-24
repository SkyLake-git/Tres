package com.tres.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

	/**
	 * hacky implementation
	 */
	public static Path getResourceFolder(String dummyFilePath) {
		URL url = ClassLoader.getSystemResource(dummyFilePath);

		Path path;
		try {
			path = Paths.get(url.toURI());
		} catch (URISyntaxException e) {
			path = Paths.get(url.getPath());
		}

		return path.getParent();
	}

	public static Stream<Path> safeWalk(Path folder) {
		try {
			return Files.walk(folder);
		} catch (IOException e) {
			e.printStackTrace();
			return Stream.empty();
		}
	}

	public static List<Path> glob(Path folder) {
		return safeWalk(folder).filter(Files::isRegularFile).collect(Collectors.toList());
	}
}
