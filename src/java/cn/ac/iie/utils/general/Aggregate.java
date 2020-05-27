package cn.ac.iie.utils.general;

import cn.ac.iie.bean.ValueTuple;
import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.json.JsonParseUtil;
import cn.ac.iie.utils.tuple.TwoTuple;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.utils.StringUtil;

import java.util.HashMap;

public class Aggregate {


    /**
     * 在内存中加载反射类用的map
     */
    private static HashMap<String, Class> map = JsonParseUtil.getMapFromHttp(FlowWriteConfig.SCHEMA_HTTP);

    /**
     * 反射成一个类
     */
    private static Object mapObject = JsonParseUtil.generateObject(map);

    private static String key;

    private static HashMap<String, ValueTuple> resultMap = new HashMap<>();

    private static Object conn;

    private static ValueTuple valueTuple = new ValueTuple();

    private static String test = "{\"bgp_as_num\":\"100\",\"bgp_route\":\"192.168.222.0/24\",\"bgp_type\":1,\"common_action\":4,\"common_address_list\":\"\",\"common_address_type\":4,\"common_app_id\":0,\"common_app_label\":\"\",\"common_c2s_byte_num\":650,\"common_c2s_pkt_num\":7,\"common_client_asn\":\"9198\",\"common_client_ip\":\"95.56.198.87\",\"common_client_location\":\"Pervomayskiy,Almaty oblysy,哈萨克斯坦\",\"common_client_port\":13555,\"common_con_duration_ms\":154122,\"common_device_id\":\"2506398\",\"common_direction\":0,\"common_encapsulation\":0,\"common_end_time\":1590388545,\"common_entrance_id\":20,\"common_has_dup_traffic\":1,\"common_isp\":\"CMCC\",\"common_l4_protocol\":\"VLAN\",\"common_link_id\":1,\"common_log_id\":126995036504993794,\"common_policy_id\":0,\"common_protocol_id\":0,\"common_recv_time\":1590388694,\"common_s2c_byte_num\":9921,\"common_s2c_pkt_num\":21,\"common_schema_type\":\"SSL\",\"common_server_asn\":\"12876\",\"common_server_ip\":\"62.210.101.44\",\"common_server_location\":\"法国\",\"common_server_port\":22,\"common_service\":7,\"common_sled_ip\":\"192.168.10.36\",\"common_start_time\":1590388490,\"common_stream_dir\":2,\"common_stream_error\":\"\",\"common_stream_trace_id\":6193492085736674541,\"common_user_region\":\"prysUgOCWSmUYcGRL5rcUvVc8zbI9MOtlb9KOvH8rZCMRVqnIEyQVtQfBp94IIIjha24tGQ4x33qtC3jSx8udADkuezGGzrTrxCB\",\"common_user_tags\":\"9PD3v4GaIgS97l4wQnRtahW00YBi3933RDQg8PpiB8R9ftXhELHploJ0Ocg1Pj0xH06svaPbY2Tp1Chb0usQPttRqhpNbHTkW3En\",\"dns_aa\":0,\"dns_ancount\":64,\"dns_arcount\":22,\"dns_cname\":\"bFh2JvWJMWTCNcVEyuroMimLhoNM3O4effDDiNA9SRlCFdzaev10\",\"dns_message_id\":744559,\"dns_nscount\":59,\"dns_opcode\":0,\"dns_qclass\":2,\"dns_qdcount\":26,\"dns_qname\":\"kankanews.com12041281\",\"dns_qr\":1,\"dns_qtype\":5,\"dns_ra\":0,\"dns_rcode\":9,\"dns_rd\":0,\"dns_rr\":\"{\\\"aEWseVK\\\":\\\"UEUZ4qlk8qOjJBZ4\\\",\\\"9jGNxy0\\\":\\\"s075dZOXDXZ\\\",\\\"yyNXYD9G\\\":\\\"EEKxB99FuYDHH2E6NrV\\\",\\\"al23zn\\\":\\\"4dX1qd5L0A\\\"}\",\"dns_sub\":1,\"dns_tc\":0,\"ftp_account\":\"JXU2RDRCJXU4QkQ1\",\"ftp_content\":\"JXU2RDRCJXU4QkQ1\",\"ftp_url\":\"ftp://test:test@76.95.92.196/soft/list.txt\",\"http_content_length\":\"37339\",\"http_content_type\":\"application/x-javascript\",\"http_cookie\":\"BD_UPN=12314753\",\"http_domain\":\"163.com\",\"http_host\":\"v.163.com\",\"http_proxy_flag\":1,\"http_referer\":\"https://www.baidu.com/\",\"http_request_body\":\"\",\"http_request_body_key\":\"\",\"http_request_header\":\"\",\"http_request_line\":\"GET www.baidu.com/ HTTP/1.1\",\"http_response_body\":\"\",\"http_response_body_key\":\"\",\"http_response_header\":\"\",\"http_response_line\":\"HTTP/1.1 200 OK\",\"http_sequence\":9,\"http_set_cookie\":\"delPer=0; path=/; domain=.baidu.com\",\"http_snapshot\":\"\",\"http_url\":\"http://v.163.com/movie/2011/7/0/3/M7B9K1R60_M7BAANT03.html\",\"http_user_agent\":\"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36\",\"http_version\":\"http1\",\"mail_account\":\"123456789\",\"mail_attachment_content\":\"\",\"mail_attachment_name\":\"%u6D4B%u8BD5%u90AE%u4EF6\",\"mail_attachment_name_charset\":\"UTF-8\",\"mail_bcc\":\"\",\"mail_cc\":\"0yrj459uw4c@msn.com\",\"mail_content\":\"\",\"mail_content_charset\":\"\",\"mail_eml_file\":\"\",\"mail_from\":\"jcgkljyfkx@msn.com\",\"mail_from_cmd\":\"5pk@163.net\",\"mail_protocol_type\":\"POP3\",\"mail_snapshot\":\"\",\"mail_subject\":\"%u6D4B%u8BD5%u90AE%u4EF6\",\"mail_subject_charset\":\"UTF-8\",\"mail_to\":\"cbs@yahoo.com\",\"mail_to_cmd\":\"xlk7nj@hotmail.com\",\"ssl_cert_verify\":0,\"ssl_client_side_latency\":5237,\"ssl_client_side_version\":\"SSLv3\",\"ssl_cn\":\"\",\"ssl_con_latency_ms\":3,\"ssl_error\":\"\",\"ssl_intercept_state\":0,\"ssl_pinningst\":1,\"ssl_san\":\"\",\"ssl_server_side_latency\":4644,\"ssl_server_side_version\":\"TLSv1.1\",\"ssl_sni\":\"cztv.com11547021\",\"ssl_version\":\"V3\",\"streaming_media_protocol\":\"RTP\",\"streaming_media_url\":\"http://home.sogua.com/lujingai/mv/play.aspx?id=30195689\",\"voip_called_account\":\"13307536537\",\"voip_called_number\":\"15301710004\",\"voip_calling_account\":\"15901848931\",\"voip_calling_number\":\"13908208553\"}";


    public static void main(String[] args) {



        resultMap = aggregateJsonToMap(resultMap, test);

        System.out.println("聚合一次之后： " + resultMap.get(key));

        resultMap = aggregateJsonToMap(resultMap, test);

        System.out.println("聚合两次之后： " + resultMap.get(key));


    }

    /**
     * 将一条新数据累加到HashMap中
     * @param map
     * @param message
     * @return
     */
    public static HashMap<String, ValueTuple> aggregateJsonToMap(HashMap<String,ValueTuple> map, String message) {

        ValueTuple valueTuple = JSON.parseObject(message, ValueTuple.class);

        key = getKey(message);

        map.put(key,addTuple(map.get(key), valueTuple));

        return map;
    }

    /**
     * 两个ValueTuple类型的对象做相应属性的聚合
     * @param result
     * @param message
     * @return
     */
    public static ValueTuple addTuple(ValueTuple result,ValueTuple message){

        if (result == null){

            result = new ValueTuple();
        }

        result.setCommon_s2c_byte_num(result.getCommon_s2c_byte_num() + message.getCommon_s2c_byte_num());
        result.setCommon_c2s_byte_num(result.getCommon_c2s_byte_num() + message.getCommon_c2s_byte_num());
        result.setCommon_s2c_pkt_num(result.getCommon_s2c_pkt_num() + message.getCommon_s2c_pkt_num());
        result.setCommon_c2s_pkt_num(result.getCommon_c2s_pkt_num() + message.getCommon_c2s_pkt_num());
        result.setCommon_sessions(result.getCommon_sessions() + message.getCommon_sessions());

        return result;
    }

    public static String getKey(String message){
        Object conn = JSONObject.parseObject(message, mapObject.getClass());
        //TODO key
        Object common_policy_id = JsonParseUtil.getValue(conn, "common_policy_id");
        Object common_action = JsonParseUtil.getValue(conn, "common_action");
        Object common_sub_action = JsonParseUtil.getValue(conn, "common_sub_action");
        Object common_client_ip = JsonParseUtil.getValue(conn, "common_client_ip");
        Object common_client_location = JsonParseUtil.getValue(conn, "common_client_location");
        Object common_sled_ip = JsonParseUtil.getValue(conn, "common_sled_ip");
        Object common_device_id = JsonParseUtil.getValue(conn, "common_device_id");
        Object common_subscriber_id = JsonParseUtil.getValue(conn, "common_subscriber_id");
        Object common_server_ip = JsonParseUtil.getValue(conn, "common_server_ip");
        Object common_server_location = JsonParseUtil.getValue(conn, "common_server_location");
        Object common_server_port = JsonParseUtil.getValue(conn, "common_server_port");
        Object common_l4_protocol = JsonParseUtil.getValue(conn, "common_l4_protocol");
        Object http_domain = JsonParseUtil.getValue(conn, "http_domain");
        Object ssl_sni = JsonParseUtil.getValue(conn, "ssl_sni");


        StringBuilder builder = new StringBuilder();
        builder.append(common_policy_id).append("_")
                .append(common_action).append("_")
                .append(common_sub_action).append("_")
                .append(common_client_ip).append("_")
                .append(common_client_location).append("_")
                .append(common_sled_ip).append("_")
                .append(common_device_id).append("_")
                .append(common_subscriber_id).append("_")
                .append(common_server_ip).append("_")
                .append(common_server_location).append("_")
                .append(common_server_port).append("_")
                .append(common_l4_protocol).append("_")
                .append(http_domain).append("_")
                .append(ssl_sni);

        return builder.toString();
    }


/*    public static ValueTuple getValueTuple(String message){

        conn = JSONObject.parseObject(message, mapObject.getClass());
        Object common_sessions = JsonParseUtil.getValue(conn, "common_sessions");

        if (StringUtil.isEmpty(common_sessions)) {
            common_sessions = 0;
        }
        Object common_c2s_pkt_num = JsonParseUtil.getValue(conn, "common_c2s_pkt_num");
        Object common_s2c_pkt_num = JsonParseUtil.getValue(conn, "common_s2c_pkt_num");
        Object common_c2s_byte_num = JsonParseUtil.getValue(conn, "common_c2s_byte_num");
        Object common_s2c_byte_num = JsonParseUtil.getValue(conn, "common_s2c_byte_num");

        valueTuple.setCommon_sessions((int) common_sessions);
        valueTuple.setCommon_c2s_pkt_num((int) common_c2s_pkt_num);
        valueTuple.setCommon_s2c_pkt_num((int) common_s2c_pkt_num);
        valueTuple.setCommon_c2s_byte_num((int) common_c2s_byte_num);
        valueTuple.setCommon_s2c_byte_num((int) common_s2c_byte_num);

        return valueTuple;

    }*/
}
