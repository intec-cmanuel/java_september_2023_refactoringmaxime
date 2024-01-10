package be.intecbrussel.calculator.utils;

import java.util.stream.Collectors;

public class CharacterValidator {
    public static boolean isValidCharacter(char characterToValidate) {
        final char[] SYMBOL_OK = getLegalSymbols();

        for (char c : SYMBOL_OK) {
            if (c == characterToValidate) {
                return true;
            }
        }

        return false;
    }

    public static String cleanInput(String input) {
        return input.chars()
                .filter(characterInt -> isValidCharacter((char) characterInt))
                .mapToObj(characterInt -> String.valueOf((char) characterInt))
                .collect(Collectors.joining());
    }


    private static char[] getLegalSymbols() {
        return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '%', '.', '(', ')'};
    }


}
