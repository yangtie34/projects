inputformat 定义产生数据的数据源
    1 描述如何拆分输入 从而并行处理
    2 描述如何获取输入的基本的统计信息
    3 描述如此从这些拆分 读取数据
 生命周期：
    1初始化后，调用配置方法 void configure(Configuration parameters);
    2获取输入的基本统计信息 BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException;
    3为输入创建可以并行处理的拆分 
    	T[] createInputSplits(int minNumSplits) throws IOException;
    	InputSplitAssigner getInputSplitAssigner(T[] inputSplits);
    4打开每个拆分 void open(T split) throws IOException;
    5读取数据
        boolean reachedEnd() throws IOException;
        OT nextRecord(OT reuse) throws IOException;
    6关闭并释放资源 void close() throws IOException;
    
具体demo，参考TextInputFormat（）
    