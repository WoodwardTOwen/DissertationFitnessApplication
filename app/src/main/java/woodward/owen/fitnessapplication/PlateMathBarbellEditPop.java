package woodward.owen.fitnessapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class PlateMathBarbellEditPop extends Activity {

    private TextView barbellName;
    private TextView barbellWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barbell_edit_popup);

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
                String tempStringName = barbellName.getText().toString();
                String tempStringWeight = barbellWeight.getText().toString();

                boolean isValidBarbell = ValidateBarbell(tempStringName);
                boolean isValidWeightNumber = ValidateWeightNumber(tempStringWeight);

                if(isValidBarbell && isValidWeightNumber) {
                    //Perform addition to spinner
                    //and output toast
                }
                else {
                    //send toast message that it has failed -> dont add item to spinner

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

        float inputVal = Float.parseFloat(barbellWeight);
        if (inputVal % 2.5 == 0) {
            return true;
        }
        else {
            return false;
        }
    }

}
