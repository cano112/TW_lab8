package webcrawler

import webcrawler.utils.ConcurrentHashSetFactory

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class Crawler(private val maxLevel: Int, private val linkParser: LinkParser) {

  def crawl(level: Int, url: String): Unit = {
    if(level > maxLevel) return
    if(Crawler.visitedUrls contains url) return

    val links: Array[String] = try {
      val links = linkParser.parseLinks(url)
      Crawler.visitedUrls.add(url)
      links
    } catch {
      case e: Exception =>
        println("Error occured while parsing url: " + url)
        Array.empty
    }

    links foreach {link => {
      if(!(Crawler.visitedUrls contains link)) {
        val futureLinks: Future[Array[String]] = Future {
          val li: Array[String] = try {
            val li = linkParser.parseLinks(link)
            Crawler.visitedUrls.add(link)
            li
          } catch {
            case e: Exception =>
              println("Error occured while parsing url: " + link)
              Array.empty
          }
          li
        }

        futureLinks onComplete {
          case Success(urls) =>
            println("From URL: " + link + " LEVEL: " + level + " " + urls.mkString(", "))
            urls foreach { l =>crawl(level+1, l)}
          case Failure(e) => e.printStackTrace()
        }
      }
    }}
  }
}

object Crawler {
  private val visitedUrls = ConcurrentHashSetFactory.createSet[String]
}
