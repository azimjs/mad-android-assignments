/*
 * Assignment InClass09.
 * MainActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass09;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.inclass09.R;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends Activity implements IFragmentCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.fragment_container, new SplashFragment(), "splash_fragment").commit();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ParseUser user = ParseUser.getCurrentUser();
                if(user == null ) {
                    showLoginFragment();
                }else{
                    showToDoFragment();
                }
            }
        }, 5000);
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
            TodoFragment todo = (TodoFragment)getFragmentManager().findFragmentByTag("todo_fragment");
            todo.showNewTaskDialog();

            return true;
        } else if (id == R.id.action_logout) {
            ParseUser.logOutInBackground(new LogOutCallback() {

                @Override
                public void done(ParseException e) {
                    Log.d("InClass09", "User was logged out");
                    showLoginFragment();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoginFragment() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment(), "login_fragment").commit();
        Log.d("InClass09", "showing login fragment");
    }

    @Override
    public void showSignupFragment() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SignupFragment(), "signup_fragment").commit();
        Log.d("InClass09", "showing Signup fragment");
    }

    @Override
    public void showToDoFragment() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodoFragment(), "todo_fragment").commit();
        Log.d("InClass09", "showing todo fragment");
    }

    @Override
    public void returnToDoFragment() {
        getFragmentManager().popBackStack();
        //getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodoFragment(), "todo_fragment").commit();
        Log.d("InClass09", "returning to todo fragment");
    }

    @Override
    public void showToDoDetailFragment(ParseObject task) {
        ItemDetailsFragment detailsFragment = new ItemDetailsFragment();
        detailsFragment.setTask(task);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, detailsFragment, "todo_detail_fragment").addToBackStack(null).commit();
        Log.d("InClass09", "showing todo detail fragment");
    }

    @Override
    public Context getAppContext(){
        return this;
    }


    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }
}
