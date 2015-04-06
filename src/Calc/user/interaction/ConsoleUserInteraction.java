package Calc.user.interaction;

import Calc.algorithm.implementation.PolishStrategy;
import Calc.algorithm.interfaces.CalculationStrategy;

import java.util.Scanner;

public class ConsoleUserInteraction {

    public static void main(String[] args) {
       
        CalculationStrategy calcStrategy = new PolishStrategy();

        System.out.println("Currency Converter: \n" +
                "\tFor exit enter: exit");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter an expression to be calculated: ");
                // 100*(3+2*(5-1))+(10-1)
                String enteredExp = scanner.nextLine();
                if ("exit".equalsIgnoreCase(enteredExp)) {
                    break;
                }
                System.out.println(calcStrategy.calculate(enteredExp));
            }
        } catch (Exception exc) {
            System.out.println("The following error happened during processing your expression\n\t" + exc);
        }

        System.out.print("\nBye-bye!");
    }
}
