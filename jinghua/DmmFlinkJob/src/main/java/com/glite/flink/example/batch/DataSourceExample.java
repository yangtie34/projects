package com.glite.flink.example.batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.GenericTypeInfo;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.apache.flink.util.Collector;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;

import com.glite.flink.example.domain.TCardPay;

/**
 * 读取数据
 * 
 * @author chenlj
 * 
 */
public class DataSourceExample {
	public static void main(String[] args) {
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		// 读文件
		DataSource<String> dataSource = env.readTextFile("hdfs://ns1/cardpay");

		// 读文件，设置编码格式
		dataSource = env.readTextFile("hdfs://ns1/cardpay", "UTF-8");

		// dataSource=env.readCsvFile("hdfs://ns1/cardpay");

		// 将collection当做数据源
		List l = new ArrayList();
		dataSource = env.fromCollection(l);

		// 复制的数据源，自己实现数据源的产生
		DataSource<TCardPay> cardPaySource = new DataSource<>(env,
				new MyInputFormat(""), new GenericTypeInfo<>(TCardPay.class),
				"");

	}

	public static void testSource() {
		ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();

		// read text file from local files system
		DataSet<String> localLines = env
				.readTextFile("file:///path/to/my/textfile");

		// read text file from a HDFS running at nnHost:nnPort
		DataSet<String> hdfsLines = env
				.readTextFile("hdfs://nnHost:nnPort/path/to/my/textfile");

		// read a CSV file with three fields
		DataSet<Tuple3<Integer, String, Double>> csvInput = env.readCsvFile(
				"hdfs:///the/CSV/file").types(Integer.class, String.class,
				Double.class);

		// read a CSV file with five fields, taking only two of them
		DataSet<Tuple2<String, Double>> csvInput1 = env
				.readCsvFile("hdfs:///the/CSV/file").includeFields("10010") // take
																			// the
																			// first
																			// and
																			// the
																			// fourth
																			// field
				.types(String.class, Double.class);

		// read a CSV file with three fields into a POJO (Person.class) with
		// corresponding fields
		// DataSet<Person>> csvInput1 = env.readCsvFile("hdfs:///the/CSV/file")
		// .pojoType(Person.class, "name", "age", "zipcode");

		// read a file from the specified path of type TextInputFormat
		DataSet<Tuple2<LongWritable, Text>> tuples = env.readHadoopFile(
				new TextInputFormat(), LongWritable.class, Text.class,
				"hdfs://nnHost:nnPort/path/to/file");

		// read a file from the specified path of type SequenceFileInputFormat
		try {
			DataSet<Tuple2<IntWritable, Text>> tuples1 = env.readSequenceFile(
					IntWritable.class, Text.class,
					"hdfs://nnHost:nnPort/path/to/file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// creates a set from some given elements
		DataSet<String> value = env.fromElements("Foo", "bar", "foobar",
				"fubar");

		// generate a number sequence
		DataSet<Long> numbers = env.generateSequence(1, 10000000);

		// Read data from a relational database using the JDBC input format
		DataSet<Tuple2<String, Integer>> dbData = env
				.createInput(
						// create and configure input format
						JDBCInputFormat
								.buildJDBCInputFormat()
								.setDrivername(
										"org.apache.derby.jdbc.EmbeddedDriver")
								.setDBUrl("jdbc:derby:memory:persons")
								.setQuery("select name, age from persons")
								.finish(),
						// specify type information for DataSet
						new TupleTypeInfo(Tuple2.class, new GenericTypeInfo<>(String.class),
								new GenericTypeInfo<>(String.class)));
	}

	class MyInputSplit implements InputSplit {

		@Override
		public int getSplitNumber() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	public static class MyInputFormat implements
			InputFormat<TCardPay, MyInputSplit> {

		public MyInputFormat() {

		}

		public MyInputFormat(String path) {

		}

		@Override
		public void configure(Configuration parameters) {
			// TODO Auto-generated method stub

		}

		@Override
		public BaseStatistics getStatistics(BaseStatistics cachedStatistics)
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MyInputSplit[] createInputSplits(int minNumSplits)
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public InputSplitAssigner getInputSplitAssigner(
				MyInputSplit[] inputSplits) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void open(MyInputSplit split) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean reachedEnd() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public TCardPay nextRecord(TCardPay reuse) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub

		}

	}
}
