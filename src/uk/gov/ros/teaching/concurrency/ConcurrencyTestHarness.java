package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarness {
    private static int count = 0;
    private boolean stop;

    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;

            @Override
            public void run() {
                while (stop == false) {
                    String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: " + Thread.currentThread().getId();
                    IAnimal a = AnimalFactory.makeAnimal(type);
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
    //If you run this main method a few times you will witness various random outputs that are probably not what you were expecting to see.
    //For example,
    //1) you can see duplicate animalnumbers and the numbers will not go up in sequence.
    //2) Also note that some threads will continue to execute even after stop=true.
    //3) Also note that count and innerCount will sometimes not match
    //4) The threads all interleave and run at random, you cannot tell which thread will start first or which will finish first
    //5) All threads are seeing the value of 'stop' as no thread runs with a value of > 100 for animal number. Note, however, this is not guaranteed - 
    // see the DelayWriteUsingVolatile example. Our example works because the JVM has decided not to hoist 'stop' out of the while loop.
    // This code could be deployed on an older JVM which would instead hoist the variable. Two of the threads might never stop in that case.
    public static void main(String [] args) {
        ConcurrencyTestHarness testee = new ConcurrencyTestHarness();
        testee.runStuff();
    }

}
