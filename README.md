# Quiz Application

## Purpose
The purpose of this app is to test user general knowledge. The app currently presents 10 questions one by one on the screen and expectes to select correct answers to calculate the score. Based on the question type there could be single answer, mutiple correct answers or a number that user enters.

## Implementation Details
All the content - Question, Image, choices, button labels are programatically generated based on QuizQuestionAnswer object. Bundle savedInstanceState is used to save the state of user score, the question, image and choices index stores and button label so that content is not lost on rotation.

For more details on implementation read the code comments. 

## Improvements
The app architecture can be made better by using Android mvvm patteren.
Accessibility can be made better by adding more content descriptors based on image changed, optiom selected.
LoaderManager could be used instead of saving data in savedInstanceState bundle.
The app can be taken to next level by adding more quiz levels.
