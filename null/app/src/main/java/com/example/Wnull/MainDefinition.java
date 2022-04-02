package com.example.Wnull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class MainDefinition extends AppCompatActivity {
private Button mainButton;
private Button addButton;
private Button searchWord;
private Button randButton;
Map<String, String> definitions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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
                addWord(inputWord.getText().toString(), inputDesc.getText().toString());
            }
        });

        wordBox.setMovementMethod(new ScrollingMovementMethod());
        searchWord = (Button) findViewById(R.id.searchWord);
        searchWord.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                searchWords(inputWord.getText().toString());
            }
        });

        randButton = (Button) findViewById(R.id.randButton);
        randButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                randomWord();
            }
        });
    }

    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addWord(String word, String def){
        if(!definitions.keySet().contains(word)) {
            definitions.put(word, def);
            defDisplay.setText("Word successfully added!");
        } else {
            defDisplay.setText("Word is already added!");
        }
        wordBox.setText(definitions.keySet().toString());
        inputWord.setText("Word");
        inputDesc.setText("Definition");
    }

    public void searchWords(String word) {
        if(definitions.keySet().contains(word)) {
            defDisplay.setText(definitions.get(word));
        } else {
            defDisplay.setText("Word not found.");
        }
    }

    public void randomWord() {
        if(definitions.size() == 0) defDisplay.setText("No words in bank");
        int rand = (int) Math.floor(Math.random() * definitions.size());
        defDisplay.setText(definitions.values().toArray()[rand].toString());
    }
}