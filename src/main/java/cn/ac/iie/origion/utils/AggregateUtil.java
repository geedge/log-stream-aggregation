package cn.ac.iie.origion.utils;


import cn.ac.iie.origion.bean.KeyBean;
import cn.ac.iie.origion.bean.ValueBean;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @ClassNameAggregateUtil
 * @Author lixkvip@126.com
 * @Date2020/6/9 11:44
 * @Version V1.0
 **/
public class AggregateUtil {


    private static ValueBean valueBean = new ValueBean();
    private static KeyBean keyBean = new KeyBean();

    /**
     * 接收到的是一个json字符串，该方法将其解析成 Bean
     * @param message
     * @return
     */
    public static String aggregate(String message){



        //TODO 获取tuple输入内容,解析成map
        Map map = JSONObject.parseObject(message);


        //TODO KEY

        keyBean.setCommon_policy_id(Integer.parseInt(map.getOrDefault("common_policy_id","0").toString()));
        keyBean.setCommon_action(Integer.parseInt(map.getOrDefault("common_action","0").toString()));
        keyBean.setCommon_sub_action(map.getOrDefault("common_sub_action","").toString());
        keyBean.setCommon_client_ip(map.getOrDefault("common_client_ip","").toString());
        keyBean.setCommon_client_location(map.getOrDefault("common_client_location","").toString());
        keyBean.setCommon_sled_ip(map.getOrDefault("common_sled_ip","").toString());
        keyBean.setCommon_device_id(map.getOrDefault("common_device_id","").toString());
        keyBean.setCommon_subscriber_id(map.getOrDefault("common_subscriber_id","").toString());
        keyBean.setCommon_server_ip(map.getOrDefault("common_server_ip","").toString());
        keyBean.setCommon_server_location(map.getOrDefault("common_server_location","").toString());
        keyBean.setCommon_server_port(Integer.parseInt(map.getOrDefault("common_server_port","0" ).toString()));
        keyBean.setCommon_l4_protocol(map.getOrDefault("common_l4_protocol","").toString());
        keyBean.setHttp_domain(map.getOrDefault("http_domain","").toString());
        keyBean.setSsl_sni(map.getOrDefault("ssl_sni","").toString());

        //TODO VALUE


        valueBean.setCommon_c2s_pkt_num(Long.parseLong(map.getOrDefault("common_c2s_pkt_num",0).toString()));
        valueBean.setCommon_s2c_pkt_num(Long.parseLong(map.getOrDefault("common_s2c_pkt_num",0).toString()));
        valueBean.setCommon_c2s_byte_num(Long.parseLong(map.getOrDefault("common_c2s_byte_num",0).toString()));
        valueBean.setCommon_s2c_byte_num(Long.parseLong(map.getOrDefault("common_s2c_byte_num",0).toString()));
        valueBean.setCommon_sessions(Long.parseLong(map.getOrDefault("common_sessions",0).toString()));



        return message;
    }
}
