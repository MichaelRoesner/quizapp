package app.logic;

import java.util.List;

// Define a record named Question
public record Question(String question, List<Answer> answers) {

    // Method to get the correct answer text
    public String getCorrectAnswer() {
        // Iterate through the answers
        for (Answer answer : answers) {
            // Check if the current answer is correct
            if (answer.correct())
                return answer.text();
        }
        // Return null if no correct answer is found (this might need additional handling in a real-world scenario)
        return null;
    }
}


//public class Question{
//    private final String question;
//    private final List<Answer> answers;
//
//    public Question(String question, List<Answer> answers) {
//        this.question = question;
//        this.answers = answers;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public String getCorrectAnswer()
//    {
//        for(Answer answer: answers)
//        {
//            if(answer.correct())
//                return answer.text();
//        }
//        return null;
//    }
//    public List<Answer> getAnswers() {
//        return answers;
//    }
//}