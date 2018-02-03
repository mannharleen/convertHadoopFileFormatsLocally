# convert Hadoop File Formats Locally

### This scala pet-project helps convertions between hadoop file formats and text formats without having to use a hadoop cluster i.e. in local mode

#### Motivation:
I developed this utility to convert certain text-like files into hadoop file formats before ingesting into HDFS. The

#### Building the code:
- The build.sbt file shipped here can be used to create an assembly jar if the need be. i recommend creating an assembly jar wherever possible
- To build an assembly jar:
    sbt assembly
- To build a jar:
    sbt package

#### Using the JAR in your code:
- Place the jar in the class path
- import csvToParquet._
- To convert csv to parquet, use: readCsvWriteParquet.main(Array("d:\\abc.csv", "d:\\abc.parquet", "string,int,double,string"))

#### Using the JAR as an application:
- java -cp convertHadoopFileFormatsLocally-assembly-0.1.jar csvToParquet.readCsvWriteParquet d:\\abc.csv d:\\abc.parquet string,int,double,string

