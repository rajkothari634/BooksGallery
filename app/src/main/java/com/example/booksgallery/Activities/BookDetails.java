package com.example.booksgallery.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booksgallery.R;

public class BookDetails extends AppCompatActivity {

    TextView authorName,title,publisher,genre;
    ImageView backImage;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        authorName = findViewById(R.id.book_detail_author);
        title = findViewById(R.id.book_detail_title);
        publisher = findViewById(R.id.book_detail_publisher);
        genre = findViewById(R.id.book_detail_genre);
        backImage = findViewById(R.id.back_arrow_image);


        authorName.setText(getIntent().getStringExtra("author"));
        title.setText(getIntent().getStringExtra("title"));
        genre.setText(getIntent().getStringExtra("genre"));
        publisher.setText(getIntent().getStringExtra("publisher"));

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
