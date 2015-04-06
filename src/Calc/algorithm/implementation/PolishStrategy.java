package Calc.algorithm.implementation;
import Calc.algorithm.interfaces.CalculationStrategy;

import java.util.LinkedList;
/**
 * Created by Liberstina on 30.03.2015.
 */
public class PolishStrategy implements CalculationStrategy {
    @Override
    public String calculate(String expression)  throws Exception{
        LinkedList<Double> numbers = new LinkedList<>();
        LinkedList<Character> operators = new LinkedList<>();
        LinkedList<String> currencies = new LinkedList<>();//2 currencies $ + €

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (isDelimiter(currentChar)) {
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
            } else if (currentChar == '$') {
                currencies.add("$");
            } else if (currentChar == '€') {
                currencies.add("€");
                StringBuilder currency = new StringBuilder();
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    currency.append(expression.charAt(i++));
                }
                if ("eur".equals(currency.toString())) {
                    currencies.add(currency.toString());
                    --i;
                } else {
                    throw new Exception("Error: incorrect expression");
                }
            } else {

                StringBuilder value = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(currentChar)) || (isCurrency(currentChar)) || (isPoint(currentChar))) {
                    value.append(expression.charAt(i++));
                }
                numbers.add(Double.parseDouble(value.toString()));

                --i;
            }
        }

        while (!operators.isEmpty()) {
            processOperator(numbers, operators.removeLast());
        }
        if (currencies.getFirst().equals("null")) {
            return String.valueOf(numbers.getFirst());
        } else {
            return String.valueOf(numbers.getFirst()) + currencies.getFirst();
        }
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
        return c == '$' || c == '€';
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
        try {
            System.out.println(new PolishStrategy().calculate("1.213 * (3.554$ + 2.366$)"));
        }
        catch (Exception exc){

            System.out.println(exc.getMessage());
        }
    }
}