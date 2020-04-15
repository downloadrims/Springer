# Springer
コロナ騒動で無料公開になったSpringerの本をダウンロードする用(期間限定)


Free+English+textbooks

https://www.springernature.com/gp/librarians/news-events/all-news-articles/industry-news-initiatives/free-access-to-textbooks-for-institutions-affected-by-coronaviru/17855960
の｢Free English textbook titles (all disciplines)｣からダウンロードできる物と同一。
今回のコロナ騒動でSpringerが期間限定で無料公開してる専門書の書籍名、著者名、ダウンロードリンクなどの情報が詰まったエクセルファイル



Springer_Data.xml
上記の｢Free+English+textbooks｣を解析して必要な情報を纏めた結果としてのXMLファイル。
書籍タイトル、著者名、Edition、ダウンロードリンクの一覧からなる。



downloadFiles.java
上記｢Springer_Data.xml｣を使って、Springer公式サイトから専門書をダウンロードして、適切に名前を付けるJavaプログラム。
ライブラリJsoupを使うので、本プログラムだけでは使えない。Jsoupは各自で準備して下さい。



