package woodward.owen.fitnessapplication.PlateMathCalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import woodward.owen.fitnessapplication.R;


public class PlateMathCalcActivity extends AppCompatActivity {
    //region Variables

    private EditText mInputWeightValueTb;
    private Spinner mBarbellSelectorCb;
    private Button mCalculateResultBnt;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    private ArrayList<String> tempArrayList = new ArrayList<>();
    private ArrayList<BarbellType> barbellList;

   //endregion

    //region checkboxes
    private CheckBox mOneTwoFiveCb;
    private CheckBox mTwoFiveCb;
    private CheckBox mFiveCb;
    private CheckBox mTenCb;
    private CheckBox mFifteenCb;
    private CheckBox mTwentyCb;
    private CheckBox mTwentyFiveCb;
//endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_math_calc);

        //Need to populate HashMap with values straight away
        //Could call the read in function if the file exists
        mBarbellSelectorCb = findViewById(R.id.barbellSelectorCb);
        setSpinnerData();

    }

    private void setSpinnerData() {
        loadBarbellData();

        ArrayAdapter<BarbellType> barbellTypeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, barbellList);
        barbellTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBarbellSelectorCb.setAdapter(barbellTypeArrayAdapter);

        //Set onlick listender doesnt work here

    }

    private void saveBarbellData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(barbellList);
        editor.putString("Barbell List", json);
        editor.apply();

    }

    private void loadBarbellData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Barbell List", null);
        Type type = new TypeToken<ArrayList<BarbellType>>() {}.getType();
        barbellList = gson.fromJson(json, type);

        if(barbellList == null) {
            barbellList = new ArrayList<>();

            BarbellType barbellStandard = new BarbellType("Standard Barbell 20kg", 20f);
            BarbellType barbellHex = new BarbellType("Hex Bar 25kg", 25f);
            BarbellType barbellSafety = new BarbellType("Safety Squat Bar 30kg", 30f);
            BarbellType barbellEz = new BarbellType("Ez Curl Bar", 10f);

            barbellList.add(barbellStandard);
            barbellList.add(barbellHex);
            barbellList.add(barbellSafety);
            barbellList.add(barbellEz);
            saveBarbellData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                barbellList.clear();
                barbellList = data.getParcelableArrayListExtra("AddedBarbellList");
                saveBarbellData();
                setSpinnerData();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate((R.menu.options_menu_plate_math), menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected (test)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PlateMathBarbellEditPop.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("barbellList", barbellList);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivityForResult(intent, 1);
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 selected (test)", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void calcPlates (View view) {

        adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tempArrayList);
        listView = findViewById(R.id.resultsListView);
        listView.setAdapter(adapter);

        //region Setting variable data
        mCalculateResultBnt = findViewById(R.id.calculatePMCBnt);
        mInputWeightValueTb = findViewById(R.id.inputWeightTxtBox);
        mOneTwoFiveCb = findViewById(R.id.weight125Cb);
        mTwoFiveCb = findViewById(R.id.weight25SmallCb);
        mFiveCb = findViewById(R.id.weight5Cb);
        mTenCb = findViewById(R.id.weight10Cb);
        mFifteenCb = findViewById(R.id.weight15Cb);
        mTwentyCb = findViewById(R.id.weight20Cb);
        mTwentyFiveCb = findViewById(R.id.weight25Cb);

        //endregion

        mCalculateResultBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    float inputVal = Float.parseFloat(mInputWeightValueTb.getText().toString());
                    String barbellChoice = mBarbellSelectorCb.getSelectedItem().toString();
                    int barbell = BarWeightDecider(barbellChoice);
                    int response = Validation(inputVal, barbell);

                    switch(response) {
                        case 1:
                            int counterList = 0;

                            float mathWeight = inputVal - barbell;
                            ArrayList<Float> plateStack = new ArrayList<>();
                            Map<Float,Integer> PlateDictionary = new HashMap<>();

                            plateStack = SelectedPlates();
                            plateStack = reverseArrayList(plateStack);

                            boolean found = false;

                            while(true) {
                                int counter = 0;

                                for(int x = 0; x <plateStack.size(); x++) {
                                    counter++;
                                    float weightToAdd = plateStack.get(x) /2;
                                    if(mathWeight / plateStack.get(x) == 1) {

                                        if(PlateDictionary.containsKey(weightToAdd)){
                                            counterList++;
                                            PlateDictionary.put(weightToAdd, PlateDictionary.get(weightToAdd) + 2);
                                            found = true;
                                            counter = 0;
                                            break;
                                        }
                                        else {
                                            PlateDictionary.put(weightToAdd, 2);
                                            counterList++;
                                            found = true;
                                            counter = 0;
                                            break;
                                        }
                                    }
                                    else if (mathWeight - plateStack.get(x) != 0 && mathWeight > plateStack.get(x)) {
                                        if(PlateDictionary.containsKey(weightToAdd)) {
                                            counterList++;
                                            mathWeight = mathWeight - plateStack.get(x);
                                            PlateDictionary.put(weightToAdd, PlateDictionary.get(weightToAdd) + 2);
                                            counter  =0;
                                            x = -1;
                                        }
                                        else {
                                            counterList++;
                                            mathWeight = mathWeight - plateStack.get(x);
                                            PlateDictionary.put(plateStack.get(x) /2, 2);
                                            counter = 0;
                                            x =-1;
                                        }
                                    }
                                }

                                if(found) {
                                    break;
                                }
                                else if (counter == plateStack.size()) {
                                    break;
                                }
                            }

                            if(found) {
                                Toast.makeText(getApplicationContext(),"Found a weight", Toast.LENGTH_SHORT).show();

                                tempArrayList.add("Clicked : "+ counterList++);
                                adapter.notifyDataSetChanged();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Please ensure plates are selected and the input weight is valid", Toast.LENGTH_SHORT).show();
                            }


                            break;
                        case 2:

                            Toast.makeText(getApplicationContext(),"No weight required, just barbell", Toast.LENGTH_SHORT).show();

                            break;
                        default:

                            Toast.makeText(getApplicationContext(),"UNRACKABLE BRO", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     *
     * @param input - the value requested to be partitioned
     * @param barbell - the value of the barbell (in numeric form)
     * @return - return 0 - no match found -> cannot be rackable
     *         - return 1 - match found, can be partitioned -> and requires plates to be loaded
     *         - return 2 - match found, cannot be partitioned -> as it is the same weight as the barbell
     *                      no plates are required -> return barbell weight
     */

    public int Validation (float input, float barbell) {
        if(input >= barbell && input % 2.5 == 0) {
            if (input > barbell) {
                return 1;
            }
            else {
                return 2;
            }
        }
        else {
            return 0;
        }
    }


    private int BarWeightDecider(String barbell)
    {
        switch (barbell){
            case "Standard Barbell - 20kg":
                return 20;
            case "Ez-Bar - 10kg":
                return 10;
            case "Hex Trap Bar - 25kg":
                return 25;
            case "Safety Squat Bar - 30kg":
                return 30;
            case "Squat Bar - 25kg":
                return 25;
            case "Axel Barbell -20kg":
                return 20;
            case "Log - 35kg":
                return 35;
            default:
                return 0;
        }
    }

    private ArrayList<Float> SelectedPlates () {

        ArrayList<Float> plateStack = new ArrayList<>();
        ConstraintLayout layout = findViewById(R.id.plateMathLayout);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox temp = (CheckBox) v;
                if(temp.isChecked()) {
                    try {
                        String checkboxValue = (String) temp.getText();
                        String cutValue = checkboxValue.substring(0, checkboxValue.length() - 2); //Removes "kg"
                        float result = Float.parseFloat(cutValue) * 2;
                        plateStack.add(result);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return plateStack;
    }

    public ArrayList<Float> reverseArrayList(ArrayList<Float> plateStack)
    {
        for (int i = 0; i < plateStack.size() / 2; i++) {
            Float temp = plateStack.get(i);
            plateStack.set(i, plateStack.get(plateStack.size() - i - 1));
            plateStack.set(plateStack.size() - i - 1, temp);
        }
        return plateStack;
    }


    //Checking whether there is a preset of plates to configure
    public ArrayList<Float> plateConfig () {

        ArrayList<Float> configList = new ArrayList<>();
        return configList;
    }

}
