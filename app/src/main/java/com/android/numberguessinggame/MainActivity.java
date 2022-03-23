package com.android.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView txtRemainder, txtResult, txtCount;
    private EditText txtGetNumber;
    private Button button;
    private String getValue;
    private int remainingRight = 3, randomValue;
    private Random randomNumber;
    private boolean prediction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtRemainder = findViewById(R.id.txtRemainingRight);
        txtResult = findViewById(R.id.txtResult);
        txtGetNumber = findViewById(R.id.edtNumber);
        button = findViewById(R.id.btnGuess);
        txtCount = findViewById(R.id.txtCount);
        randomNumber = new Random();
        randomValue = randomNumber.nextInt(5) + 1;
        System.out.println("sayımız " + randomValue);
        new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCount.setText((millisUntilFinished / 1000) + ":" );
            }

            @Override
            public void onFinish() {
                txtCount.setText("Süre Doldu");
                txtGetNumber.setEnabled(false);
                button.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Yeni Oyun");
                builder.setMessage("Tekrar Oynamak İster Misin");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
                builder.show();
            }
        }.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue = txtGetNumber.getText().toString();
                if (!TextUtils.isEmpty(getValue)) {
                    if (remainingRight > 0 && prediction == false) {
                        if (getValue.equals(String.valueOf(randomValue))) {
                            showResult("Tebrikler Bildiniz");
                            prediction = true;
                            // Toast.makeText(MainActivity.this, "Tebrikler Bildiniz", Toast.LENGTH_SHORT).show();
                        } else {
                            txtResult.setText("Tahmin Yanlış");
                            txtResult.setText(null);
                            txtGetNumber.setText("");
                        }
                        remainingRight--;
                        txtRemainder.setText("Kalan Hak : " + remainingRight);
                        if (remainingRight == 0 && prediction == false) {
                            showResult("Tahmin Hakkı Bitti");
                            txtGetNumber.setText(" ");
                        }
                    } else {
                        txtResult.setText("Oyun Bitti");
                    }
                } else {
                    txtResult.setText("Değer Boş Olmamalı");
                }
            }
        });
    }

    private void showResult(String message) {
        txtGetNumber.setEnabled(false);
        txtResult.setText(message);
    }


}