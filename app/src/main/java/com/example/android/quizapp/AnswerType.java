package com.example.android.quizapp;

//The option for answer can be single answer(RadioButton), multiple answers(CheckBox) or free input(EditText)

public enum AnswerType
{
        RADIO("radio"),         //for single correct answer
        CHECKBOX("checkbox"),   //for multiple correct answers
        EDITTEXT("editText");   //for free input type

        private String type;

        AnswerType(String type)
        {
            this.type = type;
        }

        public String gettype()
        {
            return type;
        }
    }
