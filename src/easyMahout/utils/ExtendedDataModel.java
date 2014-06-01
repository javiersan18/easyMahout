package easyMahout.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;

import org.apache.commons.lang.StringUtils;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.common.iterator.FileLineIterable;

public final class ExtendedDataModel extends FileDataModel {

	private static final long serialVersionUID = 1L;

	private static String COLON_DELIMTER = ",";

	private static Pattern COLON_DELIMITER_PATTERN = Pattern.compile(COLON_DELIMTER);

	// public ExtendedDataModel() throws IOException {
	// this(readResourceToTempFile("/org/apache/mahout/cf/taste/example/grouplens/ratings.dat"),
	// COLON_DELIMTER);
	// }

	/**
	 * @param file
	 *            , delimiter File may be in different formats, value delimiter
	 *            value delimiter value delimiter ... Delimiter may be the
	 *            commons "," "::" ";" etc.
	 * @throws IOException
	 *             if an error occurs while reading or writing files
	 */
	public ExtendedDataModel(File ratingsFile, String delimiter) throws IOException {
		super(convertGLFile(ratingsFile, delimiter));
	}

	private static File convertGLFile(File originalFile, String delimiter) throws IOException {

		if (delimiter.equals(","))
			return originalFile;
		else {
			// Change used delimiter
			COLON_DELIMTER = delimiter;
			COLON_DELIMITER_PATTERN = Pattern.compile(COLON_DELIMTER);

			// Now translate the file; remove commas, then convert delimiter to
			// comma
			String filename = originalFile.getName();
			File resultFile = new File(new File(System.getProperty("java.io.tmpdir")), filename);
			if (resultFile.exists()) {
				resultFile.delete();
			}
			Writer writer = null;
			try {
				writer = new OutputStreamWriter(new FileOutputStream(resultFile), Charsets.UTF_8);
				for (String line : new FileLineIterable(originalFile, false)) {
					// Avoid errors when there are empty lines.
					if (StringUtils.isBlank(line))
						continue;
					int lastDelimiterStart = line.lastIndexOf(COLON_DELIMTER);
					if (lastDelimiterStart < 0) {
						throw new IOException("Unexpected input format on line: (" + line + "). Possibly delimiter doesnt match. ("
								+ delimiter + ")");
					}
					String subLine = line.substring(0, lastDelimiterStart);
					String convertedLine = COLON_DELIMITER_PATTERN.matcher(subLine).replaceAll(",");
					writer.write(convertedLine);
					writer.write('\n');
				}
			} catch (IOException ioe) {
				resultFile.delete();
				throw ioe;
			} finally {
				Closeables.close(writer, false);
			}
			return resultFile;
		}
	}

	// public static File readResourceToTempFile(String resourceName) throws
	// IOException {
	// InputSupplier<? extends InputStream> inSupplier;
	// try {
	// URL resourceURL = Resources.getResource(ExtendedDataModel.class,
	// resourceName);
	// inSupplier = Resources.newInputStreamSupplier(resourceURL);
	// } catch (IllegalArgumentException iae) {
	// File resourceFile = new File("src/main/java" + resourceName);
	// inSupplier = Files.newInputStreamSupplier(resourceFile);
	// }
	// File tempFile = File.createTempFile("taste", null);
	// tempFile.deleteOnExit();
	// Files.copy(inSupplier, tempFile);
	// return tempFile;
	// }

	@Override
	public String toString() {
		return "ExtendedDataModel";
	}

}
