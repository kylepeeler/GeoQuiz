package io.kylepeeler.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    //tag for log commands
    public static final String TAG = "QuizActivity";
    //index of the questions passed to the intent
    public static final String QUESTION_INDEX = "questionIndex";
    //result code of whether the user cheated or not
    public static final int REQUEST_CODE_CHEAT = 0;

    //member variables to hold the buttons & text view
    //member variable to hold true button
    private Button mTrueButton;
    //member variable to hold false button
    private Button mFalseButton;
    //member variable to hold cheat butotn
    private Button mCheatButton;
    //member variable to hold the next ImageButton
    private ImageButton mNextButton;
    //member variable to hold the prev ImageButton
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    //an array to hold the different questions
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    //member variable to hold the current index
    private int mCurrentIndex = 0;
    //boolean to tell if someone is a cheater
    private boolean mIsCheater;

    //updates the UI based on the member variable mCurrentIndex
    private void updateQuestion(){
        //get the question string ID from the question bank
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        //set the text view text to the string ID
        mQuestionTextView.setText(question);
    }

    //checks if the current question's answer is true or false and displays it in a toast
    private void checkAnswer(boolean userPressedTrue){
        //check whether the answer is true
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        //initialize message ID variable;
        int messageResId = 0;
        //if the user is a cheater, then show the judgement toast
        if (mIsCheater){
            messageResId = R.string.judgement_toast;
        }else {
            //otherwise the user is not a cheater, show the answer
            if (userPressedTrue == answerIsTrue) {
                //set the message response to the correct string
                messageResId = R.string.correct_toast;
            } else {
                //set the message response to the incorrect string
                messageResId = R.string.incorrect_toast;
            }
        }
        //show the response toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    //updates the question
    private void showPrevQuestion() {
        //check if the index is not 0
        if (mCurrentIndex > 0){
            //if it is subtract one from it and mod it with the arrays length
            mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
            //reset whether the user is a cheater
            mIsCheater = false;
            //update the question with the new index
            updateQuestion();
        }
        else{
            //if the question is 0, we reset the index to length - 1
            mCurrentIndex = mQuestionBank.length - 1;
            //reset whether the user is a cheater
            mIsCheater = false;
            //update the question TextView with the new index
            updateQuestion();
        }
    }

    private void showNextQuestion() {
        //add one to the index and mod it with the length of the questions
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        //reset whether the user is a cheater
        mIsCheater = false;
        //update the question TextView with the new index
        updateQuestion();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //log that onCreate was called
        Log.d(TAG, "onCreate(Bundle) called");
        //set the layout to show the Quiz activity
        setContentView(R.layout.activity_quiz);
        //get the question view
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        //when the user clicks the question view, go to the next question
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showNextQuestion();
            }
        });
        //assign a listener to the true button
        mTrueButton = (Button) findViewById(R.id.true_button);
        //when the user clicks true button, check if the answer is true
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the checkAnswer method
                checkAnswer(true);
            }
        });
        //assign listener to the false button
        mFalseButton = (Button) findViewById(R.id.false_button);
        //when clicks false button, check whether the answer is false
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        //get the next button
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        //when the user clicks the next button, go to the next question
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showNextQuestion();
            }
        });
        //get the previous button
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        //assign a listener to the previous button
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //update the UI to show the previous question
                showPrevQuestion();
            }
        });

        //get the cheat button
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //get whether the answer is true
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                //generate a new intent from the CheatActivity and pass in whether the answer is true
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                //start the activity, return a result of whether the user cheated or not
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        //check if we should reload the current question state
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(QUESTION_INDEX, 0);
        }
        //update the UI
        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //log that onSaveInstanceState() called
        Log.i(TAG, "onSaveInstanceState() called");
        //when we save the index, store the question index
        savedInstanceState.putInt(QUESTION_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //log whether onStart() was called
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //log whether onPause() was called
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //log whether onResume() was called
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //log whether onStop() was called
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //log whether onDestroy() was called
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check that the resultCode returned was OK
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        //check whether the requestCode was the REQUEST_CODE_CHEAT
        if (requestCode == REQUEST_CODE_CHEAT){
            //return if no data was passed
            if (data == null){
                return;
            }
            //update whether the user was a cheater by passing the intent to the wasAnswerShown method
            //in CheatActivity
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
}
