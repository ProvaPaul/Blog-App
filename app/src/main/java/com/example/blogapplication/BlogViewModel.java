package com.example.blogapplication;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BlogViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Model>> blogsLiveData;
    private final ArrayList<Model> blogList;

    public BlogViewModel() {
        blogsLiveData = new MutableLiveData<>();
        blogList = new ArrayList<>();
        loadBlogs();
    }

    public LiveData<ArrayList<Model>> getBlogs() {
        return blogsLiveData;
    }

    private void loadBlogs() {
        FirebaseFirestore.getInstance().collection("Blogs").orderBy("timestamp")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        blogList.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Model model = snapshot.toObject(Model.class);
                            model.setId(snapshot.getId());
                            blogList.add(model);
                        }
                        blogsLiveData.setValue(blogList);
                    }
                });
    }
}
