package com.app.swap;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long pressedTime;
    //now create list of type slider items
    List<SliderItems> sliderItems = new ArrayList<>();

    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();


    //database reference

    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalViewPager);

        mRef = FirebaseDatabase.getInstance().getReference("data");

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren())
                {
                    text.add(ds.child("text").getValue(String.class));
                    id.add(ds.child("id").getValue(String.class));
                    images.add(ds.child("imagelink").getValue(String.class));

                }

                for(int i=0;i<images.size();i++)
                {

                    sliderItems.add(new SliderItems(images.get(i)));


                }

                verticalViewPager.setAdapter(new ViewPagerAdapter(MainActivity.this,sliderItems,text,id,verticalViewPager));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}

