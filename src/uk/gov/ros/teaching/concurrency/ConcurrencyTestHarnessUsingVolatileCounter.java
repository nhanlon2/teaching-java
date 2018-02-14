package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingVolatileCounter {
    private static volatile int count = 0;
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
                    System.out.println(a.getAnimalType());
                    if (count > 100) {
                        stop = true;
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
    // Animal number does not go up in sequence - you can still see duplicate animal numbers (why?)
    // Some threads will still execute after stop=true
    public static void main(String [] args) {
        ConcurrencyTestHarnessUsingVolatileCounter testee = new ConcurrencyTestHarnessUsingVolatileCounter();
        testee.runStuff();
    }

}
