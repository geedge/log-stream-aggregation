package cn.ac.iie.trident.aggregate.bolt;

import cn.ac.iie.trident.aggregate.bean.ConnectionRecordLog;
import cn.ac.iie.trident.aggregate.bean.ValueBean;
import cn.ac.iie.trident.aggregate.utils.KafkaLogNtc;
import com.alibaba.fastjson.JSONObject;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * @ClassNameKafkaBolt
 * @Author lixkvip@126.com
 * @Date2020/6/3 16:50
 * @Version V1.0
 **/
public class KafkaBolt extends BaseFunction {


    private static final long serialVersionUID = -2107081139682355171L;
    private static KafkaLogNtc kafkaLogNtc;
    private static ConnectionRecordLog connectionRecordLog = new ConnectionRecordLog();
    private static JSONObject jsonObject;

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {

        if (kafkaLogNtc == null) {
            kafkaLogNtc = KafkaLogNtc.getInstance();
        }

        //TODO 解析成json对象，方便以后的遍历
         jsonObject = JSONObject.parseObject(tuple.getStringByField("map"));

        for (String key : jsonObject.keySet()) {

            //TODO 为Key赋值

            JSONObject keys = JSONObject.parseObject(key);

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

            ValueBean valueBean = JSONObject.parseObject(jsonObject.get(key).toString(), ValueBean.class);
            connectionRecordLog.setCommon_sessions(valueBean.getCommon_sessions());
            connectionRecordLog.setCommon_c2s_pkt_num(valueBean.getCommon_c2s_pkt_num());
            connectionRecordLog.setCommon_s2c_pkt_num(valueBean.getCommon_s2c_pkt_num());
            connectionRecordLog.setCommon_c2s_byte_num(valueBean.getCommon_c2s_byte_num());
            connectionRecordLog.setCommon_s2c_byte_num(valueBean.getCommon_s2c_byte_num());

            kafkaLogNtc.sendMessage(JSONObject.toJSONString(connectionRecordLog));


        }


    }
}
