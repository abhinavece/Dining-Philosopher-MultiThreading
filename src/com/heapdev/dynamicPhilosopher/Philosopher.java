package com.heapdev.dynamicPhilosopher;

import java.util.Random;

public class Philosopher implements Runnable {

	private int id;
	private Chopstick leftChopStick;
	private Chopstick rightChopStick;
	private Random random;
	private int eatingCounter;
	private volatile boolean isFull = false;

	public Philosopher(int id, Chopstick leftChopStick, Chopstick rightChopStick) {
		this.id = id;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
		this.random = new Random();
	}

	@Override
	public void run() {

		try {

			while (!isFull) {
				think();
				if (leftChopStick.pickUp(this, State.LEFT)) {
					if (rightChopStick.pickUp(this, State.RIGHT)) {
						eat();
						rightChopStick.putDown(this, State.RIGHT);
					}
					leftChopStick.putDown(this, State.LEFT);
				}
				System.err.println(this +" CANT PIK THE CHOPSTICK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void think() throws InterruptedException {
		System.out.println(this + " is thinking...");
		Thread.sleep(random.nextInt(1000));
	}

	private void eat() throws InterruptedException {
		System.out.println(this + " is eating now...");
		++this.eatingCounter;
		Thread.sleep(1000);
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	@Override
	public String toString() {
		return " Philosopher " + id;
	}

	public int getEatingCounter() {
		return eatingCounter;
	}

	public void setEatingCounter(int eatingCounter) {
		this.eatingCounter = eatingCounter;
	}

}
