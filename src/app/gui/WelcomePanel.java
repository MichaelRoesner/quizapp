package app.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import app.MainFrame;
import app.data.QuizDataLoader;
import app.logic.DifficultyLevel;
import app.logic.QuizModel;
import app.logic.SoundPlayer;
import app.utility.ResourcePath;

public class WelcomePanel extends JPanel {

    private final MainFrame parentFrame;

    /**
     * Create the panel.
     */
    public WelcomePanel(MainFrame mainFrame) {
        this.parentFrame = mainFrame;
        setBackground(new Color(75, 0, 130));
        setLayout(null);

        // Close Button
        JButton closeButton = new JButton("X"){
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                // Set the preferred size to the width of the text plus some padding.
                size.setSize(getFontMetrics(getFont()).stringWidth(getText()) + 10, getFontMetrics(getFont()).getHeight() + 4);
                return size;
            }
        };
        closeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        closeButton.setBackground(Color.GRAY); // Set the background to grey
        closeButton.setOpaque(true);
        closeButton.setBorder(null); // No border
        closeButton.setFocusPainted(false);

        Dimension size = closeButton.getPreferredSize();
        int width = size.width + 10;
        int height = size.height + 4;

        closeButton.setBounds(620, 6, width, height);

        // Set the close action
        closeButton.addActionListener(e -> {
            SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
            parentFrame.dispose();
        });

        add(closeButton);

        // Welcome Label
        StrokedLabel welcomeLabel = new StrokedLabel("Willkommen", new Color(255, 255, 255), 2);
        welcomeLabel.setForeground(new Color(0, 0, 0));
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        welcomeLabel.setBounds(146, 33, 396, 138);
        add(welcomeLabel);

        // Difficulty selection buttons
        RoundButton easyDifficultyButton = new RoundButton("Leicht");
        easyDifficultyButton.addActionListener(e -> {
            SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
            parentFrame.switchToQuestionPanel(new QuizModel(
                    QuizDataLoader.loadQuestions(ResourcePath.QUESTION_EASY_PATH),
                    getTimePerQuestion(DifficultyLevel.EASY)));
        });
        easyDifficultyButton.setBounds(236, 169, 203, 43);
        add(easyDifficultyButton);

        RoundButton middleDifficultyButton = new RoundButton("Mittel");
        middleDifficultyButton.addActionListener(e -> {
            SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
            parentFrame.switchToQuestionPanel(new QuizModel(
                    QuizDataLoader.loadQuestions(ResourcePath.QUESTION_MEDIUM_PATH),
                    getTimePerQuestion(DifficultyLevel.MEDIUM)));
        });
        middleDifficultyButton.setBounds(236, 238, 203, 43);
        add(middleDifficultyButton);

        RoundButton hardDifficultyButton = new RoundButton("Schwer");
        hardDifficultyButton.addActionListener(e -> {
            SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
            parentFrame.switchToQuestionPanel(new QuizModel(
                    QuizDataLoader.loadQuestions(ResourcePath.QUESTION_HARD_PATH),
                    getTimePerQuestion(DifficultyLevel.HARD)));
        });
        hardDifficultyButton.setBounds(236, 302, 203, 43);
        add(hardDifficultyButton);
    }

    @Override
    public Dimension getPreferredSize() {
        // Return the preferred size of the panel
        return new Dimension(655, 436);
    }

    // Helper method to get time per question based on difficulty
    private int getTimePerQuestion(DifficultyLevel difficulty) {
        return switch (difficulty) {
            case EASY -> 30;
            case MEDIUM -> 20;
            case HARD -> 10;
        };
    }
}
