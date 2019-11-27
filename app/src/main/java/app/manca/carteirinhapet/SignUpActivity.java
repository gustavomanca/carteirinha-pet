package app.manca.carteirinhapet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import app.manca.carteirinhapet.config.FirebaseSettings;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference firebaseRef;

    private Button backButton;
    private Button submitButton;

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

                userRegister();

                // Redirects to MainActivity(Login interface) after Sign Up
                openMainActivity();
            }
        });
    }

    private void userRegister() {

        User user = new User();

        user.setUid(UUID.randomUUID().toString());
        user.setFirstName(inputFirstName.getText().toString());
        user.setLastName(inputLastName.getText().toString());
        user.setUsername(inputUsername.getText().toString());
        user.setEmail(inputEmail.getText().toString());
        user.setPassword(inputPassword.getText().toString());

        firebaseRef.child("users").child(user.getUid()).setValue(user);

        // Clean up the form, after click on submit btn
        cleanUpForm();

        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
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
