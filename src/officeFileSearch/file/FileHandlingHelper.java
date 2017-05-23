package officeFileSearch.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import officeFileSearch.CommonAbstract;
import officeFileSearch.log.CustomedLogger;
import officeFileSearch.log.LogType;

public class FileHandlingHelper extends CommonAbstract {

	/**
	 * <B>History</B>
	 * <ul>
	 * <li>Date : Apr 24, 2017
	 * <li>Developer : jmko7
	 * <li>path로 받은 폴더위치에 입력받은 file type에 해당하는 파일 list를 리턴한다.
	 * </ul>
	 * 
	 * @param path
	 * @param fileType
	 * @return
	 */
	public static String[] getFileList(String path, String[] fileType) {

		File dir = new File(path);
		if (!dir.exists()) {
//			CustomedLogger.logger(LogType.ERROR, getClassAndMethodNm(), "folder does not exist.");
			return new String[0];
		}

		List<String> returnFileList = getFileListByFolder(path, fileType);
		return returnFileList.toArray(new String[returnFileList.size()]);
	}

	/**
	 * <B>History</B>
	 * <ul>
	 * <li>Date : Apr 24, 2017
	 * <li>Developer : jmko7
	 * <li>path로 받은 폴더위치에 입력받은 file type에 해당하는 파일 list를 리턴한다. 폴더위치에 폴더가 있는경우
	 * 제귀호출(자신을 호출하여)하여 처리한다.
	 * </ul>
	 * 
	 * @param filePath
	 * @param allowTypes
	 * @return
	 */
	public static List<String> getFileListByFolder(String filePath, String[] allowTypes) {

		List<String> returnList = new ArrayList<String>();
		File dir = new File(filePath);
		File[] fileList = dir.listFiles();
		String extension = "";

		for (int i = 0; i < fileList.length; i++) {
//			CustomedLogger.logger(LogType.INFO, getClassAndMethodNm(), fileList[i] + " is scanning...");
			if (!fileList[i].isDirectory()) {
				extension = getFileExtension(fileList[i]);
				for (int j = 0; j < allowTypes.length; j++) {
					if (extension.toLowerCase().equals(allowTypes[j].toLowerCase())) {
						returnList.add(fileList[i].getPath());
					}
				}
			} else {
				returnList.addAll(getFileListByFolder(fileList[i].getPath(), allowTypes));
			}
		}
		return returnList;
	}

	/**
	 * <B>History</B>
	 * <ul>
	 * <li>Date : Apr 24, 2017
	 * <li>Developer : jmko7
	 * <li>파일의 확장자를 가져온다.
	 * </ul>
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

}
