package com.example.abdo.cinemaapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.abdo.cinemaapp.Adapters.AddAdapter;
import com.example.abdo.cinemaapp.General.Friend;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
   EditText text;
    ListView listView;
    List<Friend> list;
    AddAdapter adapter;
    StorageReference storageReference;
    FirebaseStorage storage;

    public AddFragment()
    {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add, container, false);
         text= v.findViewById(R.id.addEditText);
        storage = FirebaseStorage.getInstance();
         listView = v.findViewById(R.id.addListView);
         list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        storageReference = storage.getReference();

        final DatabaseReference myRef = database.getReference("Users");
         text.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                 
             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list = new ArrayList<>();
                        for (  DataSnapshot snap:dataSnapshot.getChildren()
                             ) {
                            final String name = snap.child("Username").getValue(String.class);
                             final String key =snap.getKey();
                            if (name.contains(text.getText().toString()))
                            {
                                    StorageReference ref = storageReference.child("images/" + snap.getKey());
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Friend f = new Friend(key, name, uri);
                                            list.add(f);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                            }
                        }
                        adapter = new AddAdapter(getContext(),list);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });
        return v;
    }

}
