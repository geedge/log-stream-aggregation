package cn.ac.iie.utils.tuple;

public class ThreeTuple<A,B,C> {

    public String first;

    public long second;

    public int third;

    public ThreeTuple(String name,long time, int sum){
        first = name;
        second = time;
        third = sum;
    }

    public String toString(){
        return "[" + first + ", " + second + ", " + third + "]";
    }
}
