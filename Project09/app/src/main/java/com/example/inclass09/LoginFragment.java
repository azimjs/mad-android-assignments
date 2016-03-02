/*
 * Assignment InClass09.
 * LoginFragment.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass09;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    IFragmentCallback mCallback = null;

    @Override
    public void onAttach(Activity activity) {
        mCallback = (IFragmentCallback)activity;
        super.onAttach(activity);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
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


        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Button)getActivity().findViewById(R.id.btn_log_create)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCallback.showSignupFragment();
            }
        });

        ((Button)getActivity().findViewById(R.id.btn_log_login)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isValid()) {
                    Toast.makeText(mCallback.getAppContext(), getString(R.string.logon_err_missing_inputs), Toast.LENGTH_LONG).show();
                } else {
                    EditText etEmail = (EditText)getActivity().findViewById(R.id.et_log_email);
                    EditText etPassword = (EditText)getActivity().findViewById(R.id.et_log_password);

                    ParseUser.logInInBackground(etEmail.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                Log.d("InClass09", "User successfully logged in");
                                mCallback.showToDoFragment();
                            } else {
                                Toast.makeText(mCallback.getAppContext(), getString(R.string.logon_err_failed_signin), Toast.LENGTH_LONG).
                                        show();
                            }
                        }
                    });
                }
            }
        });

    }

    protected boolean isValid(){
        EditText etEmail = (EditText)getView().findViewById(R.id.et_log_email);
        EditText etPassword = (EditText)getView().findViewById(R.id.et_log_password);

        if("".equalsIgnoreCase(etEmail.getText().toString().trim()) ||
                "".equalsIgnoreCase(etPassword.getText().toString().trim())){
            return false;
        }else{
            return true;
        }
    }


}
