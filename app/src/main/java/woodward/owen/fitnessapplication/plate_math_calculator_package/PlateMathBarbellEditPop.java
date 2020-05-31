package woodward.owen.fitnessapplication.plate_math_calculator_package;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;

public class PlateMathBarbellEditPop extends Activity {

    private TextView barbellName;
    private TextView barbellWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barbell_edit_popup);

        Bundle bundle = getIntent().getExtras();
        final ArrayList<BarbellType> retrievedListOfBarbells = Objects.requireNonNull(bundle).getParcelableArrayList("barbellList");
        Display();


        Button b = (findViewById(R.id.SubmitBarbellBnt));
        b.setOnClickListener(v -> {

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

                    boolean checker = CheckIfExists(barbellNameStr, Objects.requireNonNull(retrievedListOfBarbells));

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
        });

    }

    private void Display() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels, height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .7));
    }


    private boolean ValidateBarbell (String barbell){

        char[] chBarbell = barbell.toCharArray();
        for (char c : chBarbell) {
            if(!Character.isLetter(c) && !Character.isDigit(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean ValidateWeightNumber (String barbellWeight){

        try {
            int inputVal = Integer.parseInt(barbellWeight);
            return inputVal % 2.5 == 0 && inputVal <= 100;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    //May need potentially altering so its more accurate -> doesn't count spaces in comparing -> more accurate in comparisons
    private boolean CheckIfExists (String barbellName, ArrayList<BarbellType> arrayList) {
        for(BarbellType str : arrayList) {
            String tempStr = str.getBarbellName().toLowerCase();
            barbellName = barbellName.toLowerCase();

            if(tempStr.equals(barbellName)) {
                return false;
            }
        }
        return true;
    }


    private void ToastMsgSuccess (BarbellType barbell) {
        String name = barbell.getBarbellName();
        int weight = barbell.getBarbellWeight();

        Toast.makeText(this, "Barbell Added Successfully added: " + "\nBarbell: " + name + "\nWeight: " + weight + "kg", Toast.LENGTH_LONG).show();
    }

    private void ToastMsgDenied () {
        Toast.makeText(this, "This barbell cannot be added, a field has been entered incorrectly", Toast.LENGTH_SHORT).show();
    }

    private void ToastMsgAlreadyExists() {
        Toast.makeText(this, "This barbell name already exists", Toast.LENGTH_SHORT).show();
    }

}
