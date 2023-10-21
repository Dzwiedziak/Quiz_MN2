package com.example.quiz_mn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private int currentQuestionIndex = 0;

    private static final int REQUEST_CODE_PROMPT = 0;

    private static final String KEY_CURRENT_INDEX = "currentQuestionIndex";

    private boolean answerWasShown;

    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_version, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_find_resources, false)
    };

    public static final String KEY_EXTRA_ANSWER = "com.example.quiz_mn.correctAnswer";

    public static final String POINTS = "DK";

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private Button promptButton;

    private boolean answered = false;

    int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        setContentView(R.layout.activity_main);

        Log.d("Quiz", "onCreate");


        Log.d("Quiz",String.valueOf(answerWasShown));


        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        promptButton = findViewById(R.id.prompt_button);

        showCurrentQuestion();

        answerWasShown = getIntent().getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);

        points = getIntent().getIntExtra(PromptActivity.POINTS, 0);


        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true); answered = true;
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false); answered = true;
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questions.length;
                showCurrentQuestion();
                Log.d("Quiz",String.valueOf(currentQuestionIndex));
                if(currentQuestionIndex == 0) {
                    Log.d("Quiz","IN");
                    String s = points + "/" + questions.length;
                    showMessage(s);
                    points = 0;
                }
            }
        });
        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentQuestionIndex].isTrue();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                intent.putExtra(POINTS,points);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
    }


    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentQuestionIndex].isTrue();
        int messageId = 0;
        if (answerWasShown) {
            messageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                messageId = R.string.correct_answer;
                if(!answered) {
                    points = points + 1;
                }
            }
            else {
                messageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    private void showCurrentQuestion() {
        questionTextView.setText(questions[currentQuestionIndex].getId());
        answerWasShown = false;
    }
    private void showMessage(String message) {
        Toast.makeText(this, message , Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Quiz", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Quiz", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Quiz", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Quiz", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Quiz", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Quiz", "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Quiz", "onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentQuestionIndex);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null)
                return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}