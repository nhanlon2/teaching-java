package uk.gov.ros.teaching.concurrency;

import java.util.HashMap;
import java.util.Map;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingSynchronizedCounter {
    private static int count = 0;
    private AnimalFactory animalFactory = new AnimalFactory();
    private Map<Integer, Integer> animalTypes = new HashMap<>();
    private boolean stop;
    // see: https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html
    // This synchronized block makes the ++ operation on the counter atomic. 
    synchronized int  getAndIncrementCount() {
        return count++;// the '++' operation is not atomic - it incorporates a get and a set.
    }
     void addToMap(Integer i) {
        if(animalTypes.putIfAbsent(i, i)!=null) {
            stop = true;
            throw new RuntimeException("Duplicate animal numbers detected:" + i);
        }
    }
    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;

            @Override
            public void run() {
                while (stop == false) {
                    int localCount = getAndIncrementCount() ;
                    String type = "animalnumber:" + localCount
                    + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                    IAnimal a = animalFactory.makeAnimal(type);
                    if(stop) {
                    	System.out.println(a.getAnimalType());
                    }
                    addToMap(localCount);
                    if (localCount == 100000) {
                        stop = true;
                        System.out.println(a.getAnimalType());
                        System.out.println("Thread "+ Thread.currentThread().getId()+" stopped");
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
    // Animal number does not go up in sequence - however there are NEVER any duplicate animal numbers now (why?)
    // Some threads will still execute after stop=true (why?)
    // If you remove the synchronized modifier from getAndIncrementCount the program will (usually) throw an exception due to
    // duplicate animal numbers, why?
    public static void main(String [] args) {
        ConcurrencyTestHarnessUsingSynchronizedCounter testee = new ConcurrencyTestHarnessUsingSynchronizedCounter();
        testee.runStuff();
    }

}
