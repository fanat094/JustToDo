package com.yamschikov.dima.justtodo.google_auth;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

public interface GoogleInView {

    void onReadyActivityStartForResult(Intent intent, int i);

    void updateUser(FirebaseUser user);
}
