package io.kylepeeler.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "io.kylepeeler.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "io.kylepeeler.geoquiz.answer_shown";

    //fields for the show answer, the text view containing the answer, and whether the answer is true
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean mAnswerIsTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //show the cheat activity
        setContentView(R.layout.activity_cheat);
        //get whether the answer is true from the intent
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        //get the asnwer text view
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        //get the button to show the answer
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        //attach an onclick listener to the show answer button
        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //show true if answer is true
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                //or false if the answer is false
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                //set that we have shown the answer to the user
                setAnswerShownResult(true);
            }
        });
    }

    //method that creates a new intent and and assigns whether the answer is shown
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    //creates a new intent that contains whether the answer is true or not, called in QuizActivity
    public static Intent newIntent(Context packageContent, boolean answerIsTrue){
        Intent i = new Intent(packageContent, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    //returns whether the answer was shown from the passed in Intent, called in QuizActivity
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }



}
