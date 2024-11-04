package simple;

import java.util.Map;
import java.util.Scanner;

public class AsciiQuoteGenerator {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string (a-z or A-Z only) to generate ASCII art:");
        String input = scanner.nextLine();

        // Validate input
        
        if (!input.matches("[a-zA-Z0-9 ]+")) {
            System.out.println("Invalid input! Please use only letters A-Z, a-z, or spaces.");
            return;
        }

        // Build the ASCII art in a StringBuilder
        StringBuilder sb = new StringBuilder();
        StringBuilder sbMirrored = new StringBuilder(); // To hold the mirrored version
        int i = 17;
        if (input.matches(".*[qpgjy].*")) {
            i = 24;
        }

        Map<Character, String[]> asciiMap = AsciiArt.getAsciiMap();

        for (int line = 0; line < i; line++) { // Assuming each letter has 5 lines
            for (char ch : input.toCharArray()) {
                String[] asciiArt = asciiMap.get(ch);
                if (asciiArt != null) {
                    // char randomChar = (char) ('!' + (int) (Math.random() * 94));
                    // asciiArt[line] = asciiArt[line].replace(':', randomChar);
                    // asciiArt[line] = asciiArt[line].replace(':', '♦');
                    // sb.append(asciiArt[line].replace(" ", "█")).append(" "); // Bold Style
                    // sb.append(asciiArt[line].replace(" ", "░")).append(" "); // Outline Style
                    // sb.append(asciiArt[line].replace(" ", "~")).append(" "); // Wave Style
                    // String reversedLine = new StringBuilder(asciiArt[line]).reverse().toString();
                    // sb.append(reversedLine).append(" "); // Reverse Style
                    // sb.append(asciiArt[line].replace(" ", "|")).append(" "); // 3D Style
                    sbMirrored.append(asciiArt[line].replace(" ", "█")).append(" "); // Mirror Style
                }
            }
            // sb.append("\n"); // Newline after each row of ASCII art
            sbMirrored.append("\n"); // Newline after each row of mirrored ASCII art
        }

        // Print the ASCII art
        // System.out.println(sb.toString());

        // Append the mirrored version
        String[] mirroredLines = sbMirrored.toString().split("\n");
        for (int line = 0; line < mirroredLines.length; line++) {
            String tiltedLine = " ".repeat(line) + mirroredLines[mirroredLines.length - 1 - line].replace("█", "░");
            sbMirrored.append(tiltedLine).append("\n");
        }

        // Print the mirrored ASCII art
        System.out.println(sbMirrored.toString());

        // Convert ASCII art to image and save
        String outputFilePath = "logo.jpg";
        ToImageConverter.convertToImage(sbMirrored, outputFilePath);

        System.out.println("ASCII art saved as image at " + outputFilePath);

        scanner.close();
    }
}
