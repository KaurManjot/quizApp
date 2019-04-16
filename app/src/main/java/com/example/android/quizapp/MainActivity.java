package com.example.android.quizapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView question_number_tracker;
    private TextView quizQuestionTextView;
    private LinearLayout quizAnswersOptionsLinearLayout;
    private ImageView quizImageView;
    private Button nextButton;
    private int questionIndex;
    private int questionNumber;
    private int score;
//    private boolean isScoreIncremented = false;
    private HashMap<Integer, Boolean> scoreTracker = new HashMap<>();

    String[] answer1 = {"Canberra"};
    String[] answer2 = {"Pacific Ocean", "Atlantic Ocean", "Indian Ocean"};
    String[] answer3 = {"Mediterranean sea", "Red sea"};
    String[] answer4 = {"Barack Obama"};
    String[] answer5 = {"Saffron", "White", "Green", "Blue"};
    String[] answer6 = {"26"};
    String[] answer7 = {"13"};
    String[] answer8 = {"Honolulu"};
    String[] answer9 = {"Toronto"};
    String[] answer10 = {"24"};

    String[] choicesAvailable1 = {"Sydney", "Canberra", "Melbourne", "Brisbane"};
    String[] choicesAvailable2 = {"Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean"};
    String[] choicesAvailable3 = {"Mediterranean sea", "Black sea", "Arabian Sea", "Red sea"};
    String[] choicesAvailable4 = {"George W. Bush", "Barack Obama","Bill Clinton","Ronald Wilson Reagan"};
    String[] choicesAvailable5 = {"Saffron", "White", "Green", "Blue"};
    String[] choicesAvailable6 = {"26"};
    String[] choicesAvailable7 = {"13"};
    String[] choicesAvailable8 = {"Maui", "Honolulu", "Kauai", "Hilo"};
    String[] choicesAvailable9 = {"Vancouver","Montreal","Toronto", "Calgary"};
    String[] choicesAvailable10 = {"24"};

    private QuizQuestionsAnswers[] questionsAnswers = new QuizQuestionsAnswers[]{
            new QuizQuestionsAnswers(R.string.question1, R.drawable.australia, AnswerType.RADIO, answer1, choicesAvailable1),
            new QuizQuestionsAnswers(R.string.question2, R.drawable.threeoceans, AnswerType.CHECKBOX, answer2, choicesAvailable2),
            new QuizQuestionsAnswers(R.string.question3, R.drawable.earth, AnswerType.CHECKBOX, answer3, choicesAvailable3),
            new QuizQuestionsAnswers(R.string.question4, R.drawable.usa, AnswerType.RADIO, answer4, choicesAvailable4),
            new QuizQuestionsAnswers(R.string.question5, R.drawable.india, AnswerType.CHECKBOX, answer5, choicesAvailable5),
            new QuizQuestionsAnswers(R.string.question6, R.drawable.schengen, AnswerType.EDITTEXT, answer6, choicesAvailable6),
            new QuizQuestionsAnswers(R.string.question7, R.drawable.usa,AnswerType.EDITTEXT, answer7, choicesAvailable7),
            new QuizQuestionsAnswers(R.string.question8, R.drawable.hawaii, AnswerType.RADIO, answer8, choicesAvailable8),
            new QuizQuestionsAnswers(R.string.question9, R.drawable.canada, AnswerType.RADIO, answer9, choicesAvailable9),
            new QuizQuestionsAnswers(R.string.question10, R.drawable.world, AnswerType.EDITTEXT, answer10, choicesAvailable10)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question_number_tracker = findViewById(R.id.question_number_tracker);
        quizQuestionTextView = findViewById(R.id.quiz_question_text_view);
        quizAnswersOptionsLinearLayout = findViewById(R.id.quiz_answer_options_linear_layout);
        quizImageView = findViewById(R.id.quiz_image);
        nextButton = findViewById(R.id.next_button);

        if(savedInstanceState != null) {
            questionIndex = savedInstanceState.getInt("questionIndex");
        }

        //Called first time when activity is created as well as when button is clicked
        setQuestionAnswersViews();
        inflateQuestionAnswersLayout();

        createLayoutsBasedOnQuestions();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("questionIndex", questionIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        questionIndex = savedInstanceState.getInt("questionIndex");
    }

    //read questions and inflate the layout
    public void createLayoutsBasedOnQuestions() {

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionIndex == questionsAnswers.length-1) {

                    for(Boolean correctAnswers:scoreTracker.values())
                    {
                        if(correctAnswers.booleanValue() == true)
                        {
                            score++;
                        }
                    }

                    //TODO: Add logic to show results in a tost message and reset the quiz
                    String messageBasedOnScore = "Great Job!!";
                    Toast toast = Toast.makeText(MainActivity.this, messageBasedOnScore+ " You Scored:"+score + ". Quiz Restarted", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View view = toast.getView();
                    view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary, null), PorterDuff.Mode.SRC_IN);
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextColor(Color.WHITE);
                    text.setTextSize(25);
                    text.setGravity(Gravity.CENTER);
                    toast.show();

                    score = 0;
                }
                questionIndex = (questionIndex+1) % questionsAnswers.length;

                setQuestionAnswersViews();
                inflateQuestionAnswersLayout();
            }
        });
    }

    private void setQuestionAnswersViews(){
        questionNumber = questionIndex+1;
        question_number_tracker.setText("Question: "+questionNumber+"/10");
        quizQuestionTextView.setText(questionsAnswers[questionIndex].getQuestion());
        quizImageView.setImageDrawable(null);
        quizImageView.setImageResource(questionsAnswers[questionIndex].getQuestionImage());
    }

    private void inflateQuestionAnswersLayout(){
        quizAnswersOptionsLinearLayout.removeAllViews();

        switch(questionsAnswers[questionIndex].getAnswerType()){
            case RADIO:
            final RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.VERTICAL);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String selectedRadioButtonText;
                    if (checkedId != -1) {   //if radio button is selected
                        int radioId = radioGroup.indexOfChild(group.findViewById(checkedId));
                        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(radioId);
                        selectedRadioButtonText = (String) radioButton.getText();

                        if (selectedRadioButtonText.trim().equalsIgnoreCase(questionsAnswers[questionIndex].getCorrectAnswer()[0]))
                        {
                            scoreTracker.put(questionIndex,true);
//                            if(!isScoreIncremented) {  // Added if multiple times radio button us clicked score is not incremented
//                                score = score + 1;
//                                isScoreIncremented=!isScoreIncremented;    //flipping the boolean to mark that the score is already incremented once
//                            }
                        }
                        else
                        {
                            scoreTracker.put(questionIndex,false);
                        }
                    }
                }
            });

            for(String availableChoice:questionsAnswers[questionIndex].getChoicesAvailable())  {
                RadioButton option = new RadioButton(this);
                option.setText(availableChoice);
                radioGroup.addView(option);
            }
            quizAnswersOptionsLinearLayout.addView(radioGroup);
            break;
            case CHECKBOX:
                //inflate checkbox view
                for(String availableChoice:questionsAnswers[questionIndex].getChoicesAvailable())  {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(availableChoice);

//                    checkBox.setOnClickListener();

                    quizAnswersOptionsLinearLayout.addView(checkBox);
                }
            break;
            case EDITTEXT:
                //inflate Edit text view
                EditText editText = new EditText(this);
                editText.setWidth(450);
                editText.setHint("Enter number here");
                quizAnswersOptionsLinearLayout.addView(editText);
                break;
        }
    }
}
