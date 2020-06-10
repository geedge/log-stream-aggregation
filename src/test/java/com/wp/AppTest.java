package com.wp;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * Unit test for simple App.
 */
public class AppTest{


    @org.junit.Test
    public void test(){

        Config conf = new Config();
//        conf.setDebug(false);
        conf.setMessageTimeoutSecs(60);
        conf.setNumWorkers(1);

        FixedBatchSpout spout = new FixedBatchSpout(new Fields("user", "score"), 3,
                new Values("nickt1", 4),
                new Values("nickt2", 7),
                new Values("nickt3", 8),
                new Values("nickt4", 9),
                new Values("nickt5", 7),
                new Values("nickt6", 11),
                new Values("nickt7", 5)
        );
        spout.setCycle(true);
        TridentTopology topology = new TridentTopology();
        topology.newStream("spout1", spout)
                .batchGlobal()
                .each(new Fields("user"),new Debug("print:"))
                .parallelismHint(5);

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("trident-function", conf, topology.build());

    }

}



