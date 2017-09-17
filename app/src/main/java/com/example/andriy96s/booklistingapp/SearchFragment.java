package com.example.andriy96s.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Formatter;


public class SearchFragment extends Fragment {

    private SearchButtonClicked clicked;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
    Animation shake;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button button = view.findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = v.getRootView();
                EditText keyword = view.findViewById(R.id.keyword_edit_text);
                EditText quantity = view.findViewById(R.id.quantity_edit_text);
                if (keyword.getText().toString().isEmpty()) {
                    Toast keywordToast = Toast.makeText(getContext(),R.string.empty_keyword,Toast.LENGTH_SHORT);
                    keywordToast.show();
                    shakeButton();
                    return;
                }
                try {
                    Formatter f = new Formatter();
                    f.format("https://www.googleapis.com/books/v1/volumes?q=%s&maxResults=%d", keyword.getText().toString().replace(" ", ""), Integer.parseInt(quantity.getText().toString()));
                    clicked.buttonClicked(f.toString());
                } catch (NumberFormatException e) {
                    Toast quantityToast = Toast.makeText(getContext(),R.string.invalid_number,Toast.LENGTH_SHORT);
                    quantityToast.show();
                    shakeButton();
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        clicked=(SearchButtonClicked)context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    interface SearchButtonClicked {
         void buttonClicked (String url);
    }

    private void shakeButton () {
        Animation shake= AnimationUtils.loadAnimation(getContext(),R.anim.shake);
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        Button button = getActivity().findViewById(R.id.search_button);
        button.startAnimation(shake);
        v.vibrate(150);
    }

}
