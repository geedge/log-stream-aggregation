package cn.ac.iie.origion.bolt;


import cn.ac.iie.origion.bean.ValueBean;
import cn.ac.iie.origion.utils.FlowWriteConfig;
import cn.ac.iie.origion.utils.TupleUtils;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;


public class AggregateBolt extends BaseBasicBolt {
    private final static Logger logger = Logger.getLogger(AggregateBolt.class);
    private static final long serialVersionUID = 9006119186526123734L;

    private HashMap<String, ValueBean> map;
    private String key = "";
    private ValueBean value;
    private ValueBean mapValue;


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        map = new HashMap<>(16);
        key = "";
        value = new ValueBean();
        mapValue = new ValueBean();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            if (TupleUtils.isTick(tuple)) {
                //TODO 发送到kafka的 bolt
                for (String key : map.keySet()) {
                    basicOutputCollector.emit(new Values(key,map.get(key)));
                }
                map.clear();
            } else {
                //TODO 获取一条tuple数据的key和value
                key = tuple.getString(0);
                value = (ValueBean) tuple.getValue(1);
                //TODO 两个count聚合后放入HashMap中，利用HashMap的去重功能实现value的覆盖
                mapValue = map.getOrDefault(key, new ValueBean());
                mapValue = addValueBean(mapValue, value);
                map.put(key, mapValue);
            }
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "接收/解析过程出现异常");
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>(16);
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, FlowWriteConfig.AGG_TIME);
        return conf;
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
//        outputFieldsDeclarer.declare(new Fields("map"));
        outputFieldsDeclarer.declare(new Fields("key","value"));
    }

    /**
     * 将两个ValueBean中的相应的属性相加
     *
     * @param result
     * @param value2
     * @return
     */

    @SuppressWarnings("all")
    public ValueBean addValueBean(ValueBean result, ValueBean value2) {

        result.setCommon_s2c_byte_num(result.getCommon_s2c_byte_num() + value2.getCommon_s2c_byte_num());
        result.setCommon_c2s_byte_num(result.getCommon_c2s_byte_num() + value2.getCommon_c2s_byte_num());
        result.setCommon_s2c_pkt_num(result.getCommon_s2c_pkt_num() + value2.getCommon_s2c_pkt_num());
        result.setCommon_c2s_pkt_num(result.getCommon_c2s_pkt_num() + value2.getCommon_c2s_pkt_num());
        result.setCommon_sessions(result.getCommon_sessions() + value2.getCommon_sessions());


        return result;
    }

}
