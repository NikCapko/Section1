package com.example.section1.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.section1.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ErrorData {

    private static final int ERROR_NETWORK = 100001;
    private static final int ERROR_SOCKET_TIMEOUT = 100002;
    private static final int ERROR_UNEXPECTED = 100003;
    private static final int ERROR_HTTP = 100004;

    private int errorCode;
    private String errorMessage;

    public ErrorData(Context context, Throwable throwable) {
        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
            if (!isNetworkAvailable(context)) {
                errorCode = ErrorData.ERROR_NETWORK;
                errorMessage = context.getString(R.string.message_error_connect);
            } else {
                errorCode = ErrorData.ERROR_UNEXPECTED;
                errorMessage = context.getString(R.string.message_error_default);
            }
        } else if (throwable instanceof SocketTimeoutException) {
            errorCode = ErrorData.ERROR_SOCKET_TIMEOUT;
            errorMessage = context.getString(R.string.message_error_socket);
        } else if (throwable instanceof HttpException) {
            String message = null;
            if (((HttpException) throwable).response() != null) {
                if (((HttpException) throwable).response().message() != null) {
                    message = ((HttpException) throwable).response().code() + " " + ((HttpException) throwable).response().message();
                } else {
                    message = context.getString(R.string.message_error_socket);
                }
            } else {
                message = context.getString(R.string.message_error_socket);
            }
            errorCode = ErrorData.ERROR_HTTP;
            errorMessage = message;
        } else {
            errorCode = ErrorData.ERROR_UNEXPECTED;
            errorMessage = context.getString(R.string.message_error_socket);
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}