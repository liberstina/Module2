package sourceit.algorithm.implementation;
import sourceit.algorithm.interfaces.CalculationStrategy;

import java.util.LinkedList;
/**
 * Created by Liberstina on 30.03.2015.
 */
public class PolishStrategy implements CalculationStrategy {
    @Override
    public String calculate(String expression) {
        LinkedList<Double> numbers = new LinkedList<>();
        LinkedList<Character> operators = new LinkedList<>();
        LinkedList<String> currencies = new LinkedList<>();//2 currencies usd + euro

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (isPoint(currentChar)) {
                continue;
            }

            if (isDelimiter(currentChar)) {
                continue;
            }
            if (isCurrency(currentChar)){
                continue;
            }

            if (currentChar == '(') {
                operators.add('(');
            } else if (currentChar == ')') {
                while (operators.getLast() != '(') {
                    processOperator(numbers, operators.removeLast());
                }
                operators.removeLast();
            } else if (isOperator(currentChar)) {
                while (!operators.isEmpty()
                        && getPriority(operators.getLast()) >= getPriority(currentChar)) {
                    processOperator(numbers, operators.removeLast());
                }
                operators.add(currentChar);
            } else {
                StringBuilder value = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(currentChar)) || (isCurrency(currentChar))|| (isPoint(currentChar)) ){
                    value.append(expression.charAt(i++));

                    //there should be the condition for equal and different currencies, but i deleted the code i had,
                    // because it was written in midnight ravings (( and anyway didn't work
                    // disallow: $ +- number, ª +- number, $ +-*/ ª, $ +-*/ $, ª +-*/ ª
                    // allow: $ +- $, ª +- ª, $ */ number, ª*/ number

                }
                numbers.add(Double.parseDouble(value.toString()));
                --i;
            }
        }

        while (!operators.isEmpty()) {
            processOperator(numbers, operators.removeLast());
        }

        return String.valueOf(numbers.getFirst()+(currencies.getLast()));
    }


    private boolean isDelimiter(char c) {
        return c == ' ';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    private boolean isPoint(char c) {
        return c == '.';
    }
    private boolean isCurrency(char c) {
        return c == '$' || c == 'ª';
    }


    private int getPriority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private void processOperator(LinkedList<Double> st, char op) {
        Double rightOperand = st.removeLast();
        Double leftOperand = st.removeLast();
        switch (op) {
            case '+':
                st.add(leftOperand + rightOperand);
                break;
            case '-':
                st.add(leftOperand - rightOperand);
                break;
            case '*':
                st.add(leftOperand * rightOperand);
                break;
            case '/':
                st.add(leftOperand / rightOperand);
                break;
        }
    }


    public static void main(String[] args) {
        System.out.println(new PolishStrategy().calculate(String.valueOf(1.213 * (3.554 + 2.366))));
    }
}