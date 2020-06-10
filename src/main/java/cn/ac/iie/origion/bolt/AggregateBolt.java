package cn.ac.iie.origion.bolt;


import cn.ac.iie.origion.bean.ValueBean;
import cn.ac.iie.origion.utils.FlowWriteConfig;
import cn.ac.iie.origion.utils.TupleUtils;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
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

    private static HashMap<String, Integer> map = new HashMap();
    private static ValueBean valueBean = new ValueBean();
    private static ValueBean tupleValueBean = new ValueBean();
    private static String key = "";
    private static Integer value = 0;
    private static String message = "";


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        System.out.println("prepare方法执行了++++++++++++++++++++++++++");

        logger.error("prepare方法执行了++++++++++++++++++++++++++");
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

        System.out.println("执行了一次====================================" + value + "==========================" + System.currentTimeMillis());
        try {
            if (TupleUtils.isTick(tuple)) {
                System.out.println(this.map);
                //批量发送到下一个bolt
                basicOutputCollector.emit(new Values(JSON.toJSONString(this.map)));


            } else {
                message = tuple.getString(0);
                //TODO 获取一条tuple数据的key和value

                //TODO 两个count聚合后放入HashMap中，利用HashMap的去重功能实现value的覆盖
                this.map.put("192", addValueBean(value, 1));
            }
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "接收/解析过程出现异常");
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>(16);
        conf.put(org.apache.storm.Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 30);
        return conf;
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("connLog"));
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
        result.setCommon_sessions(result.getCommon_sessions() + 1L);


        return result;
    }

    public Integer addValueBean(Integer result, Integer value2) {

        return result + value2;
    }


}
