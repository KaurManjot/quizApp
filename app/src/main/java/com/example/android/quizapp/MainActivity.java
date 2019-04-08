package com.example.android.quizapp;

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

public class MainActivity extends AppCompatActivity {

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

    private Button nextButton;
    private TextView quizQuestionTextView;
    private LinearLayout quizAnswersOptionsLinearLayout;
    private ImageView quizImageView;
    private int questionIndex;
    private String answerType;

    private QuizQuestionsAnswers[] questionsAnswers = new QuizQuestionsAnswers[]{
            new QuizQuestionsAnswers(R.string.question1, R.drawable.australia, AnswerType.RADIO, answer1, choicesAvailable1),
            new QuizQuestionsAnswers(R.string.question2, R.drawable.threeoceans, AnswerType.CHECKBOX, answer2, choicesAvailable2),
            new QuizQuestionsAnswers(R.string.question3, R.drawable.earth, AnswerType.RADIO, answer3, choicesAvailable3),
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

        questionIndex = 0;
        createLayoutsBasedOnQuestions();
    }

    //read questions and inflate the layout
    public void createLayoutsBasedOnQuestions() {
        nextButton = findViewById(R.id.next_button);
        quizQuestionTextView = findViewById(R.id.quiz_question_text_view);
        quizImageView = findViewById(R.id.quiz_image);
        quizAnswersOptionsLinearLayout = findViewById(R.id.quiz_answer_options_linear_layout);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex = (questionIndex + 1) % questionsAnswers.length;
                quizQuestionTextView.setText(questionsAnswers[questionIndex].getQuestion());
                quizImageView.setImageDrawable(null);
                quizImageView.setImageResource(questionsAnswers[questionIndex].getQuestionImage());

                createAnswersView(questionIndex, quizAnswersOptionsLinearLayout);

                if (questionIndex == questionsAnswers.length - 1) {
                    nextButton.setText("Done");
                    nextButton.setEnabled(false);

                }
            }
        });
    }

    private void createAnswersView(int questionIndex, LinearLayout viewToInflate){
        viewToInflate.removeAllViews();
        answerType = questionsAnswers[questionIndex].getAnswerType();

        if(answerType.equals("radio"))
        {
            RadioGroup radioGroup = new RadioGroup(getApplicationContext());
            radioGroup.setOrientation(RadioGroup.VERTICAL);

            RadioButton option1 = new RadioButton(getApplicationContext());
            option1.setText(questionsAnswers[questionIndex].getChoicesAvailable()[0]);
            radioGroup.addView(option1);

            RadioButton option2 = new RadioButton(getApplicationContext());
            option2.setText(questionsAnswers[questionIndex].getChoicesAvailable()[1]);
            radioGroup.addView(option2);

            RadioButton option3 = new RadioButton(getApplicationContext());
            option3.setText(questionsAnswers[questionIndex].getChoicesAvailable()[2]);
            radioGroup.addView(option3);

            RadioButton option4 = new RadioButton(getApplicationContext());
            option4.setText(questionsAnswers[questionIndex].getChoicesAvailable()[3]);
            radioGroup.addView(option4);

//            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);
//            params.gravity = Gravity.CENTER;
//            radioGroup.setLayoutParams(params);
            viewToInflate.addView(radioGroup);
        }
        else if(answerType.equals("checkbox"))
        {
            //inflate checkbox view
            CheckBox checkBox1 = new CheckBox(getApplicationContext());
            checkBox1.setText(questionsAnswers[questionIndex].getChoicesAvailable()[0]);

            CheckBox checkBox2 = new CheckBox(getApplicationContext());
            checkBox2.setText(questionsAnswers[questionIndex].getChoicesAvailable()[1]);

            CheckBox checkBox3 = new CheckBox(getApplicationContext());
            checkBox3.setText(questionsAnswers[questionIndex].getChoicesAvailable()[2]);

            CheckBox checkBox4 = new CheckBox(getApplicationContext());
            checkBox4.setText(questionsAnswers[questionIndex].getChoicesAvailable()[3]);

//            checkBox1.setGravity(Gravity.CENTER);
//            checkBox2.setGravity(Gravity.CENTER);
//            checkBox3.setGravity(Gravity.CENTER);
//            checkBox4.setGravity(Gravity.CENTER);

            viewToInflate.addView(checkBox1);
            viewToInflate.addView(checkBox2);
            viewToInflate.addView(checkBox3);
            viewToInflate.addView(checkBox4);

        }
        else if(answerType.equals("editText"))
        {
            //inflate Edit text view
            EditText editText = new EditText(getApplicationContext());
            editText.setWidth(450);
            editText.setHint("Enter number here");
            viewToInflate.addView(editText);
        }
    }
}
