package com.example.blogapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class BlogDetailViewModel extends AndroidViewModel {

    private final MutableLiveData<DocumentSnapshot> blogLiveData;
    private final FirebaseFirestore firestore;

    public BlogDetailViewModel(@NonNull Application application) {
        super(application);
        blogLiveData = new MutableLiveData<>();
        firestore = FirebaseInstances.getFirestoreInstance();
    }

    public LiveData<DocumentSnapshot> getBlog() {
        return blogLiveData;
    }

    public void loadBlog(String id) {
        firestore.collection("Blogs").document(id).addSnapshotListener((value, error) -> {
            if (value != null) {
                blogLiveData.setValue(value);
            }
        });
    }

    public void updateShareCount(String id, int newCount) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("share_count", String.valueOf(newCount));
        firestore.collection("Blogs").document(id).update(map);
    }
}
