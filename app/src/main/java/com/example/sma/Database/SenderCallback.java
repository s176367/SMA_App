package com.example.sma.Database;


// @Author Gustav Kristensen s180077
public interface SenderCallback {
    void onSuccess();
    void onFailure(Exception exception);

}
