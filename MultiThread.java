import java.util.ArrayList;
import java.util.List;

public class ComplexMultithreadedProgram {

    public static void main(String[] args) {
        // create shared data structure
        List<Integer> sharedList = new ArrayList<Integer>();

        // create multiple threads to add and remove elements from the shared list
        Thread t1 = new Thread(new AddThread(sharedList));
        Thread t2 = new Thread(new AddThread(sharedList));
        Thread t3 = new Thread(new RemoveThread(sharedList));
        Thread t4 = new Thread(new RemoveThread(sharedList));

        // start the threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // wait for the threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // print the final state of the shared list
        System.out.println(sharedList);
    }

    // class to add elements to the shared list
    static class AddThread implements Runnable {

        private List<Integer> sharedList;

        public AddThread(List<Integer> sharedList) {
            this.sharedList = sharedList;
        }

        public void run() {
            for (int i = 0; i < 10000; i++) {
                synchronized (sharedList) {
                    sharedList.add(i);
                }
            }
        }
    }

    // class to remove elements from the shared list
    static class RemoveThread implements Runnable {

        private List<Integer> sharedList;

        public RemoveThread(List<Integer> sharedList) {
            this.sharedList = sharedList;
        }

        public void run() {
            for (int i = 0; i < 5000; i++) {
                synchronized (sharedList) {
                    if (!sharedList.isEmpty()) {
                        sharedList.remove(0);
                    }
                }
            }
        }
    }
}
