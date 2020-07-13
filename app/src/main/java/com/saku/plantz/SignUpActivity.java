package com.saku.plantz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText emailField,usernameField,passwordField,confirmPassword;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailField = findViewById(R.id.email);
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        signUpBtn = findViewById(R.id.signUp_btn);

        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailField.getText().toString();
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                String confirm_Password = confirmPassword.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username) || TextUtils.isEmpty(confirm_Password)) {
                    Toast.makeText(SignUpActivity.this,"Fields are empty", Toast.LENGTH_LONG).show();
                }

                if (!password.equals(confirm_Password)) {
                    Toast.makeText(SignUpActivity.this,"Passwords not match", Toast.LENGTH_LONG).show();
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,"Authentication faild" + task.getException(),Toast.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                            finish();
                        }
                    }
                });

            }
        });

    }
}