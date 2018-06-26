package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

public class BookDetails extends Activity {

    private String bookID;
    private String isbn;

    //Loading Layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Start of activity
        setContentView(R.layout.activity_book_details); // Presenting which layout
        setUpListView();
    }

    //Method to call Book object and map them to respective EditView
    public void setUpListView(){

        Intent i = getIntent();
        bookID = i.getStringExtra("BookID");
        isbn = i.getStringExtra("ISBN");

        new AsyncTask<String, Void, Book>() {
            @Override
            protected Book doInBackground(String... params) {
                return Book.getBook(params[0]);
            }
            @Override
            protected void onPostExecute(Book result) {
                setvalue("Title",R.id.titleInfo,result);
                setvalue("Author",R.id.authorInfo,result);
                setvalue("Price",R.id.priceInfo,result);
                setvalue("ISBN",R.id.isbnInfo,result);
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

    // A method to map data to the respective EditView called by setUpListView() this is to achieve method reusing
    public void setvalue(String value,int a ,Book book) {
        String bookDetail = book.get(value);

        if (bookDetail != null) {
            EditText e = (EditText) findViewById(a);
            if (value == "Price") {
                bookDetail = "S$ " + bookDetail;
            }
            e.setText(bookDetail);
        }
    }

    // A method to create a menu burger on the top-right of the app
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // A method to create an action once the item on the menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(this,EditPage.class);
                startActivity(i);
                i.putExtra("BookID",this.bookID);
                i.putExtra("ISBN",this.isbn);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
