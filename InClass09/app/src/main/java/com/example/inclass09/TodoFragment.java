/*
 * Assignment InClass09.
 * TodoFragment.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass09;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoFragment extends Fragment {

    public static final String USER_ID_KEY = "USER_ID";
    public static final String TODO_TASK_KEY = "TODO_TASK";
    public static final String TASK_OBJECT_KEY = "TodoTask";

    ParseQueryAdapter<ParseObject> adapter;
    IFragmentCallback mCallback = null;


    public static TodoFragment newInstance(String param1, String param2) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public TodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        mCallback = (IFragmentCallback)activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadToDoList();
    }

    protected void loadToDoList() {
        final ParseUser user = ParseUser.getCurrentUser();

        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery create() {
                        ParseQuery query = new ParseQuery(TodoFragment.TASK_OBJECT_KEY);
                        query.whereEqualTo(TodoFragment.USER_ID_KEY, user.getObjectId());
                        return query;
                    }
                };

        adapter = new ParseQueryAdapter<ParseObject>(mCallback.getAppContext(), factory);
        adapter.setTextKey(TodoFragment.TODO_TASK_KEY);

        ListView listView = (ListView) getView().findViewById(R.id.lvTodoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.showToDoDetailFragment(adapter.getItem(position));
            }
        });

    }

    public void showNewTaskDialog(){
        Dialog dialog = buildNewTaskDialog();
        dialog.show();
    }

    protected Dialog buildNewTaskDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mCallback.getAppContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

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
                                    Toast.makeText(mCallback.getAppContext(), getString(R.string.todo_dlg_err_missing_inputs), Toast.LENGTH_LONG).show();
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

        final ParseObject task = new ParseObject(TodoFragment.TASK_OBJECT_KEY);
        task.put(TodoFragment.USER_ID_KEY, user.getObjectId());
        task.put(TodoFragment.TODO_TASK_KEY, taskName);
        task.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (null == e) {
                    String msg = getString(R.string.todo_dlg_successful_save_template).replace(getString(R.string.save_template_tag), taskName);
                    Toast.makeText(mCallback.getAppContext(), msg, Toast.LENGTH_LONG).show();
                    loadToDoList();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
