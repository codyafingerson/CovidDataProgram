/**
 * The MinPQ class represents a minimum priority queue using an unordered array as an underlying data structure
 * @param <Key>
 * @author Cody Fingerson
 */
public class MinPQ<Key extends Comparable<Key>> {
    private int size; // number of elements on PQ
    private Key[] pq; // keys[i] = key of element i

    @SuppressWarnings("unchecked") // ignore warning saying comparable array cast to Key array is unchecked
    public MinPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1]; // Create a comparable array and cast to key array with size + 1 to compensate for index 0
        size = 0; // Initial size of the PQ object (not the pq array)
    }

    /**
     * Check if minimum priority queue is empty
     * @return true if empty, else returns false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the size of the minimum priority queue
     * @return the size of the minimum priority queue
     */
    public int size() { return size; }

    // Check which key is greater
    private boolean greater(Key k, Key k2) { return k.compareTo(k2) > 0;}

    /**
     * Delete the minimum key the minimum priority queue
     * @return the key deleted
     */
    public Key delMin() { return pq[--size];}

    /**
     * Insert into the minimum priority queue
     * @param key the key
     */
    public void insert(Key key) {
        int i = size - 1;
        // While size is greater than zero & key, pq[i] are not matching, insert the key into the queue
        while(i >= 0 && greater(key, pq[i])){
            pq[i +1] = pq[i];
            i--; // decrease
        }
        pq[i + 1] = key; //insert the key into the array
        size++; //increase the size variable
    }
}