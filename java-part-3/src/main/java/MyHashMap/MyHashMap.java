package MyHashMap;

public class MyHashMap {

    static class Node {
        String key;
        Integer value;
        Node next;

        Node(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node[] buckets;
    private int capacity= 16;
    private int size = 0;

    public MyHashMap() {
        buckets = new Node[capacity];
    }

    private int getIndex(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(String key, Integer value) {
        int index = getIndex(key);
        Node head = buckets[index];

        for (Node n = head; n != null; n = n.next) {
            if (n.key.equals(key)) {
                n.value = value;
                return;
            }
        }
        Node node = new Node(key, value);
        node.next = head;
        buckets[index] = node;
        size++;
    }

    public Integer get(String key) {
        int index = getIndex(key);
        for (Node n = buckets[index]; n != null; n = n.next) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    public int size() {
        return this.size;
    }

    public boolean containKey(String key) {
        int index = getIndex(key);
        for (Node n = buckets[index]; n != null; n = n.next) {
            if (n.key.equals(key))
                return true;
        }
        return false;
    }

    public Integer remove(String key) {
        int index = getIndex(key);
        Node n = buckets[index];
        Node prev = null;

        while (n != null) {
            if (n.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = n.next;
                } else {
                    prev.next = n.next;
                }
                size--;
                return n.value;
            }
            prev = n;
            n = n.next;
        }
        return null;
    }
}
