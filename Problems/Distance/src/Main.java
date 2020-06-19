import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double distance = scanner.nextDouble();
        double time = scanner.nextDouble();
        scanner.close();

        double avgSpeed = distance / time;

        System.out.println(avgSpeed);
    }
}