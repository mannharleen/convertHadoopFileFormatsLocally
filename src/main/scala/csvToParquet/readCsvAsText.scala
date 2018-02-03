package csvToParquet

import java.io.File
import scala.collection.JavaConverters._
import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}

trait readFile {
  private[csvToParquet] def run(csv_file_loc :String): java.util.List[Array[String]]
}

object readCsvAsText  extends readFile {
  private[csvToParquet] def main(args: Array[String]): Unit = {
    run()
  }
  private[csvToParquet] def run(csv_file_loc: String = "./abc.csv"): java.util.List[Array[String]] = {
    println(s"INFO: csv file location: $csv_file_loc")
    val settings: CsvParserSettings = new CsvParserSettings()
    settings.getFormat.setLineSeparator("\n")
    settings.getFormat.setQuoteEscape('"')
    val parser = new CsvParser(settings)
    val allRowsJ: Option[java.util.List[Array[String]]] =
    try {
      Some(parser.parseAll(new File(csv_file_loc)))
    } catch {
      case e: Exception => {
        e.printStackTrace()
        System.exit(-1)
        None
      }
    }
    allRowsJ.getOrElse(List[Array[String]]().asJava)
  }
}


