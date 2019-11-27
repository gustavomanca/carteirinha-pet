package app.manca.carteirinhapet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.model.User;
import app.manca.carteirinhapet.config.FirebaseSettings;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference firebaseRef;

    private Button backButton;
    private Button submitButton;
    private User user;
    private FirebaseAuth auth;

    EditText inputFirstName,
             inputLastName,
             inputUsername,
             inputEmail,
             inputPassword,
             inputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseRef = FirebaseSettings.getFirebase();

        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputPasswordConfirm = findViewById(R.id.inputPasswordConfirm);

        backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        submitButton = findViewById(R.id.btnSignUp);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User();
                user.setFirstName(inputFirstName.getText().toString());
                user.setLastName(inputLastName.getText().toString());
                user.setUsername(inputUsername.getText().toString());
                user.setEmail(inputEmail.getText().toString());
                user.setPassword(inputPassword.getText().toString());
                userRegister();

                // Redirects to MainActivity(Login interface) after Sign Up
                openMainActivity();
            }
        });
    }

    private void userRegister() {

        auth = FirebaseSettings.getFirebaseAuthentication();

        auth.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ) {

                    Toast.makeText(
                            SignUpActivity.this,
                            "Usuário cadastrado com sucesso!",
                            Toast.LENGTH_LONG
                    ).show();

                    FirebaseUser firebaseUser = task.getResult().getUser();
                    user.setId( firebaseUser.getUid() );
                    user.save();

                    cleanUpForm();

                } else {

                    Toast.makeText(
                            SignUpActivity.this,
                            "Erro ao cadastrar usuário!",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private void cleanUpForm() {
        inputFirstName.setText("");
        inputLastName.setText("");
        inputUsername.setText("");
        inputEmail.setText("");
        inputPassword.setText("");
        inputPasswordConfirm.setText("");
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
