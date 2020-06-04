package cn.ac.iie.trident.aggregate;


import com.alibaba.fastjson.JSON;
import cn.ac.iie.trident.aggregate.bean.ValueBean;
import org.apache.storm.tuple.Values;
import org.apache.storm.trident.operation.BaseAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;


import java.util.HashMap;


/**
 * @ClassNameAggCount
 * @Author lixkvip@126.com
 * @Date2020/6/1 10:48
 * @Version V1.0
 **/
public class AggCount extends BaseAggregator<AggCount.State> {


    static class State {
        HashMap<String,ValueBean> map = new HashMap();
        ValueBean valueBean = new ValueBean();
        ValueBean tupleValueBean = new ValueBean();
        String key = "";
    }

    @Override
    public AggCount.State init(Object batchId, TridentCollector collector) {

        return new State();
    }

    /**
     * 聚合一个tuple
     * @param state
     * @param tuple
     * @param collector
     */
    @Override
    public void aggregate(State state, TridentTuple tuple, TridentCollector collector) {


        //TODO 获取一条tuple数据的key和value
        state.key = tuple.getStringByField("key");
        state.tupleValueBean  = JSON.parseObject(tuple.getStringByField("value"),ValueBean.class);


        //TODO 获取HashMap中的key对应的value，如果没有就默认为null
        state.valueBean = state.map.getOrDefault(state.key, new ValueBean());


        //TODO 聚合两个value
        state.valueBean = addValueBean(state.valueBean,state.tupleValueBean);

        //TODO 两个count聚合后放入HashMap中，利用HashMap的去重功能实现value的覆盖
        state.map.put(state.key, state.valueBean);



    }

    /**
     * 处理一批tuple
     * @param state
     * @param collector
     */
    @Override
    public void complete(State state, TridentCollector collector) {

        collector.emit(new Values(JSON.toJSONString(state.map)));


    }

    /**
     * 将两个ValueBean中的相应的属性相加
     * @param result
     * @param value2
     * @return
     */

    public ValueBean addValueBean(ValueBean result, ValueBean value2){

        result.setCommon_s2c_byte_num(result.getCommon_s2c_byte_num() + value2.getCommon_s2c_byte_num());
        result.setCommon_c2s_byte_num(result.getCommon_c2s_byte_num() + value2.getCommon_c2s_byte_num());
        result.setCommon_s2c_pkt_num(result.getCommon_s2c_pkt_num() + value2.getCommon_s2c_pkt_num());
        result.setCommon_c2s_pkt_num(result.getCommon_c2s_pkt_num() + value2.getCommon_c2s_pkt_num());
        result.setCommon_sessions(result.getCommon_sessions() + 1L);


        return result;
    }

}
