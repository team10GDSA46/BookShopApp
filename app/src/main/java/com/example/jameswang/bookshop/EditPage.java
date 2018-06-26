package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

public class EditPage extends Activity {

    private String bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        Intent i = getIntent();
        bookID = i.getStringExtra("BookID");
        new AsyncTask<String, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(String... params) {
                Book book = Book.getBook(params[0]);
                return Book.bookList(book.get("Title"));
//                return null;
            }
            @Override
            protected void onPostExecute(List<Book> result) {
//                EditText e = (EditText) findViewById(R.id.titleUpdate);
//                e.setText(result.get(0).get("Title"));
//                ListView lv = (ListView) findViewById(R.id.listBookView);
//                lv.setAdapter(adapter);
            }
        }.execute(bookID);
        Button updateButton = (Button) findViewById(R.id.buttonUpdate);



    }

    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Book book = (Book) av.getAdapter().getItem(position);
        Intent i = new Intent(this,BookDetails.class);
        i.putExtra("BookID",book.get("BookID"));
        i.putExtra("ISBN",book.get("ISBN"));
        startActivity(i);
    }


}
