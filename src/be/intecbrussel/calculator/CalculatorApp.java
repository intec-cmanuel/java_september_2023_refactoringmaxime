package be.intecbrussel.calculator;

import be.intecbrussel.calculator.service.MathExpressionService;
import be.intecbrussel.calculator.utils.CharacterValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MathExpressionService mess = new MathExpressionService();
        StringBuilder calculFinal;

        String input = " ";
        String cleanCalculation;
        String parenthesisCalculation;
        String resultString;

        List<Integer> indexOpenParenthesis = new ArrayList<>();
        List<Character> operators = new ArrayList();
        List<Double> numbers = new ArrayList();

        double result = 0;
        double resultFinal = 0;

        boolean resultDisplay;

        System.out.println("welkom bij de rekenmachine-app\n" +
                "Om de applicatie te verlaten, voer '0' in");
        while (!input.equals("0")) {
            resultDisplay = true;
            System.out.println("Voer een berekening in: ");
            input = scanner.nextLine();

            cleanCalculation = (CharacterValidator.cleanInput(input));

            if (!input.equals(cleanCalculation))
                System.out.println("The rekening zonder onbekent tekenen: " + cleanCalculation);

            calculFinal = new StringBuilder(cleanCalculation);
            while (calculFinal.toString().contains("(")) {
                indexOpenParenthesis = mess.getOpenParenthesisIndex(cleanCalculation);
                parenthesisCalculation = mess.findPriority(cleanCalculation, indexOpenParenthesis);

                operators = mess.searchOperator(parenthesisCalculation);

                try {
                    numbers = mess.searchNumber(parenthesisCalculation);
                } catch (NumberFormatException e) {
                    System.out.println("sorry, deze rekenmachine herkent niet een van de tekenen ");
                }
                try {
                    result = mess.calculate(operators, numbers);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sorry, het is niet a correct berekening");
                }
                resultString = String.valueOf(result);

                calculFinal.delete(indexOpenParenthesis.get(0), indexOpenParenthesis.get(1) + 1);

                calculFinal.insert(indexOpenParenthesis.get(0), resultString);
                cleanCalculation = calculFinal.toString();
            }

            operators = mess.searchOperator(cleanCalculation);
            try {
                numbers = mess.searchNumber(cleanCalculation);
            } catch (NumberFormatException e) {
                System.out.println("sorry, deze rekenmachine herkent niet een van de tekenen ");
            }
            try {
                resultFinal = mess.calculate(operators, numbers);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Sorry, het is niet a correct berekening");
                resultDisplay = false;
            }
            if (input.contains("/") && resultDisplay)
                System.out.println(CharacterValidator.cleanInput(input) + " = " + resultFinal);
            else if (input.equals("0"))
                System.out.println("Dank u wel eb tot ziens");
            else if (resultDisplay)
                System.out.println(CharacterValidator.cleanInput(input) + " = " + Math.round(resultFinal));

        }
    }

}
