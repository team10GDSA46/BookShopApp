package com.example.jameswang.bookshop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String,String> {

    final static String baseURL = "http://192.168.1.87/bookshop/Service1.svc/";
    final static String imageURL = "http://192.168.1.87/bookshop/images/";

    public Book(int BookID, int Stock, double Price, int CategoryID, String Title, String Author, String ISBN){
        put("BookID",String.valueOf(BookID));
        put("Stock",String.valueOf(Stock));
        put("Price",String.valueOf(Price));
        put("CatID",String.valueOf(CategoryID));
        put("Title",Title);
        put("Author",Author);
        put("ISBN",ISBN);
    }

    public Book(JSONObject b) throws JSONException
    {
        this(b.getInt("BookID"), b.getInt("Stock"),
                b.getDouble("Price"), b.getInt("CatID"),
                b.getString("Title"), b.getString("Author"),
                b.getString("ISBN"));

    }

    public static List<String> list() {
        List<String> list = new ArrayList<String>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Books");

        try {
            for (int i =0; i<a.length(); i++)
                list.add(a.getString(i));
        } catch (Exception e) {
            Log.e("Books.list()", "JSONArray error");
        }
        return(list);
    }
    public static Book getBook(String eid) {
        JSONObject b = JSONParser.getJSONFromUrl(baseURL + "Book/" + eid);
        try {
            return new Book(b);
        } catch (Exception e) {
            Log.e("Book.getBook()", "JSONArray error");
        }
        return(null);
    }

    public static List<Book> bookList(String searchText) {
        List<Book> list = new ArrayList<Book>();
        JSONArray c;
        String httpText;
        if(searchText.isEmpty()){
            c = JSONParser.getJSONArrayFromUrl(baseURL + "AllBooks");
        }else {
            String urlEncoded = baseURL + "SearchBook/?q=" + Uri.encode(searchText);
            c = JSONParser.getJSONArrayFromUrl(urlEncoded);
        }
        try {
            for (int i =0; i<c.length(); i++) {
                JSONObject d = c.getJSONObject(i);
                list.add(new Book(d));
            }

        } catch (Exception e) {

            Log.e("Books.list()", e.getMessage());
        }
        return(list);
    }

    public static Bitmap getPhoto(String ISBN) {
        try {
            URL url = new URL(String.format("%s%s.jpg",imageURL, ISBN));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Book.getPhoto()", "Bitmap error");
        }
        return(null);
    }
}
