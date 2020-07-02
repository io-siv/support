package io.siv.support.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

public class SleepTest {

	@Test
	public void aLittle() {
		long start = System.nanoTime();
		Sleep.aLittle();
		long stop = System.nanoTime();
		long actual = TimeUnit.SECONDS.convert(stop - start, TimeUnit.NANOSECONDS);
		System.out.println(actual);
		assertTrue(actual == 1);
	}
}
