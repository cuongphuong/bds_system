package com.sys.pp.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
	public static boolean isEmpty(Path path) throws IOException {
	    if (Files.isDirectory(path)) {
	        try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
	            return !directory.iterator().hasNext();
	        }
	    }
	    return false;
	}
}
