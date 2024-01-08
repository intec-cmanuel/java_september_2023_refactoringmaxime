import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        final char[] SYMBOL_OK = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '%', ',', '.'};

        String input;
        List operators = new ArrayList();
        List numbers = new ArrayList();

        System.out.println("Voer een berekening in: ");
        input = scanner.nextLine() + "0";
        System.out.println(input);
        operators = searchOperator(input);
        numbers = searchNumber(input);
        System.out.println(numbers);
        calculate(operators, numbers);
    }

    public static List<Character> searchOperator(String input){
        char[]array;
        array = input.toCharArray();
        char operator = ' ';
        ArrayList<Character> operators = new ArrayList<>();


        for (char c : array)
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
                operator = c;
                operators.add(c);
                System.out.println(operator);
            }
        return operators;
    }

    public static List<Double> searchNumber(String input) {
        ArrayList<Character> symbol_ok = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')) ;
        char[] array;
        array = input.toCharArray();
        double num = 0;
        ArrayList<Double> numbers = new ArrayList<>();

        for (int i = 0; i < array.length-1; i++)
            if (array[i] >= 48 && array[i] <= 58 && !(symbol_ok.contains(array[i+1]))) {
                num = array[i] - 48;
                numbers.add(num);
            }
        System.out.println(numbers);
        return numbers;
    }

    public static void calculate(List<Character> operators, List<Double> numbers){
        double result = numbers.get(0);

        for (int i = 0; i < operators.size()+1; i++) {
            if (i < operators.size()) {
                switch (operators.get(i)) {
                    case '+':
                        result += numbers.get(i);
                        break;
                    case '-':
                        result -= numbers.get(i+1);
                        break;
                    case '*':
                        result *= numbers.get(i+1);
                        break;
                    case '/':
                        result /= numbers.get(i+1);
                        break;
                    case '%':
                        result %= numbers.get(i+1);
                        break;
                }
                System.out.println(operators.get(i) + " " + result);
            }

        }
        System.out.println(result);
    }
}