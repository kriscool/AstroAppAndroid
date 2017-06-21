package com.example.kriscool.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kriscool on 21.06.2017.
 */

public class futureday extends Fragment {
    Weather w = new Weather();
    View view;
    TextView dayOne;
    TextView dayTwo;
    TextView dayThree;
    TextView dayFour;
    TextView miastoo;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.futureday, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                        update();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


        return view;
    }


    public void update(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                setData(((MainActivity) getActivity()).getD1(),
                        ((MainActivity) getActivity()).getD2(),
                        ((MainActivity) getActivity()).getD3(),
                        ((MainActivity) getActivity()).getD4(),
                        view);
            }
        });
    }



    public void initializeElements(View view){
        dayOne = (TextView) view.findViewById(R.id.dayOne);
        dayTwo = (TextView) view.findViewById(R.id.dayTwo);
        dayThree = (TextView) view.findViewById(R.id.dayThree);
        dayFour = (TextView) view.findViewById(R.id.dayFour);
    }

    public void setData(String d1,String d2,String d3,String d4,View view){


        initializeElements(view);
        dayOne.setText(d1);
        dayTwo.setText(d2);
        dayThree.setText(d3);
        dayFour.setText(d4);


    }
}
