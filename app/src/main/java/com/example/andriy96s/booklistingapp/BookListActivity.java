package com.example.andriy96s.booklistingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class BookListActivity extends Activity implements  BookListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        BookListFragment fragment = (BookListFragment) getFragmentManager().findFragmentById(R.id.book_list_fragment);
        fragment.setUrl(getIntent().getStringExtra(MainActivity.EXTRA_URL));
    }

    public void onListFragmentInteraction (Book item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(item.getPreviewLink()));
        startActivity(intent);
    }
}
