package app.manca.carteirinhapet.model;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import app.manca.carteirinhapet.activity.MainActivity;
import app.manca.carteirinhapet.activity.PetRegisterActivity;
import app.manca.carteirinhapet.config.FirebaseSettings;

public class Animal {

    private String id;
    private String type;
    private String name;
    private String genre;
    private String breed;
    private String bornDate;

    public Animal() {
    }

    public void save( String userId ) {

        DatabaseReference firebaseRef = FirebaseSettings.getFirebase().child("users").child( userId );

        firebaseRef.child("pets").child( getId() ).setValue( this );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
