package app.manca.carteirinhapet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.config.FirebaseSettings;
import app.manca.carteirinhapet.model.Animal;

public class MyPetsListActivity extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private FirebaseAuth auth;
    private TextView addNewPet;
    private String currentUserId;
    private Animal pet;
    private ListView dataList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Animal> listPets = new ArrayList<>();
    private ArrayAdapter<Animal> arrayAdapterPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets_list);

        auth = FirebaseSettings.getFirebaseAuthentication();

        dataList = findViewById( R.id.dataList );

        addNewPet = findViewById( R.id.addNewPet );

        addNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MyPetsListActivity.this, PetRegisterActivity.class );
                startActivity( intent );
            }
        });

        firebaseInit();

        databaseEvent();
    }

    private void databaseEvent() {

        databaseReference.child("users").child( auth.getUid() ).child("pets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listPets.clear();

                for ( DataSnapshot objSnapshot:dataSnapshot.getChildren() ) {

                    Animal pet = objSnapshot.getValue( Animal.class );
                    listPets.add( pet );
                }

                arrayAdapterPets = new ArrayAdapter<Animal>( MyPetsListActivity.this,
                        android.R.layout.simple_list_item_1, listPets);
                dataList.setAdapter( arrayAdapterPets );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MyPetsListActivity.this, "Erro!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void firebaseInit() {
        FirebaseApp.initializeApp(MyPetsListActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
}
