/**
 * 
 */
package io.github.sidmishraw.autobot.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.ContentHandler;

import com.google.gson.Gson;

/**
 * @author sidmishraw
 * 
 *         Main driver of the Autobot project. This project will use Apache Tika
 *         and extract Author information from the PDFs fed into this.
 *
 */
public class AutobotDriver {

	/**
	 * Main entry point of the Autobot application
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("-----------------------------------------");
		System.out.println("---- AUTOBOT - PDF - INFO - EXTRACTER ---");
		System.out.println("-----------------------------------------");
		System.out.println("------- sidmishraw ----------------------");
		System.out.println("Conf file 	= " + args[0]);
		System.out.println("Outputs 	= " + args[1]);

		List<File> pdfFiles = readInputFilePaths(args[0]);

		System.setProperty("outputs", args[1]);

		processFiles(pdfFiles);
	}

	/**
	 * Fetches the list of all the PDF files
	 * 
	 * @param configurationFilePath
	 *            the configuration file path
	 * 
	 * @return the list of PDF files according to the configuration file
	 */
	private static List<File> readInputFilePaths(String configurationFilePath) {

		List<File> pdfFiles = new ArrayList<>();

		String pdfFilePath = null;

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(Paths.get(configurationFilePath).toFile())))) {

			while (null != (pdfFilePath = br.readLine())) {

				pdfFiles.add(Paths.get(pdfFilePath).toFile());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return pdfFiles;
	}

	/**
	 * Processes the PDF files using Apache tika and Jsoup for extracting
	 * information out of it.
	 * 
	 * @param pdfFiles
	 *            the list of PDF files
	 */
	private static void processFiles(List<File> pdfFiles) {

		pdfFiles.forEach(pdf -> {

			System.out.println(pdf.toString());

			AutoDetectParser parser = new AutoDetectParser();
			ContentHandler contentHandler = new ToHTMLContentHandler();
			Metadata metadata = new Metadata();

			try (InputStream is = new FileInputStream(pdf)) {

				parser.parse(is, contentHandler, metadata);

				String htmlString = contentHandler.toString();

				Document htmlDocument = Jsoup.parse(htmlString);

				Elements ptags = htmlDocument.getElementsByTag("p");

				StringBuffer sBuffer = new StringBuffer();

				int abstractIndex = 0;

				while (abstractIndex < ptags.size()
						&& !"abstract".equalsIgnoreCase(ptags.get(abstractIndex).text().trim())) {

					String text = ptags.get(abstractIndex).text().trim();

					if (null != text && !text.isEmpty()) {

						sBuffer.append(text).append("\n");
					}

					abstractIndex++;
				}

				// System.out.println("METADATA::" + sBuffer.toString());

				String title = sBuffer.toString().split("\n")[0].trim();

				String[] authorString = sBuffer.toString().substring(sBuffer.toString().indexOf("\n") + 1).trim()
						.split("\n");

				sBuffer.setLength(0);

				while (abstractIndex < ptags.size()) {

					String text = ptags.get(abstractIndex).text().trim();

					if (null != text && !text.isEmpty()) {

						sBuffer.append(text).append("\n");
					}

					abstractIndex++;
				}

				// System.out.println("CONTENT::" + sBuffer.toString());

				String content = sBuffer.toString().trim();

				sBuffer.setLength(0);

				String jsonString = new Gson().toJson(new AutobotDoc(title, authorString, content));

				try (FileWriter writer = new FileWriter(
						Paths.get(System.getProperty("outputs"), pdf.getName().split("\\.")[0] + ".json").toFile())) {

					writer.write(jsonString);
				} catch (Exception e) {

					e.printStackTrace();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		});
	}
}
