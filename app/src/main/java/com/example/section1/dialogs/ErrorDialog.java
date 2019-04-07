package com.example.section1.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.section1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ErrorDialog extends DialogFragment {

    private static final String MESSAGE_DATA = "ErrorDialog.MESSAGE_DATA";
    public static final String TAG = "ErrorDialog.TAG";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private String mMessage;
    private Unbinder unbinder;

    private OnDialogResultListener mListener;

    public static ErrorDialog newInstance(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_DATA, message);
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.setArguments(bundle);
        return errorDialog;
    }

    public void setOnResultListener(OnDialogResultListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
    }

    private void getArgs() {
        if (getArguments() != null) {
            mMessage = getArguments().getString(MESSAGE_DATA);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_error, null);
        unbinder = ButterKnife.bind(this, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        setCancelable(false);
        tvDescription.setText(mMessage);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onResultDialog(Activity.RESULT_OK);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


    public interface OnDialogResultListener {
        void onResultDialog(int statusCode);
    }
}
