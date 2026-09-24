package tests;

import datastructures.hashing.HashTable;

public class HashTableTest {
    private HashTableTest() {
    }

    public static void main(String[] args) {
        System.out.println();
        boolean stdDev = testStandardDeviation();
        if (!stdDev) {
            System.out.println(":( standardDeviation is NOT correct");
        }

        boolean avgCost = testAverageLookupCost();
        if (!avgCost) {
            System.out.println(":( averageLookupCost is NOT correct");
        }

        boolean rehash = testRehashing();
        if (!rehash) {
            System.out.println(":( rehashing is NOT correct");
        }

        if (stdDev && avgCost && rehash) {
            System.out.println("\n:) HashTable code correct!");
        }
    }

    private static boolean testRehashing() {
        System.out.print("Test rehashing... ");
        boolean ok = true;
        String res = "";
        HashTable<Integer, Integer> t = new HashTable<>(10);
        int t1 = getBucketsLength(t);
        int nrH1 = 1 + (int)((double)t1 * 0.75);

        for(int i = 0; i < nrH1 - 1; ++i) {
            t.put(i, 0);
        }

        t.put(29, 0);
        if (t.size() != nrH1) {
            ok = false;
            res = res + "\n\tIncorrect size";
        } else {
            int t2 = getBucketsLength(t);
            int t2Ref = newCapacity(t1);
            if (t2 != t2Ref) {
                ok = false;
                System.out.println(t2 + " " + t2Ref);
                res = res + "\n\tCapacity of the new array INcorrect ";
            } else {
                for(int i = 0; i < nrH1 - 1 && ok; ++i) {
                    Integer oi = t.get(i);
                    if (oi == null) {
                        ok = false;
                        res = res + "\n\tNot all data was copied";
                    }
                }

                if (ok) {
                    Integer oi = t.get(29);
                    if (oi == null) {
                        ok = false;
                        res = res + "\n\tNot all data was copied";
                    }
                }
            }
        }

        if (res.equals("")) {
            res = "ok!";
        }

        System.out.println(res);
        return ok;
    }

    private static int getBucketsLength(HashTable<?, ?> t) {
        try {
            java.lang.reflect.Field f = t.getClass().getDeclaredField("buckets");
            f.setAccessible(true);
            Object[] arr = (Object[]) f.get(t);
            return arr.length;
        } catch (Exception e) { return 0; }
    }
    
    private static int getBucketSize(HashTable<?, ?> t, int i) {
        try {
            java.lang.reflect.Field f = t.getClass().getDeclaredField("buckets");
            f.setAccessible(true);
            Object[] arr = (Object[]) f.get(t);
            Object bucket = arr[i];
            java.lang.reflect.Method m = bucket.getClass().getMethod("size");
            return (int) m.invoke(bucket);
        } catch (Exception e) { return 0; }
    }

    private static int newCapacity(int old) {
        return nextPrime(old * 2);
    }

    private static final int nextPrime(int n) {
        int nn = n;
        if (n % 2 == 0) {
            nn = n + 1;
        }

        while(!isPrime(nn)) {
            nn += 2;
        }

        return nn;
    }

    private static final boolean isPrime(int n) {
        for(int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static boolean testStandardDeviation() {
        System.out.print("Test standardDeviation... ");
        HashTable<String, Integer> th = new HashTable<>(40);

        for(int i = 0; i < 40; ++i) {
            th.put("N" + i, i);
        }

        double tuDT = th.standardDeviation();
        double miDT = myStandardDeviation(th);
        boolean res = Math.abs(tuDT - miDT) < 1.0E-8;
        if (res) {
            System.out.println("ok!");
        }

        return res;
    }

    private static final double myStandardDeviation(HashTable<?, ?> t) {
        int bucketsLength = getBucketsLength(t);
        double lMedia = (double)t.size() / bucketsLength;
        double suma = 0.0;

        for(int i = 0; i < bucketsLength; ++i) {
            double di = (double)getBucketSize(t, i) - lMedia;
            suma += di * di;
        }

        return Math.sqrt(suma / bucketsLength);
    }

    private static boolean testAverageLookupCost() {
        System.out.print("Test averageLookupCost... ");
        HashTable<String, Integer> th = new HashTable<>(40);

        for(int i = 0; i < 40; ++i) {
            th.put("N" + i, i);
        }

        double tuCML = th.averageLookupCost();
        double miCML = myAverageLookupCost(th);
        boolean res = Math.abs(tuCML - miCML) < 1.0E-8;
        if (res) {
            HashTable<Integer, Integer> thI = new HashTable<>(40);

            for(int i = 0; i < 10; ++i) {
                thI.put(i * 53, i);
            }

            tuCML = thI.averageLookupCost();
            miCML = myAverageLookupCost(thI);
            res = Math.abs(tuCML - miCML) < 1.0E-8;
        }

        if (res) {
            System.out.println("ok!");
        }

        return res;
    }

    private static final double myAverageLookupCost(HashTable<?, ?> t) {
        double colisiones = 0.0;
        int bucketsLength = getBucketsLength(t);
        for(int i = 0; i < bucketsLength; ++i) {
            int size = getBucketSize(t, i);
            colisiones += (double)(size * (size - 1) / 2);
        }

        return colisiones / (double)t.size();
    }
}
