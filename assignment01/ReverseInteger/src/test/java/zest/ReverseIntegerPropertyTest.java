package zest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

public class ReverseIntegerPropertyTest {

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