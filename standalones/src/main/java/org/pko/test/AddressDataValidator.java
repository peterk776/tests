package org.pko.test;

import java.util.regex.Pattern;

/**
 * AddressDataValidator
 *
 * @author Peter
 * @date 6.9.2017
 */
public class AddressDataValidator {

    public static final String ALLOWED_CHARS_PATTERN = "[\\p{L}\\d \\-.,'!()/\"\\\\]*";
    public static final String TWO_LETTERS_PATTERN = "[a-zA-Z]{2}";
    public static final String TWO_SAME_SPECIAL_CHARS_PATTERN = "([ -.,'!()\\/\"])\\1";
    public static final Pattern pattern2 = Pattern.compile(TWO_SAME_SPECIAL_CHARS_PATTERN, Pattern.CASE_INSENSITIVE);
    public static final String ROMAN_NUMBERS_REGEXP_PATTERN = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
    public static final String THREE_SAME_LETTERS_PATTERN = "^([A-Za-z])\\1{2,}[ \\w\\W]*";
    public static final String THREE_SAME_ROMAN_NUMBERS_PATTERN = "^([I,C,M,X])\\1{2}";
    public static final Pattern pattern3 = Pattern.compile(THREE_SAME_LETTERS_PATTERN, Pattern.CASE_INSENSITIVE);

    public AddressDataValidator() {
    }

    public static boolean isRomanNumber(String input) {
        checkNotNull(input, "Input can not be null");
        return input.matches(ROMAN_NUMBERS_REGEXP_PATTERN);
    }

    public static boolean beginnsWith3SameLetters(String input) {
        checkNotNull(input, "Input can not be null");
        return pattern3.matcher(input).find();
    }

    /**
     *
     * @param input
     * @return true if contains only allowed characters
     */
    public static boolean hasAllowedCharactersOnly(String input) {
        checkNotNull(input, "Input can not be null");
        return input.matches(ALLOWED_CHARS_PATTERN);
    }

    /**
     *
     * @param input
     * @return true if OK
     */
    public static boolean checkHasAtLeast2CharsOr1Upper(String input) {
        checkNotNull(input, "Input can not be null");
        if (input.length() == 1)
            return Character.isLetter(input.charAt(0)) && Character.isUpperCase(input.charAt(0));
        else if (input.length() == 2)
            return input.matches(TWO_LETTERS_PATTERN);
        else
            return true;
    }

    /**
     *
     * @param input
     * @return true if OK
     */
    public static boolean checkNotEmpty(String input) {
        if (input == null || "".equals(input))
            return false;

        return true;
    }

    /**
     *
     * @param input
     * @return true if OK
     */
    public static boolean check2SameSpecialCharacters(String input) {
        checkNotNull(input, "Input can not be null");
        return !pattern2.matcher(input).find();
    }

    /**
     *
     * @param input
     * @return true if OK
     */
    public static boolean checkUnallowedBeginWithSameLettes(String input) {
        checkNotNull(input, "Input can not be null");
        if (input.length() < 3)
            return true;

        if (beginnsWith3SameLetters(input)) {
            String token = getFirstToken(input);
            if (!isRomanNumber(token)) {
                return false;
            }

            if (input.contains(".") || input.contains("-")) {
                if (input.charAt(token.length()) == '.' && input.endsWith("."))
                    return false;

                if (input.charAt(token.length()) == '-' && input.endsWith("-"))
                    return false;
            }

            // additionally allow only 'III.' and 'MMM-' but let active the code above for possible changes, where more roman numbers should be supported
            if (input.startsWith("III.") && !input.endsWith("."))
                return true;

            if (input.startsWith("MMM-Str"))
                return true;

            return false;
        }

        return true;
    }

    /**
     *
     * @return true if OK
     */
    public static boolean checkFirstLetter(String input) {
        char first = input.charAt(0);
        if (Character.isLetterOrDigit(first) || '\'' == first || '"' == first)
            return true;
        else
            return false;

    }

    /**
     *
     * @param input
     * @return true if OK
     */
    public static boolean checkCharAfterFirstNumber(String input, boolean foreign) {
        String first = getFirstToken(input);

        try {
            Integer.parseInt(first); // if not a number return true
            char next = input.charAt(first.length());
            if (foreign) {
                if (Character.isLetter(next) || next == '.' || next == ' ' || next == '-' || next == ',' || next == '\\' || next == '/')
                    return true;
            } else {
                if (Character.isLetter(next) || next == '.' || next == ' ' || next == '-')
                    return true;

            }
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     *
     * @param input
     * @return true if OK
     */
    public static boolean checkCharBeforeNumber(String input) {
        checkNotNull(input, "Input can not be null");

        for (int i = 1; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                char prevChar = input.charAt(i - 1);
                if (!(prevChar == ' ' || prevChar == '.' || Character.isLetterOrDigit(prevChar)))
                    return false;
            }
        }

        return true;
    }

    /**
     * Vor einem Punkt muss ein Buchstabe oder eine Ziffer stehen.
     * Fehlernummer: DBAN166 (Strasse)
     *
     * @param input
     * @return true of OK
     */
    public static boolean checkCharBeforeDot(String input) {
        checkNotNull(input, "Input can not be null");
        int index = input.indexOf('.');
        if (index < 0)
            return true;

        if (index == 0)
            return false;

        char toCheck = input.charAt(index - 1);
        if (Character.isLetterOrDigit(toCheck))
            return true;
        else
            return false;
    }

    /**
     *
     * @param input
     * @return true if OK
     */
    public static boolean checkLastChar(String input) {
        checkNotNull(input, "Input can not be null");
        char last = input.charAt(input.length() - 1);
        if (Character.isLetterOrDigit(last) || last == '.' || last == ')' || last == '"' || last == '\'' || last == '-')
            return true;
        else
            return false;
    }

    public static String getFirstToken(String input) {
        checkNotNull(input, "Input can not be null");
        return input.split("\\W")[0];
    }

    private static void checkNotNull(String input, String message) {
        if (input == null)
            throw new IllegalArgumentException(message);
    }

}
