package app.manca.carteirinhapet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.config.FirebaseSettings;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = findViewById( R.id.logoutButton );

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth = FirebaseSettings.getFirebaseAuthentication();
                auth.signOut();

                Intent intent = new Intent( MainActivity.this, LoginActivity.class );
                startActivity( intent );
            }
        });
    }
}
