package com.example.andriy96s.booklistingapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.andriy96s.booklistingapp.BookListFragment.OnListFragmentInteractionListener;
import com.nostra13.universalimageloader.core.ImageLoader;



import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
 class BookArrayAdapter extends RecyclerView.Adapter<BookArrayAdapter.ViewHolder> {

    private final List<Book> mValues;
    private final OnListFragmentInteractionListener mListener;
    private ImageLoader imageLoader;

     BookArrayAdapter(List<Book> items, OnListFragmentInteractionListener listener, ImageLoader _imageLoader) {
        mValues = items;
        mListener = listener;
        imageLoader=_imageLoader;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.titleTextView.setText(mValues.get(position).getTitle());
        holder.descriptionTextView.setText(mValues.get(position).getDescription());
        holder.authorsTextView.setText(mValues.get(position).getAuthors());
        imageLoader.displayImage(mValues.get(position).getThumbnailLink(),holder.bookImageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(mValues.get(position));
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView (RecyclerView view) {
        super.onAttachedToRecyclerView(view);
    }

    @Override
    public int getItemCount() {
        if (mValues!=null)
        return mValues.size();
        else return 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         final View view;
         final ImageView bookImageView;
         final TextView titleTextView;
         final TextView authorsTextView;
         final TextView descriptionTextView;

        ViewHolder(View view) {
            super(view);
            this.view=view;
            bookImageView = view.findViewById(R.id.book_image);
            titleTextView = view.findViewById(R.id.title);
            authorsTextView=view.findViewById(R.id.authors);
            descriptionTextView = view.findViewById(R.id.description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleTextView.getText() + "'";
        }
    }
}
