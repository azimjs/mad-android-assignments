/*
 * Class Assignment 2A
 * MainActivity.java
 * Students { Michael Hofmeister, Azim Javedali Saiyed, Timothy Shay }
 */

package com.example.inclass2a;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        findViewById(R.id.addButton).setOnClickListener(this);
        findViewById(R.id.subtractButton).setOnClickListener(this);
        findViewById(R.id.multiplyButton).setOnClickListener(this);
        findViewById(R.id.divideButton).setOnClickListener(this);
        findViewById(R.id.clearAllButton).setOnClickListener(this);        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		float et1=0,et2=0,result;
		EditText edt1 =(EditText) findViewById(R.id.editText1);
		EditText edt2 =(EditText) findViewById(R.id.editText2);
		
		TextView tvr = (TextView) findViewById(R.id.textView1);
		
		if(v.getId() == R.id.clearAllButton)
		{
			edt1.setText("");
			edt2.setText("");
			tvr.setText(getString(R.string.text_result));
			return;
		}

		try{			
			et1 = Float.valueOf(edt1.getText().toString());
			et2 = Float.valueOf(edt2.getText().toString());
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),getString(R.string.text_err_nan), Toast.LENGTH_LONG).show();
			return;
		}			
		
		if(v.getId() == R.id.addButton)
		{
			result = (et1 + et2);
			tvr.setText(getString(R.string.text_result) + " " + result);			
		}
		else if(v.getId() == R.id.subtractButton)
		{
			result = (et1 - et2);
			tvr.setText(getString(R.string.text_result) + " " + result);			
		}
		else if(v.getId() == R.id.multiplyButton)
		{
			result = (et1 * et2);
			tvr.setText(getString(R.string.text_result) + " " + result);			
		}
		else if(v.getId() == R.id.divideButton)
		{
			result = (et1 / et2);
			if(et2==0F)
			{
				Toast.makeText(getApplicationContext(),getString(R.string.text_err_dbyz), Toast.LENGTH_LONG).show();
				return;				
			}
			tvr.setText(getString(R.string.text_result) + " " + result);			
		}
		

	}
}
