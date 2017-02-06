outputformat  主要用来设置数据如何存，存到哪，或者是如何写到哪。


在开发自定义outputformat,最简单的是直接实现OutputFormat接口。
	需要实现的方法有：
	1     void configure(Configuration parameters);   //主要是根据做一些配置工作，根据实际情况实现，比如配置 输出文件的路径，连接数据库的驱动，地址，用户名密码等
	2     void open(int taskNumber, int numTasks) throws IOException; //创建一个写文件的流对象，或者插入数据库的预处理语句对象等。
	3    void writeRecord(IT record) throws IOException;  //实现如何将每条数据保存起来
	4    void close() throws IOException;  //最后执行，主要是释放资源

对于稍复杂点的，我们需要考虑一些特殊场景
	1 在集群上运行时，并行的节点上，如何并行的写数据
	2 在集群上运行时，单个节点写失败的时候，如果处理
  由于在集群上运行，有些初始化工作和出错处理工作，是有master节点监控和处理，因此，有时还需要实现一些特殊的接口
     InitializeOnMaster 
           void initializeGlobal(int parallelism) throws IOException;
     CleanupWhenUnsuccessful
           void tryCleanupOnError() throws Exception;
 
 



  
  
   