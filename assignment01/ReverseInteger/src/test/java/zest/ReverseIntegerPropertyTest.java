package zest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

public class ReverseIntegerPropertyTest {
	// INT-32 Limit is 2,147,483,647

	@Test
	void testNearOverflowBoundaries() {
		// Safe Boundary
		// This is LESS than INT-32 Limit
		assertEquals(2147483641, ReverseInteger.reverse(1463847412));

		// The exact INT-32 Limit
		// 2,463,847,412 would reverse to 2,147,483,642, but 2,463,847,412 
		// is already bigger than an int. So we test the largest possible 
		// 10-digit number that doesn't overflow:
		// 1,534,236,469 reversed is 9,646,324,351 -> MUST return 0.
		assertEquals(0, ReverseInteger.reverse(1534236469));

		// -1,463,847,412 reversed is -2,147,483,641 (Safe)
		assertEquals(-2147483641, ReverseInteger.reverse(-1463847412));
	}

	@Property
	void reversingTwiceReturnsOriginal(@ForAll int x) {
		// Skip numbers ending with 0
		Assume.that(x % 10 != 0);

		int firstReversed = ReverseInteger.reverse(x);

		// Skip cases where the reversed number would overflow
		Assume.that(firstReversed != 0);

		int secondReversed = ReverseInteger.reverse(firstReversed);

		// Skip cases where the second reversed number would overflow
		Assume.that(secondReversed != 0);

		assertEquals(x, secondReversed);
	}
}