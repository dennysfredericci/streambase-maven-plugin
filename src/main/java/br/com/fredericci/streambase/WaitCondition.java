package br.com.fredericci.streambase;

import java.util.concurrent.CountDownLatch;

/**
 * This class represents a condition to wait, like stream base server started.
 * 
 * @author Dennys Fredericci
 */
public class WaitCondition {

	private final CountDownLatch lock;

	/**
	 * The expected value.
	 */
	private final String waitFor;

	public WaitCondition(String waitFor) {
		this.waitFor = waitFor;
		lock = new CountDownLatch(1);
	}

	public void await() throws InterruptedException {
		lock.await();
	}

	/**
	 * Check if value contains a expected value
	 */
	public void check(String value) {

		if (value.toUpperCase().contains(waitFor.toUpperCase())) {
			lock.countDown();
		}

	}

}
