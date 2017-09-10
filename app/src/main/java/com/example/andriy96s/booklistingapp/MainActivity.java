package com.example.andriy96s.booklistingapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements SearchFragment.SearchButtonClicked, BookListFragment.OnListFragmentInteractionListener {

    public static final String EXTRA_URL = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onListFragmentInteraction (Book item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(item.getPreviewLink()));
        startActivity(intent);
    }

    public void buttonClicked (String url) {
        if(findViewById(R.id.book_list_container)==null) {
            Intent intent = new Intent(this, BookListActivity.class);
            intent.putExtra(EXTRA_URL, url);
            startActivity(intent);
        } else {
            BookListFragment fragment = new BookListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            fragment.setUrl(url);
            ft.replace(R.id.book_list_container,fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }
}
