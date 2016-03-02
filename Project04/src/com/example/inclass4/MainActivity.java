/*
 * Assignment In Class 4.
 * MainActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */

package com.example.inclass4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Handler handler;
	ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final SeekBar sbComplexity = (SeekBar) findViewById(R.id.sbComplexity);
		sbComplexity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						((TextView) findViewById(R.id.tvComplexityCount))
								.setText(seekBar.getProgress() + "");
					}
				});

		((Button) findViewById(R.id.btnThreadTask)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						((TextView)findViewById(R.id.tvResultDisplay)).setText("");
						if(sbComplexity.getProgress()==0){
							Toast.makeText(MainActivity.this, getString(R.string.err_seekbar_min), Toast.LENGTH_LONG).show();
							return;
						}
						progressDialog = new ProgressDialog(MainActivity.this);
						progressDialog.setMessage(getString(R.string.dlg_progress_text));
						progressDialog.setMax(sbComplexity.getProgress());
						progressDialog.setCancelable(false);
						progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						
						handler = new Handler(new Handler.Callback() {				
							public boolean handleMessage(Message msg) {
								
								switch(msg.what){
								case ThreadTaskWork.STATE_START:
									progressDialog.show();
									break;
								case ThreadTaskWork.STATE_STEP:
									progressDialog.setProgress((Integer)msg.obj);
									break;
								case ThreadTaskWork.STATE_DONE:
									((TextView)findViewById(R.id.tvResultDisplay)).setText(Double.toString((Double)msg.obj));
									progressDialog.dismiss();
									break;
								}
										
								return false;				
							}			
						});
						
						new Thread(new ThreadTaskWork(sbComplexity.getProgress())).start();
					}
				});

		((Button) findViewById(R.id.btnAsyncTask)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						((TextView)findViewById(R.id.tvResultDisplay)).setText("");
						if(sbComplexity.getProgress()==0){
							Toast.makeText(MainActivity.this, getString(R.string.err_seekbar_min), Toast.LENGTH_LONG).show();
							return;
						}
						new AsyncTaskWork().execute();
					}
				});
		
		((Button)findViewById(R.id.btnPhotoSlideTask)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((TextView)findViewById(R.id.tvResultDisplay)).setText("");
				Intent slideShowIntent = new Intent(MainActivity.this,	SlideShowActivity.class);
				startActivity(slideShowIntent);
			}
		});
	}

	
	class ThreadTaskWork implements Runnable{
		static final int STATE_START = 0x00;
		static final int STATE_STEP = 0x01;
		static final int STATE_DONE = 0x02;
		
		private int count = 0;
		private Message msg = null;
		private double averageResult = 0F;
		
		ThreadTaskWork(int count){
			this.count = count;
		}
		
		@Override
		public void run() {
			msg = new Message();
			msg.what = STATE_START;
			msg.obj = 0;
			handler.sendMessage(msg);
			
			for(int i=0; i<count; i++){
				averageResult += HeavyWork.getNumber();
				msg = new Message();
				msg.what = STATE_STEP;
				msg.obj = i+1;
				handler.sendMessage(msg);
			}
			averageResult = averageResult/count;
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg = new Message();
			msg.what = STATE_DONE;
			msg.obj = averageResult;
			handler.sendMessage(msg);
		}
	}
	
	class AsyncTaskWork extends AsyncTask<Void, Integer, Void>{

		private double averageResult = 0F;
		private int count = 0;
		@Override
		protected Void doInBackground(Void... params) {
			for(int i=0; i<count; i++){
				averageResult += HeavyWork.getNumber();
				publishProgress(i+1);
			}
			publishProgress(count);
			averageResult = averageResult/count;
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			SeekBar sbComplexity = (SeekBar) findViewById(R.id.sbComplexity);
			count = sbComplexity.getProgress();
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage(getString(R.string.dlg_progress_text));
			progressDialog.setMax(sbComplexity.getProgress());
			progressDialog.setCancelable(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			((TextView)findViewById(R.id.tvResultDisplay)).setText(Double.toString(averageResult));
			progressDialog.dismiss();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			progressDialog.setProgress(values[0]);
		}
		
	}

}
