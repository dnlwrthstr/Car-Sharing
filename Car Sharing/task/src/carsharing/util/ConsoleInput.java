package carsharing.util;

import java.util.Scanner;

public class ConsoleInput {
    private ConsoleInput() {
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static String nextLine() {
        return scanner.nextLine().trim();
    }

    public static int nextInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
