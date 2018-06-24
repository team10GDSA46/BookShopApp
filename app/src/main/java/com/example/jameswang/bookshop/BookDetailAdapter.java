package com.example.jameswang.bookshop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookDetailAdapter extends ArrayAdapter<Book> {

    private List<Book> items;
    int resource;

    public BookDetailAdapter(Context context, int resource, List<Book> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        String title = items.get(position).get("Title");
        if (title != null) {
            TextView e = (TextView) v.findViewById(R.id.titleInfo);
            e.setText(title);
        }
        String author = items.get(position).get("Author");
        if (author != null) {
            TextView e = (TextView) v.findViewById(R.id.authorInfo);
            e.setText(author);
        }
        String price = items.get(position).get("Price");
        if (price != null) {
            TextView e = (TextView) v.findViewById(R.id.priceInfo);
            e.setText("S$ "+price);
        }
        String isbn = items.get(position).get("ISBN");
        if (isbn != null) {
            TextView e = (TextView) v.findViewById(R.id.isbnInfo);
            e.setText(isbn);
        }
        return v;
    }
}
