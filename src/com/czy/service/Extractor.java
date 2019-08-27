package com.czy.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Extractor {
	private static String jarpath;
	private static String destdir;

	public static void ExtractorWar(String a, String b) throws IOException {
		jarpath = a;
		destdir = b;
		JarFile jarfile;
		jarfile = new JarFile(jarpath);

		for (Enumeration<JarEntry> iter = jarfile.entries(); iter.hasMoreElements();) {
			JarEntry entry = iter.nextElement();
			File outfile = new File(destdir, entry.getName());
			if (!outfile.exists())
				outfile.getParentFile().mkdirs(); // create dir structure
			if (!entry.isDirectory()) {
				InputStream instream = jarfile.getInputStream(entry);
				FileOutputStream outstream = new FileOutputStream(outfile);
				while (instream.available() > 0) {
					outstream.write(instream.read());
				}
				outstream.close();
				instream.close();
			}
		}
		jarfile.close();
	}
}