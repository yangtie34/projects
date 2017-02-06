在开发之前，先 用maven下载sources（右键项目->maven->download sources）这样可以参考源程序

开发flink程序的步骤：
1、获得一个 execution environment，
2、加载/创建初始数据，
3、指定在该数据上进行的转换，
4、指定计算结果的存储地方，
5、启动程序执行。

flink 程序是lazy模式执行，只有execute方法调用后，才开始真正的执行程序，execute之前，是定义工作流程。flink程序是有向无环图。



Flink 程序是在分布式集合上实现转换（比如 filtering, mapping, updating state, joining, grouping, defining windows, aggregating）的普通程序。 
数据集合根据一些 source（比如 文件，kafka 或本地的集合）初始化创建。 
通过 sinks 返回的结果，sink 可以写入到（分布式）文件或标准输出（如终端命令行）。
Flink 运行在各种环境下，standalone 或嵌入到其他程序中。 可能在本地 JVM 中执行，或多台机器的集群上运行。

根据数据源的类型，例如有界或无界的数据源，用户可以实现一个批处理(batch program)或一个流式程序(streaming program), 
其中 DataSet API 用于前者，DataStream API 用于后者。