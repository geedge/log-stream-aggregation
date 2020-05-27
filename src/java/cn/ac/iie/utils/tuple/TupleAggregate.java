package cn.ac.iie.utils.tuple;


import com.alibaba.fastjson.JSON;


import java.util.HashMap;

public class TupleAggregate {


    private static TwoTuple<String, Integer> a = new TwoTuple<>("192.168.40.101", 1);
    private static TwoTuple<String, Integer> b = new TwoTuple<>("192.168.40.101", 1);

    private static ThreeTuple<String, Long, Integer> a1 = new ThreeTuple<>("lxk", 30L, 2);
    private static ThreeTuple<String, Long, Integer> b1 = new ThreeTuple<>("lxk", 20L, 2);


    public static TwoTuple<TwoTuple, ThreeTuple> parseJsonToTuple(String json) {

        CONN conn = JSON.parseObject(json, CONN.class);
        //二元组 key
        TwoTuple key = new TwoTuple<>(conn.getCip(), conn.getNum());
        //三元组 value
        ThreeTuple value = new ThreeTuple<>(conn.getName(), conn.getTime(), conn.getSum());


        return new TwoTuple<>(key, value);
    }

    /**
     * 聚合两个三元组
     *
     * @param tuple1
     * @param tuple2
     * @return
     */
    public static ThreeTuple addTuple(ThreeTuple tuple1, ThreeTuple tuple2) {


        tuple1.second += tuple2.second;
        tuple1.third += tuple2.third;

        return tuple1;

    }

    /**
     * 将一条新数据累加到HashMap中
     *
     * @param map
     * @param json
     * @return map1
     */
    public static HashMap aggregate(HashMap<TwoTuple, ThreeTuple> map, String json) {


        //TODO  json解析成对象，取出key聚合组成tuple 与HashMap中具有相同key的聚合

        /**
         * 还存在的问题
         * 1. key是对象 就算值一样也不会相同 (重写HashCode和equal方法)
         *
         * 2. 拿key去map中取值，如果为null，后面聚合会报错 空指针异常
         */

        //一条日志 ==》 两元组
        TwoTuple<TwoTuple, ThreeTuple> tuple = parseJsonToTuple(json);
        //取出key
        TwoTuple key = tuple.first;
        //内存中的HashMap中获取具有相同key的value
        ThreeTuple value = map.get(key);
        //将两个value聚合，赋值给value
        value = addTuple(value, tuple.second);
        //聚合的结果放回到内存中的HashMap
        map.put(key, value);

        return map;

    }

    public static void main(String[] args) {

//        HashMap<TwoTuple, ThreeTuple> map1 = new HashMap<>();
//        a1 = addTuple(a1, b1);
//        System.out.println("聚合成功:" + a1);


        CONN conn1 = new CONN();
        CONN conn2 = new CONN();

        conn1.setCip("192.168.40.101");
        conn2.setCip("192.168.40.101");

        conn1.setNum(1);
        conn2.setNum(1);

        conn1.setName("lxk");
        conn2.setName("lxk");

        conn1.setTime(100L);
        conn2.setTime(200L);

        conn1.setSum(10);
        conn2.setSum(20);

        System.out.println("conn1" + conn1);
        System.out.println("conn2" + conn2);

        String json1 = JSON.toJSONString(conn1);
        String json2 = JSON.toJSONString(conn2);


        HashMap map = new HashMap<>();

        map.put(a, a1);

        System.out.println("开始的map：" + map);

        map = aggregate(map, json1);

        System.out.println("后来的map：" + map);

        System.out.println("a的HashCode: " + a.hashCode());
        System.out.println("b的HashCode: " + b.hashCode());



    }


}

class CONN {

    //二元组使用的 key
    private String cip;
    private int num;

    //三元组使用的  value
    private String name;
    private long time;
    private int sum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CONN{" +
                "cip='" + cip + '\'' +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", sum=" + sum +
                '}';
    }
}