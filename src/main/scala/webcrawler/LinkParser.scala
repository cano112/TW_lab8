package webcrawler

import java.net.URL

import org.htmlcleaner.{HtmlCleaner, TagNode}

class LinkParser(private val cleaner: HtmlCleaner) {
  def parseLinks(url: String): Array[String] = {
    val rootNode: TagNode = cleaner.clean(new URL(url))
    val elements: Array[TagNode] = rootNode.getElementsByName("a", true)
    elements
      .map {el => el.getAttributeByName("href")}
      .filter {e => e != null && e.startsWith("http")}
      .distinct
  }
}
