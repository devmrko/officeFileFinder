package officeFileSearch.svc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileFinderImpl implements OfficeFileFinder {

	String filePath;
	String[] searchWords;

	public ExcelFileFinderImpl(String filePath, String[] searchWords) {
		super();
		this.filePath = filePath;
		this.searchWords = searchWords;
	}

	@Override
	public HashSet<String> getSearchedFile() throws InvalidFormatException, IOException {
		HashSet<String> returnSet = new HashSet<String>();
		FileInputStream excelFile = new FileInputStream(new File(filePath));
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

}
