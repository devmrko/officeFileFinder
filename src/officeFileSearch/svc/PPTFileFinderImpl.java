package officeFileSearch.svc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

public class PPTFileFinderImpl implements OfficeFileFinder {

	String filePath;
	String[] searchWords;

	public PPTFileFinderImpl(String filePath, String[] searchWords) {
		super();
		this.filePath = filePath;
		this.searchWords = searchWords;
	}

	@Override
	public HashSet<String> getSearchedFile() throws InvalidFormatException, IOException {
		HashSet<String> returnSet = new HashSet<String>();
		XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(filePath));
		for (XSLFSlide slide : ppt.getSlides()) {
			List<XSLFShape> runs = slide.getShapes();
			if (runs != null) {
				for (XSLFShape r : runs) {
					if (r instanceof XSLFTextShape) {
						XSLFTextShape textShape = (XSLFTextShape) r;
						String text = textShape.getText();
						for (String s : searchWords) {
							if (text != null && text.toLowerCase().contains(s.toLowerCase())) {
								returnSet.add(text);
							}

						}
					}
				}
			}
		}
		return returnSet;
	}

	public List<String> getSearchResultByPpt(String fileLocation, String searchWord)
			throws InvalidFormatException, IOException {
		List<String> returnMatchSentenceList = new ArrayList<String>();

		@SuppressWarnings("resource")
		XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(fileLocation));
		for (XSLFSlide s : ppt.getSlides()) {
			List<XSLFShape> runs = s.getShapes();
			if (runs != null) {
				for (XSLFShape r : runs) {
					if (r instanceof XSLFTextShape) {
						XSLFTextShape textShape = (XSLFTextShape) r;
						String text = textShape.getText();
						if (text != null && text.toLowerCase().contains(searchWord.toLowerCase())) {
							returnMatchSentenceList.add(text);
						}
					}
				}
			}
		}
		return returnMatchSentenceList;
	}

}
