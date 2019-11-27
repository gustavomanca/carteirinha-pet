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

public class SignUpActivity extends AppCompatActivity {

    private Button backButton;
    private Button submitButton;

    EditText inputFirstName,
             inputLastName,
             inputUsername,
             inputEmail,
             inputPassword,
             inputPasswordConfirm;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseInit();

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
                Log.i("name", inputFirstName.getText().toString());

                User user = new User();

                user.setUid(UUID.randomUUID().toString());
                user.setFirstName(inputFirstName.getText().toString());
                user.setLastName(inputLastName.getText().toString());
                user.setUsername(inputUsername.getText().toString());
                user.setEmail(inputEmail.getText().toString());
                user.setPassword(inputPassword.getText().toString());

                DatabaseReference userRef = databaseReference.child("users").child(user.getUid());

                userRef.setValue(user);

                // Clean up the form, after click on submit btn
                cleanUpForm();

                Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                // Redirects to MainActivity(Login interface) after Sign Up
                openMainActivity();

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

    private void firebaseInit() {
        FirebaseApp.initializeApp( SignUpActivity.this );
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
