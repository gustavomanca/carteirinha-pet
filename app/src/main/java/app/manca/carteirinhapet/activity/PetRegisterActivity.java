package app.manca.carteirinhapet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.config.FirebaseSettings;
import app.manca.carteirinhapet.model.Animal;

public class PetRegisterActivity extends AppCompatActivity {

    private DatabaseReference firebaseRef;

    private Button submitButton;
    private Animal pet;
    private FirebaseAuth auth;

    EditText petType,
             petName,
             petGenre,
             petBreed,
             petBornDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_register);

        firebaseRef = FirebaseSettings.getFirebase();

        petType = findViewById( R.id.inputPetType );
        petName = findViewById( R.id.inputPetName );
        petGenre = findViewById( R.id.inputPetGenre );
        petBreed = findViewById( R.id.inputPetBreed );
        petBornDate = findViewById( R.id.inputPetBornDate );
        submitButton = findViewById( R.id.submitNewPet );

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pet = new Animal();
                pet.setId( UUID.randomUUID().toString() );
                pet.setType( petType.getText().toString() );
                pet.setName( petName.getText().toString() );
                pet.setGenre( petGenre.getText().toString() );
                pet.setBreed( petBreed.getText().toString() );
                pet.setBornDate( petBornDate.getText().toString() );

                handleEmptyFields();
            }
        });
    }

    private void handleEmptyFields() {

        String errorMessage = "Este campo é obrigatório!";

        if ( TextUtils.isEmpty( petType.getText() ) ) {

            petType.setError( errorMessage );

        } else if ( TextUtils.isEmpty( petName.getText() ) ) {

            petName.setError(errorMessage);

        } else if ( TextUtils.isEmpty( petGenre.getText() ) ) {

            petGenre.setError( errorMessage );

        } else if ( TextUtils.isEmpty( petBreed.getText() ) ) {

            petBreed.setError( errorMessage );

        } else if ( TextUtils.isEmpty( petBornDate.getText() ) ) {

            petBornDate.setError( errorMessage );

        } else {

            petRegister();
        }
    }

    private void petRegister() {

        auth = FirebaseSettings.getFirebaseAuthentication();

        if ( auth.getCurrentUser() != null ) {

            pet.save( auth.getUid() );

            Toast.makeText(
                    PetRegisterActivity.this,
                    "Pet cadastrado com sucesso!",
                    Toast.LENGTH_LONG
            ).show();

            redirectToPetListView();
        }
    }

    private void redirectToPetListView() {

        Intent intent = new Intent( PetRegisterActivity.this, MyPetsListActivity.class );
        startActivity( intent );
        finish();
    }

    private void cleanUpForm() {

        petName.setText("");
        petGenre.setText("");
        petBreed.setText("");
        petBornDate.setText("");
    }
}
