package officeFileSearch.log;

import officeFileSearch.util.DateUtil;

public class CustomedLogger {

	public static void logger(LogType lt, String classNm, String str) {
		switch (lt) {
		case INFO:
			System.out.println("(-) " + DateUtil.getCurrentDate() + " [" + classNm + "]: " + str);
			break;
		case WARN:
			System.out.println("(!) " + DateUtil.getCurrentDate() + " [" + classNm + "]: " + str);
			break;
		case ERROR:
			System.out.println("(*) " + DateUtil.getCurrentDate() + " [" + classNm + "]: " + str);
			break;
		}
	}
}
