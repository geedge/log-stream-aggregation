package cn.ac.iie.trident.aggregate.utils;


/**
 * @author Administrator
 */
public class FlowWriteConfig {

    public static final int IPV4_TYPE = 1;
    public static final int IPV6_TYPE = 2;
    public static final String FORMAT_SPLITTER = ",";
    /**
     * System
     */
    public static final Integer SPOUT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "spout.parallelism");
    public static final Integer DATACENTER_BOLT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "datacenter.bolt.parallelism");
    public static final Integer TOPOLOGY_WORKERS = FlowWriteConfigurations.getIntProperty(0, "topology.workers");
    public static final Integer KAFKA_BOLT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "kafka.bolt.parallelism");
    public static final Integer TOPOLOGY_NUM_ACKS = FlowWriteConfigurations.getIntProperty(0, "topology.num.acks");
    public static final Integer TOPOLOGY_SPOUT_SLEEP_TIME = FlowWriteConfigurations.getIntProperty(0, "topology.spout.sleep.time");
    public static final Integer BATCH_INSERT_NUM = FlowWriteConfigurations.getIntProperty(0, "batch.insert.num");
    public static final Integer DATA_CENTER_ID_NUM = FlowWriteConfigurations.getIntProperty(0, "data.center.id.num");
    public static final Integer MAX_FAILURE_NUM = FlowWriteConfigurations.getIntProperty(0, "max.failure.num");
    public static final String MAIL_DEFAULT_CHARSET = FlowWriteConfigurations.getStringProperty(0, "mail.default.charset");


    public static final Integer AGG_TIME = FlowWriteConfigurations.getIntProperty(0, "agg.time");


    /**
     * kafka
     */
    public static final String BOOTSTRAP_SERVERS = FlowWriteConfigurations.getStringProperty(0, "bootstrap.servers");
    public static final String ZOOKEEPER_SERVERS = FlowWriteConfigurations.getStringProperty(0, "zookeeper.servers");
    public static final String HBASE_ZOOKEEPER_SERVERS = FlowWriteConfigurations.getStringProperty(0, "hbase.zookeeper.servers");
    public static final String HBASE_TABLE_NAME = FlowWriteConfigurations.getStringProperty(0, "hbase.table.name");
    public static final String GROUP_ID = FlowWriteConfigurations.getStringProperty(0, "group.id");
    public static final String RESULTS_OUTPUT_TOPIC = FlowWriteConfigurations.getStringProperty(0, "results.output.topic");
    public static final String KAFKA_TOPIC = FlowWriteConfigurations.getStringProperty(0, "kafka.topic");
    public static final String AUTO_OFFSET_RESET = FlowWriteConfigurations.getStringProperty(0, "auto.offset.reset");
    public static final String KAFKA_COMPRESSION_TYPE = FlowWriteConfigurations.getStringProperty(0, "kafka.compression.type");

    /**
     * http
     */
    public static final String SCHEMA_HTTP = FlowWriteConfigurations.getStringProperty(0, "schema.http");

}