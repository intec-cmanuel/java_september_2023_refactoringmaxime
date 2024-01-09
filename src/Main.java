import java.util.*;
import java.util.stream.Collectors;

public class Main {

    final static char[] SYMBOL_OK = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '%', '.', '(', ')'};

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String input = " ";
        String cleanCalculation;
        StringBuilder calculFinal;

        String parenthesisCalculation;
        List<Integer> indexOpenParenthesis = new ArrayList<>();

        List<Character> operators = new ArrayList();
        List<Double> numbers = new ArrayList();

        double result = 0;
        String resultStr;
        double resultFinal = 0;
        boolean resultDisplay;

        System.out.println("welkom bij de rekenmachine-app\n" +
                "Om de applicatie te verlaten, voer '0' in" );
        while (!input.equals("0")) {
            resultDisplay = true;
            System.out.println("Voer een berekening in: ");
            input = scanner.nextLine();

            cleanCalculation = (cleanInput(input));

            if (!input.equals(cleanCalculation))
                System.out.println("The rekening zonder onbekent tekenen: " + cleanCalculation);

            calculFinal = new StringBuilder(cleanCalculation);
            while (calculFinal.toString().contains("(")) {
                indexOpenParenthesis = getOpenParenthesisIndex(cleanCalculation);
                parenthesisCalculation = findPriority(cleanCalculation, indexOpenParenthesis);

                operators = searchOperator(parenthesisCalculation);

                try {
                    numbers = searchNumber(parenthesisCalculation);
                }catch (NumberFormatException e) {
                    System.out.println("sorry, deze rekenmachine herkent niet een van de tekenen ");
                }
                try {
                result = calculate(operators, numbers);
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Sorry, het is niet a correct berekening");
                }
                resultStr = String.valueOf(result);

                calculFinal.delete(indexOpenParenthesis.get(0), indexOpenParenthesis.get(1) + 1);

                calculFinal.insert(indexOpenParenthesis.get(0), resultStr);
                cleanCalculation = calculFinal.toString();
            }

            operators = searchOperator(cleanCalculation);
            try {
                numbers = searchNumber(cleanCalculation);
            } catch (NumberFormatException e) {
                System.out.println("sorry, deze rekenmachine herkent niet een van de tekenen ");
            }
            try {
                resultFinal = calculate(operators, numbers);
            }catch (IndexOutOfBoundsException e){
                System.out.println("Sorry, het is niet a correct berekening");
                resultDisplay = false;
            }
            if (input.contains("/") && resultDisplay)
                System.out.println(cleanInput(input) + " = " + resultFinal);
            else if (input.equals("0"))
                System.out.println("Dank u wel eb tot ziens");
            else if (resultDisplay)
                System.out.println(cleanInput(input) + " = " + Math.round(resultFinal));

        }
    }

    public static String cleanInput(String input){
        char[] array = input.toCharArray();
        List<Character> calculationList = new ArrayList<>();
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < SYMBOL_OK.length; j++){
              if (array[i] == SYMBOL_OK[j])
                  calculationList.add(array[i]);
            }
        }
        return calculationList.stream().map(String::valueOf).collect(Collectors.joining());
    }

    public static List<Integer> getOpenParenthesisIndex(String input){
        List<Integer> indexOpenParenthesis = new ArrayList<>();
        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == '(' || input.charAt(i) == ')')
                indexOpenParenthesis.add(i);
            if (indexOpenParenthesis.size() == 2)
                break;
        }
        return indexOpenParenthesis;
        }

    public static String findPriority(String input, List<Integer> indexOpenParenthesis){
        String calculation = " ";
        for (int i = 0; i < indexOpenParenthesis.size(); i+=2)
           calculation = input.substring(indexOpenParenthesis.get(i)+1, indexOpenParenthesis.get(i+1));
        return calculation;
    }


    public static List<Character> searchOperator(String calculation){
        List<Character> operators = new ArrayList<>() ;
        List<Character> calcul = new ArrayList<>() ;

        for (int i = 0; i < calculation.length(); i++)
            calcul.add(calculation.charAt(i));

        operators = calcul.stream()
                .filter(c -> c.equals('+') || c.equals('-') || c.equals('*') || c.equals('/') || c.equals('%'))
                .toList();
        return operators;
    }

    public static List<Double> searchNumber(String calculation) {
        List<Double> numbers = new ArrayList<>();;
        String num = " ";
        for (int i = 0; i < calculation.length(); i++) {
            if (calculation.charAt(i) >= 48 && calculation.charAt(i) <= 58 || calculation.charAt(i) == '.')
                num += calculation.charAt(i);
            else {
                numbers.add(Double.parseDouble(num));
                num = " ";
            }
            if (i == calculation.length()-1)
                numbers.add(Double.parseDouble(num));

        }
        return numbers;
    }

    public static double calculate(List<Character> operators, List<Double> numbers){
        double result = numbers.get(0);

        for (int i = 0; i < operators.size()+1; i++) {
            if (i < operators.size()) {
                switch (operators.get(i)) {
                    case '+':
                        result += numbers.get(i+1);
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
            }

        }
        return result;
    }
}