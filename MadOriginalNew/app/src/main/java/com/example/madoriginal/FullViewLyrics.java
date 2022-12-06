package com.example.madoriginal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FullViewLyrics extends AppCompatActivity {

    private ImageView imageView;
    TextView textView,desview,viewcount;
    Button btnDlt,uptbtn;
    EditText title;

    DatabaseReference ref,DataRef;
    StorageReference storageReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view_lyrics);

        imageView=findViewById(R.id.FullView_Activity);
        desview=findViewById(R.id.lycdes);
        viewcount=findViewById(R.id.count);
        textView=findViewById(R.id.FullView_Activity_textview);
        btnDlt=findViewById(R.id.lycDlt);
        uptbtn=findViewById(R.id.updatelyc);
        ref= FirebaseDatabase.getInstance().getReference().child("Lyrics");

        String LyricsKey=getIntent().getStringExtra("LyricsKey");
        DataRef=FirebaseDatabase.getInstance().getReference().child("Lyrics").child(LyricsKey);
        storageReference= FirebaseStorage.getInstance().getReference().child("LyricsImage").child(LyricsKey+".jpg");







        ref.child(LyricsKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    String Descrption =snapshot.child("Description").getValue().toString();
                    String view =snapshot.child("View Count").getValue().toString();
                    String LyricsName=snapshot.child("LyricsName").getValue().toString();
                    String ImageUrl=snapshot.child("ImageUrl").getValue().toString();
                    Picasso.get().load(ImageUrl).into(imageView);
                    textView.setText("Lyrics Title :"+""+LyricsName);
                    desview.setText("Lyrics Description :"+""+Descrption);
                    viewcount.setText("View count :"+" "+view);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDlt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(),LyricsDisplay.class));
                            }
                        });
                    }
                });

            }
        });

        uptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        startActivity(new Intent(getApplicationContext(),UploadLy.class));

                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(),UploadLy.class));
                            }
                        });
                    }
                });

            }
        });



    }
}