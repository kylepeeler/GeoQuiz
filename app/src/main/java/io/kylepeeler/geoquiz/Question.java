package io.kylepeeler.geoquiz;

/**
 * Created by Kyle on 11/23/16.
 */
//Model for a Question
public class Question {

    //private fields, buttonID & whether the answer to question
    private int mTextResId;
    private boolean mAnswerTrue;

    //constructor
    public Question(int textResId, boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    //getter for the button id
    public int getTextResId() {
        return mTextResId;
    }

    //setter for the button id
    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    //getter for the answer being true
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    //sets whether the answer is true
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
