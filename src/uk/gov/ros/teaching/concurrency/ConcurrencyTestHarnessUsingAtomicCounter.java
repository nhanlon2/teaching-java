package uk.gov.ros.teaching.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingAtomicCounter {
    private static AtomicInteger count = new AtomicInteger();
    private ConcurrentMap<Integer, Integer> animalTypes = new ConcurrentHashMap<>();
    private AnimalFactory animalFactory = new AnimalFactory();
    private boolean stop;
    
    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;

            @Override
            public void run() {
                while (stop == false) {
                    int localCount = count.getAndIncrement();
                    String type = "animalnumber:" + localCount
                    + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                    IAnimal a = animalFactory.makeAnimal(type);
                    if(animalTypes.containsKey(localCount)) {
                        stop = true;
                        throw new RuntimeException("Duplicate animal numbers detected");
                    } else {
                        animalTypes.putIfAbsent(localCount, localCount);
                    }
                    
                    if(!stop) {
                    	System.out.println(a.getAnimalType());
                    }
                    if (localCount == 100) {
                        stop = true;
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
    public static void main(String [] args) {
        ConcurrencyTestHarnessUsingAtomicCounter testee = new ConcurrencyTestHarnessUsingAtomicCounter();
        testee.runStuff();
    }

}
