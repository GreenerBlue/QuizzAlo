package com.bundle.track.quizzalo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CheckCategory extends AppCompatActivity {

    TextView instr;
    Button forw, bk;
    String chosenCat;
    String[] allq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_category);

        Intent i = getIntent();
        chosenCat = i.getStringExtra("chosen");
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        instr = (TextView)findViewById(R.id.instrDisp);
        forw = (Button)findViewById(R.id.confCatg);
        bk = (Button)findViewById(R.id.denyCatg);

        instr.setText(chosenCat + " Was chosen.");

    }

    public void startQs(View v){
        try {
            extractFile();
        }
        catch(IOException e){
            Log.e("IOException","Try again later");
        }

        Intent outi = new Intent(this,QuestionsRun.class);
        outi.putExtra("list_q",allq);
        outi.putExtra("left_q",3);
        startActivity(outi);
    }

    public void toMain(View v){
        Intent previ = new Intent(this, MainActivity.class);
        previ.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(previ);
    }

    public void extractFile() throws IOException {

        String str = "";
        int res;
        StringBuffer buf = new StringBuffer();
        switch(chosenCat){
            case "Category 1":
                res = R.raw.category1;
                break;

            case "Category 2":
                res = R.raw.category2;
                break;

            case "Category 3":
                res=R.raw.category3;
                break;

            default:
                res=R.raw.dummy;
        }
        InputStream is = this.getResources().openRawResource(res);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if (is != null) {
            while ((str = reader.readLine()) != null) {
                buf.append(str + "\n");
            }
        }
        is.close();

        String contents = buf.toString();
        allq = contents.split("\n");

    }


}
