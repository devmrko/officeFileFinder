package officeFileSearch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return format.format(cal.getTime());
	}
	
}
