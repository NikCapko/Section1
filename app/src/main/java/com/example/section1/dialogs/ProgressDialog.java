package com.example.section1.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.section1.R;

public class ProgressDialog extends DialogFragment {

    public static final String TAG = "ProgressDialog.TAG";

    public static ProgressDialog newInstance() {
        ProgressDialog progressDialog = new ProgressDialog();
        return progressDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_progress, null);
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialog);
        builder.setView(view);

        return builder.create();
    }
}
