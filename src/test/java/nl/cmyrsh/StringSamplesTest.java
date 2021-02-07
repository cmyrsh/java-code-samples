package nl.cmyrsh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class StringSamplesTest {

    StringSamples samples = new StringSamples();
    /**
     * Rigorous Test.
     */
    @Test
    void testApp() {
        System.out.println(samples.findOccurances("aaaabbbcca"));
        System.out.println(samples.findOccurances("cccaaaaabbbbbbbbb"));
    }
}
