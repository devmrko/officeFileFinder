package officeFileSearch;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import officeFileSearch.file.FileHandlingHelper;
import officeFileSearch.log.CustomedLogger;
import officeFileSearch.log.LogType;
import officeFileSearch.svc.ExcelFileFinderImpl;
import officeFileSearch.svc.OfficeFileFinder;
import officeFileSearch.svc.PPTFileFinderImpl;
import officeFileSearch.svc.WordFileFinderImpl;
import officeFileSearch.util.StringUtil;

public class App extends CommonAbstract {

	private static void usage(Options opts) {
		HelpFormatter hf = new HelpFormatter();
		String runProgram = "java " + App.class.getName() + " [options]";
		hf.printHelp(runProgram, opts);
	}

	public static void main(String args[]) {

		CustomedLogger.logger(LogType.INFO, getClassAndMethodNm(), "[MS office file finder]");

		String path = "";
		String[] searchWords = new String[0];

		CommandLineParser parser = new DefaultParser();

		Options opts = new Options();
		opts.addOption("p", "path", true, "location path to search");
		opts.addOption("s", "search word list", true, "list of search word, seperated by ':'");
		opts.addOption("h", "help", true, "help message");

		try {
			CommandLine cmd = parser.parse(opts, args);

			if (cmd.hasOption("h")) {
				usage(opts);
			}

			if (cmd.hasOption("p") && cmd.hasOption("s")) {
				path = cmd.getOptionValue("p");
				searchWords = cmd.getOptionValue("s").split(":");

				String[] fileType = { "docx", "doc", "ppt", "pptx", "xls", "xlsx" };

				CustomedLogger.logger(LogType.INFO, getClassAndMethodNm(),
						"search words: " + StringUtil.getStringByStringArray(searchWords));

				String[] fileList = FileHandlingHelper.getFileList(path, fileType);

				if (fileList != null) {

					String extension_str;
					OfficeFileFinder off = null;
					HashSet resultSet;

					for (String item : fileList) {
						// System.out.println("# current search file: " + item);
						// String[] returnMatchStentence;
						try {
							extension_str = FileHandlingHelper.getFileExtension(new File(item));
							if ("docx".equals(extension_str) || "doc".equals(extension_str)) {
								off = new WordFileFinderImpl(item, searchWords);

							} else if ("xlsx".equals(extension_str) || "xls".equals(extension_str)) {
								off = new ExcelFileFinderImpl(item, searchWords);

							} else if ("pptx".equals(extension_str) || "ppt".equals(extension_str)) {
								off = new PPTFileFinderImpl(item, searchWords);

							} else {
								return;
							}
							resultSet = off.getSearchedFile();
							Iterator i = resultSet.iterator();
							if(resultSet.size() > 0) {
								CustomedLogger.logger(LogType.INFO, getClassAndMethodNm(), "[Matched]: " + item);
								while (i.hasNext()) {
									CustomedLogger.logger(LogType.INFO, getClassAndMethodNm(), "[Matched sentence] " + i.next().toString());
								}
								
							} else {
								CustomedLogger.logger(LogType.INFO, getClassAndMethodNm(), "[Not Matched]: " + item);
							}
						} catch (Exception e) {
							CustomedLogger.logger(LogType.ERROR, getClassAndMethodNm(), item + ": format does not match.");
						}
					}
				}

			} else {
				usage(opts);
			}

		} catch (ParseException e) {
			usage(opts);
			return;
		}

	}

}
