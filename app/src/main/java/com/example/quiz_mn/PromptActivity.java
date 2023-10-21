package com.example.quiz_mn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class PromptActivity extends AppCompatActivity {

    TextView answerTextView;
    Button showAnswerButton;
    Button back;
    private boolean correctAnswer;
    public static final String KEY_EXTRA_ANSWER_SHOWN = "com.example.quiz_mn_answerShown";

    public static final String POINTS = "xD";

    Intent resultIntent;

    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        answerTextView = findViewById(R.id.answer_text_view);
        showAnswerButton = findViewById(R.id.show_answer_button);
        back = findViewById(R.id.back_button);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);

        points = getIntent().getIntExtra(MainActivity.POINTS, 0);

        resultIntent = new Intent(PromptActivity.this, MainActivity.class);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(resultIntent, 1);
            }
        });

    }

    private void setAnswerShownResult(boolean answerWasShown) {
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        resultIntent.putExtra(POINTS,points);
        setResult(RESULT_OK, resultIntent);
    }
}