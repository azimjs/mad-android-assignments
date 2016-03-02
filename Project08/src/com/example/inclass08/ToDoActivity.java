/*
 * Assignment InClass08.
 * ToDoActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass08;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ToDoActivity extends Activity {

	public static final String USER_ID_KEY = "USER_ID";
	public static final String TODO_TASK_KEY = "TODO_TASK";
	public static final String TASK_OBJECT_KEY = "TodoTask";
	
	ParseQueryAdapter<ParseObject> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		loadToDoList();
	}

	protected void loadToDoList() {
		final ParseUser user = ParseUser.getCurrentUser();
		
		ParseQueryAdapter.QueryFactory<ParseObject> factory =
			     new ParseQueryAdapter.QueryFactory<ParseObject>() {
			       public ParseQuery create() {
			         ParseQuery query = new ParseQuery(ToDoActivity.TASK_OBJECT_KEY);
			         query.whereEqualTo(ToDoActivity.USER_ID_KEY, user.getObjectId());
			         return query;
			       }
			     };
		
		adapter = new ParseQueryAdapter<ParseObject>(this, factory);
		adapter.setTextKey(ToDoActivity.TODO_TASK_KEY);

		ListView listView = (ListView) findViewById(R.id.lvTodoList);
		listView.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		if (id == R.id.action_add) {
			Dialog dialog = buildNewTaskDialog();
			dialog.show();

			return true;
		} else if (id == R.id.action_logout) {
			ParseUser.logOutInBackground(new LogOutCallback() {

				@Override
				public void done(ParseException e) {
					Log.d("InClass08", "User was logged out");
					Intent intent = new Intent(ToDoActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
			});
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected Dialog buildNewTaskDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.activity_todo_dialog, null))
				// Add action buttons
				.setPositiveButton(R.string.todo_dlg_btn_ok_text,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								EditText etTask = (EditText) ((Dialog) dialog)
										.findViewById(R.id.et_todo_dlg_task);
								if("".equalsIgnoreCase(etTask.getText().toString().trim())){
									Toast.makeText(ToDoActivity.this, getString(R.string.todo_dlg_err_missing_inputs), Toast.LENGTH_LONG).show();
								}else{
									createNewTask(etTask.getText().toString());
								}
							}
						})
				.setNegativeButton(R.string.todo_dlg_btn_cancel_text,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						})
				.setTitle(getString(R.string.todo_dlg_new_task_title));
		return builder.create();
	}

	protected void createNewTask(final String taskName) {
		ParseUser user = ParseUser.getCurrentUser();
		
		final ParseObject task = new ParseObject(ToDoActivity.TASK_OBJECT_KEY);
		task.put(ToDoActivity.USER_ID_KEY, user.getObjectId());
		task.put(ToDoActivity.TODO_TASK_KEY, taskName);
		task.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if(null == e){
					String msg = getString(R.string.todo_dlg_successful_save_template).replace(getString(R.string.save_template_tag), taskName);
					Toast.makeText(ToDoActivity.this, msg, Toast.LENGTH_LONG).show();
					loadToDoList();
				}else{
					e.printStackTrace();
				}
			}
		});
	}
}