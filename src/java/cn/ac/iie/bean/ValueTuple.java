package cn.ac.iie.bean;

/**
 * @ClassNameValueTuple
 * @Author lixkvip@126.com
 * @Date2020/5/27 16:18
 * @Version V1.0
 **/
public class ValueTuple {

    private int common_sessions;
    private int common_c2s_pkt_num;
    private int common_s2c_pkt_num;
    private int common_c2s_byte_num;
    private int common_s2c_byte_num;

    public int getCommon_sessions() {
        return common_sessions;
    }

    public void setCommon_sessions(int common_sessions) {
        this.common_sessions = common_sessions;
    }

    public int getCommon_c2s_pkt_num() {
        return common_c2s_pkt_num;
    }

    public void setCommon_c2s_pkt_num(int common_c2s_pkt_num) {
        this.common_c2s_pkt_num = common_c2s_pkt_num;
    }

    public int getCommon_s2c_pkt_num() {
        return common_s2c_pkt_num;
    }

    public void setCommon_s2c_pkt_num(int common_s2c_pkt_num) {
        this.common_s2c_pkt_num = common_s2c_pkt_num;
    }

    public int getCommon_c2s_byte_num() {
        return common_c2s_byte_num;
    }

    public void setCommon_c2s_byte_num(int common_c2s_byte_num) {
        this.common_c2s_byte_num = common_c2s_byte_num;
    }

    public int getCommon_s2c_byte_num() {
        return common_s2c_byte_num;
    }

    public void setCommon_s2c_byte_num(int common_s2c_byte_num) {
        this.common_s2c_byte_num = common_s2c_byte_num;
    }

    @Override
    public String toString() {
        return "ValueTuple{" +
                "common_sessions=" + common_sessions +
                ", common_c2s_pkt_num=" + common_c2s_pkt_num +
                ", common_s2c_pkt_num=" + common_s2c_pkt_num +
                ", common_c2s_byte_num=" + common_c2s_byte_num +
                ", common_s2c_byte_num=" + common_s2c_byte_num +
                '}';
    }
}
