package com.example.booksgallery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksgallery.Activities.BookDetails;
import com.example.booksgallery.Classes.Book;
import com.example.booksgallery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerAdapterBookGallery extends RecyclerView.Adapter<RecyclerAdapterBookGallery.ViewHolder> {

    List<Book> booksList;
    Context context;
    List<Integer> goneItemView;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout,parent,false);
        return new ViewHolder(view);
    }

    public RecyclerAdapterBookGallery(Context context, List<Book> booksList){
        this.booksList = booksList;
        this.context = context;
        goneItemView = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterBookGallery.ViewHolder holder, int position) {
        Book bookData = booksList.get(position);
        holder.title.setText(bookData.title);
        holder.authorName.setText(bookData.authorName);
        holder.publisher.setText(bookData.publisher);
        holder.genre.setText(bookData.genre);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetails.class);
                intent.putExtra("title",holder.title.getText());
                intent.putExtra("author",holder.authorName.getText());
                intent.putExtra("publisher",holder.publisher.getText());
                intent.putExtra("genre",holder.genre.getText());
                context.startActivity(intent);
            }
        });
    }

    public void addInGoneList(int pos){
        Log.i("inadd", String.valueOf(pos));
        goneItemView.add(pos);
    }
    public void removeInGoneList(int pos){
        goneItemView.remove(pos);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
    public class  ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView authorName,title,genre,publisher;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.book_item_author_name);
            title = itemView.findViewById(R.id.book_item_name);
            genre = itemView.findViewById(R.id.book_item_genre);
            publisher = itemView.findViewById(R.id.book_item_publisher);
        }
    }
}
