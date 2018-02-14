package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingSynchronizedBlockWrongly {
    private static volatile int count = 0;
    private AnimalFactory animalFactory = new AnimalFactory();
    private boolean stop;

    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;
            private Object runLock = new Object();
            @Override
            public void run() {
                while (stop == false) {
                    //due to context switching, most of the time all three threads will get into the while loop at least once.
                    synchronized (runLock) {
                        String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                        IAnimal a = animalFactory.makeAnimal(type);
//                        if(!stop) {
//                        	System.out.println(a.getAnimalType()); //uncommenting this will cause all threads to stop at 100 exactly (why?)
//                        }
                        if (count == 101) {
                            stop = true;
                            System.out.println("Thread "+ Thread.currentThread().getId()+" stopped");
                        }
                    }
                }
            }
        };
        Thread threadOne = new Thread(myRunnable);
        Thread threadTwo = new Thread(myRunnable);
        Thread threadThree = new Thread(myRunnable);
        threadOne.start();
        threadTwo.start();
        threadThree.start();

    }
    
    //Threads execute after stop = true AND we now ALWAYS get animal count going to 102 (why?)
    public static void main(String [] args) {
        ConcurrencyTestHarnessUsingSynchronizedBlockWrongly testee = new ConcurrencyTestHarnessUsingSynchronizedBlockWrongly();
        testee.runStuff();
    }

}
