package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    private EditText searchBoxText;
    @Override
    /*  Create activity page
        Set layout of mainactivity
        Create an object for searchbox and set Text watcher to it
        Create the list view object and set on item click listener to it
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBoxText = (EditText) findViewById(R.id.searchBox);
        searchBoxText.addTextChangedListener(searchBoxWatcher);
        setUpListView(searchBoxText.getText().toString());
        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);
    }

    //Method for TextWatcher
    private final TextWatcher searchBoxWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            //Display the images based on the books found via title
            setUpListView(searchBoxText.getText().toString());

            //To run the UI search on the background and UI display on the front using abstract method of AsyncTasking
            //AsyncTask<[data type]input, [data type]progress, [data type]result>>
            new AsyncTask<String, Void, List<Book>>() {
                @Override
                protected List<Book> doInBackground(String... params) {
                    return Book.bookList(params[0]);
                }
                @Override
                protected void onPostExecute(List<Book> result) {
                    //Make a Toast to inform user that no data found based on input
                    if (result.isEmpty()) {
                        Toast t = Toast.makeText(MainActivity.this, "No Books Found", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
            }.execute(searchBoxText.getText().toString());
        }
    };

    public void setUpListView(String searchtxt){
            new AsyncTask<String, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(String... params) {
                return Book.bookList(params[0]);
            }
            @Override
            protected void onPostExecute(List<Book> result) {
                MyBookAdapter adapter = new MyBookAdapter(MainActivity.this, R.layout.row, result);
                ListView lv = (ListView) findViewById(R.id.listView1);
                lv.setAdapter(adapter);
            }
        }.execute(searchtxt);
    }

    /*  Set on click event select the object that is being click on and pass the
        other information to the next activity via "key-value" pair
     */
    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Book book = (Book) av.getAdapter().getItem(position);
        Intent i = new Intent(this,BookDetails.class);
        i.putExtra("BookID",book.get("BookID"));
        i.putExtra("ISBN",book.get("ISBN"));
        startActivity(i);
    }
}
