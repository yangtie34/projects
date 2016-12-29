package com.glite.flink.example.batch.aggregate;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.CrossFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.operators.base.JoinOperatorBase.JoinHint;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.JoinOperator.JoinOperatorSets;
import org.apache.flink.api.java.operators.join.JoinFunctionAssigner;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.util.Collector;

import com.glite.flink.example.domain.TCard;
import com.glite.flink.example.domain.TCardPay;

/**
 * join 相当于sql中的连接查询
 * 这里只有join和leftouterjoin
 * @author chenlj
 *
 */
public class JoinExample {
	public static void main(String[] args){
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSource<String> paySource=env.readTextFile("hdfs://ns1/cardpay");
		DataSource<String> cardSource=env.readTextFile("hdfs://ns1/card");
		
		DataSet<TCardPay> paySet=paySource.flatMap(new FlatMapFunction<String, TCardPay>() {
			@Override
			public void flatMap(String value, Collector<TCardPay> out)
					throws Exception {
				out.collect(new TCardPay(value));
				
			}
		});
		DataSet<TCard> cardSet=cardSource.flatMap(new FlatMapFunction<String, TCard>() {
			@Override
			public void flatMap(String value, Collector<TCard> out)
					throws Exception {
				out.collect(new TCard(value));
				
			}
		})
		;
		/*
		 * join操作和flatjoin操作的输入输出，有点像map和flapmap
		 */
		DataSet<Tuple3<String, String, Float>> joinedSets= paySet.join(cardSet)
				 .where("cardId").equalTo("card_no").with(new FlatJoinFunction<TCardPay, TCard, Tuple3<String,String,Float>>() {
					@Override
					public void join(TCardPay first, TCard second,
							Collector<Tuple3<String, String, Float>> out)
							throws Exception {
							
					}
				});
		paySet.join(cardSet).where("cardId").equalTo("card_no").with(new JoinFunction<TCardPay, TCard, Tuple3<String,String,Float>>() {
			@Override
			public Tuple3<String, String, Float> join(TCardPay first,
					TCard second) throws Exception {
				
				return null;
			}
		});
		
		
		/*
		 * 针对tuple的连接操作，还可以用project操作
		 */
		DataSet<Tuple3<Integer, Byte, String>> input1 = null;
		DataSet<Tuple2<Integer, Double>> input2 = null;
		DataSet<Tuple4<Integer, String, Double, Byte>>
				            result =
				            input1
				            	.join(input2)
				            
				                  // key definition on first DataSet using a field position key
				                  .where(0)
				                  // key definition of second DataSet using a field position key
				                  .equalTo(0)
				                  // select and reorder fields of matching tuples
				                  .projectFirst(0,2).projectSecond(1).projectFirst(1);
		
		
		/*
		 * 为了帮助优化器选择正确的执行策略， 用户可以提示join的数据集的大小
		 */
		input1.joinWithHuge(input2)
				// key definition on first DataSet using a field position key
		        .where(0)
		        // key definition of second DataSet using a field position key
		        .equalTo(0)
		        // select and reorder fields of matching tuples
		        .projectFirst(0,2).projectSecond(1).projectFirst(1);
		
		
		/*
		 * flink runtime可以用很多种方式进行join， 每种方式在不同的环境下结果不一样。 
		 * 系统会自动选择一种合理的方式， 但允许用户手动选择一种策略， 这种情况下， 用户可以选择一种指定的方式进行join。
		 * 有如下提示：
		 *	OPTIMIZER_CHOOSES: 等价于没有提示， 让系统做选择。
		 *	BROADCAST_HASH_FIRST: 广播第一个数据集并根据它来创建一张hash table， 这个hash table 会被第二个数据集来调查（probe）。 这种策略适合第一个数据集比较小。
		 *	BROADCAST_HASH_SECOND: 广播第二个数据集并根据它来创建一张hash table， 这个hash table 会被第一个数据集来调查（probe）。 这种策略适合第二个数据集比较小。
		 *	REPARTITION_HASH_FIRST: 系统分区（shuffles）每一个输入（除非这个输入已经被分区）并根据第一个数据集创建一张hash table。 这种策略适合第一个数据集比第二个数据集小，但2个数据集仍然很大。 注意： 这是默认的fallback策略， 当无法评估大小时并且没有预先存在的parition和可以重复使用的sort－order。
		 *	REPARTITION_HASH_SECOND: 系统分区（shuffles）每一个输入（除非这个输入已经被分区）并根据第二个数据集创建一张hash table。 这种策略适合第一个数据集比第二个数据集大，但2个数据集仍然很大。
		 *	REPARTITION_SORT_MERGE: 系统分区（shuffles）每一个输入（除非这个输入已经被分区）并对每个数据集进行排序（除非它已经排序了） 2个数据集通过merge 排序的数据集， 这种策略非常适合，当一个或2个数据集都已经排好序。
		 */
		input1.join(input2, JoinHint.BROADCAST_HASH_FIRST)
        	.where("id").equalTo("key");
		
		
		/*
		 * cross 创建笛卡尔集
		 * 有with  project
		 */
		
		input1.cross(input2).with(new CrossFunction<Tuple3<Integer,Byte,String>, Tuple2<Integer,Double>, Tuple4<Integer, String, Double, Byte>>() {

			@Override
			public Tuple4<Integer, String, Double, Byte> cross(
					Tuple3<Integer, Byte, String> val1,
					Tuple2<Integer, Double> val2) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		/*
		 * coGroup
		 */
		input1.coGroup(input2).where(0).equalTo(0).with(new CoGroupFunction<Tuple3<Integer,Byte,String>, Tuple2<Integer,Double>, Tuple4<Integer, String, Double, Byte>>() {

			@Override
			public void coGroup(Iterable<Tuple3<Integer, Byte, String>> first,
					Iterable<Tuple2<Integer, Double>> second,
					Collector<Tuple4<Integer, String, Double, Byte>> out)
					throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*
		 * union 
		 * 两个数据集类型必须完全相同
		 */
		DataSet<Tuple3<Integer, Byte, String>> input1_u = null;
		DataSet<Tuple3<Integer, Byte, String>> input2_u = null;
		DataSet<Tuple3<Integer, Byte, String>> uDataSet=input1_u.union(input2_u);
		
		
	}
}
