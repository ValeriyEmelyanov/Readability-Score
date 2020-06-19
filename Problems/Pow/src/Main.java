import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        double number = scanner.nextDouble();
        double degree = scanner.nextDouble();

        double result = Math.pow(number, degree);

        System.out.println(result);
    }
}