package cn.ac.iie.trident.aggregate.spout;

import cn.ac.iie.trident.aggregate.utils.FlowWriteConfig;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.trident.OpaqueTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;

/**
 * @ClassNameKafkaSpout
 * @Author lixkvip@126.com
 * @Date2020/6/4 11:55
 * @Version V1.0
 **/
public class TridentKafkaSpout {


    /**
     * kafka生产者适配器（单例），用来代理kafka生产者发送消息
     */
    private static OpaqueTridentKafkaSpout opaqueTridentKafkaSpout;

    public static OpaqueTridentKafkaSpout getInstance() {
        if (opaqueTridentKafkaSpout == null) {

            BrokerHosts zkHosts = new ZkHosts(FlowWriteConfig.ZOOKEEPER_SERVERS);
            TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(zkHosts, FlowWriteConfig.KAFKA_TOPIC);
            kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
            kafkaConfig.startOffsetTime = -1L;
            kafkaConfig.socketTimeoutMs=60000;

            //不透明事务型Spout
            opaqueTridentKafkaSpout = new OpaqueTridentKafkaSpout(kafkaConfig);
        }
        return opaqueTridentKafkaSpout;
    }

}
