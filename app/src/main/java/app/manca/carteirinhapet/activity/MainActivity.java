package app.manca.carteirinhapet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.config.FirebaseSettings;

public class MainActivity extends AppCompatActivity {

    private TextView logoutLabel;
    private Button myPetsButton, vetsButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutLabel = findViewById( R.id.logoutLabel );
        myPetsButton = findViewById( R.id.myPetsButton );
        vetsButton = findViewById( R.id.vetsButton );

        logoutLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth = FirebaseSettings.getFirebaseAuthentication();
                auth.signOut();

                Intent intent = new Intent( MainActivity.this, LoginActivity.class );
                startActivity( intent );
                finish();
            }
        });

        myPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, MyPetsListActivity.class );
                startActivity( intent );
                finish();
            }
        });

        vetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, VetsListActivity.class );
                startActivity( intent );
                finish();
            }
        });

    }
}
