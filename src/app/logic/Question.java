package app.logic;

import java.util.List;

// Define a record named Question

public class Question{
    private final String question;
    private final List<Answer> answers;

    public Question(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer()
    {
        for(Answer answer: answers)
        {
            if(answer.isCorrect())
                return answer.getText();
        }
        return null;
    }
    public List<Answer> getAnswers() {
        return answers;
    }
}