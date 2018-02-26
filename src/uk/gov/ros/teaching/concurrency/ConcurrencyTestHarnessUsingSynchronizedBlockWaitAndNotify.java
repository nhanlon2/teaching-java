package uk.gov.ros.teaching.concurrency;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class ConcurrencyTestHarnessUsingSynchronizedBlockWaitAndNotify {
	private static int count = 0;
	private boolean stop;

	public void runStuff() {

		Runnable myRunnable = new Runnable() {
			private int innerCount;
			private Object runLock = new Object();

			@Override
			public void run() {
				synchronized (runLock) {
					while (stop == false) {
						String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: "
								+ Thread.currentThread().getId();
						IAnimal a = AnimalFactory.makeAnimal(type);
						if(!stop) {
	                    	System.out.println(a.getAnimalType());
	                    }
						if (count == 10001) {
							stop = true;
							System.out.println("Thread "+ Thread.currentThread().getId()+" stopped");
						}
						if(count%5==0) {
						    try {
						        runLock.wait(); // you must call wait and notify on the object that owns the lock
						    }
						    catch (InterruptedException e) {};
						}
						if(count%4==0) {
						    runLock.notifyAll(); // you must call wait and notify on the object that owns the lock
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

	// NO Threads execute after stop = true (why?)
	// There is context switching between threads after every 5 numbers during run (why?)
	// If you comment out the notifyAll code, all threads will end up waiting and the program never terminates
	public static void main(String[] args) {
		ConcurrencyTestHarnessUsingSynchronizedBlockWaitAndNotify testee = new ConcurrencyTestHarnessUsingSynchronizedBlockWaitAndNotify();
		testee.runStuff();
	}

}
