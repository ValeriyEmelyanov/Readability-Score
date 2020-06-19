package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int[] AGES = {6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 25};

    private static String text;
    private static int sentences;
    private static int words;
    private static int characters;
    private static int syllables;
    private static int polysyllables;

    public static void main(String[] args) {
        text = readFile(args[0]);
        analyzeText();
        printAnalysis();
        String algorithm = getAlgorithm();
        calculateAlgorithm(algorithm);
    }

    private static String getAlgorithm() {
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        final Scanner scanner = new Scanner(System.in);
        String algorithm = scanner.nextLine();
        scanner.close();
        return algorithm;
    }

    private static void calculateAlgorithm(String algorithm) {

        double avdAge = 0;
        switch (algorithm) {
            case "ARI":
                avdAge = calcARI();
                break;
            case "FK":
                avdAge = calcFK();
                break;
            case "SMOG":
                avdAge = calcSMOG();
                break;
            case "CL":
                avdAge = calcCL();
                break;
            case "all":
                avdAge = (calcARI() + calcFK() + calcSMOG() + calcCL()) / 4.0;
                break;
            default:
                throw new IllegalArgumentException("Invalid algorithm");
        }
        System.out.println();
        System.out.println(String.format(
                "This text should be understood in average by %2.2f year olds.",
                avdAge));
    }

    private static int getAgeByReadabilityIndex(double score) {
        int index = (int) Math.round(score) - 1;
        return index >= AGES.length ? AGES[AGES.length - 1] : AGES[index];
    }

    private static String readFile(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid file!");
        }
    }

    private static void analyzeText() {
        sentences = text.split("[.!?]\\s*").length;

        String[] wordsArr = text.replaceAll("[.!?]", "").split("[\\s\\p{Z}\\p{Space}\\p{Blank}]+");
        words = wordsArr.length;

        characters = text.replaceAll("[\\s\\p{Z}\\p{Space}\\p{Blank}]+", "").length();

        syllables = 0;
        polysyllables = 0;
        int count;
        Pattern pattern = Pattern.compile("[aeiouyAEIOUY]{1,3}");
        Matcher matcher;
        for (String word : wordsArr) {
            if (word.endsWith("e")) {
                word = word.substring(0, word.length() - 1);
            }
            matcher = pattern.matcher(word);
            count = 0;
            while (matcher.find()) {
                count++;
            }
            syllables += count == 0 ? 1 : count;
            polysyllables += count > 2 ? 1 : 0;
        }
    }

    private static void printAnalysis() {
        System.out.println("The text is:");
        System.out.println();
        System.out.println(String.format("Words: %d", words));
        System.out.println(String.format("Sentences: %d", sentences));
        System.out.println(String.format("Characters: %d", characters));
        System.out.println(String.format("Syllables: %d", syllables));
        System.out.println(String.format("Polysyllables: %d", polysyllables));
        System.out.println();
    }

    private static int calcARI() {
        double score = 4.71 * characters / words + 0.5 * words / sentences - 21.43;
        int age = getAgeByReadabilityIndex(score);
        System.out.println(String.format(
                "Automated Readability Index: %2.2f (about %d year olds)",
                score, age));
        return age;
    }

    private static int calcFK() {
        double score = 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
        int age = getAgeByReadabilityIndex(score);
        System.out.println(String.format(
                "Flesch–Kincaid readability tests: %2.2f (about %d year olds)",
                score, age));
        return age;
    }

    private static int calcSMOG() {
        double score = 1.043 * Math.sqrt(polysyllables * 30.0 / sentences) + 3.1291;
        int age = getAgeByReadabilityIndex(score);
        System.out.println(String.format(
                "Simple Measure of Gobbledygook: %2.2f (about %d year olds)",
                score, age));
        return age;
    }

    private static int calcCL() {
        double avgNumOfChars = characters * 100.0 / words;
        double avgNumOfSents = sentences * 100.0 / words;
        double score = 0.0588 * avgNumOfChars - 0.296 * avgNumOfSents - 15.8;
        int age = getAgeByReadabilityIndex(score);
        System.out.println(String.format(
                "Coleman–Liau index: %2.2f (about %d year olds)",
                score, age));
        return age;
    }
}
