package app.utility;

import java.awt.*;
import java.io.*;

// Utility class for loading and registering a custom font
public class FontController {
    private static Font customFont;

    // Method to load a custom font from the specified path and set its size
    public static Font loadFont(String path) throws IOException, FontFormatException {
        customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path));
        customFont = customFont.deriveFont(40f); // Set the font size to 40 points (adjust as needed)
        registerFont(); // Register the loaded font
        return customFont; // Return the loaded font
    }

    // Method to register the loaded custom font with the local graphics environment
    public static void registerFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);
    }
}
