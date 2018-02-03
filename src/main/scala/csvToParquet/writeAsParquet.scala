package csvToParquet

import scala.collection.JavaConversions._
import org.apache.avro.Schema
import org.apache.avro.generic._
import org.apache.hadoop.fs.Path
import org.apache.parquet.avro.AvroParquetWriter
import org.apache.parquet.hadoop.metadata.CompressionCodecName
import org.json.JSONObject

import scala.collection.mutable.ListBuffer
import scala.util.Try


object writeAsParquet {

  private[csvToParquet] def main(args: Array[String]): Unit = {
    run()
  }

  private[csvToParquet] def run(parquet_file_loc: String = "./abc.parquet"): Unit = {
    val lst_records: scala.collection.mutable.ListBuffer[GenericRecord] = createAvroStream()
    createParquetWriter(parquet_file_loc, lst_records)
  }

  private[csvToParquet] def createParquetWriter(path: String, lst_records: scala.collection.mutable.ListBuffer[GenericRecord]) = {
    println(s"INFO: parquet file location: $path")
    val compressionCodecName = CompressionCodecName.GZIP
    val blockSize = 256 * 1024 * 1024
    val pageSize = 1 * 1024 * 1024
    val outputPath = new Path(path)
    val obj = AvroParquetWriter.builder[GenericRecord](outputPath).withSchema(lst_records(0).getSchema).withCompressionCodec(compressionCodecName).build()
    lst_records.foreach(x=> obj.write(x))
    obj.close()
  }

  private[csvToParquet] def createAvroStream(inp: java.util.List[Array[String]] = ListBuffer(Array("header1","header2","header3","header4"), Array("line0","0","a","4"), Array("line1","1","b","8")), schema_field_types: Array[String] = Array("string", "int", "string", "string")): scala.collection.mutable.ListBuffer[GenericRecord] = {
    val header = inp.head
    val data = inp.tail
    assert(header.length == schema_field_types.length, {println(s"ERROR: Number of row headers (=${header.length}) does not match with number of column types (=${schema_field_types.length}) provided")})
    val schemaString = new JSONObject().put("namespace", "mannharleen.test").put("type", "record").put("name", "name").
      put("fields", ({for ( i <- 0 to (header.length- 1)) yield new JSONObject().put("name",header(i)).put("type",Array(schema_field_types(i), "null") )
            } ).toArray ).toString

    println(s"INFO: parquet schema autocreated as: ${schemaString}")
    val schema: Schema = new Schema.Parser().parse(schemaString)
    val lst_records = scala.collection.mutable.ListBuffer.empty[GenericRecord]

    data.foreach(row => {
      val genericRecord: GenericRecord = new GenericData.Record(schema)
      var i = 0
      row.foreach(value => {

        if (schema_field_types(i) == "int")  try {genericRecord.put(header(i), value.toInt)} catch {case e: Exception => e.printStackTrace()}
        else if (schema_field_types(i) == "double")  try {genericRecord.put(header(i), value.toDouble)} catch {case e: Exception => e.printStackTrace()}
        else genericRecord.put(header(i), value)

        i += 1
      })
      lst_records += genericRecord
    } )
    lst_records
  }
}
