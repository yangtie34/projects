package test.dev;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;

public class StringCutter {
	public static void main(String[] args) throws UnsupportedEncodingException {
		/*String urlstr = "/fgh/88/*\n\t\tsdf";
		System.out.println(urlstr);
		String[]urls = urlstr.split("\n");
		System.out.println(urls);
		List<String> urlset = new ArrayList<String>();
		CollectionUtils.addAll(urlset, urls);
		for (int i = 0; i < urlset.size(); i++) {
			String url = urlset.get(i).trim().replace("*", ".*");
			System.out.println("++"+url+"++");
			Pattern pattern = Pattern.compile("^"+url);
			Matcher matcher = pattern.matcher("/fgh/88/sd");
			if (matcher.matches()) {
				System.out.println("匹配成功");
				break;
			}
		}*/
		String name = ") - response data:<xml><Content>鎮ㄥ悜鎴戝彂閫佷簡鏂囧瓧娑堟伅锛氫綘濂?/Content>"
				+ "<CreateTime>1464171822</CreateTime><FromUserName>gh_b97efc15bbaf</FromUserName>"
				+ "<MsgType>text</MsgType><ToUserName>o1Qx0v48tNLPBZuyp3qrLAvMFwBY</ToUserName></xml>";
		String result = new String(name.getBytes("GBK"),"UTF-8");
		System.out.println(result);
	}
}
