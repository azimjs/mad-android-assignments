/*
 * Assignment InClass09.
 * SignupFragment.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass09;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    IFragmentCallback mCallback = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (IFragmentCallback)activity;
    }

    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Button) getActivity().findViewById(R.id.btn_signup_signup))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!isValid()) {
                            Toast.makeText(
                                    mCallback.getAppContext(),
                                    getString(R.string.signup_err_missing_inputs),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!passwordsMatch()) {
                            Toast.makeText(
                                    mCallback.getAppContext(),
                                    getString(R.string.signup_err_pass_mismatch),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        registerNewUser();

                    }
                });

        ((Button) getActivity().findViewById(R.id.btn_signup_cancel))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mCallback.showLoginFragment();

                    }
                });
    }

    protected void registerNewUser() {
        EditText etName = (EditText)getView().findViewById(R.id.et_signup_name);
        EditText etEmail = (EditText)getView().findViewById(R.id.et_signup_email);
        EditText etPassword = (EditText)getView().findViewById(R.id.et_signup_password);

        ParseUser user = new ParseUser();
        user.setUsername(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.add("Name", etName.getText().toString());
        user.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                if (null == e) {
                    Toast.makeText(mCallback.getAppContext(),
                            getString(R.string.signup_success_text),
                            Toast.LENGTH_LONG).show();

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startTodoActivity();
                        }
                    }, 3000);
                } else {
                    if (e.getCode() == e.EMAIL_TAKEN) {
                        Toast.makeText(mCallback.getAppContext(), getString(R.string.signup_err_email_taken),
                                Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(mCallback.getAppContext(), e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    protected void startTodoActivity() {
        mCallback.showToDoFragment();
    }

    protected boolean isValid() {
        EditText etName = (EditText)getView().findViewById(R.id.et_signup_name);
        EditText etEmail = (EditText)getView().findViewById(R.id.et_signup_email);
        EditText etPassword = (EditText)getView().findViewById(R.id.et_signup_password);
        EditText etConfPassword = (EditText)getView().findViewById(R.id.et_signup_conf_password);

        if ("".equalsIgnoreCase(etName.getText().toString().trim())
                || "".equalsIgnoreCase(etEmail.getText().toString().trim())
                || "".equalsIgnoreCase(etPassword.getText().toString().trim())
                || "".equalsIgnoreCase(etConfPassword.getText().toString()
                .trim())) {
            return false;
        } else {
            if (!etPassword.getText().toString()
                    .equalsIgnoreCase(etConfPassword.getText().toString())) {
                return false;
            }
            return true;
        }
    }

    protected boolean passwordsMatch() {
        EditText etPassword = (EditText)getView().findViewById(R.id.et_signup_password);
        EditText etConfPassword = (EditText)getView().findViewById(R.id.et_signup_conf_password);
        if (!etPassword.getText().toString()
                .equalsIgnoreCase(etConfPassword.getText().toString())) {
            return false;
        }
        return true;
    }


}
