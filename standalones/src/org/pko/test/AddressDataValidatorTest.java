package org.pko.test;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * AddressDataValidatorTest
 *
 * @author Peter Kolarik
 * @date 6.9.2017
 * @project LogaWEB
 */
public class AddressDataValidatorTest extends TestCase {

    AddressDataValidator validator;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        validator = new AddressDataValidator();
    }

    @Test
    public void testRomanNumbers() {
        String[] testarray = { "III", "VVV", "XXX", "CCC", "DDD", "MMM", "IV", "I", "CCCC", "XX", "XIX", "MCMXX", "MI" };
        boolean[] results = { true, false, true, true, false, true, true, true, false, true, true, true, true };
        for (int i = 0; i < testarray.length; i++) {
            // System.out.println("validate: " + testarray[i]);
            assertEquals(results[i], validator.isRomanNumber(testarray[i]));
        }
    }

    @Test
    public void test3SameLetters() {
        String[] testarray = { "III", "VVV XX", "XXXX", "AACCC ", "aaaa ", "bbb ", "aAa ", "Xxx ", "xxx", "XXX. 54-Strasse /()\\ " };
        boolean[] results = { true, true, true, false, true, true, true, true, true, true };
        for (int i = 0; i < testarray.length; i++) {
            // System.out.println("validate: " + testarray[i]);
            assertEquals(results[i], validator.beginnsWith3SameLetters(testarray[i]));
        }
    }

    @Test
    public void testAllowedChars() {
        assertTrue(validator.hasAllowedCharactersOnly("abcABC 123!"));
        assertTrue(validator.hasAllowedCharactersOnly("Straße 231"));
        assertTrue(validator.hasAllowedCharactersOnly("Straße äüö ÄÜÖ 231"));
        assertTrue(validator.hasAllowedCharactersOnly("Naša nová ulicaľščťžýáíéňúäô 231"));
        assertTrue(validator.hasAllowedCharactersOnly("abcABC 123! \\ / ' , . () - "));
        assertFalse(validator.hasAllowedCharactersOnly("abcABC *"));
        assertFalse(validator.hasAllowedCharactersOnly(" 9 + abc"));
    }

    @Test
    public void testHasAtLeast2CharsOr1Upper() {
        assertTrue(validator.checkHasAtLeast2CharsOr1Upper("aa"));
        assertTrue(validator.checkHasAtLeast2CharsOr1Upper("B"));
        assertTrue(validator.checkHasAtLeast2CharsOr1Upper("AB"));
        assertTrue(validator.checkHasAtLeast2CharsOr1Upper("Ab"));
        assertTrue(validator.checkHasAtLeast2CharsOr1Upper("aB"));
        assertTrue(validator.checkHasAtLeast2CharsOr1Upper("aaacc"));
        assertFalse(validator.checkHasAtLeast2CharsOr1Upper("b"));
    }

    @Test
    public void testNotEmpty() {
        assertFalse(validator.checkNotEmpty(null));
        assertFalse(validator.checkNotEmpty(""));
        assertTrue(validator.checkNotEmpty("Neue 7"));
    }

    @Test
    public void testCheckUnallowedBeginWithSameLettes() {
        // if all valid roman numbers were allowed
        // assertFalse(validator.checkUnallowedBeginWithSameLettes("IIII"));
        // assertTrue(validator.checkUnallowedBeginWithSameLettes("III"));
        // assertFalse(validator.checkUnallowedBeginWithSameLettes("AAA"));
        // assertFalse(validator.checkUnallowedBeginWithSameLettes("AAA Neue 7"));
        // assertFalse(validator.checkUnallowedBeginWithSameLettes("AAA. Neue 7"));
        // assertTrue(validator.checkUnallowedBeginWithSameLettes("XXX. 54-Strasse /()\\\\ -"));
        // assertTrue(validator.checkUnallowedBeginWithSameLettes("MMM-54 Strasse /()\\\\ -/"));
        // assertFalse(validator.checkUnallowedBeginWithSameLettes("MMM."));
        // assertFalse(validator.checkUnallowedBeginWithSameLettes("CCC-"));
        // assertTrue(validator.checkUnallowedBeginWithSameLettes("MMM.5"));

        assertFalse(validator.checkUnallowedBeginWithSameLettes("IIII"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("III"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("AAA"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("AAA Neue 7"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("AAA. Neue 7"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("XXX. 54-Strasse /()\\\\ -"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("MMM-54 Strasse /()\\\\ -/"));
        assertTrue(validator.checkUnallowedBeginWithSameLettes("MMM-Strasse 54 /()\\\\ -/"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("MMM."));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("CCC-"));
        assertFalse(validator.checkUnallowedBeginWithSameLettes("III."));
        assertTrue(validator.checkUnallowedBeginWithSameLettes("III.5"));

    }

    @Test
    public void testCheckFirstLetter() {
        assertTrue(validator.checkFirstLetter("Abc"));
        assertTrue(validator.checkFirstLetter("abc "));
        assertTrue(validator.checkFirstLetter("1 abc "));
        assertTrue(validator.checkFirstLetter("\" abc "));
        assertTrue(validator.checkFirstLetter("' abc "));
        assertFalse(validator.checkFirstLetter("- abc "));
        assertFalse(validator.checkFirstLetter("! abc "));
    }

    @Test
    public void testCheckCharAfterFirstNumber() {
        assertTrue(validator.checkCharAfterFirstNumber("Abc", false));
        assertTrue(validator.checkCharAfterFirstNumber("1 Neue", false));
        assertTrue(validator.checkCharAfterFirstNumber("1. Neue", false));
        assertTrue(validator.checkCharAfterFirstNumber("1-Neue", false));
        assertFalse(validator.checkCharAfterFirstNumber("1/Neue", false));

        assertTrue(validator.checkCharAfterFirstNumber("xxeue", true));
        assertTrue(validator.checkCharAfterFirstNumber("1 Neue", true));
        assertTrue(validator.checkCharAfterFirstNumber("1. Neue", true));
        assertTrue(validator.checkCharAfterFirstNumber("1-Neue", true));
        assertTrue(validator.checkCharAfterFirstNumber("1/Neue", true));
        assertTrue(validator.checkCharAfterFirstNumber("1\\Neue", true));
        assertTrue(validator.checkCharAfterFirstNumber("1,Neue", true));
        assertTrue(validator.checkCharAfterFirstNumber("1, Neue", true));
        assertFalse(validator.checkCharAfterFirstNumber("1(Neue", true));
    }

    @Test
    public void testFirstToken() {
        assertEquals("123", validator.getFirstToken("123"));
        assertEquals("abc", validator.getFirstToken("abc"));
        assertEquals("123", validator.getFirstToken("123 "));
        assertEquals("123", validator.getFirstToken("123."));
        assertEquals("123", validator.getFirstToken("123-"));
        assertEquals("123", validator.getFirstToken("123/"));
        assertEquals("123", validator.getFirstToken("123\\"));
        assertEquals("123", validator.getFirstToken("123()"));
        assertEquals("123", validator.getFirstToken("123()"));
    }

    @Test
    public void testCheckCharBeforeNumber() {
        assertTrue(validator.checkCharBeforeNumber("1. Test"));
        assertTrue(validator.checkCharBeforeNumber("1. Test 1"));
        assertTrue(validator.checkCharBeforeNumber("1. Test1"));
        assertTrue(validator.checkCharBeforeNumber("1. Test.1"));
        assertTrue(validator.checkCharBeforeNumber("12. Test.21"));
        assertFalse(validator.checkCharBeforeNumber(")1. Test1"));
        assertFalse(validator.checkCharBeforeNumber("1. Test,1"));
    }

    @Test
    public void testCheckCharBeforeDot() {
        assertTrue(validator.checkCharBeforeDot("1.Neue"));
        assertTrue(validator.checkCharBeforeDot("N."));
        assertFalse(validator.checkCharBeforeDot("."));
        assertFalse(validator.checkCharBeforeDot("-."));
        assertFalse(validator.checkCharBeforeDot("/."));
    }

    @Test
    public void testCheckLastChar() {
        assertTrue(validator.checkLastChar("1.Neue 1"));
        assertTrue(validator.checkLastChar("1.Neue"));
        assertTrue(validator.checkLastChar("1.Neue\""));
        assertTrue(validator.checkLastChar("1.Neue (34)"));
        assertTrue(validator.checkLastChar("1.Neue '"));
        assertTrue(validator.checkLastChar("1.Neue 5."));
        assertFalse(validator.checkLastChar("1.Neue 5.("));
        assertFalse(validator.checkLastChar("1.Neue 5.(/"));
        assertFalse(validator.checkLastChar("1.Neue 5.(\\"));
    }

    @Test
    public void test2SameSpecialCharacters() {
        assertFalse(validator.check2SameSpecialCharacters("1. '' Neue 5.(\\"));
        assertFalse(validator.check2SameSpecialCharacters("1.  Neue 5.(\\")); // two spaces
        assertFalse(validator.check2SameSpecialCharacters("1. Neue 5. -- 7"));
        assertFalse(validator.check2SameSpecialCharacters("1. Neue 5. )) 7"));
        assertFalse(validator.check2SameSpecialCharacters("1. Neue 5.. ) 7"));
        assertFalse(validator.check2SameSpecialCharacters("1. Neue 5'' ) 7"));
        assertTrue(validator.check2SameSpecialCharacters("1 Neue 5. - ' \" 7"));
        assertTrue(validator.check2SameSpecialCharacters("1 Neue 55 NN XX. - ' \" 7"));
    }
}
