package webcrawler

import org.htmlcleaner.HtmlCleaner

object WebCrawler {

  def main(args: Array[String]): Unit = {
    val url = "http://agh.edu.pl"

    val crawler: Crawler = new Crawler(2, new LinkParser(new HtmlCleaner))
    crawler.crawl(0, url)

  }
}
