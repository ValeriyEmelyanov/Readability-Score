import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int size = scanner.nextInt();
        scanner.close();

        final int[][] matrix = createAndFillMatrix(size);

        printMatrix(matrix);

    }

    private static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int i : ints) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    private static int[][] createAndFillMatrix(int size) {
        final int[][] matrix = new int[size][size];

        int counter = 1;
        int bound = size * size;
        Point point = new Point(0, 0);
        Limit limit = new Limit(size - 1, 0, size - 1, 1);
        Direction direction = Direction.RIGHT;

        while (counter <= bound) {
            matrix[point.y][point.x] = counter;

            point.x += direction.dX;
            point.y += direction.dY;

            direction = Direction.getDirection(direction, point, limit);
            counter++;
        }

        return matrix;
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Limit {
        int rightLimit;
        int leftLimit;
        int upLimit;
        int downLimit;

        public Limit(int rightLimit, int leftLimit, int upLimit, int downLimit) {
            this.rightLimit = rightLimit;
            this.leftLimit = leftLimit;
            this.upLimit = upLimit;
            this.downLimit = downLimit;
        }
    }

    enum Direction {
        RIGHT(1, 0), UP(0, 1), LEFT(-1, 0), DOWN(0, -1);

        int dX;
        int dY;

        Direction(int dX, int dY) {
            this.dX = dX;
            this.dY = dY;
        }

        public static Direction getDirection(Direction direction, Point point, Limit limit) {
            if (point.x == limit.rightLimit && direction == Direction.RIGHT) {
                limit.rightLimit--;
                return Direction.UP;
            }
            if (point.y == limit.upLimit && direction == Direction.UP) {
                limit.upLimit--;
                return Direction.LEFT;
            }
            if (point.x == limit.leftLimit && direction == Direction.LEFT) {
                limit.leftLimit++;
                return Direction.DOWN;
            }
            if (point.y == limit.downLimit && direction == Direction.DOWN) {
                limit.downLimit++;
                return Direction.RIGHT;
            }
            return direction;
        }
    }
}