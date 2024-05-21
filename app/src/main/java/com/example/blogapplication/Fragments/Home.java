package com.example.blogapplication.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.blogapplication.Adapter;
import com.example.blogapplication.Model;
import com.example.blogapplication.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Model> list;
    private Adapter adapter;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupRecyclerView();  // Encapsulated the RecyclerView setup
        setupSearchView();  // Encapsulated the SearchView setup
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupRecyclerView() {
        list = new ArrayList<>();
        adapter = new Adapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        binding.rvBlogs.setLayoutManager(linearLayoutManager);
        binding.rvBlogs.setAdapter(adapter);

        fetchBlogs();  // Encapsulated the data fetching logic
    }

    private void fetchBlogs() {
        FirebaseFirestore.getInstance().collection("Blogs").orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Handle the error
                    return;
                }

                if (value != null) {
                    list.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Model model = snapshot.toObject(Model.class);
                        if (model != null) {
                            model.setId(snapshot.getId());
                            list.add(model);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setupSearchView() {
        binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String newText) {
        ArrayList<Model> filteredList = new ArrayList<>();
        for (Model item : list) {
            if (item.getTittle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            // Show a message if no matching items found
        } else {
            adapter.filter_list(filteredList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
