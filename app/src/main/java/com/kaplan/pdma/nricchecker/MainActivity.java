package com.kaplan.pdma.nricchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String check_digit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://developer.android.com/guide/topics/ui/controls/spinner.html
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.check_digits, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        final EditText editText = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberText = editText.getText().toString();
                if(numberText.length() != 7) {
                    Toast.makeText(MainActivity.this, "Input must be 7-digit", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        int number = Integer.parseInt(numberText);
                        int[] weights = {2,3,4,5,6,7,2};
                        int sum = 0;
                        for(int i = 0; i < weights.length; i++) {
                            int d = number % 10; //extract last digit
                            sum += weights[i] * d;
                            number = number / 10; //remove last digit;
                        }
                        int cd = 11 - (sum % 11);
                        String[] letters = {"A","B", "C","D","E","F","G","H", "I", "Z", "J"};
                        String letter = letters[cd-1];

                        if(letter.equalsIgnoreCase(check_digit)) {
                            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }

                    } catch(NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Input must be numbers only", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        check_digit = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
