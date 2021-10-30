package com.example.testapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";

    private ArrayList<Counter> counters= new ArrayList<Counter>();
    private ListView CL;
    private ArrayAdapter<Counter> adapter;

    private Counter editCounter;
    private int editPosition;
    ActivityResultLauncher<Intent> addCounterLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        String eData = result.getData().getStringExtra("Edit Counter");
                        if(!eData.isEmpty()){
                            String lines[] = eData.split("\\r?\\n");
                            editCounter.setName(lines[0]);
                            editCounter.setCurrValue(Integer.parseInt(lines[1]));
                            editCounter.setInitValue(Integer.parseInt(lines[2]));
                            editCounter.setDate(lines[3]);
                            editCounter.setComment(lines[4]);
                            //System.out.println(editPosition);
                            System.out.println(counters.size() + "  on activity result call::::");
                            System.out.println("OKOKOKOKOKOK");
                            counters.add(new Counter());
                            counters.set(editPosition, editCounter);
                            CL = (ListView) findViewById(R.id.counterList);
                            CL.setAdapter(adapter);
                            saveInFile();
                        }
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        String eData = result.getData().getStringExtra("Edit Counter");
                        if(eData.isEmpty()){
                            counters.remove(editPosition);
                            CL = (ListView) findViewById(R.id.counterList);
                            CL.setAdapter(adapter);
                            saveInFile();
                        }
                        else{
                            String lines[] = eData.split("\\r?\\n");
                            editCounter.setName(lines[0]);
                            editCounter.setCurrValue(Integer.parseInt(lines[1]));
                            editCounter.setInitValue(Integer.parseInt(lines[2]));
                            editCounter.setDate(lines[3]);
                            editCounter.setComment(lines[4]);
                            //System.out.println(editPosition);
                            //System.out.println(counters.size() + "  on activity result call::::");
                            //System.out.println("OKOKOKOKOKOK");
                            //counters.add(new Counter());
                            counters.set(editPosition, editCounter);
                            CL = (ListView) findViewById(R.id.counterList);
                            CL.setAdapter(adapter);
                            saveInFile();
                        }
                    }
                }
            }
        );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CL = (ListView) findViewById(R.id.counterList);

        adapter = new ArrayAdapter<Counter>(this, android.R.layout.simple_list_item_1, counters);
        CL.setAdapter(adapter);

        CL.setOnItemClickListener((arg0, arg1, position, arg3) -> {
            Intent intent = new Intent(MainActivity.this, AddModifyActivity.class);
            //Get Values
            Counter counter = counters.get(position);
            System.out.println(counters.size()+" on create call:::");
            editCounter = counter;
            editPosition = position;
            intent.putExtra("My Counter", counter);
            resultLauncher.launch(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println(counters.size()+" onStart call:::");
        loadFromFile();
        adapter = new ArrayAdapter<Counter>(this, android.R.layout.simple_list_item_1, counters);
        CL.setAdapter(adapter);
    }


    public void addCounter(View view){
        counters.add(new Counter());
        adapter.notifyDataSetChanged();
        CL = (ListView) findViewById(R.id.counterList);
        Counter counter = counters.get(counters.size() - 1);
        editPosition = (counters.size()-1);
        editCounter = counter;
        Intent intent = new Intent(this, AddModifyActivity.class);

        intent.putExtra("My Counter", counter);
        addCounterLauncher.launch(intent);

    }

    /*
    Load and Save were copied from somewhere
    loads the saved file (if any) and inputs the data into the listview
     */
    private void loadFromFile() {
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            counters = gson.fromJson(in,listType);
        }
        catch(FileNotFoundException e) {
            counters = new ArrayList<Counter>();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void saveInFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();

            fos.close();
        }
        catch(FileNotFoundException e) {
            throw new RuntimeException();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}