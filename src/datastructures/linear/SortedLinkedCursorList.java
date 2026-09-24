package datastructures.linear;

public class SortedLinkedCursorList<T extends Comparable<T>> extends LinkedCursorList<T> {
    @Override
    public void insert(T e) {
        this.begin();
        while (!this.isEnd() && e.compareTo(this.get()) > 0) {
            this.next();
        }
        super.insert(e);
    }
}
