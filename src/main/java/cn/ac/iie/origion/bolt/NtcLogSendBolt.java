package cn.ac.iie.origion.bolt;


import cn.ac.iie.origion.bean.ConnectionRecordLog;
import cn.ac.iie.origion.bean.ValueBean;
import cn.ac.iie.origion.utils.FlowWriteConfig;
import cn.ac.iie.origion.utils.KafkaLogNtc;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送到kafka的bolt
 */
public class NtcLogSendBolt extends BaseBasicBolt {


    private static final long serialVersionUID = 3237813470939823159L;
    private static Logger logger = Logger.getLogger(NtcLogSendBolt.class);
    private KafkaLogNtc kafkaLogNtc;

    private static ConnectionRecordLog connectionRecordLog;


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        kafkaLogNtc = KafkaLogNtc.getInstance();
        connectionRecordLog = new ConnectionRecordLog();
    }
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            HashMap<String,ValueBean> hashMap = (HashMap) tuple.getValue(0);
            if (hashMap.size() != 0) {
                for (String key : hashMap.keySet()) {
                    JSONObject keys = JSONObject.parseObject(key);

                    ValueBean valueBean = hashMap.get(key);
                    connectionRecordLog.setCommon_recv_time(System.currentTimeMillis());
                    connectionRecordLog.setCommon_policy_id(Integer.parseInt(keys.getString("common_policy_id")));
                    connectionRecordLog.setCommon_action(Integer.parseInt(keys.getString("common_action")));
                    connectionRecordLog.setCommon_sub_action(keys.getString("common_sub_action"));
                    connectionRecordLog.setCommon_client_ip(keys.getString("common_client_ip"));
                    connectionRecordLog.setCommon_client_location(keys.getString("common_client_location"));
                    connectionRecordLog.setCommon_sled_ip(keys.getString("common_sled_ip"));
                    connectionRecordLog.setCommon_device_id(keys.getString("common_device_id"));
                    connectionRecordLog.setCommon_subscriber_id(keys.getString("common_subscriber_id"));
                    connectionRecordLog.setCommon_server_ip(keys.getString("common_server_ip"));
                    connectionRecordLog.setCommon_server_location(keys.getString("common_server_location"));
                    connectionRecordLog.setCommon_server_port(Integer.parseInt(keys.getString("common_server_port")));
                    connectionRecordLog.setCommon_l4_protocol(keys.getString("common_l4_protocol"));
                    connectionRecordLog.setHttp_domain(keys.getString("http_domain"));
                    connectionRecordLog.setSsl_sni(keys.getString("ssl_sni"));

                    //TODO 为Value赋值

                    connectionRecordLog.setCommon_sessions(valueBean.getCommon_sessions());
                    connectionRecordLog.setCommon_c2s_pkt_num(valueBean.getCommon_c2s_pkt_num());
                    connectionRecordLog.setCommon_s2c_pkt_num(valueBean.getCommon_s2c_pkt_num());
                    connectionRecordLog.setCommon_c2s_byte_num(valueBean.getCommon_c2s_byte_num());
                    connectionRecordLog.setCommon_s2c_byte_num(valueBean.getCommon_s2c_byte_num());

                    kafkaLogNtc.sendMessage(JSONObject.toJSONString(connectionRecordLog));

                }
            }
            } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志发送Kafka过程出现异常");
            e.printStackTrace();
        }
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
