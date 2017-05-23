package officeFileSearch.svc;

import java.io.IOException;
import java.util.HashSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface OfficeFileFinder {

	public HashSet<?> getSearchedFile() throws InvalidFormatException, IOException;

}
