package com.glite.flink.utils.handle;

import java.util.List;

public interface RedisResultHandler {
	public <T> void handle(List<T> l);
}
