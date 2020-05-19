package com.example.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class gameover extends AppCompatActivity {

    private Bundle results;
    private int countTrue;
    private int countFalse;
    private double time;
    private String email;
    private TextView tvTime;
    private TextView tvTrue;
    private TextView tvFalse;
    private Button btnSend;
    private Button btnTry;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        results = getIntent().getExtras();
        tvTime = findViewById(R.id.tvTime);
        tvTrue = findViewById(R.id.tvTrue);
        tvFalse = findViewById(R.id.tvFalse);
        btnSend = findViewById(R.id.btnSend);
        btnTry = findViewById(R.id.btnTry);
        btnExit = findViewById(R.id.btnExit);

        email = results.getString("email");
        time = results.getDouble("time");
        countTrue = results.getInt("correct");
        countFalse = results.getInt("wrong");

        tvTime.setText(String.valueOf(time));
        tvTrue.setText(String.valueOf(countTrue));
        tvFalse.setText(String.valueOf(countFalse));

        View.OnClickListener oclTry = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent result = new Intent(gameover.this, MainActivity.class);
                result.putExtra("email", email);
                startActivity(result);
            }
        };

        View.OnClickListener oclEmail = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Результат игры");
                emailIntent.putExtra(Intent.EXTRA_TEXT,
                        "Время игры: " + String.format("%.2f", time) + " c.\n" +
                                "Правильных ответов: " + countTrue + "\n" +
                                "Неправильных ответов: " + countFalse + "\n");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(gameover.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        View.OnClickListener oclExit = new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v){
                finishAffinity();
            }
        };

        btnTry.setOnClickListener(oclTry);
        btnSend.setOnClickListener(oclEmail);
        btnExit.setOnClickListener(oclExit);
    }
}
