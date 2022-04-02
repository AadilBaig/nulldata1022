package com.example.Wnull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainDefinition extends AppCompatActivity {
private Button mainButton;
private Button addButton;
private Button searchWord;
private HashMap<String, String> definitions = new HashMap<String, String>();
private TextView wordBox;
private TextView defDisplay;
EditText inputWord;
EditText inputDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_definition);
        mainButton = (Button) findViewById(R.id.mainButton);
        defDisplay = (TextView) findViewById(R.id.defDisplay);
        wordBox = (TextView) findViewById(R.id.wordBox);
        inputWord = (EditText) findViewById(R.id.inputWord);
        inputDesc = (EditText) findViewById(R.id.inputDesc);

        mainButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                openActivity1();
            }
        });

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                addWord();
            }
        });

        wordBox.setMovementMethod(new ScrollingMovementMethod());
        searchWord = (Button) findViewById(R.id.searchWord);
        searchWord.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                searchWords();
            }
        });
    }

    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addWord(){
        if(!definitions.keySet().contains(inputWord.getText().toString())) {
            System.out.println(inputWord.getText().toString() + ' ' + inputDesc.getText().toString());
            definitions.put(inputWord.getText().toString(), inputDesc.getText().toString());
        }
        wordBox.setText(definitions.keySet().toString());
        defDisplay.setText("Word successfully added!");
        inputWord.setText("Word");
        inputDesc.setText("Definition");
    }

    public void searchWords() {
        for (String i : definitions.keySet()) {
            System.out.println(i);
        }
        TextView defDisplay = (TextView) findViewById(R.id.defDisplay);
        defDisplay.setText(definitions.get(inputWord.getText().toString()));
        System.out.println(definitions.get(inputWord.getText().toString()));
    }
}