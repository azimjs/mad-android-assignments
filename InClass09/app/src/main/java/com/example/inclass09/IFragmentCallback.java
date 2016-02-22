/*
 * Assignment InClass09.
 * IFragmentCallback.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass09;

import android.content.Context;

import com.parse.ParseObject;

/**
 * Created by mhofmeister on 6/18/2015.
 */
public interface IFragmentCallback {
    void showLoginFragment();
    void showSignupFragment();
    void showToDoFragment();
    void returnToDoFragment();
    void showToDoDetailFragment(ParseObject task);
    Context getAppContext();
}
