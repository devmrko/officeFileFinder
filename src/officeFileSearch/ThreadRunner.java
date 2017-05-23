package officeFileSearch;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import officeFileSearch.svc.ApachePOIHelper;

public class ThreadRunner implements Runnable {

	private Item itemVo;

	public ThreadRunner(Item itemVo) {
		this.itemVo = itemVo;
	}

	@Override
	public void run() {
		ApachePOIHelper aph = new ApachePOIHelper();
		String[] returnMatchStentence;
		try {
			returnMatchStentence = aph.getMatchSentence(itemVo.getItem(), itemVo.getSearchWords());
			if (returnMatchStentence.length > 0) {
				System.out.println("### mached ###");
				System.out.println("# " + itemVo.getItem());
				for (String match : returnMatchStentence) {
					System.out.println(">>> " + match);
				}
			} else {
				// System.out.println("# not mached");
			}
		} catch (InvalidFormatException | IOException e) {
			System.out.println(">>> format does not match.");
		}
	}

}
