package officeFileSearch.svc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import officeFileSearch.file.FileHandlingHelper;

public class ApachePOIHelper {
	/**
	 * <B>Project Name : </B>officeFileSearch<br/>
	 * <B>Package Name : </B>officeFileSearch<br/>
	 * <B>File Name : </B>ApachePOIHelper<br/>
	 * <B>Description</B>
	 * <ul>
	 * <li>여기에 해당 클래스에 대한 개요를 입력합니다.
	 * <li>논리적으로 내용이 분리되는 경우 li 태그를 사용하여 개행 합니다.
	 * </ul>
	 * 
	 * @author jmko7
	 * @since Apr 21, 2017
	 */

	public List<String> getSearchResultByDoc(String fileLocation, String searchWord)
			throws InvalidFormatException, IOException {
		List<String> returnMatchSentenceList = new ArrayList<String>();
		@SuppressWarnings("resource")
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(fileLocation));
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					if (text != null && text.toLowerCase().contains(searchWord.toLowerCase())) {
						returnMatchSentenceList.add(text);
					}
				}
			}
		}
		return returnMatchSentenceList;
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

	@SuppressWarnings("deprecation")
	public List<String> getSearchResultByXls(String fileLocation, String searchWord)
			throws InvalidFormatException, IOException {
		List<String> returnMatchSentenceList = new ArrayList<String>();

		FileInputStream excelFile = new FileInputStream(new File(fileLocation));
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(excelFile);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

			Iterator<Row> iterator = workbook.getSheetAt(i).iterator();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					switch (currentCell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						String text = currentCell.getStringCellValue();
						if (text != null && text.toLowerCase().contains(searchWord.toLowerCase())) {
							returnMatchSentenceList.add(text);
						}
						break;
					}

				}

			}
		}
		return returnMatchSentenceList;
	}

	public String[] getMatchSentence(String fileLocation, String[] searchWords)
			throws InvalidFormatException, IOException {

		List<String> returnMatchSentenceList = new ArrayList<String>();
		boolean[] matchBools = new boolean[searchWords.length];
		boolean wholeMatchBool = true;
		for (int i = 0; i < searchWords.length; i++) {
			matchBools[i] = false;
		}

		List<String> curList = null;
		String extenstion = "";
		try {
			extenstion = FileHandlingHelper.getFileExtension(new File(fileLocation));
			if (extenstion.toLowerCase().contains("doc") || extenstion.toLowerCase().contains("docx")) {
				for (int i = 0; i < searchWords.length; i++) {
					curList = getSearchResultByDoc(fileLocation, searchWords[i]);
					if (curList.size() > 0) {
						returnMatchSentenceList.addAll(curList);
						matchBools[i] = true;
					}
				}
			} else if (extenstion.toLowerCase().contains("ppt") || extenstion.toLowerCase().contains("pptx")) {
				for (int i = 0; i < searchWords.length; i++) {
					curList = getSearchResultByPpt(fileLocation, searchWords[i]);
					if (curList.size() > 0) {
						returnMatchSentenceList.addAll(curList);
						matchBools[i] = true;
					}
				}
			} else if (extenstion.toLowerCase().contains("xls") || extenstion.toLowerCase().contains("xlsx")) {
				for (int i = 0; i < searchWords.length; i++) {
					curList = getSearchResultByXls(fileLocation, searchWords[i]);
					if (curList.size() > 0) {
						returnMatchSentenceList.addAll(curList);
						matchBools[i] = true;
					}
				}
			}

		} catch (Exception e) {
			// System.out.println(">>> " + fileLocation + " is not a suitable
			// file.");
			wholeMatchBool = false;
		}

		for (int i = 0; i < matchBools.length; i++) {
			if (!matchBools[i])
				wholeMatchBool = false;
		}

		return wholeMatchBool ? returnMatchSentenceList.toArray(new String[returnMatchSentenceList.size()])
				: new String[0];
	}

}
