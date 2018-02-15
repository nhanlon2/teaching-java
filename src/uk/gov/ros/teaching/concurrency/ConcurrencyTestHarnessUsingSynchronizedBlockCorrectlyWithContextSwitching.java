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

			@Override
			public void run() {
				while (stop == false) {
					String type = "animalnumber:" + count++ + " inner count: " + innerCount++ + " thread: "
							+ Thread.currentThread().getId();
					IAnimal a = animalFactory.makeAnimal(type);
					synchronized (runLock) {
						if (!stop) {
							System.out.println(a.getAnimalType());
						}
					}
					if (count == 101) {
						stop = true;
						System.out.println("Thread " + Thread.currentThread().getId() + " stopped");
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
	// There is context switching between threads (why?)
	// This class will get work done three times faster than the other
	// synchronized class - why?
	public static void main(String[] args) {
		ConcurrencyTestHarnessUsingSynchronizedBlockCorrectlyWithContextSwitching testee = new ConcurrencyTestHarnessUsingSynchronizedBlockCorrectlyWithContextSwitching();
		testee.runStuff();
	}

}
