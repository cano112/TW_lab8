package webcrawler.utils

import java.util.concurrent.ConcurrentHashMap

import scala.collection.mutable

object ConcurrentHashSetFactory {
  def createSet[T]: mutable.Set[T] = {
    import scala.collection.JavaConverters._
    java.util.Collections.newSetFromMap(new ConcurrentHashMap[T, java.lang.Boolean]()).asScala
  }
}
