import java.io.{FileNotFoundException, FileWriter}

import scala.io.Source.fromFile

object CookBook extends App {
  val srcName = "C:/Users/Kasutaja/IdeaProjects/CookBook/src/main/Ressources/CookBook.txt"
  val dstName = "C:/Users/Kasutaja/IdeaProjects/CookBook/src/main/Ressources/Cook_result.txt"
  var myText = Seq[String]()

  def openSource(fName:String) = {
    try {
      println(s"Opening the file $fName")
      val filePointer = fromFile(fName)
      val myLines = filePointer.getLines.toSeq
      filePointer.close()
      myLines
    }
    catch {
      case ex: FileNotFoundException =>
        println(s"file not found $ex")
        var someSeq = Seq[String]()
        someSeq
    }
  }

  def processSeq(mySeq:Seq[String]) = {
    val results:Seq[String] = Seq()

    val recipeName = "^([A-Z ':]+$)\\b" //a line with all uppercase characters

    for (line <- mySeq) {
      if (line matches recipeName) results :+ ('\n' + line + '\n')
      if (line.startsWith("    ") && (line.contains(",") || line.contains(".")) && !line.contains("]") && !line.contains("\"") && !line.contains(":")) results :+ line
    }
    results
  }

  def saveSeq(destName:String, mySeq: Seq[String]) = {
    println(s"Saving sequence to file $destName")
    mySeq.foreach(println)
    val fw = new FileWriter(destName, true)
    mySeq.foreach(fw.write)
    fw.close()
    scala.util.Success
  }

  val mySeq = openSource(srcName)
  val filteredSeq = processSeq(mySeq)
  filteredSeq.foreach(println)
  saveSeq(dstName,filteredSeq)

}
