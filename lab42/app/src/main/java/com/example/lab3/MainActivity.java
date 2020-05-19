package com.example.lab3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String email;
    private String stringColors[] = new String[] { "Черный", "Синий", "Зеленый", "Красный", "Жёлтый" };
    private int colors[] = new int[] { Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW };
    private int curColor;
    private int curColorName;
    private int time = 0;
    private Button corAnswer;
    private TextView timerView;
    private TextView nameColor;
    private TextView viewColor;
    private CheckBox vsbAnswers;
    private RadioButton maxSize;
    private RadioButton minSize;
    private ProgressBar progBar;
    private CountDownTimer timer;
    private int millisCount;
    private Spinner spinView;
    private int countAns;
    private int countTrue;
    private int countFalse;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Bundle args = getIntent().getExtras();
         if (args != null) {
            email = args.getString("email");
         }
         countTrue = 0;
         countFalse = 0;
         countAns = 0;

         findViewById(R.id.btnStart).setVisibility(View.VISIBLE);
         findViewById(R.id.setingsLayout).setVisibility(View.VISIBLE);
         findViewById(R.id.gameLayout).setVisibility(View.GONE);

         Button start = findViewById(R.id.btnStart);
         maxSize = findViewById(R.id.radioButton2);
         minSize = findViewById(R.id.radioButton);
         timerView = findViewById(R.id.tvTimer);
         spinView  = findViewById(R.id.spinner);
         nameColor = findViewById(R.id.tvNamecolor);
         viewColor = findViewById(R.id.tvViewcolor);
         vsbAnswers = findViewById(R.id.checkBox);
         progBar = findViewById(R.id.progressBar);


         View.OnClickListener ocl = new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        start();
                    }
                };
         View.OnClickListener orcl = new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        onRadioButtonClicked(v);
                    }
                };

         start.setOnClickListener(ocl);
         minSize.setOnClickListener(orcl);
         maxSize.setOnClickListener(orcl);
    }


//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState){
//        savedInstanceState.putInt("correct", countTrue);
//        savedInstanceState.putInt("wrong", countFalse);
//        savedInstanceState.putInt("ans", countAns);
//        savedInstanceState.putInt("time", time);
//        savedInstanceState.putInt("flag", flag);
//        super.onSaveInstanceState(savedInstanceState);
//    }
//
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState){
//
//        if(savedInstanceState.getInt("ans") > 0) {
//            super.onRestoreInstanceState(savedInstanceState);
//            countTrue = savedInstanceState.getInt("correct");
//            countFalse = savedInstanceState.getInt("wrong");
//            time = savedInstanceState.getInt("time");
//            countAns = savedInstanceState.getInt("ans");
//            findViewById(R.id.btnStart).setVisibility(View.GONE);
//            findViewById(R.id.setingsLayout).setVisibility(View.GONE);
//
//
//            View.OnClickListener onClickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    countAns++;
//                    if(v == corAnswer) {
//                        countTrue++;
//                        if(vsbAnswers.isChecked())
//                            Toast.makeText(getApplicationContext(), "Правильно!", Toast.LENGTH_SHORT).show();
//                    } else
//                    {
//                        countFalse++;
//                        if(vsbAnswers.isChecked())
//                            Toast.makeText(getApplicationContext(), "Неправильно!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    generate();
//                }
//            };
//            Button btnYes = findViewById(R.id.btnYes),
//                    btnNo = findViewById(R.id.btnNo);
//            // Привязываем к ним обработчик событий
//            btnYes.setOnClickListener(onClickListener);
//            btnNo.setOnClickListener(onClickListener);
//
//            // Делаем видимыми элементы игры
//            findViewById(R.id.gameLayout).setVisibility(View.VISIBLE);
//
//            // Генерируем стартовые данные
//            generate();
//        } else {
//            findViewById(R.id.btnStart).setVisibility(View.VISIBLE);
//            findViewById(R.id.setingsLayout).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.gameLayout).setVisibility(View.GONE);
//        }
//    }

    private void onRadioButtonClicked(View view){
        switch (view.getId()){
            case R.id.radioButton:
                if(minSize.isChecked()){
                    nameColor.setTextSize(18);
                    viewColor.setTextSize(18);
                    timerView.setTextSize(18);
                }
                break;
            case R.id.radioButton2:
                if(maxSize.isChecked()){
                    nameColor.setTextSize(36);
                    viewColor.setTextSize(36);
                    timerView.setTextSize(36);
                }
                break;
        }
    }

    private void start() {

        time = Integer.valueOf(spinView.getSelectedItem().toString());
        findViewById(R.id.btnStart).setVisibility(View.GONE);
        findViewById(R.id.setingsLayout).setVisibility(View.GONE);
        millisCount = time;
        progBar.setMax(millisCount);


        timer = new CountDownTimer(millisCount * 1000, 100) {

            public void onTick(long millisUntilFinished) {
                timerView.setText(millisUntilFinished / 1000 + "." + (millisUntilFinished % 1000) / 100);
                time -= 100;
                progBar.setProgress(progBar.getMax() - (int)(millisUntilFinished / 1000));
            }

            public void onFinish() {
                findViewById(R.id.btnStart).setVisibility(View.VISIBLE);
                findViewById(R.id.setingsLayout).setVisibility(View.VISIBLE);

                findViewById(R.id.gameLayout).setVisibility(View.GONE);

                Intent result = new Intent(MainActivity.this, gameover.class);
                result.putExtra("time", Double.valueOf(spinView.getSelectedItem().toString()));
                result.putExtra("correct", countTrue);
                result.putExtra("wrong", countFalse);
                result.putExtra("email", email);
                countTrue = 0;
                countFalse = 0;
                countAns = 0;
                startActivity(result);
                finish();
            }

        }.start();

        // Обработчик событий кнопок-ответов
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countAns++;
                if(v == corAnswer) {
                        countTrue++;
                        if(vsbAnswers.isChecked())
                        Toast.makeText(getApplicationContext(), "Правильно!", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        countFalse++;
                        if(vsbAnswers.isChecked())
                        Toast.makeText(getApplicationContext(), "Неправильно!", Toast.LENGTH_SHORT).show();
                    }

                generate();
            }
        };

        // Находим кнопки ответов
        Button btnYes = findViewById(R.id.btnYes),
                btnNo = findViewById(R.id.btnNo);
        // Привязываем к ним обработчик событий
        btnYes.setOnClickListener(onClickListener);
        btnNo.setOnClickListener(onClickListener);

        // Делаем видимыми элементы игры
        findViewById(R.id.gameLayout).setVisibility(View.VISIBLE);

        // Генерируем стартовые данные
        generate();
    }

    // Функция генерации данных
    private void generate() {

        curColorName = (int)Math.floor(Math.random() * stringColors.length);
        curColor = colors[(int)Math.floor(Math.random() * colors.length)];
        TextView tvLeft = findViewById(R.id.tvNamecolor);
        tvLeft.setText(stringColors[curColorName]);
        tvLeft.setTextColor(curColor);
        curColor = (int)Math.floor(Math.random() * colors.length);
        TextView tvRight = findViewById(R.id.tvViewcolor);
        tvRight.setText(stringColors[(int)Math.floor(Math.random() * stringColors.length)]);
        tvRight.setTextColor(colors[curColor]);
        if (colors[curColorName] == colors[curColor])
            corAnswer = findViewById(R.id.btnYes);
        else
            corAnswer = findViewById(R.id.btnNo);
    }
}
