package csvToParquet

import org.apache.avro.generic.GenericRecord

object readCsvWriteParquet {
  def main(args: Array[String]): Unit = {
    //usage: java -cp convertHadoopFileFormatsLocally-assembly-0.1.jar csvToParquet.readCsvWriteParquet d:\\abc.csv d:\\abc.parquet string,int,double,string
    if (args.length == 3 && args(2).split(",").filter(x=> ! (x.equals("string") | x.equals("int") | x.equals("double") ) ).length == 0 ) {
      run(args(0), args(1), args(2).split(","))
    } else {
      println("ERROR: please specify the input paramaters correctly")
      System.exit(-2)
    }
  }
  private[csvToParquet] def run(csv_file_loc: String = "./abc.csv", parquet_file_loc: String = "./abc.parquet", schema_field_types: Array[String] = Array("string", "int", "double", "string")) = {
    //read csv
    val csv_iterator = readCsvAsText.run(csv_file_loc)

    //create avro schema for the csv that contains headers
    val avro_listBuffer: scala.collection.mutable.ListBuffer[GenericRecord] = writeAsParquet.createAvroStream(csv_iterator, schema_field_types)

    // write to parquet ()
    writeAsParquet.createParquetWriter(parquet_file_loc, avro_listBuffer)
  }

}
