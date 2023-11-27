package app.gui;

import app.MainFrame;
import app.logic.Answer;
import app.logic.Question;
import app.logic.SoundPlayer;
import app.utility.CloseButton;
import app.utility.ResourcePath;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ScorePanel extends JPanel {

    /**
     * Create the panel.
     */
    public ScorePanel(MainFrame parentFrame, Map<Question, Answer> answeredQuestion, int score) {

        setBackground(new Color(75, 0, 130));
        setLayout(null);

        // Close Button
        CloseButton closeButton = new CloseButton(parentFrame);
        Dimension size = closeButton.getPreferredSize();
        closeButton.setBounds(654, 6, size.width + 10, size.height + 10);
        add(closeButton);

        StrokedLabel lblNewLabel = new StrokedLabel("Quiz Result", new Color(255, 255, 255), 2, ResourcePath.PIXEL, 40f);
        lblNewLabel.setText("Quiz Results");
        //lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setBounds(10, 6, 153, 34);
        add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Score");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_1.setBounds(605, 44, 64, 27);
        add(lblNewLabel_1);

        JLabel scoreLabel = new JLabel(score + "/" + answeredQuestion.size());
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(new Color(128, 255, 255));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        scoreLabel.setBounds(605, 70, 64, 22);
        add(scoreLabel);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        int questionNumber = 1;
        // Add individual panels to the container panel

        for(Question question: answeredQuestion.keySet())
        {
            String questionText = question.getQuestion();
            String correctAnswer = question.getCorrectAnswer();
            Answer answer = answeredQuestion.get(question);

            String selectedAnswer = "No Option Selected";
            if(answer!=null)
                selectedAnswer = answer.getText();
            AnsweredQuestionPanel panel = new AnsweredQuestionPanel(questionNumber, questionText, selectedAnswer, correctAnswer);
            containerPanel.add(panel);
            questionNumber++;
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setBounds(10, 110, 680, 437);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

        if (score == answeredQuestion.size()) {
            SoundPlayer.playSound(ResourcePath.SOUND_WIN_PATH);
        } else {
            SoundPlayer.playSound(ResourcePath.SOUND_LOSE_PATH);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        // Return the preferred size of the panel
        return new Dimension(700, 556);
    }
}
