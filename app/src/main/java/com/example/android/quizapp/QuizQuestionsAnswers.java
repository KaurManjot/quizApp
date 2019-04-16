package com.example.android.quizapp;

public class QuizQuestionsAnswers {

    //Question resource id from strings.xml file
    private int questionResId;
    private int questionImageId;

    private AnswerType answerType;

    //The actual answer to compare user input against
    private String answer[];

    private String[] choicesAvailable;

    public QuizQuestionsAnswers(int questionResId, int questionImageId, AnswerType answerType, String[] answer, String[] choicesAvailable){
        this.questionResId = questionResId;
        this.questionImageId = questionImageId;
        this.answerType = answerType;
        this.answer = answer;
        this.choicesAvailable = choicesAvailable;
    }

    public int getQuestion(){
        return this.questionResId;
    }
    public int getQuestionImage(){
        return this.questionImageId;
    }

    public AnswerType getAnswerType()
    {
        return this.answerType;
    }

    public String[] getCorrectAnswer(){
        return this.answer;
    }

    public String[] getChoicesAvailable() {
        return this.choicesAvailable;
    }

}
