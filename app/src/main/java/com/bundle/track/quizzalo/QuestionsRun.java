package com.bundle.track.quizzalo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;

public class QuestionsRun extends AppCompatActivity {

    TextView qbox;
    RadioGroup radioGroup;
    RadioButton op1,op2,op3,op4,goodOp;
    String[] qset;
    int numleft,score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_run);

        qbox = (TextView)findViewById(R.id.questionBox);
        radioGroup = (RadioGroup)findViewById(R.id.optsGroup);
        op1 = (RadioButton)findViewById(R.id.optionBox1);
        op2 = (RadioButton)findViewById(R.id.optionBox2);
        op3 = (RadioButton)findViewById(R.id.optionBox3);
        op4 = (RadioButton)findViewById(R.id.optionBox4);

        Intent ix = getIntent();
        qset = ix.getStringArrayExtra("list_q");
        numleft = ix.getIntExtra("left_q",3);
        score = ix.getIntExtra("cur_score",0);

        Log.i("Score after get",Integer.toString(score));

        if(numleft>=1)
            fillQuestions();

        else
            dispScore();

    }

    public void fillQuestions() {

        Random rand = new Random();
        int value = rand.nextInt(3);

        String[] parts = qset[value].split(",");

        qbox.setText(parts[0]);
        op1.setText(parts[1]);
        op2.setText(parts[2]);
        op3.setText(parts[3]);
        op4.setText(parts[4]);

        int w = Integer.parseInt(parts[5]);

        switch(w){
            case 1:
                goodOp = op1; break;
            case 2:
                goodOp = op2; break;
            case 3:
                goodOp = op3; break;
            case 4:
                goodOp = op4; break;
            default:
                goodOp = op1;
        }
        Log.i("Good op",Integer.toString(w));
    }

    public void dispScore() {
        Intent calc = new Intent(this,ScoreDisp.class);
        calc.putExtra("score",score);
        startActivity(calc);
    }

    @Override
    public void onBackPressed(){
        // We do nothing.
    }

    public void scoreNext(View v){

        Log.i("Good checked id",Integer.toString(radioGroup.getCheckedRadioButtonId()));
        Log.i("Good button id",Integer.toString(goodOp.getId()));

        if(radioGroup.getCheckedRadioButtonId() == goodOp.getId())
            score++;

        Log.i("Score before send",Integer.toString(score));

        Intent tosame = new Intent(this,QuestionsRun.class);
        tosame.putExtra("list_q",qset);
        tosame.putExtra("left_q",numleft-1);
        tosame.putExtra("cur_score",score);
        startActivity(tosame);

    }

}
