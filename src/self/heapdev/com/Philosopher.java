package self.heapdev.com;

import java.util.Random;

public class Philosopher implements Runnable {

	private int id;
	private Chopstick leftChopstick;
	private Chopstick rightChopstick;
	private Random random = null;
	private int eatingCounter;
	private volatile boolean isFull = false;

	public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
		this.id = id;
		this.leftChopstick = leftChopstick;
		this.rightChopstick = rightChopstick;
		this.random = new Random();
	}

	@Override
	public void run() {
		try {
			while (!isFull) {
				think();
				if (leftChopstick.lockUp(this, State.LEFT)) {
					if (rightChopstick.lockUp(this, State.RIGHT)) {
						System.out.println(this + " is eating with... ");
						eat();
						rightChopstick.lockDown(this, State.RIGHT);
					}
					leftChopstick.lockDown(this, State.LEFT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void eat() throws InterruptedException {
		System.out.println(this + " is eating with... ");
		++eatingCounter;
		Thread.sleep(random.nextInt(1000));
	}

	private void think() throws InterruptedException {
		System.out.println(this + " is thinking...");
		Thread.sleep(random.nextInt(1000));
	}

	@Override
	public String toString() {
		return "Philosopher " + id;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public int getEatingCounter() {
		return eatingCounter;
	}

	public void setEatingCounter(int eatingCounter) {
		this.eatingCounter = eatingCounter;
	}

}
