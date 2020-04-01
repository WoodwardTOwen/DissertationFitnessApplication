package woodward.owen.fitnessapplication.plate_math_calculator_package;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import woodward.owen.fitnessapplication.R;

public class PlateMathBarbellDeletePopUp extends Activity {

    private ArrayList<BarbellType> retrievedListOfBarbells;

    private Spinner mBarbellSpinnerRemover;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //Changed from NoNull to Nullable

        super.onCreate(savedInstanceState);
        setContentView(R.layout.barbell_delete_pop_up);
        mBarbellSpinnerRemover = findViewById(R.id.BarbellRemoverSpinner);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        retrievedListOfBarbells = bundle.getParcelableArrayList("barbellList");
        setSpinnerData();
        Display();

        Button b = findViewById(R.id.deleteRemoveBarbellBnt);
        b.setOnClickListener(v -> {
            BarbellType barbellObj = (BarbellType) mBarbellSpinnerRemover.getSelectedItem();
            final String barbellName = (barbellObj.getBarbellName());

            AlertDialog.Builder diaLogBuilder = new AlertDialog.Builder(PlateMathBarbellDeletePopUp.this);
            diaLogBuilder.setTitle("Confirmation of Barbell Deletion");
            diaLogBuilder.setMessage("Are you sure you want to delete " + barbellName + "?");

            diaLogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    for (BarbellType str : retrievedListOfBarbells) {
                        if (str.getBarbellName().equals(barbellName)) {
                            retrievedListOfBarbells.remove(str);
                            break;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Successfully Removed " + barbellName, Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("AddedBarbellList", retrievedListOfBarbells);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            diaLogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Cancel Procedure -> do NOT remove barbell
                    Toast.makeText(getApplicationContext(), "Transaction Cancelled", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            AlertDialog alert = diaLogBuilder.create();
            alert.show();
        });

    }

    private void Display() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels, height = displayMetrics.heightPixels;
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setLayout((int) (width * .8), (int) (height * .7));  // In landscape
        } else {
            getWindow().setLayout((int) (width * .8), (int) (height * .7));  // In portrait
        }
    }

    private void setSpinnerData() {
        //Create adapter to place to create bridge between data and View
        ArrayAdapter<BarbellType> barbellTypeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, retrievedListOfBarbells);
        barbellTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBarbellSpinnerRemover.setAdapter(barbellTypeArrayAdapter);
    }
}
