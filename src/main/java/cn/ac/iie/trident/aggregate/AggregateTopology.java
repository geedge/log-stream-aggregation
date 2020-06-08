package cn.ac.iie.trident.aggregate;

import cn.ac.iie.trident.aggregate.bolt.KafkaBolt;
import cn.ac.iie.trident.aggregate.spout.TridentKafkaSpout;
import cn.ac.iie.trident.aggregate.utils.FlowWriteConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.trident.OpaqueTridentKafkaSpout;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.windowing.InMemoryWindowsStoreFactory;
import org.apache.storm.tuple.Fields;

import java.util.concurrent.TimeUnit;


/**
 * @ClassNameWcTopo
 * @Author lixkvip@126.com
 * @Date2020/5/29 10:38
 * @Version V1.0
 **/
public class AggregateTopology {




    public static void main(String[] args) {
        //TODO 创建一个topo任务
        TridentTopology topology = new TridentTopology();
        //TODO 为Topo绑定Spout
        OpaqueTridentKafkaSpout opaqueTridentKafkaSpout = TridentKafkaSpout.getInstance();

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

        Config config = new Config();
//        config.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 10);
        config.setDebug(false);
        config.setNumWorkers(FlowWriteConfig.TOPOLOGY_WORKERS); //worker的数量
        LocalCluster cluster = new LocalCluster();


        cluster.submitTopology("trident-wordcount", config, topology.build());
//        StormSubmitter.submitTopology("kafka2storm_opaqueTrident_topology", config,topology.build());
    }
}
