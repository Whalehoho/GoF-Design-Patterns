package simple;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ToImageConverter {

    public static void convertToImage(StringBuilder sb, String filePath) {
        // Convert StringBuilder to String and split into lines
        String asciiArt = sb.toString();
        String[] lines = asciiArt.split("\n");

        // Set up a monospaced font and calculate the image size based on text size
        Font font = new Font("Courier New", Font.PLAIN, 16);  // Monospaced font for ASCII art
        int lineHeight = 15;  // Approximate line height based on font size
        int width = calculateImageWidth(lines, font);
        int height = lines.length * lineHeight;

        // Create BufferedImage to draw the ASCII text
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color and clear the image
        g2d.setColor(Color.WHITE); // White background
        g2d.fillRect(0, 0, width, height);

        // Set font color and font
        g2d.setColor(Color.BLACK); // Black text color
        g2d.setFont(font);

        // Draw each line of ASCII art text onto the image with spacing adjustments
        int y = lineHeight;
        for (String line : lines) {
            g2d.drawString(line, 10, y); // Adjust as necessary for alignment
            y += lineHeight; // Increment y position by line height for next line
        }

        // Release graphics resources and save the image
        g2d.dispose();
        
        try {
            ImageIO.write(image, "jpg", new File(filePath));
            System.out.println("Image saved successfully at " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save image.");
        }
    }

    // Helper method to calculate image width based on the longest line of ASCII art text
    private static int calculateImageWidth(String[] lines, Font font) {
        int maxWidth = 0;
        FontMetrics metrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
                                    .getGraphics()
                                    .getFontMetrics(font);
        for (String line : lines) {
            int lineWidth = metrics.stringWidth(line);
            if (lineWidth > maxWidth) {
                maxWidth = lineWidth;
            }
        }
        return maxWidth + 20;  // Add padding to the width
    }
}

