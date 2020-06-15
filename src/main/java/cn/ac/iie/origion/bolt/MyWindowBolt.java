package cn.ac.iie.origion.bolt;

import cn.ac.iie.origion.bean.KeyBean;
import cn.ac.iie.origion.bean.ValueBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassNameMyWindowBolt
 * @Author lixkvip@126.com
 * @Date2020/6/9 14:45
 * @Version V1.0
 **/
public class MyWindowBolt extends BaseWindowedBolt {


    private static OutputCollector collector;
    private HashMap<String, ValueBean> map;
    private String message;


    @Override

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        map = new HashMap<>(16);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("key","value"));
//        declarer.declare(new Fields("map"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return super.getComponentConfiguration();
    }

    @Override
    public void execute(TupleWindow inputWindow) {
        //TODO 清空上一个窗口聚合的数据
        map.clear();

        //TODO 遍历一个窗口的数据
        for (Tuple tuple : inputWindow.getNew()) {

            //TODO 将一个Tuple解析成String
            message = tuple.getStringByField("source");

            //TODO 获取Tuple中的value Bean
            ValueBean valueBean = JSONObject.parseObject(message, ValueBean.class);
            //TODO 获取Tuple中的key String
            String key = JSONObject.toJSONString(JSONObject.parseObject(message, KeyBean.class));
            //TODO 获取map中的value Bean
            ValueBean mapValueBean = map.getOrDefault(key, new ValueBean());
            //TODO 将tuple中的value和map中的value做累加
            mapValueBean = addValueBean(mapValueBean, valueBean);

            //TODO 将累加后的结果放到map中
            map.put(key, mapValueBean);
        }

        //TODO 遍历map将 K V发送出去
        for (String key : map.keySet()) {
            collector.emit(new Values(key,map.get(key)));
        }
//        collector.emit(new Values(map));

    }

    @SuppressWarnings("all")
    /**
     * 将两个ValueBean中的相应的属性相加
     * @param result
     * @param value2
     * @return
     */

    public ValueBean addValueBean(ValueBean result, ValueBean value2) {

        result.setCommon_s2c_byte_num(result.getCommon_s2c_byte_num() + value2.getCommon_s2c_byte_num());
        result.setCommon_c2s_byte_num(result.getCommon_c2s_byte_num() + value2.getCommon_c2s_byte_num());
        result.setCommon_s2c_pkt_num(result.getCommon_s2c_pkt_num() + value2.getCommon_s2c_pkt_num());
        result.setCommon_c2s_pkt_num(result.getCommon_c2s_pkt_num() + value2.getCommon_c2s_pkt_num());
        result.setCommon_sessions(result.getCommon_sessions() + 1L);
        return result;
    }


}
