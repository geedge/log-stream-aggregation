package cn.ac.iie.trident.aggregate.bean;

/**
 * @ClassNameKeyBean
 * @Author lixkvip@126.com
 * @Date2020/6/3 18:52
 * @Version V1.0
 **/
public class KeyBean {

    private long common_recv_time;
    private int common_policy_id;
    private int common_action;
    private String common_sub_action;
    private String common_client_ip;
    private String common_client_location;
    private String common_sled_ip;
    private String common_device_id;
    private String common_subscriber_id;
    private String common_server_ip;
    private String common_server_location;
    private int common_server_port;
    private String common_l4_protocol;
    private String http_domain;
    private String ssl_sni;

    public long getCommon_recv_time() {
        return common_recv_time;
    }

    public void setCommon_recv_time(long common_recv_time) {
        this.common_recv_time = common_recv_time;
    }

    public int getCommon_policy_id() {
        return common_policy_id;
    }

    public void setCommon_policy_id(int common_policy_id) {
        this.common_policy_id = common_policy_id;
    }

    public int getCommon_action() {
        return common_action;
    }

    public void setCommon_action(int common_action) {
        this.common_action = common_action;
    }

    public String getCommon_sub_action() {
        return common_sub_action;
    }

    public void setCommon_sub_action(String common_sub_action) {
        this.common_sub_action = common_sub_action;
    }

    public String getCommon_client_ip() {
        return common_client_ip;
    }

    public void setCommon_client_ip(String common_client_ip) {
        this.common_client_ip = common_client_ip;
    }

    public String getCommon_client_location() {
        return common_client_location;
    }

    public void setCommon_client_location(String common_client_location) {
        this.common_client_location = common_client_location;
    }

    public String getCommon_sled_ip() {
        return common_sled_ip;
    }

    public void setCommon_sled_ip(String common_sled_ip) {
        this.common_sled_ip = common_sled_ip;
    }

    public String getCommon_device_id() {
        return common_device_id;
    }

    public void setCommon_device_id(String common_device_id) {
        this.common_device_id = common_device_id;
    }

    public String getCommon_subscriber_id() {
        return common_subscriber_id;
    }

    public void setCommon_subscriber_id(String common_subscriber_id) {
        this.common_subscriber_id = common_subscriber_id;
    }

    public String getCommon_server_ip() {
        return common_server_ip;
    }

    public void setCommon_server_ip(String common_server_ip) {
        this.common_server_ip = common_server_ip;
    }

    public String getCommon_server_location() {
        return common_server_location;
    }

    public void setCommon_server_location(String common_server_location) {
        this.common_server_location = common_server_location;
    }

    public int getCommon_server_port() {
        return common_server_port;
    }

    public void setCommon_server_port(int common_server_port) {
        this.common_server_port = common_server_port;
    }

    public String getCommon_l4_protocol() {
        return common_l4_protocol;
    }

    public void setCommon_l4_protocol(String common_l4_protocol) {
        this.common_l4_protocol = common_l4_protocol;
    }

    public String getHttp_domain() {
        return http_domain;
    }

    public void setHttp_domain(String http_domain) {
        this.http_domain = http_domain;
    }

    public String getSsl_sni() {
        return ssl_sni;
    }

    public void setSsl_sni(String ssl_sni) {
        this.ssl_sni = ssl_sni;
    }

    @Override
    public int hashCode() {

        return ("" + getCommon_recv_time() + getCommon_policy_id() + getCommon_action() + getCommon_sub_action() + getCommon_client_ip() + getCommon_client_location() + getCommon_sled_ip() + getCommon_device_id() + getCommon_subscriber_id() + getCommon_server_ip() + getCommon_server_location() + getCommon_server_port() + getCommon_l4_protocol() + getHttp_domain() + getSsl_sni()).hashCode();

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof KeyBean) {
            KeyBean keyBean = (KeyBean) o;
            return (this.getCommon_recv_time()==(keyBean.getCommon_recv_time()) &&
                    this.getCommon_policy_id()==(keyBean.getCommon_policy_id()) &&
                    this.getCommon_action()==(keyBean.getCommon_action()) &&
                    this.getCommon_sub_action().equals(keyBean.getCommon_sub_action()) &&
                    this.getCommon_client_ip().equals(keyBean.getCommon_client_ip()) &&
                    this.getCommon_client_location().equals(keyBean.getCommon_client_location()) &&
                    this.getCommon_sled_ip().equals(keyBean.getCommon_sled_ip()) &&
                    this.getCommon_device_id().equals(keyBean.getCommon_device_id()) &&
                    this.getCommon_subscriber_id().equals(keyBean.getCommon_subscriber_id()) &&
                    this.getCommon_server_ip().equals(keyBean.getCommon_server_ip()) &&
                    this.getCommon_server_location().equals(keyBean.getCommon_server_location()) &&
                    this.getCommon_server_port()==(keyBean.getCommon_server_port()) &&
                    this.getCommon_l4_protocol().equals(keyBean.getCommon_l4_protocol()) &&
                    this.getHttp_domain().equals(keyBean.getHttp_domain()) &&
                    this.getSsl_sni().equals(keyBean.getSsl_sni()));
        }
        return false;
    }
}
