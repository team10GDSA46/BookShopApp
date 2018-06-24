package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class BookDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        setUpListView();
    }

    public void setUpListView(){
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        Intent i = getIntent();
        Log.i(">>>>>>>>>>111111",i.getStringExtra("BookID"));
        String bookID = i.getStringExtra("BookID");
        Log.i(">>>>>>>>>>222222",i.getStringExtra("BookID"));
        Book book = Book.getBook(bookID);
        List<Book> bookDetail = Book.bookList(book.get("Title"));
        Log.i(">>>>>>>>>>444444",i.getStringExtra("BookID"));
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageBitmap(Book.getPhoto(true, book.get("ISBN")));
        Log.i(">>>>>>>>>>555555",i.getStringExtra("BookID"));
        BookDetailAdapter adapter = new BookDetailAdapter(this, R.layout.book_details_row, bookDetail);
        Log.i(">>>>>>>>>>666666",i.getStringExtra("BookID"));
        ListView lv = (ListView) findViewById(R.id.listBookView);
        lv.setAdapter(adapter);
    }
}
