package com.example.beerpongclub.SignIn;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.beerpongclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private SignInViewModel signinViewModel;

    private FirebaseAuth mAuth;
    private EditText EMail;
    private EditText Username;
    private EditText password;
    private EditText confirm_password;
    private Button createAccButton;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signinViewModel = ViewModelProviders.of(this, new SigninViewModelFactory())
                .get(SignInViewModel.class);

        //Getting Views
        EMail = (EditText) findViewById(R.id.Email);
        Username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.password_repeat);
        createAccButton = (Button) findViewById(R.id.button_createUser);

        //Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signinViewModel.getSignInFormState().observe(this, new Observer<SignInFormState>() {
            //observes every Error entry of every Input
            @Override
            public void onChanged(SignInFormState signInFormState) {
                if(signInFormState == null) {
                    return;
                }
                createAccButton.setEnabled(signInFormState.isDataValid());
                if(signInFormState.getEmailError() != null) {
                    EMail.setError(getString(signInFormState.getEmailError()));
                }
                if(signInFormState.getPasswordError() != null) {
                    password.setError(getString(signInFormState.getPasswordError()));
                }
                if(signInFormState.getUsernameError() != null) {
                    Username.setError(getString(signInFormState.getUsernameError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                signinViewModel.signInDataChanged(EMail.getText().toString(), password.getText().toString(), confirm_password.getText().toString(), Username.getText().toString());
            }
        };
        Username.addTextChangedListener(afterTextChangedListener);
        EMail.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);
        confirm_password.addTextChangedListener(afterTextChangedListener);

    }

    public void createAccount(View view) {
        String pass_str = password.getText().toString();
        String rep_pass = confirm_password.getText().toString();
        if (!pass_str.equals(rep_pass)) {
            confirm_password.setError(getString(R.string.password_missmatch_signIn));
        } else {

            mAuth.createUserWithEmailAndPassword(EMail.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user.getDisplayName() != null) {
                                    Toast.makeText(SignInActivity.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(SignInActivity.this, "User == null", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                switch (errorCode) {
                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        EMail.setError(task.getException().getMessage());
                                }
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }


    }
}
