package app.manca.carteirinhapet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

import app.manca.carteirinhapet.R;
import app.manca.carteirinhapet.config.FirebaseSettings;
import app.manca.carteirinhapet.model.Animal;
import app.manca.carteirinhapet.model.Vet;

import static android.widget.Toast.LENGTH_LONG;

public class VetsListActivity extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private FirebaseAuth auth;
    private Button addNewVet;
    private String currentUserId;
    private Vet vet;
    private ListView vetList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Vet> listVets = new ArrayList<>();
    private ArrayAdapter<Vet> vetArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vets_list);

        auth = FirebaseSettings.getFirebaseAuthentication();

        vetList = findViewById( R.id.vetList );

        addNewVet = findViewById( R.id.addNewVet );

        addNewVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( VetsListActivity.this, VetsRegisterActivity.class );
                startActivity( intent );
                finish();
            }
        });

        firebaseInit();

        databaseEvent();
    }

    private void databaseEvent() {

        databaseReference.child("users").child( auth.getUid() ).child("vets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listVets.clear();

                for ( DataSnapshot objSnapshot:dataSnapshot.getChildren() ) {

                    Vet vet = objSnapshot.getValue( Vet.class );
                    listVets.add( vet );
                }

                vetArrayAdapter = new ArrayAdapter<Vet>( VetsListActivity.this,
                        android.R.layout.simple_list_item_1, listVets);
                vetList.setAdapter( vetArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(VetsListActivity.this, "Erro!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void firebaseInit() {
        FirebaseApp.initializeApp(VetsListActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
}
