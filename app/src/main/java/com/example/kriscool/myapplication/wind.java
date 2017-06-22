package com.example.kriscool.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by kriscool on 21.06.2017.
 */

public class wind extends Fragment {
    Weather w = new Weather();
    View view;
    TextView miasto;
    TextView szerokosc;
    TextView time;
    TextView cisnienie;
    TextView opis;
    TextView tempe;
    ImageView image;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.wind, container, false);
    time = (TextView) view.findViewById(R.id.time);
        CountDownTimer timer = new CountDownTimer(300000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar calendar = Calendar.getInstance();
                time.setText(String.format("%02d:%02d:%02d", calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
            }

            public void onFinish() {
            }
        };
        timer.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
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


    public void update() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                setData(((MainActivity) getActivity()).getWheather(),
                        ((MainActivity) getActivity()).getD0(),
                        view);
            }
        });
    }


    public void initializeElements(View view) {
        miasto = (TextView) view.findViewById(R.id.town);
        szerokosc = (TextView) view.findViewById(R.id.location);
        tempe = (TextView) view.findViewById(R.id.temp);
        cisnienie = (TextView) view.findViewById(R.id.cisnienie);
        opis = (TextView) view.findViewById(R.id.desc);
        image = (ImageView) view.findViewById(R.id.imageView1);
    }

    public void setData(Weather w, String d0,View view) {
        initializeElements(view);
        miasto.setText(w.getMiasto());
        szerokosc.setText(w.getSzerokosc() +" " + w.getDlugosc());
        tempe.setText(w.getTemperatura());
        cisnienie.setText(w.getCisnienie());

        String[] parts = d0.split("-");
        String part1 = parts[1];
        String[] parts2 = part1.split("\\.");
        String pogoda2 = parts2[0];
        String pogoda = pogoda2.toLowerCase();
        opis.setText(d0);

            if(pogoda.contains("cloudy") ){
                image.setImageResource(R.drawable.chumrki);}
            else if(pogoda.contains("sunny")) {
                image.setImageResource(R.drawable.slonecznie);
            }else if(pogoda.contains("rain") || pogoda.contains("showers")) {
                image.setImageResource(R.drawable.deszcz);
            }else if(pogoda.contains("storm")){
                image.setImageResource(R.drawable.burza);
            }


        }


}
