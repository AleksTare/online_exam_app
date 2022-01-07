package kursi;

import javafx.beans.property.SimpleBooleanProperty;

public class Pyetje {

    private int questionId;

    private String questionText;

    private SimpleBooleanProperty correct;

    private int testId;

    public Pyetje(String questionText, Boolean correct, int testId) {
        this.questionText = questionText;
        this.correct = new SimpleBooleanProperty(correct);
        this.testId = testId;
    }

    public Pyetje(int questionId, String questionText, Boolean  correct, int testId) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correct = new SimpleBooleanProperty(correct);
        this.testId = testId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean  getCorrect() {
        return correct.get();
    }

    public void setCorrect(Boolean  correct) {
        this.correctProperty().set(correct);
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public SimpleBooleanProperty correctProperty() {
        return this.correct;
    }
}
