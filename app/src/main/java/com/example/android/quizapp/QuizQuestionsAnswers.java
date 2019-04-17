package com.example.android.quizapp;

/*
    Class to hold question and data related to that question like - answer to the question, choices that will be displayed for the question,
     image associated to the question etc
 */
public class QuizQuestionsAnswers
{
    private int questionResId;
    private int questionImageId;

    /* Based on AnswerType Enum - that can have values - RADIO, CHECKBOX, EDITTEXT inflate LinearLayout to present user with options*/
    private AnswerType answerType;

    /* The expected answer to compare the user input against */
    private String answer[];

    /* Choices available to user to show on screen */
    private String[] choicesAvailable;

    public QuizQuestionsAnswers(int questionResId, int questionImageId, AnswerType answerType, String[] answer, String[] choicesAvailable){
        this.questionResId = questionResId;
        this.questionImageId = questionImageId;
        this.answerType = answerType;
        this.answer = answer;
        this.choicesAvailable = choicesAvailable;
    }

    public int getQuestion()
    {
        return this.questionResId;
    }

    public int getQuestionImage()
    {
        return this.questionImageId;
    }

    public AnswerType getAnswerType()
    {
        return this.answerType;
    }

    public String[] getCorrectAnswer()
    {
        return this.answer;
    }

    public String[] getChoicesAvailable()
    {
        return this.choicesAvailable;
    }
}
