package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingSynchonizedCounter {
    private static int count = 0;
    private AnimalFactory animalFactory = new AnimalFactory();
    private boolean stop;
    
    synchronized int getAndIncrementCount() {
        return count++;// the '++' operation is not atomic - it incorporates a get and a set.
        // hence we encapsulate it in this synchronised method
    }
    
    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;

            @Override
            public void run() {
                while (stop == false) {
                    String type = "animalnumber:" + getAndIncrementCount() 
                    + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                    IAnimal a = animalFactory.makeAnimal(type);
                    if(!stop) {
                    	System.out.println(a.getAnimalType());
                    }
                    if (count == 101) {
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
        ConcurrencyTestHarnessUsingSynchonizedCounter testee = new ConcurrencyTestHarnessUsingSynchonizedCounter();
        testee.runStuff();
    }

}
