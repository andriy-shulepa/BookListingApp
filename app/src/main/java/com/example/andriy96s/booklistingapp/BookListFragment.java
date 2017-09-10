package com.example.andriy96s.booklistingapp;


import android.app.Activity;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.app.LoaderManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BookListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Book>> {
    private String EXTRA_URL = "URL";
    private String url;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        recyclerView= view.findViewById(R.id.book_recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle extra = new Bundle();
        extra.putString(EXTRA_URL, url);
        getLoaderManager().initLoader(0, extra, this);
        Activity context = getActivity();
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
    @Override
    public void onResume () {
        super.onResume();
        mListener = (OnListFragmentInteractionListener) getActivity();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Book item);
    }


    private void updateUI(List<Book> books) {
        Activity activity = getActivity();
        progressBar.setVisibility(View.GONE);

        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(activity);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        BookArrayAdapter adapter = new BookArrayAdapter(books, mListener, imageLoader);
        recyclerView.setAdapter(adapter);
        if (books.isEmpty())  {
            recyclerView.setVisibility(View.GONE);
            TextView emptyTextView =  getActivity().findViewById(R.id.empty_text_view);
            emptyTextView.setVisibility(View.VISIBLE);
            ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info==null || !info.isConnectedOrConnecting()) emptyTextView.setText(R.string.no_internet);
            else  emptyTextView.setText(R.string.no_results);

        }

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(getActivity(), args.getString(EXTRA_URL));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {

        updateUI(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        loader.cancelLoad();
    }

    public void setUrl(String _url) {
        url=_url;
    }

}
