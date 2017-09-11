package org.pko.test;

import org.junit.Test;
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
            assertEquals(results[i], AddressDataValidator.isRomanNumber(testarray[i]));
        }
    }

    @Test
    public void test3SameLetters() {
        String[] testarray = { "III", "VVV XX", "XXXX", "AACCC ", "aaaa ", "bbb ", "aAa ", "Xxx ", "xxx", "XXX. 54-Strasse /()\\ " };
        boolean[] results = { true, true, true, false, true, true, true, true, true, true };
        for (int i = 0; i < testarray.length; i++) {
            // System.out.println("validate: " + testarray[i]);
            assertEquals(results[i], AddressDataValidator.beginnsWith3SameLetters(testarray[i]));
        }
    }

    @Test
    public void testAllowedChars() {
        assertTrue(AddressDataValidator.hasAllowedCharactersOnly("abcABC 123!"));
        assertTrue(AddressDataValidator.hasAllowedCharactersOnly("Straße 231"));
        assertTrue(AddressDataValidator.hasAllowedCharactersOnly("Straße äüö ÄÜÖ 231"));
        assertTrue(AddressDataValidator.hasAllowedCharactersOnly("Naša nová ulicaľščťžýáíéňúäô 231"));
        assertTrue(AddressDataValidator.hasAllowedCharactersOnly("abcABC 123! \\ / ' , . () - "));
        assertFalse(AddressDataValidator.hasAllowedCharactersOnly("abcABC *"));
        assertFalse(AddressDataValidator.hasAllowedCharactersOnly(" 9 + abc"));
    }

    @Test
    public void testHasAtLeast2CharsOr1Upper() {
        assertTrue(AddressDataValidator.checkHasAtLeast2CharsOr1Upper("aa"));
        assertTrue(AddressDataValidator.checkHasAtLeast2CharsOr1Upper("B"));
        assertTrue(AddressDataValidator.checkHasAtLeast2CharsOr1Upper("AB"));
        assertTrue(AddressDataValidator.checkHasAtLeast2CharsOr1Upper("Ab"));
        assertTrue(AddressDataValidator.checkHasAtLeast2CharsOr1Upper("aB"));
        assertTrue(AddressDataValidator.checkHasAtLeast2CharsOr1Upper("aaacc"));
        assertFalse(AddressDataValidator.checkHasAtLeast2CharsOr1Upper("b"));
    }

    @Test
    public void testNotEmpty() {
        assertFalse(AddressDataValidator.checkNotEmpty(null));
        assertFalse(AddressDataValidator.checkNotEmpty(""));
        assertTrue(AddressDataValidator.checkNotEmpty("Neue 7"));
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

        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("IIII"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("III"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("AAA"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("AAA Neue 7"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("AAA. Neue 7"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("XXX. 54-Strasse /()\\\\ -"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("MMM-54 Strasse /()\\\\ -/"));
        assertTrue(AddressDataValidator.checkUnallowedBeginWithSameLettes("MMM-Strasse 54 /()\\\\ -/"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("MMM."));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("CCC-"));
        assertFalse(AddressDataValidator.checkUnallowedBeginWithSameLettes("III."));
        assertTrue(AddressDataValidator.checkUnallowedBeginWithSameLettes("III.5"));

    }

    @Test
    public void testCheckFirstLetter() {
        assertTrue(AddressDataValidator.checkFirstLetter("Abc"));
        assertTrue(AddressDataValidator.checkFirstLetter("abc "));
        assertTrue(AddressDataValidator.checkFirstLetter("1 abc "));
        assertTrue(AddressDataValidator.checkFirstLetter("\" abc "));
        assertTrue(AddressDataValidator.checkFirstLetter("' abc "));
        assertFalse(AddressDataValidator.checkFirstLetter("- abc "));
        assertFalse(AddressDataValidator.checkFirstLetter("! abc "));
    }

    @Test
    public void testCheckCharAfterFirstNumber() {
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("Abc", false));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1 Neue", false));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1. Neue", false));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1-Neue", false));
        assertFalse(AddressDataValidator.checkCharAfterFirstNumber("1/Neue", false));

        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("xxeue", true));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1 Neue", true));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1. Neue", true));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1-Neue", true));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1/Neue", true));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1\\Neue", true));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1,Neue", true));
        assertTrue(AddressDataValidator.checkCharAfterFirstNumber("1, Neue", true));
        assertFalse(AddressDataValidator.checkCharAfterFirstNumber("1(Neue", true));
    }

    @Test
    public void testFirstToken() {
        assertEquals("123", AddressDataValidator.getFirstToken("123"));
        assertEquals("abc", AddressDataValidator.getFirstToken("abc"));
        assertEquals("123", AddressDataValidator.getFirstToken("123 "));
        assertEquals("123", AddressDataValidator.getFirstToken("123."));
        assertEquals("123", AddressDataValidator.getFirstToken("123-"));
        assertEquals("123", AddressDataValidator.getFirstToken("123/"));
        assertEquals("123", AddressDataValidator.getFirstToken("123\\"));
        assertEquals("123", AddressDataValidator.getFirstToken("123()"));
        assertEquals("123", AddressDataValidator.getFirstToken("123()"));
    }

    @Test
    public void testCheckCharBeforeNumber() {
        assertTrue(AddressDataValidator.checkCharBeforeNumber("1. Test"));
        assertTrue(AddressDataValidator.checkCharBeforeNumber("1. Test 1"));
        assertTrue(AddressDataValidator.checkCharBeforeNumber("1. Test1"));
        assertTrue(AddressDataValidator.checkCharBeforeNumber("1. Test.1"));
        assertTrue(AddressDataValidator.checkCharBeforeNumber("12. Test.21"));
        assertFalse(AddressDataValidator.checkCharBeforeNumber(")1. Test1"));
        assertFalse(AddressDataValidator.checkCharBeforeNumber("1. Test,1"));
    }

    @Test
    public void testCheckCharBeforeDot() {
        assertTrue(AddressDataValidator.checkCharBeforeDot("1.Neue"));
        assertTrue(AddressDataValidator.checkCharBeforeDot("N."));
        assertFalse(AddressDataValidator.checkCharBeforeDot("."));
        assertFalse(AddressDataValidator.checkCharBeforeDot("-."));
        assertFalse(AddressDataValidator.checkCharBeforeDot("/."));
    }

    @Test
    public void testCheckLastChar() {
        assertTrue(AddressDataValidator.checkLastChar("1.Neue 1"));
        assertTrue(AddressDataValidator.checkLastChar("1.Neue"));
        assertTrue(AddressDataValidator.checkLastChar("1.Neue\""));
        assertTrue(AddressDataValidator.checkLastChar("1.Neue (34)"));
        assertTrue(AddressDataValidator.checkLastChar("1.Neue '"));
        assertTrue(AddressDataValidator.checkLastChar("1.Neue 5."));
        assertFalse(AddressDataValidator.checkLastChar("1.Neue 5.("));
        assertFalse(AddressDataValidator.checkLastChar("1.Neue 5.(/"));
        assertFalse(AddressDataValidator.checkLastChar("1.Neue 5.(\\"));
    }

    @Test
    public void test2SameSpecialCharacters() {
        assertFalse(AddressDataValidator.check2SameSpecialCharacters("1. '' Neue 5.(\\"));
        assertFalse(AddressDataValidator.check2SameSpecialCharacters("1.  Neue 5.(\\")); // two spaces
        assertFalse(AddressDataValidator.check2SameSpecialCharacters("1. Neue 5. -- 7"));
        assertFalse(AddressDataValidator.check2SameSpecialCharacters("1. Neue 5. )) 7"));
        assertFalse(AddressDataValidator.check2SameSpecialCharacters("1. Neue 5.. ) 7"));
        assertFalse(AddressDataValidator.check2SameSpecialCharacters("1. Neue 5'' ) 7"));
        assertTrue(AddressDataValidator.check2SameSpecialCharacters("1 Neue 5. - ' \" 7"));
        assertTrue(AddressDataValidator.check2SameSpecialCharacters("1 Neue 55 NN XX. - ' \" 7"));
    }
}
