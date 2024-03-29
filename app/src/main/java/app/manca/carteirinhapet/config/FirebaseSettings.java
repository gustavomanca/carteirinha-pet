package app.manca.carteirinhapet.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class FirebaseSettings {

    private static DatabaseReference firebaseRef;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebase() {

        if ( firebaseRef == null ) {

            firebaseRef = FirebaseDatabase.getInstance().getReference();
        }

        return firebaseRef;
    }

    public static FirebaseAuth getFirebaseAuthentication() {

        if ( auth == null ) {
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }
}
