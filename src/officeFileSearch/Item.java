package officeFileSearch;

public class Item {
	/**
	 * <B>Project Name : </B>officeFileSearch<br/>
	 * <B>Package Name : </B>officeFileSearch<br/>
	 * <B>File Name : </B>Item<br/>
	 * <B>Description</B>
	 * <ul>
	 * <li>���⿡ �ش� Ŭ������ ���� ���並 �Է��մϴ�.
	 * <li>�������� ������ �и��Ǵ� ��� li �±׸� ����Ͽ� ���� �մϴ�.
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
