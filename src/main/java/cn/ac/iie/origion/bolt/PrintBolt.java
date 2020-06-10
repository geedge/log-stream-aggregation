package cn.ac.iie.origion.bolt;

import cn.ac.iie.origion.bean.ValueBean;

import cn.ac.iie.origion.utils.FlowWriteConfig;
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
 * @ClassNamePrintBolt
 * @Author lixkvip@126.com
 * @Date2020/6/9 13:53
 * @Version V1.0
 **/
public class PrintBolt extends BaseBasicBolt {

    private static final long serialVersionUID = -3663610927224396615L;
    private static Logger logger = Logger.getLogger(NtcLogSendBolt.class);


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
    }

    @SuppressWarnings("all")
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            HashMap<String,ValueBean> hashMap = (HashMap) tuple.getValue(0);

            if (hashMap.size() != 0) {
                for (String key : hashMap.keySet()) {
                    System.out.println(key);
                    System.out.println(JSONObject.toJSONString(hashMap.get(key)));
                }

            }
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志发送Kafka过程出现异常");
            e.printStackTrace();
        }
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
