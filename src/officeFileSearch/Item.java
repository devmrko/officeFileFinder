package officeFileSearch;

public class Item {
	/**
	 * <B>Project Name : </B>officeFileSearch<br/>
	 * <B>Package Name : </B>officeFileSearch<br/>
	 * <B>File Name : </B>Item<br/>
	 * <B>Description</B>
	 * <ul>
	 * <li>여기에 해당 클래스에 대한 개요를 입력합니다.
	 * <li>논리적으로 내용이 분리되는 경우 li 태그를 사용하여 개행 합니다.
	 * </ul>
	 * 
	 * @author jmko7
	 * @since Apr 21, 2017
	 */

	private String item;
	private String[] searchWords;

	public String[] getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String[] searchWords) {
		this.searchWords = searchWords;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
