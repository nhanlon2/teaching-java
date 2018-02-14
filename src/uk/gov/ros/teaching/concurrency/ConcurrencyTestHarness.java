package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarness {
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
    //If you run this main method a few times you will witness various random outputs that are probably not what you were expecting to see.
    //For example,
    //1) you can see duplicate animalnumbers and the numbers will not go up in sequence.
    //2) Also note that some threads will continue to execute even after stop=true.
    //3) Also note that count and innerCount will sometimes not match
    //4) The threads all interleave and run at random, you cannot tell which thread will start first or which will finish first
    //5) All threads are seeing the value of stop as no thread runs with a value of > 100 for animal number.
    public static void main(String [] args) {
        ConcurrencyTestHarness testee = new ConcurrencyTestHarness();
        testee.runStuff();
    }

}
