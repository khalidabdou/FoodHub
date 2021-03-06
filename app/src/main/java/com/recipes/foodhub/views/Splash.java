package com.recipes.foodhub.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.recipes.foodhub.R;
import com.recipes.foodhub.data.SharedPref;
import com.recipes.foodhub.model.user;
import com.recipes.foodhub.presenter.doInBackgroundTask;
import com.recipes.foodhub.presenter.user_manager;
import com.wang.avi.AVLoadingIndicatorView;

public class Splash extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient mGoogleApiClient;
    public static Context mContext;
    private user_manager manager_user;
    private user us;
    private int saved;
    private SharedPref sharedPref;
    com.wang.avi.AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
        avi=findViewById(R.id.aviMain);
        sharedPref=new SharedPref(mContext);
        saved=sharedPref.getInt("saved");
        manager_user=new user_manager(mContext);
        mAuth = FirebaseAuth.getInstance();
        authStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if (user!=null){
                us=new user();
                if (saved==1){
                    startActivity(new Intent(Splash.this,MainActivity.class));
                    Toast.makeText(Splash.this, "deja", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    us.setEmail(user.getEmail());
                    us.setImage_url(user.getPhotoUrl().toString());
                    us.setName(user.getDisplayName());
                    us.setUserid(user.getUid());
                    us.setFollowers(0);
                    us.setFollowing(0);

                    manager_user.adduserShered(us);
                    Toast.makeText(Splash.this, "saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Splash.this,MainActivity.class));
                }catch (Exception ex){
                    Toast.makeText(mContext, ex.toString(), Toast.LENGTH_SHORT).show();
                }

            }else Toast.makeText(Splash.this, "user null", Toast.LENGTH_SHORT).show();
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });


    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);


    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(mContext, "success auth", Toast.LENGTH_SHORT).show();
                avi.show();

            }else {
                Toast.makeText(mContext, result.getStatus().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else  {
                            Toast.makeText(getApplicationContext(), "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(mContext, "er" + connectionResult, Toast.LENGTH_SHORT).show();
    }

}
