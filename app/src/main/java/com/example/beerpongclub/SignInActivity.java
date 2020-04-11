package com.example.beerpongclub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText EMail;
    private EditText Username;
    private EditText password;
    private EditText confirm_password;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Getting Views
        EMail = (EditText) findViewById(R.id.Email);
        Username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.password_repeat);

        //Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void createAccount(View view) {
        String pass_str= password.getText().toString();
        String rep_pass = confirm_password.getText().toString();
        if(!pass_str.equals(rep_pass)) {
            Toast.makeText(SignInActivity.this, "Password does not match! ", Toast.LENGTH_LONG).show();
            Toast.makeText(SignInActivity.this, pass_str, Toast.LENGTH_LONG).show();
            Toast.makeText(SignInActivity.this, rep_pass, Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(EMail.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.
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
