package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

public class BookDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        setUpListView();
    }

    public void setUpListView(){

        Intent i = getIntent();
        String bookID = i.getStringExtra("BookID");
        String isbn = i.getStringExtra("ISBN");

        new AsyncTask<String, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(String... params) {
                Book book = Book.getBook(params[0]);
                return Book.bookList(book.get("Title"));
            }
            @Override
            protected void onPostExecute(List<Book> result) {
                BookDetailAdapter adapter = new BookDetailAdapter(BookDetails.this, R.layout.book_details_row, result);
                ListView lv = (ListView) findViewById(R.id.listBookView);
                lv.setAdapter(adapter);
            }
        }.execute(bookID);
        new AsyncTask<String, Void,Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                return Book.getPhoto(params[0]);
            }
            @Override
            protected void onPostExecute(Bitmap result) {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                image.setImageBitmap(result);
            }
        }.execute(isbn);



    }
}
