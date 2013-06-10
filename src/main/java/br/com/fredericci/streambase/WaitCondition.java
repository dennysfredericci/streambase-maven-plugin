package br.com.fredericci.streambase;

import java.util.concurrent.CountDownLatch;

public class WaitCondition {

	private CountDownLatch lock;

	private final String waitFor;

	public WaitCondition(String waitFor) {
		this.waitFor = waitFor;

		if (waitFor != null) {
			lock = new CountDownLatch(1);
		}
	}

	public void await() throws InterruptedException {

		if (waitFor != null) {
			lock.await();
		}

	}

	public void check(String line) {

		if (waitFor != null) {
			if (line.toUpperCase().contains(waitFor.toUpperCase())) {
				lock.countDown();
			}

		}

	}

}
