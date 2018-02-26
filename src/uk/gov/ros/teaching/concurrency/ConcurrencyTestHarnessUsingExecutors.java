package uk.gov.ros.teaching.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingExecutors {
    private static int count = 0;
    private static final int CONCURRENCY = 6;
    static final CountDownLatch ready  = new CountDownLatch(CONCURRENCY);
    static final CountDownLatch start  = new CountDownLatch(1);
    static final CountDownLatch done  = new CountDownLatch(1);// stop all threads at same time, change this value from 1 to 
                                                                //CONCURRENCY if you want all threads to count to at least 100
    private static class MyRunnabletest implements Runnable {
        private int innerCount;

        @Override
        public void run() {
            ready.countDown();//when all threads reach this point execution will commence
            try {
                start.await();
            } catch (InterruptedException e) {
            }
            while (done.getCount()>0) {
                String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                IAnimal a = AnimalFactory.makeAnimal(type);
                if(done.getCount()>0) {
                    System.out.println(a.getAnimalType());
                }
                if (count > 100) {
                    System.out.println("Thread "+ Thread.currentThread().getId()+" stopped");
                    done.countDown();
                }
            }
        }
    }

    public void runStuff() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for(int i =0; i< CONCURRENCY; i++) {
            executor.execute(new MyRunnabletest());
        }
        ready.await();
        long startTime = System.nanoTime();
        start.countDown();
        done.await();
        long endTime = System.nanoTime();
        System.out.println("program took: " +  (endTime-startTime) + " nano seconds");
    }
    //no need to use volatile variables here - all threads will see the change to the countdown latches.
    public static void main(String [] args) throws InterruptedException {
        ConcurrencyTestHarnessUsingExecutors testee = new ConcurrencyTestHarnessUsingExecutors();
        testee.runStuff();
    }

}
