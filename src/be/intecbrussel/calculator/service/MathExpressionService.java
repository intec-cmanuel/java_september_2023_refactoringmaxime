package be.intecbrussel.calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MathExpressionService {
    public double calculate(List<Character> operators, List<Double> numbers) {
        double result = numbers.get(0);

        for (int i = 0; i < operators.size() + 1; i++) {
            if (i < operators.size()) {
                switch (operators.get(i)) {
                    case '+':
                        result += numbers.get(i + 1);
                        break;
                    case '-':
                        result -= numbers.get(i + 1);
                        break;
                    case '*':
                        result *= numbers.get(i + 1);
                        break;
                    case '/':
                        result /= numbers.get(i + 1);
                        break;
                    case '%':
                        result %= numbers.get(i + 1);
                        break;
                }
            }

        }
        return result;
    }

    public String findPriority(String input, List<Integer> indexOpenParenthesis) {
        String calculation = " ";
        for (int i = 0; i < indexOpenParenthesis.size(); i += 2)
            calculation = input.substring(indexOpenParenthesis.get(i) + 1, indexOpenParenthesis.get(i + 1));
        return calculation;
    }

    public List<Character> searchOperator(String calculation) {
        List<Character> operators = new ArrayList<>();
        List<Character> calcul = new ArrayList<>();

        for (int i = 0; i < calculation.length(); i++)
            calcul.add(calculation.charAt(i));

        operators = calcul.stream()
                .filter(c -> c.equals('+') || c.equals('-') || c.equals('*') || c.equals('/') || c.equals('%'))
                .toList();
        return operators;
    }

    public List<Double> searchNumber(String calculation) {
        List<Double> numbers = new ArrayList<>();
        ;
        String num = " ";
        for (int i = 0; i < calculation.length(); i++) {
            if (calculation.charAt(i) >= 48 && calculation.charAt(i) <= 58 || calculation.charAt(i) == '.')
                num += calculation.charAt(i);
            else {
                numbers.add(Double.parseDouble(num));
                num = " ";
            }
            if (i == calculation.length() - 1)
                numbers.add(Double.parseDouble(num));

        }
        return numbers;
    }

    public List<Integer> getOpenParenthesisIndex(String cleanInput) {
        List<Integer> indexOpenParenthesis = new ArrayList<>();
        for (int i = 0; i < cleanInput.length(); i++) {
            if (cleanInput.charAt(i) == '(' || cleanInput.charAt(i) == ')') indexOpenParenthesis.add(i);
            if (indexOpenParenthesis.size() == 2) break;
        }
        return indexOpenParenthesis; // ( )  5 + (4 + (3 - 2))
    }


}
