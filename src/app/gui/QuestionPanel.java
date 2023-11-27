package app.gui;

import app.MainFrame;
import app.logic.Answer;
import app.logic.Question;
import app.logic.QuizModel;
import app.logic.SoundPlayer;
import app.utility.OptionColor;
import app.utility.ResourcePath;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class QuestionPanel extends JPanel {

    // Components for the QuestionPanel
    private final RoundLabel option1Label;
    private final MainFrame parentFrame;
    private final Map<RoundLabel, Answer> labelAnswers;
    private final QuizModel quizModel;
    private final Timer questionTimer;
    private final JLabel timerLabel;
    private final StrokedLabel questionLabel;
    private final RoundLabel  option2Label;
    private final RoundLabel option3Label;
    private final RoundLabel option4Label;
    private RoundLabel selectedLabel;

    /**
     * Create the panel.
     */
    public QuestionPanel(MainFrame mainFrame, QuizModel quizModel) {
        this.parentFrame = mainFrame;
        labelAnswers = new HashMap<>();
        this.quizModel = quizModel;
        this.selectedLabel = null;

        setBackground(new Color(100, 0, 0));
        setLayout(null);

        // Components initialization
        questionLabel = new StrokedLabel("Willkommen", new Color(255, 255, 255), 2);
        questionLabel.setForeground(new Color(0, 0, 0));
        questionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        questionLabel.setBounds(53, 38, 618, 85);

        // Add components to the panel
        add(questionLabel);

        // Set the close action
        JButton closeButton = new JButton("X"){
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                // This will set the preferred size to the width of the text plus some padding.
                size.setSize(getFontMetrics(getFont()).stringWidth(getText()) + 10, getFontMetrics(getFont()).getHeight() + 4);
                return size;
            }
        };

        // Initialize and configure option labels
        closeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        closeButton.setBackground(Color.GRAY); // Set the background to grey
        closeButton.setOpaque(true);
        closeButton.setBorder(null); // No border
        closeButton.setFocusPainted(false);

        Dimension size = closeButton.getPreferredSize();
        int width = size.width + 10;
        int height = size.height + 4;

        closeButton.setBounds(694, 6, width, height);

        // Set the close action
        closeButton.addActionListener(e -> {
            SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
            mainFrame.switchToWelcomePanel();
        });

        add(closeButton);

        // Initialize and configure option labels
        option1Label = new RoundLabel("New button");
        option1Label.setBounds(53, 222, 280, 61);
        option1Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
                selectedLabel = option1Label;
                option1Label.changeBackgroundColor(OptionColor.selectedColor);
                option2Label.setDefaultColor();
                option3Label.setDefaultColor();
                option4Label.setDefaultColor();
            }
        });
        add(option1Label);

        option2Label = new RoundLabel("New button");
        option2Label.setBounds(391, 222, 280, 61);
        option2Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
                selectedLabel = option2Label;
                option2Label.changeBackgroundColor(OptionColor.selectedColor);
                option1Label.setDefaultColor();
                option3Label.setDefaultColor();
                option4Label.setDefaultColor();
            }
        });
        add(option2Label);

        option3Label = new RoundLabel("New button");
        option3Label.setBounds(53, 329, 280, 61);
        option3Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
                selectedLabel = option3Label;
                option3Label.changeBackgroundColor(OptionColor.selectedColor);
                option1Label.setDefaultColor();
                option2Label.setDefaultColor();
                option4Label.setDefaultColor();
            }
        });
        add(option3Label);

        option4Label = new RoundLabel("New button");
        option4Label.setBounds(391, 329, 280, 61);
        option4Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
                selectedLabel = option4Label;
                option4Label.changeBackgroundColor(OptionColor.selectedColor);
                option1Label.setDefaultColor();
                option2Label.setDefaultColor();
                option3Label.setDefaultColor();
            }
        });
        add(option4Label);

        RoundButton continueButton = new RoundButton("weiter");
        continueButton.setBounds(585, 413, 85, 31);
        continueButton.customizeFont(15);
        continueButton.addActionListener(e -> {
            if(selectedLabel!=null)
            {
                checkSelectedOption(selectedLabel);
            }
        });
        add(continueButton);

        timerLabel = new JLabel("29");
        timerLabel.setForeground(new Color(255, 255, 255));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        timerLabel.setBounds(337, 147, 57, 43);
        add(timerLabel);


        // Timer initialization
        questionTimer = new Timer(1000, e -> updateTimer());

        // Start the quiz
        startQuiz();

    }

    // Method to update the timer label
    private void updateTimer() {
        int currentTime = Integer.parseInt(timerLabel.getText());
        currentTime--;
        if (currentTime <= 0) {
            questionTimer.stop();
            checkSelectedOption(selectedLabel);


        } else {
            timerLabel.setText(String.valueOf(currentTime));
        }
    }

    // Method to check the selected option and handle accordingly
    private void checkSelectedOption(RoundLabel selectedLabel) {

        if(selectedLabel == null)
        {
            quizModel.recordAnswer(null);

        }

        else
        {
            quizModel.recordAnswer(labelAnswers.get(selectedLabel));
            Answer answer = labelAnswers.get(selectedLabel);
            if(answer.correct())
            {
                SoundPlayer.playSound(ResourcePath.SOUND_RIGHT_PATH);
                selectedLabel.changeBackgroundColor(OptionColor.correctColor);
            }
            else
            {
                SoundPlayer.playSound(ResourcePath.SOUND_WRONG_PATH);
                selectedLabel.changeBackgroundColor(OptionColor.wrongColor);
            }

        }

        // Use Timer to introduce a delay before loading the next question or transitioning to the score panel
        Timer timer = new Timer(2000, e -> operationWithDelay());
        timer.setRepeats(false); // Make sure the timer only runs once
        timer.start();

    }
    // Method to perform operations with a delay (e.g., reset labels, load next question, switch to score panel)
    private void operationWithDelay() {

        if(selectedLabel != null)
        {
            selectedLabel.setDefaultColor();
            selectedLabel = null;
        }

        if (quizModel.hasMoreQuestions()) {
            loadNextQuestion();
        } else {
            parentFrame.switchToScorePanel(quizModel.getAnsweredQuestion(),quizModel.getScore());
        }
    }

    // Method to load the next question and update the UI
    private void loadNextQuestion() {
        selectedLabel = null;
        Question currentQuestion = quizModel.getNextQuestion();
        if (currentQuestion != null) {
            // Update components with question details

            // Set the question text and adjust label font size
            questionLabel.setText(currentQuestion.question());
            questionLabel.adjustLabelFontSize();

            // Populate option labels with answer text and associate answers with labels
            Answer answer = currentQuestion.answers().get(0);
            option1Label.setText(answer.text());
            labelAnswers.put(option1Label, answer);

            answer = currentQuestion.answers().get(1);
            option2Label.setText(answer.text());
            labelAnswers.put(option2Label, answer);


            answer = currentQuestion.answers().get(2);

            option3Label.setText(answer.text());
            labelAnswers.put(option3Label, answer);
            answer = currentQuestion.answers().get(3);
            option4Label.setText(answer.text());
            labelAnswers.put(option4Label, answer);

            // Reset the timer label
            timerLabel.setText(String.valueOf(quizModel.getTimePerQuestion()));
            // Start the timer
            questionTimer.start();
        }
    }

    // Method to start the quiz by resetting the quiz model and loading the first question
    public void startQuiz() {
        quizModel.resetQuiz();
        loadNextQuestion(); // Load the first question
    }

    // Override the getPreferredSize method to define the preferred size of the panel
    @Override
    public Dimension getPreferredSize() {
        // Return the preferred size of the panel
        return new Dimension(730, 468);
    }

}