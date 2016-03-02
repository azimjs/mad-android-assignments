/*
 * Assignment In Class 2b.
 * MainActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */

package com.example.inclass2b;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnPerformOpt = (Button) findViewById(R.id.btnPerformOperation);

		btnPerformOpt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et1 = (EditText) findViewById(R.id.editText1);
				EditText et2 = (EditText) findViewById(R.id.editText2);
				TextView tv = (TextView)findViewById(R.id.textView1);
				RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
				
				if (R.id.rbClearAll == radioGroup.getCheckedRadioButtonId()) {
					et1.setText(getString(R.string.text_default_input));
					et2.setText(getString(R.string.text_default_input));
					return;
				}
				
				String resultText = getString(R.string.text_result) + "  ";
				// Basic error checking on input values
				Float opt1 = 0F;
				Float opt2 = 0F;
				try {
					opt1 = Float.parseFloat(et1.getText().toString());
					opt2 = Float.parseFloat(et2.getText().toString());

				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(), getString(R.string.text_err_NaN_input), Toast.LENGTH_LONG).show();
					return;
				}

				// Get the selection operation from the radiogroup
				

				
				if (R.id.rbAdd == radioGroup.getCheckedRadioButtonId()) {
					resultText += Float.toString(opt1 + opt2);
				}

				if (R.id.rbSubtract == radioGroup.getCheckedRadioButtonId()) {
					resultText += Float.toString(opt1 - opt2);
				}

				if (R.id.rbMultiply == radioGroup.getCheckedRadioButtonId()) {
					resultText += Float.toString(opt1 * opt2);
				}

				if (R.id.rbDivide == radioGroup.getCheckedRadioButtonId()) {
					// Need to check for Divide by zero
					if(opt2 == 0F){
						//output divide by zero error
						Toast.makeText(getApplicationContext(), getString(R.string.text_err_divide_by_zero), Toast.LENGTH_LONG).show();
						return;
					}else{
						resultText += Float.toString(opt1 / opt2);
					}

				}
				
				

				
				
				
				tv.setText(resultText);
			}
		});
	}

}
