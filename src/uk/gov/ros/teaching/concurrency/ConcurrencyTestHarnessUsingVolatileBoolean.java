package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingVolatileBoolean {
    private static int count = 0;
    private AnimalFactory animalFactory = new AnimalFactory();
    private volatile boolean stop;

    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;

            @Override
            public void run() {
                while (stop == false) {
                    String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                    IAnimal a = animalFactory.makeAnimal(type);
                    if (count == 101) {
                        stop = true;
                        System.out.println("Thread "+ Thread.currentThread().getId()+" stopped");
                    }
                    if(!stop) {
                    	System.out.println(a.getAnimalType());
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
    //using volatile has no effect here - on my jvm. However, see the DelayWriteUsingVolatile example
    //Whenever variables are shared between threads the result is non deterministic unless those variables are
    //accessed through a synchronized block or are volatile. This code could be deployed on another JVM and
    // two of the threads might never terminate.
    public static void main(String [] args) {
        ConcurrencyTestHarnessUsingVolatileBoolean testee = new ConcurrencyTestHarnessUsingVolatileBoolean();
        testee.runStuff();
    }

}
