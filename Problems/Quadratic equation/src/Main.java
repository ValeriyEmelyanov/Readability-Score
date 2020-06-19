import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        double d = Math.sqrt(b * b - 4 * a * c);
        double x1 = (-b + d) / (2 * a);
        double x2 = (-b - d) / (2 * a);

        System.out.printf("%f %f", Math.min(x1, x2), Math.max(x1, x2));
    }
}