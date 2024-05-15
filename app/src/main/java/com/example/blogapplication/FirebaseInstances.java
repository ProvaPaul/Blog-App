package com.example.blogapplication;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseInstances {

    private static FirebaseStorage firebaseStorageInstance;
    private static FirebaseFirestore firebaseFirestoreInstance;

    private FirebaseInstances() {}

    public static synchronized FirebaseStorage getStorageInstance() {
        if (firebaseStorageInstance == null) {
            firebaseStorageInstance = FirebaseStorage.getInstance();
        }
        return firebaseStorageInstance;
    }

    public static synchronized FirebaseFirestore getFirestoreInstance() {
        if (firebaseFirestoreInstance == null) {
            firebaseFirestoreInstance = FirebaseFirestore.getInstance();
        }
        return firebaseFirestoreInstance;
    }
}
