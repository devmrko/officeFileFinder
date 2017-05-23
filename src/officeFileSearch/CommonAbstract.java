package officeFileSearch;

public abstract class CommonAbstract {
	public static String getClassAndMethodNm() {
		return Thread.currentThread().getStackTrace()[2].getClassName() + "."
				+ Thread.currentThread().getStackTrace()[2].getMethodName();
	}

}
