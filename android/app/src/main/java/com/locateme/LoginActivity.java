package com.locateme;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use client id for web instead of android to avoid audience issues
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.web_client_id))
                        .requestEmail()
                        .build())
                .build();

        Log.d(TAG, "webid:" + getString(R.string.web_client_id));
        googleApiClient.connect();

        setContentView(R.layout.activity_login);
        findViewById(R.id.close_login_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "req=" + requestCode + ", res=" + resultCode);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "req=" + requestCode + ", isSuccess=" + result.isSuccess());

            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                Log.d(TAG, "idToken=" + account.getIdToken());
                setResult(1, new Intent().putExtra("idToken", account.getIdToken()));
                finish();
            } else {
                Log.d(TAG, "status:" + result.getStatus());
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                Log.d(TAG, "Failed resolution", e);
                e.printStackTrace();
            }
        }
    }
}
