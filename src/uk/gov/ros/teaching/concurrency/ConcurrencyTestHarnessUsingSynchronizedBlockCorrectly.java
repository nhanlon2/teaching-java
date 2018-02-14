package uk.gov.ros.teaching.concurrency;

import java.util.HashSet;
import java.util.Set;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingSynchronizedBlockCorrectly {
    private static  int count = 0;
    private AnimalFactory animalFactory = new AnimalFactory();
    private boolean stop;

    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;
            private Object runLock = new Object();
            private Set<Long> threadNums = new HashSet<>();
            @Override
            public void run() {
                while (stop == false) {
                    synchronized (runLock) {
                        String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                        IAnimal a = animalFactory.makeAnimal(type);
                        //System.out.println(a.getAnimalType());
                        Long threadID = Thread.currentThread().getId();
                        if(!threadNums.contains(threadID)) {
                            System.out.println(threadID);
                            threadNums.add(threadID);
                        }
                        if (count > 100000000) {
                            stop = true;
                            System.out.println("done");
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
    
    //NO Threads execute after stop = true (why?)
    //There is no more context switching between threads (why?)
    public static void main(String [] args) {
        ConcurrencyTestHarnessUsingSynchronizedBlockCorrectly testee = new ConcurrencyTestHarnessUsingSynchronizedBlockCorrectly();
        testee.runStuff();
    }

}
