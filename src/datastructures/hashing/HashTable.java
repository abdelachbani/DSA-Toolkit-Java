package datastructures.hashing;

import datastructures.linear.LinkedCursorList;
import datastructures.interfaces.CursorList;
import datastructures.interfaces.Map;

public class HashTable<K, V> implements Map<K, V> {
    public static final double LOAD_FACTOR = 0.75;
    private static boolean REHASHING = true;
    protected CursorList<HashEntry<K, V>>[] buckets;
    protected int size;
    private int rehashCount;

    public static void setRehashing(boolean b) {
        REHASHING = b;
    }

    protected int hashIndex(K k) {
        int hashIndex = k.hashCode() % this.buckets.length;
        if (hashIndex < 0) {
            hashIndex += this.buckets.length;
        }

        return hashIndex;
    }

    @SuppressWarnings("unchecked")
    public HashTable(int estimatedMaxSize) {
        int n = (int)((double)estimatedMaxSize / LOAD_FACTOR);
        int capacity = nextPrime(n);
        this.buckets = new CursorList[capacity];

        for(int i = 0; i < this.buckets.length; ++i) {
            this.buckets[i] = new LinkedCursorList<>();
        }

        this.size = 0;
        this.rehashCount = 0;
    }

    public static final int nextPrime(int n) {
        if (n % 2 == 0) {
            ++n;
        }

        while(!isPrime(n)) {
            n += 2;
        }

        return n;
    }

    protected static final boolean isPrime(int n) {
        for(int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public int getRehashCount() {
        return this.rehashCount;
    }

    private CursorList<HashEntry<K, V>> locate(K k) {
        int pos = this.hashIndex(k);
        CursorList<HashEntry<K, V>> bucket = this.buckets[pos];
        bucket.begin();

        while(!bucket.isEnd() && !bucket.get().key.equals(k)) {
            bucket.next();
        }

        return bucket;
    }

    public V get(K k) {
        V value = null;
        CursorList<HashEntry<K, V>> bucket = this.locate(k);
        if (!bucket.isEnd()) {
            value = bucket.get().value;
        }

        return value;
    }

    public V remove(K k) {
        V value = null;
        CursorList<HashEntry<K, V>> bucket = this.locate(k);
        if (!bucket.isEnd()) {
            value = bucket.get().value;
            bucket.remove();
            --this.size;
        }

        return value;
    }

    public V put(K k, V v) {
        V oldValue = null;
        CursorList<HashEntry<K, V>> bucket = this.locate(k);
        if (bucket.isEnd()) {
            bucket.insert(new HashEntry<>(k, v));
            ++this.size;
            if (this.loadFactor() > LOAD_FACTOR && REHASHING) {
                ++this.rehashCount;
                this.rehash();
            }
        } else {
            oldValue = bucket.get().value;
            bucket.get().value = v;
        }

        return oldValue;
    }

    @SuppressWarnings("unchecked")
    protected final void rehash() {
        int newCapacity = nextPrime(buckets.length * 2);
        CursorList<HashEntry<K, V>>[] oldBuckets = this.buckets;
        this.buckets = new CursorList[newCapacity];

        for(int i = 0; i < this.buckets.length; ++i) {
            this.buckets[i] = new LinkedCursorList<>();
        }

        this.size = 0;

        for(int i = 0; i < oldBuckets.length; ++i) {
            CursorList<HashEntry<K, V>> l = oldBuckets[i];
            l.begin();

            while(!l.isEnd()) {
                HashEntry<K, V> e = l.get();
                CursorList<HashEntry<K, V>> ll = this.buckets[this.hashIndex(e.key)];
                ll.end();
                ll.insert(new HashEntry<>(e.key, e.value));
                ++this.size;
                l.next();
            }
        }
    }

    public CursorList<K> keys() {
        CursorList<K> l = new LinkedCursorList<>();

        for(int i = 0; i < this.buckets.length; ++i) {
            this.buckets[i].begin();

            while(!this.buckets[i].isEnd()) {
                l.insert(this.buckets[i].get().key);
                this.buckets[i].next();
            }
        }

        return l;
    }

    public final double loadFactor() {
        return (double)this.size / (double)this.buckets.length;
    }

    public final String toString() {
        StringBuilder res = new StringBuilder();

        for(CursorList<HashEntry<K, V>> bucket : this.buckets) {
            bucket.begin();

            while(!bucket.isEnd()) {
                res.append(bucket.get() + "\n");
                bucket.next();
            }
        }

        return res.toString();
    }

    public final double standardDeviation() {
        double avgLength = this.loadFactor();
        double res = 0.0;

        for(int i = 0; i < this.buckets.length; ++i) {
            double di = (double)this.buckets[i].size() - avgLength;
            res += di * di;
        }

        return Math.sqrt(res / (double)this.buckets.length);
    }

    public final double averageLookupCost() {
        double collisions = 0.0;

        for(int i = 0; i < this.buckets.length; ++i) {
            collisions += (double)(this.buckets[i].size() * (this.buckets[i].size() - 1) / 2);
        }

        return collisions / (double)this.size;
    }

    public String histogram() {
        String res = "";
        int[] histo = new int[10];

        for(int i = 0; i < this.buckets.length; ++i) {
            int bucketLength = this.buckets[i].size();
            if (bucketLength < 9) {
                histo[bucketLength]++;
            } else {
                histo[9]++;
            }
        }

        for(int i = 0; i < histo.length; ++i) {
            res = res + i + "\t" + histo[i] + "\n";
        }

        return res;
    }

    public CursorList<V> valuesList(int n){
        CursorList<V> res = new LinkedCursorList<>();
        CursorList<K> keyList = this.keys();
        Map<V, Integer> valueFrequency = new HashTable<>(this.size());

        for (keyList.begin(); !keyList.isEnd(); keyList.next()) {
            K k = keyList.get();
            V v = this.get(k);
            valueFrequency.put(v, valueFrequency.get(v) != null ? valueFrequency.get(v) + 1 : 1);
            if (valueFrequency.get(v) >= n) {
                res.insert(v);
            }
        }
        return res;
    }
}
