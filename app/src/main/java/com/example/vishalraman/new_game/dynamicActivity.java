package com.example.vishalraman.new_game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Vishal Raman on 10/10/2016.
 */
public class dynamicActivity extends AppCompatActivity {
   ArrayList<String> usedWord=new ArrayList<String>();
    private FastDictionary simple_fast_dictionary;
    private InputStream is=null;
    EditText ed[] = new EditText[100];
    TextView tv[] = new TextView[3];
    TextView tvSelected,tvplayerTurn;
    int index;
    ArrayList<Integer> allIds=new ArrayList<Integer>();
    String word = "";
    String passChar = "";
    Switch sw;
    boolean player1_Turn = true;
    boolean player2_Turn = false;
    int player1_Score = 0;
    int player2_Score = 0;
    boolean fKeyup=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_grid);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth() / 8;

        for (int i = 1; i <= 8; i++) {
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 1; j <= 8; j++) {
                int id = i * 10 + j;
                ed[id] = new EditText(this);
                ed[id].setBackgroundResource(R.drawable.box);
                ed[id].setGravity(Gravity.CENTER_HORIZONTAL);  //at center
                ed[id].setTypeface(null, Typeface.BOLD);   //bold
                //ed[id].setAllCaps(true);

                // ed[id].setFilters(new InputFilter[] {new InputFilter.AllCaps()});

                //allEds.add(ed);
                ed[id].setId(id);
                ActionBar.LayoutParams lp = new ActionBar.LayoutParams(width, ActionBar.LayoutParams.WRAP_CONTENT);


                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(1);
                ed[id].setFilters(FilterArray);

                //
               //  ed[id].addTextChangedListener(filterTextWatcher);   //main
                ed[id].addTextChangedListener(new CustomTextWatcher(ed[id]));


                ed[id].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        sw = (Switch) findViewById(R.id.switch1);
                        if (sw.isChecked())

                        {
                            addChar(((EditText) v).getText().toString());
                            checking(((EditText) v).getId());
                           /* String temp = ed[11].getText().toString();
                            temp.toUpperCase();
                            ed[55].setText(temp);
                            ed[55].setFocusable(false);
                            ed[55].setTextColor(Color.BLUE);*/
                        }


                        return true;
                    }
                });

                l.addView(ed[id], lp);
            }
            ll.addView(l);

        }
        //for text views
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        for (int i = 1; i <= 2; i++) {
            tv[i] = new TextView(this);
            tv[i].setText("Player " + i + " Score : " + 0);
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT);
            l.addView(tv[i], lp);

        }
        ll.addView(l);



/*
        ed[44].setText("p");
        ed[44].setFocusable(false);*/


        Button bChallenge = (Button) findViewById(R.id.bChallenge);
        Button bPass = (Button) findViewById(R.id.bPass);
        Button bReset = (Button) findViewById(R.id.bReset);
        tvSelected = (TextView) findViewById(R.id.tvSelected);
        tvplayerTurn= (TextView) findViewById(R.id.tvPlayerTurn);

        bChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean k=word.isEmpty();
                Log.d("testing","wordnow"+k);
                if(allIds.size()==0)
                {
                    Toast.makeText(dynamicActivity.this, "Please Select in a sequence !!!!", Toast.LENGTH_SHORT).show();
                    word="";
                }
               else if(checkForSequence(allIds)&&k==false ){
                    bChallengeMethod(word);
                    sw.setChecked(false);
                    word = "";
                    tvSelected.setText("Selected Text : ");
                }else{
                    Toast.makeText(dynamicActivity.this, "Please Select in a sequence or select more than one !!", Toast.LENGTH_SHORT).show();
                    word="";
                    tvSelected.setText("Selected Text : ");

                }


            }
        });


        bPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bPassMethod(passChar);
            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bResetMethod();
            }
        });



        try {
            is=getAssets().open("words.txt");
            simple_fast_dictionary=new FastDictionary(is);///change
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkForSequence( ArrayList all) {
        boolean flag = true;
        int z = 0;
        if (all.size() >= 2) {
            int x = (int) all.get(0);
            int y = (int) all.get(1);
            if (y == (x + 1)) {
                Log.d("testing", "x=" + x);
                for (int i = 1; i < all.size(); i++) {
                    if ((int) all.get(i) == x + 1) {
                        x++;
                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                }
                all.clear();
            } else if (y == (x + 10)) {
                // Log.d("testing","y and x"+y+" "+x+10);
                for (int i = 1; i < all.size(); i++) {

                    Log.d("testing", "y and x" + (int) all.get(i) + "  " + z);

                    if ((int) all.get(i) == x + 10) {
                        Log.d("testing is here", "y and x" + (int) all.get(i) + "  " + z);
                        x = x + 10;

                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                }
                all.clear();

            } else {
                flag = false;
            }
            all.clear();
            return flag;
        }
        return  false;
    }

    private void bChallengeMethod(String word) {
      //  Log.d("testing","under chal"+word);
        boolean wordReturned= simple_fast_dictionary.isWord(word);
        if(player1_Turn==true)
        {
            if(usedWord.contains(word))
            {
                player2_Score=player2_Score+word.length();
                tv[2].setText("Player2 Score :"+player2_Score);
            }

            else if(wordReturned==true)
            {

                player1_Score=player1_Score+word.length();
                Log.d("testingw","word  "+player1_Score);

                tv[1].setText("Player1 Score :"+player1_Score);
                usedWord.add(word);
            }
            else
            {
                player2_Score=player2_Score+word.length();
                tv[2].setText("Player2 Score :"+player2_Score);

            }
            player1_Turn=false;
            player2_Turn=true;
            tvplayerTurn.setText("Player2 Turn");


        }
        else
        {
            if(usedWord.contains(word))
            {
                player1_Score=player1_Score+word.length();
                tv[1].setText("Player1 Score :"+player1_Score);
            }

           else if(wordReturned==true)
            {
                player2_Score=player2_Score+word.length();

                tv[2].setText("Player2 Score : "+player2_Score);
                usedWord.add(word);
            }
            else
            {
                player1_Score=player1_Score+word.length();
                tv[1].setText("Player1 Score :"+player1_Score);
            }
            player2_Turn=false;
            player1_Turn=true;


            tvplayerTurn.setText("Player1 Turn");

        }
    }

    boolean flag1 =true;///..............
    private void bResetMethod() {
        player1_Score=0;
        player2_Score=0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                int id = i * 10 + j;

                     //ed[id].setFocusable(true);

               flag1 = false;///....................
                ed[id].setText("");


            }


        }

        for (int i = 1; i <= 2; i++) {
            tv[i].setText("Player " + i + " Score : " + 0);
        }
        tvplayerTurn.setText("Player1 Turn");
        tvSelected.setText("Selected Text : ");
       // sw.setChecked(true);
        //sw.setChecked(false);
    }


    private void bPassMethod(String c) {
        char k=c.charAt(0);
        if(c.equals("")||Character.isDigit(k))
        {
            Toast.makeText(dynamicActivity.this, "Plese Enter a character !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(player1_Turn==true)
            {
                player1_Turn=false;
                player2_Turn=true;
                tvplayerTurn.setText("Player2 Turn");



            }
            else
            {
                player2_Turn=false;
                player1_Turn=true;
                tvplayerTurn.setText("Player1 Turn");


            }
        }
        passChar="";
        Log.d("Testing pass method ", "word " + c);



    }






    //for challenge method
    private void addChar(String ch) {
        word += ch;
        tvSelected.setTextColor(Color.BLUE);
        tvSelected.setText("Selected Text : " + word);
        // Log.d("Testing","word"+word);
    }

    //for challenge method
    private String addPassChar(String s) {
        return s;
    }
/*
 private TextWatcher filterTextWatcher = new TextWatcher() {



        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO THE CALCULATIONS HERE AND SHOW THE RESULT AS PER YOUR CALCULATIONS
            //Log.d("Testing","word"+ s);
            passChar=""+s;


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    */


    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;

        public CustomTextWatcher(EditText e) {
            mEditText = e;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

           String t="";

            passChar =""+s;
            if(passChar.contentEquals(s))
            {}
            else
            {
                Toast.makeText(dynamicActivity.this,"Invalid word",Toast.LENGTH_SHORT).show();
            }
            if(flag1==true)//........
            {
                index=mEditText.getId();
                ed[index].setTextColor(Color.BLUE);
                ed[index].setFocusable(false);
            }
            else {
                for (int i = 1; i <= 8; i++) {
                    for (int j = 1; j <= 8; j++) {
                        int id = i * 10 + j;
                        ed[id].setFocusableInTouchMode(true);
                        flag1=true;
                    }
                }
            }



        }

        public void afterTextChanged(Editable s) {


          //  mEditText.setAllCaps(true);
          //if(flag == true) {..//////////////
              // mEditText.setFocusable(false);
          // }//////
        }
    }

    public void checking( int id)
    {
        allIds.add(id);
        Log.d("testing ","arr"+allIds);
    }


}

