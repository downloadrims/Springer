package parseXls;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class downloadFiles {
	public static final String basePath = "【保存フォルダのパス】\\Springer\\";
	public static final String xmlFile = "Springer_Data.xml";

	public static void main(String[] args) {
		download();
		// check();
	}

	public static void check() {
		try {
			Document doc = Jsoup.parse(
					new BufferedInputStream(new FileInputStream(basePath + xmlFile)),
					"utf-8", "", Parser.xmlParser());
			Elements elems = doc.select("folder");
			for (Element e : elems) {
				File[] files = new File(basePath).listFiles(new MyFilenameFilter(e.attr("genre")));
				System.out.printf("%s:doc=%d, file=%d\r\n",
						e.attr("genre"), e.childrenSize(), files[0].list().length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void download() {
		try {
			Document doc = Jsoup.parse(
					new BufferedInputStream(new FileInputStream(basePath + xmlFile)),
					"utf-8", "", Parser.xmlParser());
			for (Element folder : doc.select("Root > folder")) { // 各フォルダー
				// フォルダー作成
				Files.createDirectories(Paths.get(basePath + folder.attr("genre")));
				for (Element file : folder.select("> file")) {
					// ファイル名作成
					String fileName = String.format("[%s](%s) _(%s)",
							file.select("title").text(), file.select("auther").text(),
							file.select("edition").text());
					String filePath = basePath + folder.attr("genre") + "\\" + fileName + ".pdf";
					// ファイルのダウンロード
					try(
							BufferedInputStream in = new BufferedInputStream(
									new URL(file.select("link").text()).openStream());
							FileOutputStream fos = new FileOutputStream(filePath)) {
						byte dataBuffer[] = new byte[1024];
						int bytesRead;
						while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
							fos.write(dataBuffer, 0, bytesRead);
						}
					} catch (IOException e) {
						System.out.println("エラー発生:" + fileName + ":" + file.select("link").text());
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyFilenameFilter implements FilenameFilter {
	String genre;
	MyFilenameFilter(String s) {
		genre = s;
	}

	@Override
	public boolean accept(File file, String str) {
		if (file.isFile()) {
			return false;
		}
		return str.equals(genre);
	}
}