package app.manca.carteirinhapet.activity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.config.FirebaseSettings;
import app.manca.carteirinhapet.model.User;

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton, signInButton;
    private EditText email, password;
    private User user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkIfUserIsLogged();

        signUpButton = findViewById(R.id.loginSignUpBtn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });

        email = findViewById( R.id.loginEmail );
        password = findViewById( R.id.loginPassword );
        signInButton = findViewById( R.id.signInButton );

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User();

                user.setEmail( email.getText().toString() );
                user.setPassword( password.getText().toString() );

                handleEmptyFields();
            }
        });
    }

    private void handleEmptyFields() {

        String errorMessage = "Este campo é obrigatório!";


        if ( TextUtils.isEmpty( email.getText() ) ) {

            email.setError( errorMessage );

        } else if ( TextUtils.isEmpty( password.getText() ) )  {

            password.setError( errorMessage );

        } else {

            email.setError( null );
            password.setError( null );

            loginValidation();
        }
    }

    private void checkIfUserIsLogged() {

        auth = FirebaseSettings.getFirebaseAuthentication();

        if ( auth.getCurrentUser() != null ) {

            openLoggedActivity();
        }
    }

    private void loginValidation() {

        auth = FirebaseSettings.getFirebaseAuthentication();
        auth.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ) {

                    openLoggedActivity();

                    Toast.makeText(
                            LoginActivity.this,
                            "Sucesso ao fazer login!",
                            Toast.LENGTH_LONG
                    ).show();

                } else {

                    Toast.makeText(
                            LoginActivity.this,
                            "Erro ao fazer login! Verifique seu e-mail e senha!",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private void openLoggedActivity() {

        Intent intent = new Intent( LoginActivity.this, MainActivity.class );
        startActivity( intent );
        finish();
    }

    public void openSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}
