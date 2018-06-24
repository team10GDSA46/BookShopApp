package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    private EditText searchBoxText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBoxText = (EditText) findViewById(R.id.searchBox);
        searchBoxText.addTextChangedListener(searchBoxWatcher);
        setUpListView(searchBoxText.getText().toString()).setOnItemClickListener(this);
    }
    private final TextWatcher searchBoxWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            setUpListView(searchBoxText.getText().toString()).setOnItemClickListener(MainActivity.this);
            if (Book.bookList(searchBoxText.getText().toString()).isEmpty()) {
                Toast t = Toast.makeText(MainActivity.this, "No Books Found", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    };

    public ListView setUpListView(String searchtxt){
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        List<Book> books = Book.bookList(searchtxt);
        MyBookAdapter adapter = new MyBookAdapter(this, R.layout.row, books);
        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        return lv;
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Book book = (Book) av.getAdapter().getItem(position);
        Intent i = new Intent(this,BookDetails.class);
        i.putExtra("BookID",book.get("BookID"));
        i.putExtra("Title",book.get("Title"));

        Log.i(">>>>>>>>>>",book.get("BookID"));
        startActivity(i);
    }
}
