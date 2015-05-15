package com.ftinc.lol52.api;

import android.content.Context;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.ftinc.lol52.R;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import timber.log.Timber;

/**
 * Created by r0adkll on 1/2/15.
 */
public class AppErrorHandler implements ErrorHandler {

    private Context mCtx;

    /**
     * Constructor
     */
    public AppErrorHandler(Context ctx){
        mCtx = ctx;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        String errorDescription;

        if (cause.getKind() == RetrofitError.Kind.NETWORK) {
            errorDescription = mCtx.getString(R.string.error_network);
        } else {
            if (cause.getResponse() == null) {
                errorDescription = mCtx.getString(R.string.error_no_response);
            } else {

                // Error message handling - return a simple error to Retrofit handlers..
                try {
                    LolError errorResponse = (LolError) cause.getBodyAs(LolError.class);
                    errorDescription = errorResponse.error;
                } catch (Exception ex) {
                    try {
                        errorDescription = (String) cause.getBodyAs(String.class);
                    }catch (Exception ex2){

                        try {
                            errorDescription = mCtx.getString(R.string.error_network_http_error, cause.getResponse().getStatus());
                        } catch (Exception ex3) {
                            Timber.e("handleError: " + ex2.getLocalizedMessage());
                            errorDescription = mCtx.getString(R.string.error_unknown);
                        }
                    }
                }
            }
        }

        Timber.e("API Error [%s][%s]", errorDescription, cause.getLocalizedMessage());
        return new Exception(errorDescription, cause);
    }

    @JsonObject
    public static class LolError {
        @JsonField String status;
        @JsonField String error;
    }
}