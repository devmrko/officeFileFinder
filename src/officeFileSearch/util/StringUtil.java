package officeFileSearch.util;

public class StringUtil {

	public static String getStringByStringArray(String[] inputStringArray) {
		StringBuffer sb = new StringBuffer();
		sb.append("{ ");
		for (String string : inputStringArray) {
			sb.append(string + ", ");
		}
		sb.append(" }");
		return sb.toString();
	}

}
