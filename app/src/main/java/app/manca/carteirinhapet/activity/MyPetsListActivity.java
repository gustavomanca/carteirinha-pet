package app.manca.carteirinhapet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import app.manca.carteirinhapet.R;

public class MyPetsListActivity extends AppCompatActivity {

    private TextView addNewPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets_list);

        addNewPet = findViewById( R.id.addNewPet );

        addNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MyPetsListActivity.this, PetRegisterActivity.class );
                startActivity( intent );
            }
        });
    }
}
