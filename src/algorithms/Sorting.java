package algorithms;

/** 
 *  The Sorting class contains:
 *  - The implementation of the in-place Divide & Conquer sorting algorithms
 *    Quick Sort and Merge Sort. 
 *  - A method to check if two generic arrays are equal.
 */
public class Sorting {
    
    // QUICK SORT -------------------------------------------------
    /** 
     *  Quick sort algorithm (Hoare, 1963). 
     *  Uses the partition algorithm due to Weiss, 
     *  with median of 3 for the calculation of the pivot.
     *  
     *  @param a Its elements implement the Comparable interface
     */
    public static <T extends Comparable<T>> void quickSort(T[]  a) {
        quickSort(a, 0, a.length - 1);
    }
   
    // Sorts the array a[left, right] by quickSort, left <= right
    private static <T extends Comparable<T>> void quickSort(T[] a, 
                                                            int left, int right) {
        if (left < right) {
            T pivot = medianOfThree(a, left, right);
            int i = left;
            int j = right - 1;
            for ( ; i < j;) {
                while (pivot.compareTo(a[++i]) > 0) { ; }
                while (pivot.compareTo(a[--j]) < 0) { ; }
                swap(a, i, j);
            }
            swap(a, i, j);        // Undo the last change
            swap(a, i, right - 1);  // Restore the pivot
            // PIVOT SORTED -->
            quickSort(a, left, i - 1);     // Rec. sort the smaller elements
            quickSort(a, i + 1, right);     // Rec. sort the larger elements
        }
    }

    // Swaps elements ind1 and ind2 of array a
    private static <T> void swap(T[] a, int ind1, int ind2) {
        T tmp = a[ind1];    
        a[ind1] = a[ind2];
        a[ind2] = tmp;   
    }
 
    // After calculating the Median of 3 of subArray a[left, right], 
    // returns the value of the pivot
    private static <T extends Comparable<T>> T medianOfThree(T[] a, 
                                                        int left, int right) {    
        int c = (left + right) / 2;   
        if (a[c].compareTo(a[left]) < 0)   { swap(a, left, c); }
        if (a[right].compareTo(a[left]) < 0) { swap(a, left, right); }
        if (a[right].compareTo(a[c]) < 0)   { swap(a, c, right); }
        // hide the pivot in position right-1
        swap(a, c, right - 1);
        return a[right - 1];
    }

    // MERGE SORT --------------------------------------------   
    // VERSION 1:   
    /**
     * Ascendingly sorts the array v.
     * 
     * @param v  Its elements must implement the Comparable interface
     */
    public static <T extends Comparable<T>> void mergeSort(T[] v) {
        mergeSort(v, 0, v.length - 1);
    }
    
    /**
     * PRECONDITION i<=f: ascendingly sorts the subarray v[i, f].
     * 
     * @param v  Its elements implement the Comparable interface
     * @param i  Lower bound of the interval to sort
     * @param f  Upper bound of the interval to sort
     */
    private static <T extends Comparable<T>> void mergeSort(T[] v, 
                                                             int i, int f) {
        if (i < f) {
            int m = (i + f) / 2;
            mergeSort(v, i, m);
            mergeSort(v, m + 1, f);
            merge(v, i, f, m);
        }
    }        
    
    /**
     * Internally merges the subarrays v[i, m] and v[m + 1, f], 
     * both sorted Asc.
     * 
     * @param v  Its elements implement the Comparable interface
     * @param i  Lower bound of the interval to merge
     * @param f  Upper bound of the interval to merge
    */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge(T[] v, 
                                                         int i, int f, int m) {
        int a = i, b = m + 1, k = 0;
        T[] aux = (T[]) new Comparable[f - i + 1];
        while (a <= m && b <= f) {
            if (v[a].compareTo(v[b]) < 0) { aux[k++] = v[a++]; }
            else                          { aux[k++] = v[b++]; }
        }
        while (a <= m) { aux[k++] = v[a++]; }
        while (b <= f) { aux[k++] = v[b++]; }
        
        for (a = i, k = 0; a <= f; a++, k++) { v[a] = aux[k]; }
    }   
    
    // MERGE SORT --------------------------------------------   
    // VERSION 2 
    /**
     * Ascendingly sorts the array v.
     * 
     * @param v  Its elements must implement the Comparable interface
     */
    public static <T extends Comparable<T>> void mergeSortFunctional(T[] v) {
       System.arraycopy(mergeSortFunctional(v, 0, v.length - 1), 0, v, 0, v.length);
    }
    
    /**
     * PRECONDITION i<=f: returns an array with the elements of the subarray 
     * v[i, f] sorted Asc. 
     * 
     * @param v  Its elements implement the Comparable interface
     * @param i  Lower bound of the interval to sort
     * @param f  Upper bound of the interval to sort
     * @return T[], the resulting array of sorting v[i, f]
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> T[] mergeSortFunctional(T[] v,
                                                             int i, int f) {
        if (i == f) {
            T[] aux = (T[]) new Comparable[1];
            aux[0] = v[i];
            return aux;
        } else if(i+1 == f){
            if(v[i].compareTo(v[f]) < 0){
                T[] aux = (T[]) new Comparable[2];
                aux[0] = v[i];
                aux[1] = v[f];
                return aux;
            }
            else{
                T[] aux = (T[]) new Comparable[2];
                aux[0] = v[f];
                aux[1] = v[i];
                return aux;
            }
        }
        else {
            int m = (i + f) / 2;
            T[] v1 = mergeSortFunctional(v, i, m);
            T[] v2 = mergeSortFunctional(v, m+1, f);
            return mergeFunctional(v1, v2);
        }
    }        
    
    /**
     * Returns the array merge of v1 and v2, two arrays sorted Asc.
     * 
     * @param v1  Its elements implement the Comparable interface
     * @param v2  Its elements implement the Comparable interface
     * @return T[], the array resulting from the merge of v1 and v2
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> T[] mergeFunctional(T[] v1, T[] v2) {
        int a = 0, b = 0, k = 0;
        T[] aux = (T[]) new Comparable[v1.length + v2.length];

        while (a < v1.length && b < v2.length) {
            if (v1[a].compareTo(v2[b]) < 0) { aux[k++] = v1[a++]; }
            else                          { aux[k++] = v2[b++]; }
        }
        while (a < v1.length) { aux[k++] = v1[a++]; }
        while (b < v2.length) { aux[k++] = v2[b++]; }
        return aux;
    }
    
    // Auxiliary Method --------------------------------------------
    /** 
     *  Checks if arrays a and b are equal element by element.
     *  
     *  @param a  Its elements implement the Comparable interface
     *  @return boolean, the result of the check
     */
    public static <T extends Comparable<T>> boolean areEqual(T[] a, T[] b) {
        boolean equal = true;
        if (a.length != b.length) { equal = false; }
        else {
            for (int i = 0; i < a.length && equal; i++) {
                equal = (a[i].compareTo(b[i]) == 0);
            }
        }    
        return equal;
    }    
}
