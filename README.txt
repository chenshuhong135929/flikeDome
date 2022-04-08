
flink 处理的流程
1，获取执行环境（exccution cnvironmcnt）
2，读取数据源（source）
3，定义基本数据的转换操作（transformations）
4，定义计算结果的输出位置（sink)
5，触发程序运行（execute）


用dataStream流处理来操作批处理，可以在运行的时候加多个参数 -Dexecution.runtime-mode=BATCH jar


Flink 提交作业和执行任务，需要几个关键组件，客户端（client）,作业管理器（JobManager）
和任务管理器（TaskManager）,我们的代码由客户端获取并做转换，之后提交给JobManager,
所以JobManager就是Flink  集群里的“管事人”，对作业进行中央调度管理，而它获取到要执行
的作业后，，进一步处理转换，然后分发任务给众多的TaskManager ,这里的TaskMananger,就是
真正的“干活的人”，数据的处理操作都是它们来做的


			

启动flink集群
下载地址
https://flink.apache.org/zh/downloads.html#section-7
下载Binaries 进行下载

linux服务器上的操作

解压
tar -xvf flink-1.14.4-bin-scala_2.1
运行
./bin/start-cluster.sh
停止
./bin/stop-cluster.sh
查看
jps
强制结束
kill -9  进程ID


集群配置信息
flink-1.14.4/conf/flink-conf.yaml
jobmanager.rpc.address: localhost(jobManager服务器的ip)
taskmanager.numberOfTaskSlots: 1（修改可用资源数量）

=======================================================
配置taskManager的ip
flink-1.14.4/conf/workers
ip
ip
=====================================================
命令行提交做业
./bin/flink run -m ip:port   -c  (程序启动入口) -p  2  -a   -s  ./jar包



3.2 部署模式
	在一些应用场景中，对于集群资源分配喝占用的方式，可能会有特定的需求，flink为各种场景提供了不同的部署模式，主要有以下三种
	1，会话模式（session mode）
	2，单作业模式（per-job mode）
	3，应用模式（application mode）
	它们的区别在于，集群的生命周期以及资源分配方式，以及应用的main方法到底在哪里执行-客户端（clinet）还是jobManager
	

 
3.3 程序与数据流（DataFlow）
	1,所有的flink程序都是由三部分组成的：Source ,Transformation 和sink
	2,source 负责读取数据源，Transformation利用各种算子进行处理加工，Sink负责输出
	

	
	
	
3.4 并行度（Paralleli）
	1,每一个算子（operator）可以包含一个或者多个子任务（operator subtask），
	这些子任务在不同线程，不同物理机或者不同的容器中完全可以独立地执行
	2，一个特定算子的子任务（subtask）的个数被称之为其并行度（parallelism）
	
	代码设置并行度setParallelism（）
	
3.5 数据传输形式
	一个程序中，不同的算子可能具有不同的并行度
	算子之间传输数据的形式可以是one-to-one (forwarding)的模式也可以是
	redistributing 的模式，具体是那一种形式，取决于算子的种类
	One-to-one:stream维护着分区以及元素的顺序（比如source和map之间）。
	这意味着map算子任务看到的元素的个数以及顺序跟source算子的子任务
	生产的元素的个数，顺序相同，map,fliter,fatMap等算子都是one-to-one的对应关系
	Redistributing:stream 的分区会发生改变，每一个算子的任务依据所选择的
	transformation发送数据到不同的目标任务，例如，keyBy基于hashCode重分区
	而broadcast和rebalance会随机重新分区，这些算子都会引起redistribute
	过程，而redistribute过程就类似于Spark中的shuffle过程
	

3.6 任务（Task) 和任务槽（task slots)
	flink 中每一个TaskManager都是一个jvm进程，它可能会在独立的线程上执行一个或者多个子任务
	为了控制一个TaskManager能接收到多少个task，taskmanager通过task slot来进行控制（一个 taskMananger至少有一个slot）
	
	
3.7 任务共享slot
	默认情况下，flink允许子任务共享slot ,这样的结果是，一个slot可以保存作业的整个管道
	当我们将资料密集喝非密集型的任务同时放到一个slot中，它们就可以自行分配对资源占用的比例，
	从而保证最重的活平均分配给所有的TaskManager
	
========================================================================================================================================

Hadoop学习


数据存储单位
bit  byte  kb mb gb tb pb eb zb yb bb nb db  
1byte = 8bit  1k=1024byte  1mb=1024k 1g=1024m 1t=1024g 1p=1024t


大数据特点（4v）
1,Volume(大量)
	截止目前，人类生产的所有印刷材料的数据量是200PB  ，而历史上全人类总共说过的话
	数据量大约是5EB，当前，典型个人计算机硬盘的容量为TB量级，而一些大企业数据量已经接近
	EB量级


2，Velocity(高速)
	这些大数据区分于传统数据挖掘的最显著特征，根据IDC的“数据宇宙”的报告，预计到
	2025年，球数据使用量将达到163ZB，在如此海量的数据面前，处理数据的效率
	就是企业的生命
	天猫双十一 ：20173分01秒，天猫交易额超过100亿
			     2020年96秒，天猫交易额超过100亿


3，Variety(多样)
	这些类型的多样性也让数据分为结构化数据和非结构化数据，相对以往便于存储的以
	数据库/文本的结构数据，非结构化数据越来越多，包括网络日志，音频，视频，图片，
	地理位置信息等，这些多类型的数据的处理能力提出了更高要求



4，Value(低价值密度)
	价值密度的高低与数据总量的大小成反比
	比如，在一天监控视频中，我们只关心某个时间段的数据，任何快速对有价值数据“提纯”成为面前
	大数据背景下待解决的难题
















