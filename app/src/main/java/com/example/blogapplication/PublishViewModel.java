package com.example.blogapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;

public class PublishViewModel extends ViewModel {

    public void uploadData(Uri filepath, String title, String desc, String author, Context context, ProgressDialog pd, Runnable onSuccess) {
        FirebaseStorage storage = FirebaseInstances.getStorageInstance();
        StorageReference reference = storage.getReference().child("images/" + filepath.toString() + ".jpg");

        reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(Task<Uri> task) {
                        String file_url = task.getResult().toString();
                        String date = (String) DateFormat.format("dd", new Date());
                        String month = (String) DateFormat.format("MMM", new Date());
                        String final_date = date + " " + month;

                        HashMap<String, String> map = new HashMap<>();
                        map.put("tittle", title);
                        map.put("desc", desc);
                        map.put("author", author);
                        map.put("date", final_date);
                        map.put("img", file_url);
                        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                        map.put("share_count", "0");

                        FirebaseFirestore firestore = FirebaseInstances.getFirestoreInstance();
                        firestore.collection("Blogs").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(context, "Post Uploaded!!!", Toast.LENGTH_SHORT).show();
                                    onSuccess.run();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
