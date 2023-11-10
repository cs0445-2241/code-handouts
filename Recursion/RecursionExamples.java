public class RecursionExamples {

    public static void main(String[] args){

        solveTowers(4, 0, 2, 1);
        System.out.println("------------------");
        iterativeSolveTowers(4, 0, 2, 1);
        System.out.println("------------------");
        //Build a 3-node chain
        Node firstNode = 
            new Node(10);
        firstNode.next = new Node(5);
        firstNode.next.next = new Node(0);
        System.out.print("Chain in reverse order: ");
        displayChainBackwards(firstNode);
        System.out.println();
        firstNode = reverse(firstNode);
        System.out.print("Chain in reverse order: ");
        displayChainBackwards(firstNode);
        System.out.println();
        int x = 2, y = 20;
        System.out.print(x + "^" + y + " = ");
        System.out.println(power(x, y));

    }

    private static void displayChainBackwards(Node nodeOne){
        if(nodeOne != null){
            displayChainBackwards(nodeOne.next);
            System.out.print(nodeOne.data + " ");
        }
    }

    private static Node reverse(Node first){
        Node result = null;
        if(first == null || first.next == null){
            result = first;
        } else {
            Node second = first.next;
            Node newFirst = reverse(second);
            second.next = first;
            first.next = null;
            result = newFirst;
        }

        return result;
    }

    private static class Node {
        int data;
        Node next;

        private Node(int data){
            this.data = data;
            next = null;
        }
    }

     /**
     * Compute x to the power of a non-negative exponent y >= 0
     * @param x the base
     * @param y the exponent
     * @return x to the power of y
     * @throws IllegalArgumentException when y < 0
     */
    public static int power(int x, int y){
        if(y < 0){
            throw new IllegalArgumentException("Negative exponent is not allowed");
        }
        //return powerIterative(x, y);
        return powerRecursive(x, y);

    }

    private static int powerIterative(int x, int y){
        int result = x;
        for(int i=1; i<y; i++){ //y-1 iterations because result starts from x
            result = result*x;
        }
        return result;
    }

    private static int powerRecursive(int x, int y){
        int result = -1;
        if(y == 0){ //Base case
            result = 1;
        } else { //Recursive case
            int xPowerYOver2 = powerRecursive(x, y/2); //one recursive call!
            result = xPowerYOver2*xPowerYOver2;
            if(y%2 == 1){ //y is odd?
                result = x*result;
            }
        }
        return result;
    }

    public static void solveTowers(int nDisks, int start, int finish, int temp){
        if(nDisks > 0){
            solveTowers(nDisks-1, start, temp, finish);
            System.out.println("Move a disk from " + start + " to " + finish);
            solveTowers(nDisks-1, temp, finish, start);
        }
    }

    public static void iterativeSolveTowers(int nDisks, int start, int finish, int temp){
        while(nDisks > 0){
            solveTowers(nDisks-1, start, temp, finish);
            System.out.println("Move a disk from " + start + " to " + finish);
            //solveTowers(nDisks-1, temp, finish, start);
            nDisks = nDisks - 1;
            int swap = start;
            start = temp;
            temp = swap;
        }
    }

}