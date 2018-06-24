package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyBookAdapter extends ArrayAdapter<Book> {

    private List<Book> items;
    int resource;

    public MyBookAdapter(Context context, int resource, List<Book> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);
        String title = items.get(position).get("Title");
        if (title != null) {
            TextView e = (TextView) v.findViewById(R.id.title);
            e.setText(title);
        }
        String author = items.get(position).get("Author");
        if (author != null) {
            TextView e = (TextView) v.findViewById(R.id.author);
            e.setText(author);

        }
        String ISBN = items.get(position).get("ISBN");
        if (ISBN != null) {

            new AsyncTask<String, Void,Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    return Book.getPhoto(params[0]);
                }
                @Override
                protected void onPostExecute(Bitmap result) {
                    ImageView image = (ImageView) v.findViewById(R.id.bookimage);
                    image.setImageBitmap(result);
                }
            }.execute(ISBN);
        }
        return v;
    }
}
