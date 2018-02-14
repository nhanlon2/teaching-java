package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingVolatileBoolean {
    private static int count = 0;
    private AnimalFactory animalFactory = new AnimalFactory();
    private boolean stop;

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
    // With the stop as volatile you will (usually) not see execution after stop = true
    // If you remove the volatile modifier, some threads will still execute after stop=true
    public static void main(String [] args) {
        ConcurrencyTestHarnessUsingVolatileBoolean testee = new ConcurrencyTestHarnessUsingVolatileBoolean();
        testee.runStuff();
    }

}
