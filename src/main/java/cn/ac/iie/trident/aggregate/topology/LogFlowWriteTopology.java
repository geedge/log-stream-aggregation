package cn.ac.iie.trident.aggregate.topology;


import cn.ac.iie.trident.aggregate.AggCount;
import cn.ac.iie.trident.aggregate.ParseJson2KV;
import cn.ac.iie.trident.aggregate.bolt.KafkaBolt;
import cn.ac.iie.trident.aggregate.spout.TridentKafkaSpout;
import cn.ac.iie.trident.aggregate.utils.FlowWriteConfig;
import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.trident.OpaqueTridentKafkaSpout;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.windowing.InMemoryWindowsStoreFactory;
import org.apache.storm.tuple.Fields;

import java.util.concurrent.TimeUnit;

/**
 * Storm程序主类
 *
 * @author Administrator
 */

public class LogFlowWriteTopology {
    private static Logger logger = Logger.getLogger(LogFlowWriteTopology.class);

    private TopologyBuilder builder;
    private static TridentTopology tridentTopology;





    private static Config createTopologConfig() {
        Config conf = new Config();
        conf.setDebug(false);
        conf.setMessageTimeoutSecs(60);
        conf.setNumWorkers(FlowWriteConfig.TOPOLOGY_WORKERS);
        return conf;
    }


    private static StormTopology buildTopology() {

        OpaqueTridentKafkaSpout opaqueTridentKafkaSpout = TridentKafkaSpout.getInstance();

        tridentTopology.newStream("kafka2storm_opaqueTrident", opaqueTridentKafkaSpout)
                .parallelismHint(FlowWriteConfig.SPOUT_PARALLELISM)
                .each(new Fields("str"), new ParseJson2KV(), new Fields("key", "value"))
                .parallelismHint(FlowWriteConfig.DATACENTER_BOLT_PARALLELISM)
                .slidingWindow(new BaseWindowedBolt.Duration(FlowWriteConfig.AGG_TIME, TimeUnit.SECONDS), new BaseWindowedBolt.Duration(FlowWriteConfig.AGG_TIME, TimeUnit.SECONDS), new InMemoryWindowsStoreFactory(), new Fields("key", "value"), new AggCount(), new Fields("map"))
                .each(new Fields("map"), new KafkaBolt(), new Fields());
        return tridentTopology.build();
    }

    public static void main(String[] args) throws Exception {


        Config conf = new Config();
        conf.setDebug(false);
        conf.setMessageTimeoutSecs(60);
        conf.setNumWorkers(FlowWriteConfig.TOPOLOGY_WORKERS);

        //TODO 创建一个topo任务
        TridentTopology topology = new TridentTopology();
        //TODO 为Topo绑定Spout
        OpaqueTridentKafkaSpout opaqueTridentKafkaSpout = TridentKafkaSpout.getInstance();

    /*    topology.newStream("kafka2storm_opaqueTrident", opaqueTridentKafkaSpout)
                .parallelismHint(FlowWriteConfig.SPOUT_PARALLELISM)//6
                .each(new Fields("str"), new ParseJson2KV(), new Fields("key", "value"))
                .parallelismHint(FlowWriteConfig.DATACENTER_BOLT_PARALLELISM)//9
                .slidingWindow(new BaseWindowedBolt.Duration(FlowWriteConfig.AGG_TIME, TimeUnit.SECONDS), new BaseWindowedBolt.Duration(FlowWriteConfig.AGG_TIME, TimeUnit.SECONDS), new InMemoryWindowsStoreFactory(), new Fields("key", "value"), new AggCount(), new Fields("map"))
                .each(new Fields("map"), new KafkaBolt(), new Fields());*/


        topology.newStream("kafka2storm_opaqueTrident", opaqueTridentKafkaSpout)
                .name("one")
                .parallelismHint(FlowWriteConfig.SPOUT_PARALLELISM) //spout的并行度
                .name("two")
                .each(new Fields("str"), new ParseJson2KV(), new Fields("key", "value"))
                .parallelismHint(FlowWriteConfig.DATACENTER_BOLT_PARALLELISM) //处理数据的并行度
                .name("three")
                .slidingWindow(new BaseWindowedBolt.Duration(FlowWriteConfig.AGG_TIME, TimeUnit.SECONDS), new BaseWindowedBolt.Duration(FlowWriteConfig.AGG_TIME, TimeUnit.SECONDS), new InMemoryWindowsStoreFactory(), new Fields("key", "value"), new AggCount(), new Fields("map"))
                .name("four")
                .each(new Fields("map"), new KafkaBolt(), new Fields())
                .name("five")
                .parallelismHint(FlowWriteConfig.KAFKA_BOLT_PARALLELISM) //写到kafka的并行度
                .name("six");

        if(args.length == 0){//本地模式运行

            LocalCluster cluster = new LocalCluster();

            cluster.submitTopology("trident-function", conf, topology.build());
            Thread.sleep(100000);
            cluster.shutdown();
        }else{//集群模式运行
            StormSubmitter.submitTopology(args[0], conf, topology.build());
        }

    }
}
