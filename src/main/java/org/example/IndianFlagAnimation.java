package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;


public class IndianFlagAnimation extends JPanel {
    private int waveOffset = 0; // Controls the waving effect for animation

    public IndianFlagAnimation() {
        // Timer to create the waving animation effect
        Timer timer = new Timer(30, e -> {
            waveOffset += 5; // Increment wave offset for smooth animation
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Calculate flag dimensions and position
        int flagX = width / 4;
        int flagY = height / 4;
        int flagWidth = width / 2;
        int flagHeight = height / 3;

        // Draw the flagpole
        drawFlagPole(g2d, flagX, flagY, flagHeight);

        // Draw the three stripes of the Indian flag
        drawFlagStripe(g2d, flagX, flagY, flagWidth, flagHeight / 3, new Color(255, 153, 51)); // Saffron
        drawFlagStripe(g2d, flagX, flagY + flagHeight / 3, flagWidth, flagHeight / 3, Color.WHITE); // White
        drawFlagStripe(g2d, flagX, flagY + 2 * (flagHeight / 3), flagWidth, flagHeight / 3, new Color(19, 136, 8)); // Green

        // Draw the Ashoka Chakra in the center of the white stripe
        drawAshokaChakra(g2d, flagX + flagWidth / 2, flagY + flagHeight / 2, flagHeight / 6);
    }

    private void drawFlagPole(Graphics2D g2d, int x, int y, int flagHeight) {
        g2d.setColor(new Color(102, 51, 0)); // Wooden pole color
        g2d.fillRect(x - 20, y - 20, 20, flagHeight + 100); // Pole dimensions
    }

    private void drawFlagStripe(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        // Create a sine wave shape for the fluttering effect
        GeneralPath wave = new GeneralPath();
        wave.moveTo(x, y);

        for (int i = 0; i <= width; i++) {
            // Simulate multiple smaller waves for realism
            int offset = (int) (Math.sin((i + waveOffset) * 0.02) * 10
                    + Math.sin((i + waveOffset) * 0.05) * 5);
            wave.lineTo(x + i, y + offset);
        }

        wave.lineTo(x + width, y + height); // Bottom-right corner
        wave.lineTo(x, y + height);        // Bottom-left corner
        wave.closePath();                  // Close the path

        g2d.setColor(color);
        g2d.fill(wave);
    }

    private void drawAshokaChakra(Graphics2D g2d, int centerX, int centerY, int radius) {
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2)); // Thin stroke for the Chakra
        g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2); // Outer circle

        // Draw 24 spokes inside the Chakra
        for (int i = 0; i < 24; i++) {
            double angle = Math.toRadians(i * 15); // Spokes at 15-degree intervals
            int x1 = centerX + (int) (Math.cos(angle) * radius);      // Outer point of the spoke
            int y1 = centerY + (int) (Math.sin(angle) * radius);
            int x2 = centerX + (int) (Math.cos(angle) * radius / 2);  // Inner point of the spoke
            int y2 = centerY + (int) (Math.sin(angle) * radius / 2);
            g2d.drawLine(centerX, centerY, x1, y1);  // Main spoke
            g2d.drawLine(x2, y2, x1, y1);           // Decorative spoke
        }
    }

    public static void main(String[] args) {
        // Create the JFrame for displaying the animation
        JFrame frame = new JFrame("Realistic Fluttering Indian Flag");
        IndianFlagAnimation panel = new IndianFlagAnimation();

        // Set up the frame
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
