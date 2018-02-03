name := "convertHadoopFileFormatsLocally"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % "2.7.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies ++= Seq(
  //"com.amazonaws" % "aws-java-sdk" % "1.7.4",
  //"org.apache.hadoop" % "hadoop-aws" % "2.7.4",
  "org.apache.hadoop" % "hadoop-common" % "2.7.1",
  "org.apache.parquet" % "parquet-avro" % "1.8.1",
  "org.apache.avro"  %  "avro"  %  "1.7.7",
  "com.univocity"  %  "univocity-parsers"  %  "2.5.9",
  "org.json"  %  "json" % "20171018",

  //"org.scalatest" % "scalatest_2.11" % "3.0.4" % "test"
  //"org.apache.spark" %% "spark-core" % "2.2.1",
  //"org.apache.spark" %% "spark-sql" % "2.2.1"
  //"com.eed3si9n" %% "sbt-assembly" % "0.14.6"
)



assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("META-INF", "services", "org.apache.hadoop.fs.FileSystem") => MergeStrategy.concat
  /*case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) => MergeStrategy.discard
      case ("org.apache.hadoop.fs.FileSystem" :: Nil) => MergeStrategy.concat
    }*/
  case _ => MergeStrategy.first
}
