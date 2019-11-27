package app.manca.carteirinhapet.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class FirebaseSettings {

    private static DatabaseReference firebaseRef;

    public static DatabaseReference getFirebase() {

        if ( firebaseRef == null ) {

            firebaseRef = FirebaseDatabase.getInstance().getReference();
        }

        return firebaseRef;
    }
}
