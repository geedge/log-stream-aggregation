package cn.ac.iie.utils.tuple;

public class TwoTuple<A, B> {

    public A first;

    public B second;

    public TwoTuple(){};
    public TwoTuple(A cip, B num) {
        first = cip;
        second = num;
    }

    public String toString() {
        return "[" + first + ", " + second + "]";
    }

    @Override
    public int hashCode() {

        int result = (first != null && second != null) ? (first.toString() + second).hashCode() : 0;

        return result;
    }

    @Override
    public boolean equals(Object o) {

        return this.first.toString().equals(((TwoTuple) o).first.toString()) && this.first.toString().equals(((TwoTuple) o).first.toString());
    }
}
