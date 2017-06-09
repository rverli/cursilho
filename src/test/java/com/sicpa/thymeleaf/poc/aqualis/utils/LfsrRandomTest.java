package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.math.BigInteger;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * As Showed in {@link https://en.wikipedia.org/wiki/Linear-feedback_shift_register#Non-binary_Galois_LFSR}.
 * 
 * @author Gsalomao
 */
public class LfsrRandomTest {

	@Test
	public void testRandomWithMask1101() {
		
		String mask = "1101";
		assertLfsrRandom(mask);
	}

	@Test
	public void testRandomWithMask11001() {
		
		String mask = "11001";
		assertLfsrRandom(mask);
	}
	
	@Test
	public void testRandomWithMask101001() {
		
		String mask = "101001";
		assertLfsrRandom(mask);
	}
	
	@Test
	public void testRandomWithMask1100001() {
		
		String mask = "1100001";
		assertLfsrRandom(mask);
	}
	
	@Test
	public void testRandomWithMask101000000001() {
		
		String mask = "101000000001";
		assertLfsrRandom(mask);
	}
	
	@Test
	public void testNextWithSameState() {
		
		LfsrRandom lfsrRandom = new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), BigInteger.TEN);
		int firstInt = lfsrRandom.nextInt();
		int secondInt = lfsrRandom.nextInt();

		lfsrRandom = new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), BigInteger.TEN);
		int thirdInt = lfsrRandom.nextInt();
		int fourthInt = lfsrRandom.nextInt();
		
		Assert.assertEquals(firstInt, thirdInt);
		Assert.assertEquals(secondInt, fourthInt);
		
		BigInteger state = BigInteger.valueOf(1258L);
		lfsrRandom = new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), state);
		firstInt = lfsrRandom.nextInt();
		secondInt = lfsrRandom.nextInt();

		lfsrRandom = new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), state);
		thirdInt = lfsrRandom.nextInt();
		fourthInt = lfsrRandom.nextInt();
		
		Assert.assertEquals(firstInt, thirdInt);
		Assert.assertEquals(secondInt, fourthInt);

	}
	
	@Test
	public void testNextWithSameStateAgain() {
		BigInteger state = BigInteger.valueOf(1258L);
		
		LfsrRandom lfsrRandom = new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), BigInteger.TEN);
		int firstInt = lfsrRandom.nextInt();
		int secondInt = lfsrRandom.nextInt();
		
		state = BigInteger.valueOf(firstInt);
		lfsrRandom = new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), state);
		int thirdInt = lfsrRandom.nextInt();
		
		Assert.assertEquals(secondInt, thirdInt);
	}
			
	@Test
	public void testNextWithSeed() {
		BigInteger state = BigInteger.valueOf(1258L);
		
		LfsrRandom lfsrRandom = new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), BigInteger.TEN);
		lfsrRandom.setSeed(123L);
		int firstInt = lfsrRandom.nextInt();
		int secondInt = lfsrRandom.nextInt();
		
		state = BigInteger.valueOf(firstInt);
		lfsrRandom.setSeed(state.longValue());
		int thirdInt = lfsrRandom.nextInt();
		
		Assert.assertEquals(secondInt, thirdInt);
		
		state = BigInteger.valueOf(123L);
		lfsrRandom.setSeed(state.longValue());
		int fourthInt = lfsrRandom.nextInt();
		
		Assert.assertEquals(firstInt, fourthInt);
	}
	
	/**
	 * <p>Performs tests of lfsrRandom using a bit mask.</p>
	 * <p>The assertion generates at a first time all possibilities derived by the given bit mask, 
	 * at second time generates all possibilities again using another seed and verifies that both generations performs the same values.</p>
	 * @param mask the bit mask
	 */
	private void assertLfsrRandom(String mask) {
		long maskValue = Long.parseLong(mask, 2);
		long period = Long.highestOneBit(maskValue)-1;
		int maskDegree = Long.toBinaryString(period).length();
		
		long state = 1;
		LfsrRandom lfsrRandom = new LfsrRandom(BigInteger.valueOf(maskValue), BigInteger.valueOf(state));
		System.out.println("mask: '"+ mask +"', period: '" +period+ "', mask degree: '"+maskDegree+"', seed: '"+state+"'");
		
		HashSet<Integer> numbers = new HashSet<Integer>();
		for(long index = 0; index < period; index++) {
			Integer next = lfsrRandom.nextInt();
			if(!numbers.add(next))
				Assert.fail("Repeated number: "+next+", at index: "+index+"/"+period);
		}
		
		state = (System.currentTimeMillis() % period) + 1;
		lfsrRandom = new LfsrRandom(BigInteger.valueOf(maskValue), BigInteger.valueOf(state));
		System.out.println("mask: '"+ mask +"', period: '" +period+ "', mask degree: '"+maskDegree+"', seed: '"+state+"'");
		
		for(long index = 0; index < period; index++) {
			Integer next = lfsrRandom.nextInt();
			if(!numbers.remove(next))
				Assert.fail("number not found: "+next+", at index: "+index+"/"+period);
		}
		
		Assert.assertTrue("Some ("+numbers.size()+") values are not removed: "+ numbers.toArray(), numbers.isEmpty());
	}
}
