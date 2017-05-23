package officeFileSearch.svc;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordFileFinderImpl implements OfficeFileFinder {

	String filePath;
	String[] searchWords;

	public WordFileFinderImpl(String filePath, String[] searchWords) {
		super();
		this.filePath = filePath;
		this.searchWords = searchWords;
	}

	@Override
	public HashSet<String> getSearchedFile() throws InvalidFormatException, IOException {
		HashSet<String> returnSet = new HashSet<String>();
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(filePath));
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					for (String s : searchWords) {
						if (text != null && text.toLowerCase().contains(s.toLowerCase())) {
							returnSet.add(text);
						}
					}
				}
			}
		}
		return returnSet;
	}

}
