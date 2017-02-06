package com.glite.flink.example.toredis.output;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

public class RedisHsetMapper implements RedisMapper<Tuple2<String, String>>{
	private RedisCommand redisCommand;
 	private String REDIS_ADDITIONAL_KEY="";
 	public RedisHsetMapper(String topKey){
 		this.REDIS_ADDITIONAL_KEY=topKey;
 		this.redisCommand = RedisCommand.HSET;
 	}
 	
 	
	@Override
	public RedisCommandDescription getCommandDescription() {
		return new RedisCommandDescription(redisCommand, REDIS_ADDITIONAL_KEY);
	}

	@Override
	public String getKeyFromData(Tuple2<String, String> data) {
		return data.f0;
	}

	@Override
	public String getValueFromData(Tuple2<String, String> data) {
		return data.f1;
	}

}
