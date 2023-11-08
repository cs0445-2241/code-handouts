import java.util.Arrays;
import java.util.Queue;
import java.util.Random;

public class SortingAlgorithms<T extends Comparable<? super T>> {

    private static final int THRESHOLD = 5;
    public static void main(String[] args){
        Student[] students = new Student[7];
        students[0] = new Student("foo", 4.0);
        students[1] = new Student("foo2", 3.9);
        students[2] = new Student("bar2", 3.8);
        students[3] = new Student("bar", 4.0);
        students[4] = new Student("alice2", 3.95);
        students[5] = new Student("foo3", 3.89);
        students[6] = new Student("alice", 4.0);

        new SortingAlgorithms<Student>(students);   

        Integer[] array = 
        {20, 10, 0, 30, 15, 1, 2, 90, 9, 35, -1, 3, 15, 4, -4, 0};

        new SortingAlgorithms<Integer>(array);   
          

    }

    public SortingAlgorithms(T[] data){
        // selectionSort(data, 0, data.length-1);
        // recursiveSelectionSort(data, 0, data.length-1);
        //Node chain = createChain(data, data.length);
        // selectionSort(chain);
        // insertionSort(data, 0, data.length-1);
        // System.out.println(Arrays.toString(data));
        // recursiveInsertionSort(data, 0, data.length-1);
        //chain = insertionSort(chain);
        //printChain(chain);

        //ShellSort(data, 0, data.length-1);
        //mergeSort(data);
        quickSort(data, 0, data.length-1);

        System.out.println(Arrays.toString(data));


    }
    
    /**
     * Sort a region inside the array bewteen start and end inclusive
     * not stable
     * @param <T>
     * @param a
     * @param start
     * @param end
     */
    public void selectionSort(T[] a, int start, int end){
        int n = end - start + 1;
        for(int i=0; i<n; i++){
            //find smallest item in region starting from i
            int smallestIndex = smallestIndex(a, start+i, end);
            //swap it with a[start+i]
            swap(a, smallestIndex, start+i);
        }
    }

    /**
     * return the index of the smalles item in a's range between start and end
     * inclusive
     * @param a
     * @param start
     * @param end
     * @return
     */
    private int smallestIndex(T[] a, int start, int end){
        T smallest = a[start];
        int smallestIndex = start;
        for(int i=start+1; i<=end; i++){
            if(a[i].compareTo(smallest) < 0){
                smallest = a[i];
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    /**
     * swaps a[first] and a[second]
     * @param array
     * @param first
     * @param second
     */
    private void swap(T[] a, int first, int second){
        T temp = a[first];
        a[first] = a[second];
        a[second] = temp;
    }

    private void recursiveSelectionSort(T[] a, int start, int end){
        if(start <= end){
            int smallestIndex = smallestIndex(a, start, end);
            swap(a, start, smallestIndex);
            recursiveSelectionSort(a, start+1, end);
        }
    }

    public void selectionSort(Node firstNode){
        Node current = firstNode;
        while(current != null){
            //find smallest item in the rest of the chain
            //starting from current
            Node smallest = smallestNode(current);

            //swap smallest item with current.data
            swap(current, smallest);
            current = current.next;
        }
    }

    private Node smallestNode(Node firstNode){
        Node smallest = firstNode;
        Node current = firstNode;
        while(current != null){
            if(current.data.compareTo(smallest.data) < 0){
                smallest = current;
            }
            current = current.next;
        }
        return smallest;
    }

    private Node createChain(T[] a, int n){
        if(n == 0){
            return null;
        }
        Node firstNode = new Node(a[0], null);
        Node current = firstNode;
        for(int i=1; i<n; i++){
            current.next = new Node(a[i], null);
            current = current.next;
        }
        return firstNode;
    }
    
    private void printChain(Node firstNode){
       Node current = firstNode;
       while(current != null){
        System.out.print(current.data + " ");
        current = current.next;
       }
       System.out.println();
    }
    
    /**
     * swap first's and second's data items
     * @param first
     * @param second
     */
    private void swap(Node first, Node second){
        T temp = first.data;
        first.data = second.data;
        second.data = temp;
    }

    /**
     * Insertion Sort
     */

     public void insertionSort(T[] a, int start, int end){

        for(int unsorted = start+1; unsorted <= end; unsorted++){
            //insert a[unsorted] into the sorted region 
            //between start and unsorted-1
            insertInOrder(a, start, unsorted-1, a[unsorted]);
        }

     }

     /**
      * insert item into its sorted position in the sorted region between
      * start and end inclusive
      * @param a
      * @param start
      * @param end
      * @param item
      */
     private void insertInOrder(T[] a, int start, int end, T item){
        //if item > a[end] no shifting needed
        //a[end+1] = item
        //index is to be the correct position of item
        //shifting: a[index + 1] = array[index]
        int index = end+1;
        //if(index == start || a[index-1].compareTo(item) <= 0) stop
        while(index > start && a[index-1].compareTo(item) > 0){
            a[index] = a[index-1];
            index -= 1;
        }
        a[index] = item;
     }


     private void recursiveInsertionSort(T[] a, int start, int end){
        if(start < end){
            //start .. end-1 will be sorted
            recursiveInsertionSort(a, start, end-1);
            //insert a[end] into that sorted region
            recursiveInsertInOrder(a, start, end-1, a[end]);
        }
     }
     private void recursiveInsertInOrder(T[] a, int start, int end, T item){

        if(a[end].compareTo(item) <= 0){
            a[end+1] = item;
        } else if(start == end){
            a[end+1] = a[end];
            a[end] = item;
        } else {
            a[end+1] = a[end];
            recursiveInsertInOrder(a, start, end-1, item);
        }        
     }

     public Node insertionSort(Node firstNode){
        Node sorted = firstNode;
        if(firstNode != null){
            Node unsorted = firstNode.next;
            sorted.next = null;
    
            while(unsorted != null){
                Node temp = unsorted.next;
                sorted = insertInOrder(sorted, unsorted);
                unsorted = temp;
            } 
        }        
        return sorted;
     }

     private Node insertInOrder(Node sorted, Node item){
        Node result = sorted;
        Node current = sorted;
        Node previousNode = null;
        //if current == null || current.data > item.data stop
        while(current != null && current.data.compareTo(item.data) <= 0){
            previousNode = current;
            current = current.next;
        }

        if(previousNode != null){
            previousNode.next = item;
            item.next = current;
        } else {
            item.next = current;
            result = item;
        }
        return result;
     }


     /** Shell Sort */

     public void ShellSort(T[] a, int start, int end){
        int n = end - start + 1;
        for(int space=n/2; space >= 1; space=space/2){
            if(space %2  == 0){
                space++;
            }
            for(int i=0; i<space; i++){
                insertionSort(a, start+i, end, space);
            }
        }
     }

     public void insertionSort(T[] a, int start, int end, int space){

        for(int unsorted = start+space; unsorted <= end; unsorted += space){
            //insert a[unsorted] into the sorted region 
            //between start and unsorted-1
            insertInOrder(a, start, unsorted-space, a[unsorted], space);
        }

     }

     /**
      * insert item into its sorted position in the sorted region between
      * start and end inclusive
      * @param a
      * @param start
      * @param end
      * @param item
      */
     private void insertInOrder(T[] a, int start, int end, T item, int space){
        
        int index = end+space;
        //if(index == start || a[index-space].compareTo(item) <= 0) stop
        while(index > start && a[index-space].compareTo(item) > 0){
            a[index] = a[index-space];
            index -= space;
        }
        a[index] = item;
     }

     public void mergeSort(T[] a){
        @SuppressWarnings("unchecked")
        T[] output = (T[])new Comparable<?>[a.length];
        mergeSort(a, 0, a.length-1, output);
     }

     public void mergeSort(T[] a, int start, int end, T[] temp){
        int n = end - start + 1;
        if(n > 1){
            int mid = start + (end - start)/2;
            mergeSort(a, start, mid, temp);
            mergeSort(a, mid+1, end, temp);
            if(a[mid].compareTo(a[mid+1]) > 0){
                merge(a, start, mid, end, temp);            }
        }
     }

     public void merge(T[] a, int start, int mid, int end, T[] output){
        int i = start;
        int j = mid+1;
        int k = start;

        while(i <= mid && j <= end){
            if(a[i].compareTo(a[j]) <= 0){
                output[k] = a[i];
                i++;
            } else {
                output[k] = a[j];
                j++;
            }
            k++;
        }

        while(i <= mid){
            output[k] = a[i];
            i++;
            k++;
        }

        while(j <= end){
            output[k] = a[j];
            j++;
            k++;
        }

        for(int l=start; l<= end; l++){
            a[l] = output[l];
        }


     }


     public void quickSort(T[] a, int start, int end){
        int n = end - start + 1;
        if(n > THRESHOLD){
            int pivotIndex = partition(a, start, end);
            quickSort(a, start, pivotIndex-1);
            quickSort(a, pivotIndex+1, end);
        } else {
            insertionSort(a, start, end);
        }
     } 
     
     public void quickSort2(T[] a, int start, int end){
        int n = end - start + 1;
        while(n > THRESHOLD){
            int pivotIndex = partition(a, start, end);
            quickSort(a, start, pivotIndex-1);
            //quickSort(a, pivotIndex+1, end);
            start = pivotIndex + 1;
            n = end - start + 1;
        }
        insertionSort(a, start, end);
        }
     }  

     public int partition(T[] a, int start, int end){
        // pivot is last element
        // int pivotIndex = end;
        // T pivot = a[pivotIndex];
        // int indexFromLeft = start;
        // int indexFromRight = end-1;

        // random pivot
        // int pivotIndex = 
        //     start + new Random().nextInt(end-start+1);
        // swap(a, pivotIndex, end);
        // pivotIndex = end;
        // T pivot = a[pivotIndex];
        // int indexFromLeft = start;
        // int indexFromRight = end-1;

        //median of first, middle, last
        int mid = start + (end - start)/2;
        SortFirstMiddleLast(a, start, end);
        swap(a, mid, end-1);
        int pivotIndex = end-1;
        T pivot = a[end-1];
        int indexFromLeft = start+1;
        int indexFromRight = end-2;

        boolean done = false;
        while(!done){
            while(a[indexFromLeft].compareTo(pivot) < 0){
                indexFromLeft++;
            }

            while(indexFromRight > start 
                && a[indexFromRight].compareTo(pivot) > 0){
                indexFromRight--;
            }

            if(indexFromLeft < indexFromRight){
                swap(a, indexFromLeft, indexFromRight);
                indexFromLeft++;
                indexFromRight--;
            } else {
                done = true;
            }
        }
        swap(a, indexFromLeft, pivotIndex);
        pivotIndex = indexFromLeft;
        return pivotIndex;       
     }


    private void order(T[] a, int first, int second){
        if(a[first].compareTo(a[second]) > 0){
            swap(a, first, second);
        }
    }
     private void SortFirstMiddleLast(T[] a, int start, int end){
        int mid = start + (end - start)/2;
        order(a, start, mid);
        order(a, mid, end);

        order(a, start, mid);
     }

    private static class Student implements Comparable<Student>{
        private String name;
        private Double gpa;

        private Student(String name, Double gpa){
            this.name = name;
            this.gpa = gpa;
        }

        public int compareTo(Student other){
            return gpa.compareTo(other.gpa);
        }

        public String toString(){
            return "(" + name + " " + gpa + ")";
        }
    }

    private class Node {
        T data;
        Node next;

        private Node(T data, Node next){
            this.data = data;
            this.next = next;
        }
    }


    public void radixSort(int[] a, int maxDigits){
        Queue<Integer>[] buckets = new Queue[10];
        for(int i=0; i<maxDigits; i++){
            for(int j=0; j<10; j++){
                buckets[j] = new Deque<>();
            }
            for(int j=0; j<a.length; j++){
                //insert a[j] into buckets[ith digit of a[j]]
            }
            //remove the items from the buckets in order and place them
            //into the array a
        }
    }
}
