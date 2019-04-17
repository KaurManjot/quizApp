package com.example.android.quizapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Declaring scoreTracker static to avoid score value reset on device rotation*/
    private static HashMap<Integer, Boolean> scoreTracker = new HashMap<>();

    /* Declaring datatypes to hold layout and views */
    private LinearLayout quizAnswersOptionsLinearLayout;
    private TextView question_number_tracker;
    private TextView quizQuestionTextView;
    private ImageView quizImageView;
    private Button nextButton;

    private int questionIndex;
    private int questionNumber;
    private int score;
    private String buttonLabel;

    /* Generate questions */
    private QuizQuestionsAnswers[] questionsAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* to get the strings from resources the data generation need to be under onCreate

        /* Generate expected answers array*/
        String[] answer1 = {getString(R.string.answer1)};
        String[] answer2 = {getString(R.string.answer2a), getString(R.string.answer2b), getString(R.string.answer2c)};
        String[] answer3 = {getString(R.string.answer3a), getString(R.string.answer3b)};
        String[] answer4 = {getString(R.string.answer4)};
        String[] answer5 = {getString(R.string.answer5a), getString(R.string.answer5b), getString(R.string.answer5c), getString(R.string.answer5d)};
        String[] answer6 = {getString(R.string.answer6)};
        String[] answer7 = {getString(R.string.answer7)};
        String[] answer8 = {getString(R.string.answer8)};
        String[] answer9 = {getString(R.string.answer9)};
        String[] answer10 = {getString(R.string.answer10)};

        /* Generate choices available for each answers */
        String[] choicesAvailable1 = {getString(R.string.choicesAvailable1a), getString(R.string.choicesAvailable1b), getString(R.string.choicesAvailable1c), getString(R.string.choicesAvailable1d)};
        String[] choicesAvailable2 = {getString(R.string.choicesAvailable2a), getString(R.string.choicesAvailable2b), getString(R.string.choicesAvailable2c), getString(R.string.choicesAvailable2d)};
        String[] choicesAvailable3 = {getString(R.string.choicesAvailable3a), getString(R.string.choicesAvailable3b), getString(R.string.choicesAvailable3c), getString(R.string.choicesAvailable3d)};
        String[] choicesAvailable4 = {getString(R.string.choicesAvailable4a), getString(R.string.choicesAvailable4b), getString(R.string.choicesAvailable4c), getString(R.string.choicesAvailable4d)};
        String[] choicesAvailable5 = {getString(R.string.choicesAvailable5a), getString(R.string.choicesAvailable5b), getString(R.string.choicesAvailable5c), getString(R.string.choicesAvailable5d)};
        String[] choicesAvailable6 = {getString(R.string.choicesAvailable6)};
        String[] choicesAvailable7 = {getString(R.string.choicesAvailable7)};
        String[] choicesAvailable8 = {getString(R.string.choicesAvailable8a), getString(R.string.choicesAvailable8b), getString(R.string.choicesAvailable8c), getString(R.string.choicesAvailable8d)};
        String[] choicesAvailable9 = {getString(R.string.choicesAvailable9a), getString(R.string.choicesAvailable9b), getString(R.string.choicesAvailable9c), getString(R.string.choicesAvailable9d)};
        String[] choicesAvailable10 = {getString(R.string.choicesAvailable10)};

        questionsAnswers = new QuizQuestionsAnswers[]
                {
                        new QuizQuestionsAnswers(R.string.question1, R.drawable.australia, AnswerType.RADIO, answer1, choicesAvailable1),
                        new QuizQuestionsAnswers(R.string.question2, R.drawable.threeoceans, AnswerType.CHECKBOX, answer2, choicesAvailable2),
                        new QuizQuestionsAnswers(R.string.question3, R.drawable.earth, AnswerType.CHECKBOX, answer3, choicesAvailable3),
                        new QuizQuestionsAnswers(R.string.question4, R.drawable.usa, AnswerType.RADIO, answer4, choicesAvailable4),
                        new QuizQuestionsAnswers(R.string.question5, R.drawable.india, AnswerType.CHECKBOX, answer5, choicesAvailable5),
                        new QuizQuestionsAnswers(R.string.question6, R.drawable.schengen, AnswerType.EDITTEXT, answer6, choicesAvailable6),
                        new QuizQuestionsAnswers(R.string.question7, R.drawable.usa, AnswerType.EDITTEXT, answer7, choicesAvailable7),
                        new QuizQuestionsAnswers(R.string.question8, R.drawable.hawaii, AnswerType.RADIO, answer8, choicesAvailable8),
                        new QuizQuestionsAnswers(R.string.question9, R.drawable.canada, AnswerType.RADIO, answer9, choicesAvailable9),
                        new QuizQuestionsAnswers(R.string.question10, R.drawable.world, AnswerType.EDITTEXT, answer10, choicesAvailable10)
                };

        buttonLabel = getString(R.string.btnLabelNext);

        /* Get all the views and layouts that will be dynamically generated based on every question later*/
        quizAnswersOptionsLinearLayout = findViewById(R.id.quiz_answer_options_linear_layout);
        question_number_tracker = findViewById(R.id.question_number_tracker);
        quizQuestionTextView = findViewById(R.id.quiz_question_text_view);
        quizImageView = findViewById(R.id.quiz_image);
        nextButton = findViewById(R.id.next_button);

        /* To handle persistence of data on rotation */
        if (savedInstanceState != null)
        {
            questionIndex = savedInstanceState.getInt("questionIndex");
            score = savedInstanceState.getInt("score");
            buttonLabel = savedInstanceState.getString("buttonLabel");
        }

        /* Set the button label to "Next" for first time. Later text will be updated to "Submit" */
        nextButton.setText(buttonLabel);

        /* Called when activity is created as well as when button is clicked */
        setQuestionAnswersViews();
        inflateQuestionAnswersLayout();
        createLayoutsBasedOnQuestions();
    }

    /*
        Add the values to Bundle that need to saved on rotation
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("questionIndex", questionIndex);
        savedInstanceState.putString("buttonLabel", buttonLabel);
    }

    /*
        Retrieve the values from Bundle that were saved on rotation
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        questionIndex = savedInstanceState.getInt("questionIndex");
    }

    /* Create layout based on type of question asked, calculate total score and display in toast message */
    public void createLayoutsBasedOnQuestions()
    {
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /* Update text on the button to "Submit whe last question is displayed */
                if (questionIndex == questionsAnswers.length - 2)
                {
                    buttonLabel = getString(R.string.btnLabelSubmit);
                    nextButton.setText(buttonLabel);
                }

                /* Based on correct answers in HashMap calculate the final score */
                if (questionIndex == questionsAnswers.length - 1)
                {
                    for (Boolean correctAnswers : scoreTracker.values())
                    {
                        if (correctAnswers.booleanValue() == true)
                        {
                            score++;
                        }
                    }

                    /* Reset the button text back "Next */
                    buttonLabel = getString(R.string.btnLabelNext);
                    nextButton.setText(buttonLabel);

                    /* Adding Toast message variation based on score */
                    String messageBasedOnScore;
                    if (score >= 8)
                        messageBasedOnScore = getString(R.string.grtJob) + "\n";
                    else if (score <= 4)
                        messageBasedOnScore = getString(R.string.tryAgain) + "\n";
                    else
                        messageBasedOnScore = getString(R.string.niceTry) + "\n";

                    /* Create Toast message with user score */
                    Toast toast = Toast.makeText(MainActivity.this, messageBasedOnScore + " "+getString(R.string.youScored) + score + "\n "+ getString(R.string.quizRestarted), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View view = toast.getView();
                    view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary, null), PorterDuff.Mode.SRC_IN);
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextColor(Color.WHITE);
                    text.setTextSize(25);
                    text.setGravity(Gravity.CENTER);
                    toast.show();

                    /* Reset score to 0 and clear score tracker HashMap*/
                    scoreTracker.clear();
                    score = 0;
                }
                /* Logic to iterate over each question using questionIndex. Note this logic will be updated if total number of questions !=10 */
                questionIndex = (questionIndex + 1) % questionsAnswers.length;

                /* Inflate/set all the views on screen*/
                setQuestionAnswersViews();
                inflateQuestionAnswersLayout();
            }
        });
    }

    /* Inflate 3 views - Question count tracker text, Question text view and image for the question */
    private void setQuestionAnswersViews()
    {
        questionNumber = questionIndex + 1;
        question_number_tracker.setText(getString(R.string.question)+" " + questionNumber + "/"+questionsAnswers.length);
        quizQuestionTextView.setText(questionsAnswers[questionIndex].getQuestion());
        quizImageView.setImageDrawable(null);
        quizImageView.setImageResource(questionsAnswers[questionIndex].getQuestionImage());
    }

    /*
        Based on answer type - multiple choice, single answer,
        text create views with Checkbox, radio button, EditText respectively
        and attach listeners when each one is selected inside LinearLayout
    */
    private void inflateQuestionAnswersLayout()
    {
        quizAnswersOptionsLinearLayout.removeAllViews();

        /* switch on AnswerType Enum - that can have values - RADIO, CHECKBOX, EDITTEXT*/
        switch (questionsAnswers[questionIndex].getAnswerType())
        {
            case RADIO:
                final RadioGroup radioGroup = new RadioGroup(this);
                radioGroup.setOrientation(RadioGroup.VERTICAL);

                /* Attach listener to get the label for user selected radio button and update the score hashmap */
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        String selectedRadioButtonText;
                        if (checkedId != -1)
                        {
                            int radioId = radioGroup.indexOfChild(group.findViewById(checkedId));
                            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(radioId);
                            selectedRadioButtonText = (String) radioButton.getText();

                            /*
                                The user selection is compared to the actual answer everytime a radio button in RadioGroup is selected/updated
                                 Instead of incrementing scores here, a boolean value is added to HashMap that will be read at the end of quiz to calculate
                                 the score.
                                 If the user change the selection say from correct to incorrect or vice-versa, HashMap will update the score by overwriting the
                                 previous value.
                             */
                            if (selectedRadioButtonText.trim().equalsIgnoreCase(questionsAnswers[questionIndex].getCorrectAnswer()[0]))
                            {
                                scoreTracker.put(questionIndex, true);
                            }
                            else
                            {
                                scoreTracker.put(questionIndex, false);
                            }
                        }
                    }
                });


                /*
                    Add all the radio button dynamically based on answer array instead of hardcoding so that if new choice is added it will dynamically generate the
                        option for user.
                 */
                for (String availableChoice : questionsAnswers[questionIndex].getChoicesAvailable())
                {
                    RadioButton option = new RadioButton(this);
                    option.setText(availableChoice);
                    radioGroup.addView(option);
                }

                /* Add the RadioGroup to LinearLayout */
                quizAnswersOptionsLinearLayout.addView(radioGroup);

                break;

            case CHECKBOX:
                final ArrayList<String> selectedAnswers = new ArrayList<>();
                final List<String> expectedAnswersList = Arrays.asList(questionsAnswers[questionIndex].getCorrectAnswer());

                /*
                   Add all the checkboxes dynamically based on answer array instead of hardcoding so that if new choice is added it will dynamically generate the
                   option for user.
                 */
                for (String availableChoice : questionsAnswers[questionIndex].getChoicesAvailable())
                {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(availableChoice);

                    /* Attach listener to get the label for user selected checkbox and update the score hashmap*/
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                        {
                            String checkBoxText = (String) buttonView.getText();

                            /* The user selection in compared to the actual answer everytime a CheckBox is selected/updated and added to the selevcted answers list */
                            if (isChecked)
                            {
                                selectedAnswers.add(checkBoxText);
                            }
                            else
                            {
                                selectedAnswers.remove(checkBoxText);
                            }

                            /*
                                 Instead of incrementing scores here, a boolean value true is added to HashMap(that will be read at the end of quiz to calculate the score)
                                  whenever all the users selected options match with expected answer array.
                                  If the user change the selection say from correct to incorrect or vice-versa, HashMap will update the score by overwriting the
                                  previous value.
                             */
                            if (selectedAnswers.containsAll(expectedAnswersList))
                            {
                                scoreTracker.put(questionIndex, true);
                            }
                            else
                            {
                                scoreTracker.put(questionIndex, false);
                            }
                        }
                    });

                    /* Add checkbox to LinearLayout */
                    quizAnswersOptionsLinearLayout.addView(checkBox);
                }
                break;
            case EDITTEXT:
                EditText editText = new EditText(this);
                editText.setWidth(450);
                editText.setHint(getString(R.string.editTextHelpText));
                quizAnswersOptionsLinearLayout.addView(editText);

                /* Attach listener to get the entered text for user and update the score hashmap */
                editText.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void onTextChanged(CharSequence c, int start, int before, int count)
                    {
                        /*
                             The user entered text is compared to the actual answer everytime text is changed
                              Instead of incrementing scores here, a boolean value is added to HashMap that will be read at the end of quiz to calculate
                              the score.
                              If the user change the text say from correct to incorrect or vice-versa, HashMap will update the score by overwriting the
                              previous value.
                         */
                        if (c.toString().trim().equalsIgnoreCase(questionsAnswers[questionIndex].getCorrectAnswer()[0]))
                        {
                            scoreTracker.put(questionIndex, true);
                        }
                        else
                        {
                            scoreTracker.put(questionIndex, false);
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence c, int start, int count, int after)
                    {
                        // this space intentionally left blank
                    }

                    @Override
                    public void afterTextChanged(Editable c)
                    {
                        // this space intentionally left blank
                    }
                });

                break;
        }
    }
}
