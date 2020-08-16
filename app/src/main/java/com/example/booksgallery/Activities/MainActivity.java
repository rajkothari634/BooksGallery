package com.example.booksgallery.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.booksgallery.Adapters.RecyclerAdapterBookGallery;
import com.example.booksgallery.Classes.Book;
import com.example.booksgallery.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView bookHeader;
    ImageView searchImage,backArrow;
    EditText searchEditText;
    RecyclerView recyclerView;
    RecyclerAdapterBookGallery recyclerAdapterBookGallery;
    List<Book> bookDetailsList;
    List<Book> tempList;
    String offset = null;
    ProgressBar progressBar;
    String api_key = "";
    Boolean searchActive = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookHeader = findViewById(R.id.header_title);
        searchImage = findViewById(R.id.search_image);
        backArrow = findViewById(R.id.back_arrow_image);
        searchEditText = findViewById(R.id.search_edit_text);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        bookDetailsList = new ArrayList<>();
        tempList = new ArrayList<>();
        Book b = new Book("csce","crregr","regergreg","grgtg","viehi");
        bookDetailsList.add(b);

        recyclerAdapterBookGallery = new RecyclerAdapterBookGallery(this,bookDetailsList);

        //call api to fetch data
        populateRecycler();
        //
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapterBookGallery);

        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if(totalItemCount==lastVisibleItemPosition+1){
                    Log.i("checkpopulatecalling","goingto" + " " + totalItemCount);
                    if(offset != null && !offset.equals("End Of Books") && !searchActive ) {
                        populateRecycler();
                    }
                }
            }
        };

        recyclerView.setOnScrollListener(recyclerViewOnScrollListener);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                for(int i=0;i<tempList.size();i++) {
                    Book book = tempList.get(i);
                    if(book.getTitle().toLowerCase().startsWith(editable.toString().toLowerCase())
                           || book.getGenre().toLowerCase().startsWith(editable.toString().toLowerCase())
                           || book.getPublisher().toLowerCase().startsWith(editable.toString().toLowerCase())
                           || book.getAuthorName().toLowerCase().startsWith(editable.toString().toLowerCase())) {
                       if(!bookDetailsList.contains(tempList.get(i))){
                           bookDetailsList.add(tempList.get(i));
                       }
                    }else{
                       bookDetailsList.remove(book);
                    }
                }
                recyclerAdapterBookGallery.notifyDataSetChanged();
            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchImage.setVisibility(View.GONE);
                bookHeader.setVisibility(View.GONE);
                backArrow.setVisibility(View.VISIBLE);
                searchEditText.setVisibility(View.VISIBLE);
                searchActive = true;
                tempList = new ArrayList<>();
                tempList.addAll(bookDetailsList);
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEditText.setText("");
                bookDetailsList.clear();
                bookDetailsList.addAll(tempList);
                recyclerAdapterBookGallery.notifyDataSetChanged();
                Log.i("sizeafterbackarrow", String.valueOf(bookDetailsList.size()));
                searchActive = false;
                searchImage.setVisibility(View.VISIBLE);
                bookHeader.setVisibility(View.VISIBLE);
                backArrow.setVisibility(View.GONE);
                searchEditText.setVisibility(View.GONE);
            }
        });

    }

    public void populateRecycler(){
        if(api_key.length()==0){
            Toast.makeText(this,"api key is empty Please fill the api_key",Toast.LENGTH_LONG).show();
            return;
        }
        searchActive = true;
        progressBar.setVisibility(View.VISIBLE);
        Log.i("checking",offset+ " ");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        new JsonObjectRequest(Request.Method.GET,)
        String url = "https://api.airtable.com/v0/"+ api_key+"/Imported table?pageSize=10";
        if(offset != null){
            url = url + "&offset=" + offset;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            offset  = !jsonObject.isNull("offset") ?  jsonObject.getString("offset") : "End Of Books";
                            JSONArray jsonArray = jsonObject.getJSONArray("records");
                            for(int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Book book = new Book();
                                book.setId(!object.isNull("id") ? object.getString("id") : "");
                                JSONObject insideObj = object.getJSONObject("fields");
                                book.setTitle(!insideObj.isNull("Title") ? object.getJSONObject("fields").getString("Title") : "Title Null");
                                book.setAuthorName(!insideObj.isNull("Author") ? insideObj.getString("Author") : "Author Name Null");
                                book.setGenre(!insideObj.isNull("Genre")? insideObj.getString("Genre") : "Genre Null");
                                book.setPublisher(!insideObj.isNull("Publisher") ? insideObj.getString("Publisher") : "Publisher Null");

                                bookDetailsList.add(book);
                                tempList.add(book);
                                recyclerAdapterBookGallery.notifyDataSetChanged();
                                Log.i("sizeofbooks", String.valueOf(bookDetailsList.size()) + " " + recyclerAdapterBookGallery.getItemCount());
                            }
                            searchActive = false;
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("error", "" + volleyError);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("Authorization", "Bearer keyM6r5HW5sOg1noh");
            return params;
        }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
