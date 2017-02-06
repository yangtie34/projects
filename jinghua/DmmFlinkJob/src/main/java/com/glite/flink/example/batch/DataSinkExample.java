package com.glite.flink.example.batch;

import java.io.IOException;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.TextOutputFormat.TextFormatter;
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.table.Row;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;

public class DataSinkExample {
	public static void main() {
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		DataSource<String> paySource = env.readTextFile("hdfs://ns1/cardpay");
		DataSet<Tuple3<String, Float, String>> paySet = paySource
				.flatMap(new FlatMapFunction<String, Tuple3<String, Float, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple3<String, Float, String>> out)
							throws Exception {
						String[] items = value.replaceAll("'", "").split(",");
						out.collect(new Tuple3<>(items[1], Float
								.valueOf(items[2]), items[5]));
					}
				});

		// write DataSet to a file on the local file system
		paySet.writeAsText("file:///my/result/on/localFS");

		// write DataSet to a file on a HDFS with a namenode running at
		// nnHost:nnPort
		paySet.writeAsText("hdfs://nnHost:nnPort/my/result/on/localFS");

		// write DataSet to a file and overwrite the file if it exists
		paySet.writeAsText("file:///my/result/on/localFS", WriteMode.OVERWRITE);

		// tuples as lines with pipe as the separator "a|b|c"
		paySet.writeAsCsv("file:///path/to/the/result/file", "\n", "|");

		// this writes tuples in the text formatting "(a, b, c)", rather than as
		// CSV lines
		paySet.writeAsText("file:///path/to/the/result/file");

		// this writes values as strings using a user-defined TextFormatter
		// object
		paySet.writeAsFormattedText("file:///path/to/the/result/file",
				new TextFormatter<Tuple3<String, Float, String>>() {
					public String format(Tuple3<String, Float, String> value) {
						return value.f1 + " - " + value.f0;
					}
				});
		paySet.output(new OutputFormat<Tuple3<String,Float,String>>() {
			
			@Override
			public void writeRecord(Tuple3<String, Float, String> record)
					throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void open(int taskNumber, int numTasks) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void configure(Configuration parameters) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void close() throws IOException {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		DataSet<Row> rowSet=null;
		rowSet.output(
		// build and configure OutputFormat
		JDBCOutputFormat
				.buildJDBCOutputFormat()
				.setDrivername("org.apache.derby.jdbc.EmbeddedDriver")
				.setDBUrl("jdbc:derby:memory:persons")
				.setQuery(
						"insert into persons (name, age, height) values (?,?,?)")
				.finish());
		
	}
}
