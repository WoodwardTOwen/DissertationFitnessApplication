package woodward.owen.fitnessapplication.plate_math_calculator_package;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;


public class PlateMathCalcActivity extends AppCompatActivity {
    //region Variables

    private EditText mInputWeightValueTb;
    private Spinner mBarbellSelectorCb;
    private Button mCalculateResultBnt;
    private ListView listView;
    private ArrayList<BarbellType> barbellList;

   //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_math_calc);
        mBarbellSelectorCb = findViewById(R.id.barbellSelectorCb);
        listView = findViewById(R.id.resultsListView);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        getSupportActionBar().setTitle("Plate Math Calculator");

        setSpinnerData();
        CheckSelectedItem();
    }

    private void setSpinnerData() {
        loadBarbellData();

        //Create adapter to place to create bridge between data and View
        ArrayAdapter<BarbellType> barbellTypeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, barbellList);
        barbellTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBarbellSelectorCb.setAdapter(barbellTypeArrayAdapter);
    }

    private void saveBarbellData() {
        //Saves data to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(barbellList);
        editor.putString("Barbell List", json);
        editor.apply();

    }

    private void loadBarbellData() {
        // load the data to be displayed within the spinner from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Barbell List", null);
        Type type = new TypeToken<ArrayList<BarbellType>>() {}.getType();
        barbellList = gson.fromJson(json, type);

        //If a saved file cannot be found, then create default values for spinner
        if(barbellList == null) {
            barbellList = new ArrayList<>();

            BarbellType barbellStandard = new BarbellType("Standard Barbell 20kg", 20);
            BarbellType barbellHex = new BarbellType("Hex Bar 25kg", 25);
            BarbellType barbellSafety = new BarbellType("Safety Squat Bar 30kg", 30);
            BarbellType barbellEz = new BarbellType("Ez Curl Bar 10kg", 10);

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

        //bringing back the data if a new barbell has been made -> apply the new changes
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
                Intent intent = new Intent(this, PlateMathBarbellEditPop.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("barbellList", barbellList);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivityForResult(intent, 1);
                return true;
            case R.id.item2:
                Intent intentDelete = new Intent(this, PlateMathBarbellDeletePopUp.class);
                Bundle bundleDelete = new Bundle();
                bundleDelete.putParcelableArrayList("barbellList", barbellList);
                intentDelete.putExtras(bundleDelete);
                intentDelete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivityForResult(intentDelete, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void calcPlates (View view) {
        mCalculateResultBnt = findViewById(R.id.calculatePMCBnt);
        mInputWeightValueTb = findViewById(R.id.inputWeightTxtBox);
        closeKeyBoard();

                try {

                    float inputVal = Float.parseFloat(mInputWeightValueTb.getText().toString());
                    BarbellType barbellObj = (BarbellType) mBarbellSelectorCb.getSelectedItem();
                    int barbell = (barbellObj.getBarbellWeight());


                    int response = Validation(inputVal, barbell);

                    switch(response) {
                        case 1:

                            float mathWeight = inputVal - barbell;
                            float secondTime = mathWeight;
                            ArrayList<Float> plateStack; //Might throw n error here -> change back to New ArrayList if error is thrown
                            Map<Float,Integer> PlateDictionary = new LinkedHashMap<>();
                            List<Map.Entry<Float, Integer>> mListofPlateDictionaryItems;

                            plateStack = SelectedPlates();
                            plateStack = reverseArrayList(plateStack);

                            boolean found = false;
                            int doubleChecker =0;

                            while(true) {
                                int counter = 0;

                                for(int x = 0; x <plateStack.size(); x++) {
                                    counter++;
                                    float weightToAdd = plateStack.get(x) /2;
                                    if(mathWeight / plateStack.get(x) == 1) {

                                        if(PlateDictionary.containsKey(weightToAdd)){
                                            PlateDictionary.put(weightToAdd, PlateDictionary.get(weightToAdd) + 2);
                                            found = true;
                                            counter = 0;
                                            break;
                                        }
                                        else {
                                            PlateDictionary.put(weightToAdd, 2);
                                            found = true;
                                            counter = 0;
                                            break;
                                        }
                                    }
                                    else if (mathWeight - plateStack.get(x) != 0 && mathWeight > plateStack.get(x)) {
                                        if(PlateDictionary.containsKey(weightToAdd)) {
                                            mathWeight = mathWeight - plateStack.get(x);
                                            PlateDictionary.put(weightToAdd, PlateDictionary.get(weightToAdd) + 2);
                                            counter  =0;
                                            x = -1;
                                        }
                                        else {
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
                                    mathWeight = secondTime;
                                    PlateDictionary.clear();
                                    Collections.reverse(plateStack);
                                    doubleChecker++;
                                    if(doubleChecker == 2)
                                    {
                                        break;
                                    }
                                }
                            }
                            if(found) {
                                Toast.makeText(getApplicationContext(),"Found a weight", Toast.LENGTH_SHORT).show();
                                PlateMathCustomAdapter mPlateDictionaryAdapter;
                                mListofPlateDictionaryItems = new ArrayList<>(PlateDictionary.entrySet());
                                mPlateDictionaryAdapter = new PlateMathCustomAdapter(getApplicationContext(), mListofPlateDictionaryItems);
                                listView.setAdapter(mPlateDictionaryAdapter);
                            }
                            else {
                                listView.setAdapter(null);
                                Toast.makeText(getApplicationContext(),"Please ensure plates are selected and the input weight is valid", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2:
                            listView.setAdapter(null);
                            Toast.makeText(getApplicationContext(),"No weight required, just barbell", Toast.LENGTH_LONG).show();

                            break;
                        case 3:
                            listView.setAdapter(null);
                            Toast.makeText(getApplicationContext(),"The Input needs to be 1000kg or under", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            listView.setAdapter(null);
                            Toast.makeText(getApplicationContext(),"The Desired Weight Cannot be Loaded", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

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
            if(input > 1000){
                return 3;
            }
            else if (input > barbell) {
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

    public ArrayList<Float> reverseArrayList(ArrayList<Float> plateStack) {
        for (int i = 0; i < plateStack.size() / 2; i++) {
            Float temp = plateStack.get(i);
            plateStack.set(i, plateStack.get(plateStack.size() - i - 1));
            plateStack.set(plateStack.size() - i - 1, temp);
        }
        return plateStack;
    }

    public void CheckSelectedItem() {
        mBarbellSelectorCb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BarbellType selectedItemText = (BarbellType) parent.getItemAtPosition(position);
                ToastMsgSuccess(selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void ToastMsgSuccess (BarbellType barbell) {
        String name = barbell.getBarbellName();
        int weight = barbell.getBarbellWeight();
        Toast.makeText(this, "Barbell: " + name + "\nWeight: " + weight + "kg", Toast.LENGTH_SHORT).show();
    }

    private void closeKeyBoard () {
        InputMethodManager input = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert input != null;
        input.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
