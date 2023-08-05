package com.codequiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


public class LoginActivity extends AppCompatActivity {

    //creating variable for edit text, textview, button, progress bar and firebase auth.
    private TextInputEditText userNameEdt, passwordEdt;
    private Button loginBtn;
    private AppCompatButton googleSignIn;
    private TextView newUserTV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private static final String TAG = "quizapp";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private String clientId = "651132403714-bslgtkscklmlsc8hk6ii60e5gsgqdt7g.apps.googleusercontent.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initializing all our variables.
        userNameEdt = findViewById(R.id.idEmail);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        googleSignIn = findViewById(R.id.googleSignIn);
        newUserTV = findViewById(R.id.idTVNewUser);
        mAuth = FirebaseAuth.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                signIn();
            }
        });

        //adding click listner for our new user tv.
        newUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        //adding on click listener for our login button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hiding our progress bar.
                loadingPB.setVisibility(View.VISIBLE);
                //getting data from our edit text on below line.
                String email = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                //on below line validating the text input.
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                    return;
                }
                //on below line we are calling a sign in method and passing email and password to it.
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //on below line we are checking if the task is succes or not.
                        if (task.isSuccessful()) {
                            //on below line we are hiding our progress bar.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                            //on below line we are opening our mainactivity.
                            Intent i = new Intent(LoginActivity.this, StartScreenActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            //hiding our progress bar and displaying a toast message.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //in on start method checking if the user is already sign in.
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            //if the user is not null then we are opening a main activity on below line.
            Intent i = new Intent(LoginActivity.this, StartScreenActivity.class);
            startActivity(i);
            this.finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                // Google Sign In was successful, authenticate with Firebase
                Log.d(TAG, ""+task.getResult(ApiException.class));
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "firebaseAuthWithGoogle"+ e.getStatusCode());
                loadingPB.setVisibility(View.GONE);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "hello");
                        if (task.isSuccessful()) {
                            Intent i = new Intent(LoginActivity.this, StartScreenActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "didnt work didnt work");
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}