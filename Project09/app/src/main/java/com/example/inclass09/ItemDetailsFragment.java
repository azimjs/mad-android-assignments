/*
 * Assignment InClass09.
 * ItemDetailsFragment.java
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
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TASK_ID = "taskId";
    private static final String ARG_TASK_NAME = "taskName";


    IFragmentCallback mCallback = null;

    // TODO: Rename and change types of parameters
    private String taskId;
    private String taskName;
    private ParseObject task;


    @Override
    public void onAttach(Activity activity) {
        mCallback = (IFragmentCallback)activity;
        super.onAttach(activity);
    }

    // TODO: Rename and change types and number of parameters
    public static ItemDetailsFragment newInstance(String taskId, String taskName) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        args.putSerializable(ARG_TASK_NAME, taskName);
        fragment.setArguments(args);
        return fragment;
    }

    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskId = getArguments().getString(ARG_TASK_ID);
            taskName = getArguments().getString(ARG_TASK_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_details, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((TextView)getView().findViewById(R.id.details_tvTaskName)).setText(task.getString(TodoFragment.TODO_TASK_KEY));

        ((Button)getView().findViewById(R.id.btn_task_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.d("InClass09", "Successfully deleted object");
                        mCallback.returnToDoFragment();
                    }
                });
            }
        });
    }


    public void setTask(ParseObject task){
        this.task = task;
    }
}
