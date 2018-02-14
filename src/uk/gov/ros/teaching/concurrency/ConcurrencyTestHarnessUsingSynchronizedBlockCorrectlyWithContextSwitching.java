package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingSynchronizedBlockCorrectlyWithContextSwitching {
    private static int count = 0;
    private AnimalFactory animalFactory = new AnimalFactory();
    private boolean stop;

    public void runStuff() {

        Runnable myRunnable = new Runnable() {
            private int innerCount;
            private Object runLock = new Object();
            
            synchronized boolean getStop(){
                return stop;
            }
            synchronized void setStop(boolean val) {
                stop = val;
            }
            @Override
            public void run() {
                while (getStop() == false) {
                    String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: "
                            + Thread.currentThread().getId();
                    IAnimal a = animalFactory.makeAnimal(type);
                    System.out.println(a.getAnimalType());
                    if (count > 100) {
                        setStop(true);
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

    // NO Threads execute after stop = true (why?)
    // There is  context switching between threads (why?)
    public static void main(String[] args) {
        ConcurrencyTestHarnessUsingSynchronizedBlockCorrectlyWithContextSwitching testee = new ConcurrencyTestHarnessUsingSynchronizedBlockCorrectlyWithContextSwitching();
        testee.runStuff();
    }

}
