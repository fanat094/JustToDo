package com.yamschikov.dima.justtodo.faceboook_auth;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

public interface FacebookView {

    void onReadyActivityStartForResult(Intent intent, int i);

    void updateUser(FirebaseUser user);
}
