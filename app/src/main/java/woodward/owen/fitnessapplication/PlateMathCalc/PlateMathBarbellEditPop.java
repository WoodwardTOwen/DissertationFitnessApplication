package woodward.owen.fitnessapplication.PlateMathCalc;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import woodward.owen.fitnessapplication.HomePage;
import woodward.owen.fitnessapplication.R;

public class PlateMathBarbellEditPop extends Activity {

    private TextView barbellName;
    private TextView barbellWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barbell_edit_popup);

        Bundle bundle = getIntent().getExtras();
        final ArrayList<BarbellType> retrievedListOfBarbells = bundle.getParcelableArrayList("barbellList");


        DisplayMetrics displayMetrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels, height = displayMetrics.heightPixels;
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setLayout((int)(width*.8),(int)(height*.7));  // In landscape
        } else {
            getWindow().setLayout((int)(width*.8),(int)(height*.7));  // In portrait
        }


        Button b = (findViewById(R.id.SubmitBarbellBnt));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Call methods to check whether values are valid for input and assign it to the boolean values below

                barbellName = findViewById(R.id.barbellTypeTB);
                barbellWeight = findViewById(R.id.barbellWeightTb);

                boolean isValidBarbell = ValidateBarbell(barbellName.getText().toString());
                boolean isValidWeightNumber = ValidateWeightNumber(barbellWeight.getText().toString());

                if(isValidBarbell && isValidWeightNumber) {
                    //Perform addition to spinner
                    try {
                        String barbellNameStr = barbellName.getText().toString();
                        int barbellWeightStr = Integer.parseInt(barbellWeight.getText().toString());

                        boolean checker = CheckIfExists(barbellNameStr, retrievedListOfBarbells);

                        if(checker) {
                            BarbellType tempBarbell = new BarbellType(barbellNameStr, barbellWeightStr);
                            retrievedListOfBarbells.add(tempBarbell);
                            ToastMsgSuccess(tempBarbell);

                            Intent intent = new Intent();
                            intent.putExtra("AddedBarbellList", retrievedListOfBarbells);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else {
                            ToastMsgAlreadyExists();
                        }

                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    //send toast message that it has failed -> dont add item to spinner
                    ToastMsgDenied();
                }
            }
        });

    }

    public boolean ValidateBarbell (String barbell){

        char[] chBarbell = barbell.toCharArray();
        for (char c : chBarbell) {
            if(!Character.isLetter(c) && !Character.isDigit(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean ValidateWeightNumber (String barbellWeight){

        int inputVal = Integer.parseInt(barbellWeight);
        if (inputVal % 2.5 == 0 || inputVal > 100) {
            return true;
        }
        else {
            return false;
        }
    }


    //May need potentially altering so its more accurate -> doesn't count spaces in comparing -> more accurate in comparisons
    public boolean CheckIfExists (String barbellName, ArrayList<BarbellType> arrayList) {
        for(BarbellType str : arrayList) {
            String tempStr = str.getBarbellName().toLowerCase();
            barbellName = barbellName.toLowerCase();

            if(tempStr.equals(barbellName)) {
                return false;
            }
        }
        return true;
    }


    public void ToastMsgSuccess (BarbellType barbell) {
        String name = barbell.getBarbellName();
        int weight = barbell.getBarbellWeight();

        Toast.makeText(this, "Barbell Added Successfully added: " + "\nBarbell: " + name + "\nWeight: " + weight, Toast.LENGTH_LONG).show();
    }

    public void ToastMsgDenied () {
        Toast.makeText(this, "This barbell cannot be added, a field has been entered incorrectly", Toast.LENGTH_SHORT).show();
    }

    public void ToastMsgAlreadyExists() {
        Toast.makeText(this, "This barbell name already exists", Toast.LENGTH_SHORT).show();
    }

}
