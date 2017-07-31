package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when a button is pressed
     *
     * @param myButton The button that was pressed
     */
    public void onButtonPressed(View myButton) {

        switch (myButton.getId()) {
            case R.id.button_send:
                checkAnserws();
                break;
            case R.id.button_reset:
                reset();
                break;
        }
    }

    /**
     * Check all answers and show results
     */
    public void checkAnserws() {

        int[] results = new int[6];

        int questionNumber;

        // Question 1 - Radio button - Correct: D
        questionNumber = 1;
        results[questionNumber - 1] = checkRadioQuestion(R.id.q1_radio_group, R.id.q1_radio_button_D);

        // Question 2 - Radio button - Correct: A
        questionNumber = 2;
        results[questionNumber - 1] = checkRadioQuestion(R.id.q2_radio_group, R.id.q2_radio_button_A);

        // Question 3 - EditText - Correct: question_3_correct_answer
        questionNumber = 3;
        results[questionNumber - 1] = checkTextQuestion(R.id.q3_edit_text, R.string.question_3_correct_answer);

        // Question 4 - EditText - Correct: question_4_correct_answer
        questionNumber = 4;
        results[questionNumber - 1] = checkTextQuestion(R.id.q4_edit_text, R.string.question_4_correct_answer);

        // Question 5 - CheckBoxes - Correct: B and C
        questionNumber = 5;
        CheckBox q5CbA = (CheckBox) findViewById(R.id.q5_check_box_A);
        CheckBox q5CbB = (CheckBox) findViewById(R.id.q5_check_box_B);
        CheckBox q5CbC = (CheckBox) findViewById(R.id.q5_check_box_C);
        if ((!q5CbA.isChecked()) &&
                q5CbB.isChecked() &&
                q5CbC.isChecked()) {
            results[questionNumber - 1] = 1;
        }

        // For Question 6 - RadioButton - Correct: A
        questionNumber = 6;
        results[questionNumber - 1] = checkRadioQuestion(R.id.q6_radio_group, R.id.q6_radio_button_A);

        // Show results
        showResults(results);

    }

    /**
     * Prepare and show the results
     *
     * @param results Array with the results
     */
    private void showResults(int results[]) {

        // Prepare result String
        String EOL = "\n";
        String resultText = "";

        for (int i = 0; i < results.length; i++) {
            switch (results[i]) {
                case 1:
                    resultText += getString(R.string.result_text_correct, i + 1);
                    break;
                case 0:
                    resultText += getString(R.string.result_text_not_correct, i + 1);
                    break;
                case -1:
                    resultText += getString(R.string.result_text_no_answer, i + 1);
            }
            resultText += EOL;
        }

        // Show toast with results
        Toast.makeText(this, resultText, Toast.LENGTH_LONG).show();
    }

    /**
     * Checks the answer for a RadioGroup type question
     *
     * @param radioGroupId         the id of the radio group for this question
     * @param correctRadioButtonId the id of the radio button with the correct answer
     * @return 1 if correct, 0 if not, -1 if no answer provided
     */
    private int checkRadioQuestion(int radioGroupId, int correctRadioButtonId) {
        RadioGroup rg = (RadioGroup) findViewById(radioGroupId);
        int checked = rg.getCheckedRadioButtonId();
        if (checked == -1) {
            return -1;
        } else if (checked == correctRadioButtonId) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Checks the answer for a EditText type question
     *
     * @param editTextId            the id of the radio group for this question
     * @param correctAnswerStringId the id of the radio button with the correct answer
     * @return 1 if correct, 0 if not, -1 if no answer provided
     */
    private int checkTextQuestion(int editTextId, int correctAnswerStringId) {
        EditText et = (EditText) findViewById(editTextId);
        String etText = et.getText().toString();
        if (etText.equals("")) {
            return -1;
        } else if (etText.toUpperCase().equals(getString(correctAnswerStringId).toUpperCase())) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Reset views
     */
    private void reset() {

        ((RadioGroup) findViewById(R.id.q1_radio_group)).clearCheck();
        ((RadioGroup) findViewById(R.id.q2_radio_group)).clearCheck();

        ((EditText) findViewById(R.id.q3_edit_text)).setText("");
        ((EditText) findViewById(R.id.q4_edit_text)).setText("");

        ((CheckBox) findViewById(R.id.q5_check_box_A)).setChecked(false);
        ((CheckBox) findViewById(R.id.q5_check_box_B)).setChecked(false);
        ((CheckBox) findViewById(R.id.q5_check_box_C)).setChecked(false);

        ((RadioGroup) findViewById(R.id.q6_radio_group)).clearCheck();

        // Set focus on first question
        RadioGroup rgq1 = (RadioGroup) findViewById(R.id.q1_radio_group);
        rgq1.requestFocus();

        // Show toast with reset message
        Toast.makeText(this, getString(R.string.quiz_reset), Toast.LENGTH_LONG).show();
    }

}
