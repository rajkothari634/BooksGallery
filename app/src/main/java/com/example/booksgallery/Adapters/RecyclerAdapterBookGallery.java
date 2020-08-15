package com.example.booksgallery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksgallery.Classes.Book;
import com.example.booksgallery.R;

import java.util.HashMap;
import java.util.List;

public class RecyclerAdapterBookGallery extends RecyclerView.Adapter<RecyclerAdapterBookGallery.ViewHolder> {

    List<Book> booksList;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout,parent,false);
        return new ViewHolder(view);
    }

    public RecyclerAdapterBookGallery(Context context, List<Book> booksList){
        this.booksList = booksList;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterBookGallery.ViewHolder holder, int position) {
        Book bookData = booksList.get(position);
        holder.title.setText(bookData.title);
        holder.authorName.setText(bookData.authorName);
        holder.publisher.setText(bookData.publisher);
        holder.genre.setText(bookData.genre);
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
