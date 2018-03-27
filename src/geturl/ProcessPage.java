package geturl;

import java.nio.channels.NonWritableChannelException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//用于获取界面元素
public class ProcessPage {
	//最大页数
	int maxPageNumber = 100;
	
	//可用的钓鱼网址检索网站格式
	String websiteFormat = "https://www.phishtank.com/phish_search.php?page=%d&valid=y&Search=Search";
	
	public ProcessPage() {
		
		for (int i = 1; i < maxPageNumber; i++) {
			//循环获取每页url
			String nowUrl = String.format(websiteFormat, i);
			System.out.println(nowUrl);
			
			String urldomain = getUrlDomainName(nowUrl);
			
			HttpHeaders httpheader = new HttpHeaders();
			boolean hopNumber = httpheader.getHeaders(nowUrl);
			Document doc = Jsoup.connect(nowUrl).get();
			FileUtil.writeToFile(doc.toString(), outPath + "\\" + "Document " + docName + ".txt");
		}
	}
	
	//获取url域名
	public static String getUrlDomainName(String htmlurl) {
		//论文版正则
//		Pattern p = Pattern.compile("(http://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?",Pattern.CASE_INSENSITIVE);
		//web版正则
//		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
		//居银银版正则
		Pattern p = Pattern.compile("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62}|(:[0-9]{1,4}))+\\.?");
		Matcher m = p.matcher(htmlurl);
		if(m.find()) {
			return m.group();
		}
		return null;
	}
}
