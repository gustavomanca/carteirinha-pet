package app.manca.carteirinhapet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.config.FirebaseSettings;
import app.manca.carteirinhapet.model.User;
import app.manca.carteirinhapet.model.Vet;

public class VetsRegisterActivity extends AppCompatActivity {

    private DatabaseReference firebaseRef;

    private Button submitNewVetButton, cancelNewVetButton;
    private Vet vet;
    private FirebaseAuth auth;

    EditText inputVetName, inputVetContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vets_register);

        firebaseRef = FirebaseSettings.getFirebase();

        inputVetName = findViewById( R.id.inputVetName );
        inputVetContact = findViewById( R.id.inputVetContact );

        cancelNewVetButton = findViewById( R.id.newVetCancel );
        submitNewVetButton = findViewById( R.id.submitNewVet );

        cancelNewVetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectToVetListView();
            }
        });

        submitNewVetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vet = new Vet();
                vet.setId( UUID.randomUUID().toString() );
                vet.setName( inputVetName.getText().toString() );
                vet.setContact( inputVetContact.getText().toString() );

                handleEmptyFields();
            }
        });
    }

    private void handleEmptyFields() {

        String errorMessage = "Este campo é obrigatório!";

        if (TextUtils.isEmpty(inputVetName.getText())) {

            inputVetName.setError(errorMessage);

        } else if (TextUtils.isEmpty(inputVetContact.getText())) {

            inputVetContact.setError(errorMessage);

        } else {

            vetRegister();
        }
    }

    private void vetRegister() {

        auth = FirebaseSettings.getFirebaseAuthentication();

        if ( auth.getCurrentUser() != null ) {

            vet.save( auth.getUid() );

            Toast.makeText(
                    VetsRegisterActivity.this,
                    "Veterinário cadastrado com sucesso!",
                    Toast.LENGTH_LONG
            ).show();

            redirectToVetListView();
        }
    }

    private void redirectToVetListView() {

        Intent intent = new Intent( VetsRegisterActivity.this, VetsListActivity.class );
        startActivity( intent );
        finish();
    }
}
