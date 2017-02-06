package com.glite.flink.example.toredis.output;

import java.io.IOException;

import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisConfigBase;
import org.apache.flink.streaming.connectors.redis.common.container.RedisCommandsContainer;
import org.apache.flink.streaming.connectors.redis.common.container.RedisCommandsContainerBuilder;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.flink.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisOutPutFormat<IN> implements OutputFormat{
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(RedisOutPutFormat.class);

	/**
	 * This additional key needed for {@link RedisDataType#HASH} and {@link RedisDataType#SORTED_SET}.
	 * Other {@link RedisDataType} works only with two variable i.e. name of the list and value to be added.
	 * But for {@link RedisDataType#HASH} and {@link RedisDataType#SORTED_SET} we need three variables.
	 * <p>For {@link RedisDataType#HASH} we need hash name, hash key and element.
	 * {@code additionalKey} used as hash name for {@link RedisDataType#HASH}
	 * <p>For {@link RedisDataType#SORTED_SET} we need set name, the element and it's score.
	 * {@code additionalKey} used as set name for {@link RedisDataType#SORTED_SET}
	 */
	private String additionalKey;
	private RedisMapper<IN> redisSinkMapper;
	private RedisCommand redisCommand;

	private FlinkJedisConfigBase flinkJedisConfigBase;
	private RedisCommandsContainer redisCommandsContainer;

	/**
	 * Creates a new {@link RedisSink} that connects to the Redis server.
	 *
	 * @param flinkJedisConfigBase The configuration of {@link FlinkJedisConfigBase}
	 * @param redisSinkMapper This is used to generate Redis command and key value from incoming elements.
	 */
	public RedisOutPutFormat(FlinkJedisConfigBase flinkJedisConfigBase, RedisMapper<IN> redisSinkMapper) {
		Preconditions.checkNotNull(flinkJedisConfigBase, "Redis connection pool config should not be null");
		Preconditions.checkNotNull(redisSinkMapper, "Redis Mapper can not be null");
		Preconditions.checkNotNull(redisSinkMapper.getCommandDescription(), "Redis Mapper data type description can not be null");

		this.flinkJedisConfigBase = flinkJedisConfigBase;

		this.redisSinkMapper = redisSinkMapper;
		RedisCommandDescription redisCommandDescription = redisSinkMapper.getCommandDescription();
		this.redisCommand = redisCommandDescription.getCommand();
		this.additionalKey = redisCommandDescription.getAdditionalKey();
	}

	@Override
	public void configure(Configuration parameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open(int taskNumber, int numTasks) throws IOException {
		this.redisCommandsContainer = RedisCommandsContainerBuilder.build(this.flinkJedisConfigBase);
		
	}

	@Override
	public void writeRecord(Object input) throws IOException {
		String key = redisSinkMapper.getKeyFromData((IN)input);
		String value = redisSinkMapper.getValueFromData((IN)input);

		switch (redisCommand) {
			case RPUSH:
				this.redisCommandsContainer.rpush(key, value);
				break;
			case LPUSH:
				this.redisCommandsContainer.lpush(key, value);
				break;
			case SADD:
				this.redisCommandsContainer.sadd(key, value);
				break;
			case SET:
				this.redisCommandsContainer.set(key, value);
				break;
			case PFADD:
				this.redisCommandsContainer.pfadd(key, value);
				break;
			case PUBLISH:
				this.redisCommandsContainer.publish(key, value);
				break;
			case ZADD:
				this.redisCommandsContainer.zadd(this.additionalKey, value, key);
				break;
			case HSET:
				this.redisCommandsContainer.hset(this.additionalKey, key, value);
				break;
			default:
				throw new IllegalArgumentException("Cannot process such data type: " + redisCommand);
		}
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
