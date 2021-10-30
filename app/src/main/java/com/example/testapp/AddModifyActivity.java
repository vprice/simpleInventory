package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddModifyActivity extends AppCompatActivity {
    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify);

        //Take the passed data to this activity
        Intent intent = getIntent();
        counter = (Counter) intent.getSerializableExtra("My Counter");

        //Place all current values to positions
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(counter.getDate());

        TextView counterName = (TextView) findViewById(R.id.counterName);
        counterName.setText(counter.getName());

        TextView currValue = (TextView) findViewById(R.id.currValue);
        currValue.setText(String.valueOf(counter.getCurrValue()));

        TextView initValue = (TextView) findViewById(R.id.initValue);
        initValue.setText(String.valueOf(counter.getInitValue()));

        TextView comment = (TextView) findViewById(R.id.comment);
        comment.setText(counter.getComment());
    }

    /*
    Increment button Implementation: When pressed, the current value is increased by one.
     */
    public void incCounter(View view){
        EditText currValue = (EditText) findViewById(R.id.currValue);
        String value = currValue.getText().toString();
        counter.setCurrValue(Integer.parseInt(value) + 1);
        currValue.setText(String.valueOf(counter.getCurrValue()));
    }
    /*
    Decrement button Implementation: When pressed, the current value is decreased by one.
     */
    public void decCounter(View view){
        EditText currValue = (EditText) findViewById(R.id.currValue);
        String value = currValue.getText().toString();
        counter.setCurrValue(Integer.parseInt(value) - 1);
        currValue.setText(String.valueOf(counter.getCurrValue()));
    }

    /*
    When the reset button is pressed the current value is set to the initial value before the saved value
    - Problem: when initial value is changed in place, the initial value is still the previous counter
     */
    public void resetCounter(View view){
        EditText currValue = (EditText) findViewById(R.id.currValue);
        counter.setCurrValue(counter.getInitValue());
        currValue.setText(String.valueOf(counter.getCurrValue()));
    }

    public void delCounter(View view){
        Intent intent = new Intent();
        intent.putExtra("Edit Counter", "");
        setResult(RESULT_OK, intent);

        finish();
    }

    /*
    When the back button is pressed, the coutner is saved to its changed values, updated in the first activity, and saved.
     */
    @Override
    public void onBackPressed() {

        EditText name = (EditText) findViewById(R.id.counterName);
        EditText currValue = (EditText) findViewById(R.id.currValue);
        EditText initValue = (EditText) findViewById(R.id.initValue);
        EditText comments = (EditText) findViewById(R.id.comment);


        String newName = name.getText().toString();
        int newCurrValue = Integer.parseInt(currValue.getText().toString());
        int newInitValue = Integer.parseInt(initValue.getText().toString());
        String newComment = comments.getText().toString();

        counter.setName(newName);
        counter.setCurrValue(newCurrValue);
        counter.setInitValue(newInitValue);
        counter.setNewDate();
        String newDate = counter.getDate();
        counter.setComment(newComment);

        if(newName.isEmpty() || newName == "\n") {
            newName = "No name";
        }
        if(newInitValue < 0){
            counter.setCurrValue(0);
            newCurrValue = 0;
        }
        if(newCurrValue < 0) {
            counter.setCurrValue(0);
            newInitValue = 0;
        }
        if(newComment.isEmpty() || newComment == "/n") {
            newComment = "No Comments";
        }

        Intent intent = new Intent();
        intent.putExtra("Edit Counter", newName + "\n" + newCurrValue+ "\n" + newInitValue
                + "\n" + newDate + "\n" + newComment);
        setResult(RESULT_OK, intent);

        finish();
    }
}