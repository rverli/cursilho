package com.sicpa.thymeleaf.poc.aqualis.utils;

/* 
 * Galois linear feedback shift register (LFSR) (Java)
 * 
 * Copyright (c) 2016 Project Nayuki
 * All rights reserved. Contact Nayuki for licensing.
 * https://www.nayuki.io/page/galois-linear-feedback-shift-register
 */

import java.math.BigInteger;
import java.util.Random;


/**
 * <p>Galois LFSR random number generator. This can be used in place of java.util.Random.
 * In this class, a polynomial is represented as a BigInteger, where the coefficient of
 * the <var>x</var><sup><var>k</var></sup> term is represented by bit <var>k</var>.</p>
 * See the original here: {@link https://www.nayuki.io/res/galois-linear-feedback-shift-register/LfsrRandom.java} <br/>
 * Learn more about here: {@link https://en.wikipedia.org/wiki/Linear-feedback_shift_register#Non-binary_Galois_LFSR} 
 */
public final class LfsrRandom extends Random {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final BigInteger characteristic;
	private final int degree;
	private BigInteger state;
	
	
	/**
	 * Constructs an LFSR with the specified characteristic polynomial and initial state polynomial.
	 * The characteristic polynomial must have degree at least 2. The state polynomial must have degree
	 * less than the degree of the characteristic polynomial, and must not be the zero polynomial.
	 * @param charistic the characteristic polynomial
	 * @param state the initial state polynomial
	 * @throws IllegalArgumentException if the polynomials do not satisfy the requirements
	 */
	public LfsrRandom(BigInteger charis, BigInteger state) {
		validateCharacteristic(charis);
		validateState(charis, state);
		
		this.characteristic = charis;
		this.degree = charis.bitLength() - 1;
		this.state = state;
	}

	private void validateCharacteristic(BigInteger charis) {
		if (charis.signum() == -1)
			throw new IllegalArgumentException("Invalid characteristic polynomial - negative");
		if (charis.bitLength() < 2)
			throw new IllegalArgumentException("Invalid characteristic polynomial - degree too low");
	}

	private void validateState(BigInteger charis, BigInteger state) {
		if (BigInteger.ZERO.equals(state))
			throw new IllegalArgumentException("Invalid state polynomial - all zero");
		if (state.bitLength() >= charis.bitLength())
			throw new IllegalArgumentException("Invalid state polynomial - degree >= char poly degree");
	}
	
	public boolean nextBoolean() {
		boolean result = state.testBit(0);      // Use bit 0 in the LFSR state as the result
		state = state.shiftLeft(1);             // Multiply by x
		if (state.testBit(degree))              // If degree of state polynomial matches degree of characteristic polynomial
			state = state.xor(characteristic);  // Then subtract the characteristic polynomial from the state polynomial
		
		return result;
	}
	
	@Override
	public void setSeed(long seed) {
		this.state = BigInteger.valueOf(seed);
	}
	
	protected int next(int bits) {
		nextBoolean();
		BigInteger result = state;
		/*for (int i = 0; i < bits; i++) {
			result = result.shiftLeft(1).or( state.testBit(i) ? BigInteger.ONE : BigInteger.ZERO);
		}*/
		return result.intValue();
	}
	
	public void printDebug() {
		System.out.printf("characteristic: degree=%d  poly = %s%nstate: degree=%d  poly = %s%n",
			degree, polynomialToString(characteristic), state.bitLength() - 1, polynomialToString(state));
	}
	
	private static String polynomialToString(BigInteger poly) {
		StringBuilder sb = new StringBuilder();
		boolean head = true;
		for (int i = poly.bitLength() - 1; i >= 0; i--) {
			if (poly.testBit(i)) {
				if (head) head = false;
				else sb.append(" + ");
				sb.append("x^" + i);
			}
		}
		return sb.toString();
	}
	
}
