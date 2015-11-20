package cchao.org.weatherapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cchao.org.mylibrary.fancycoverflow.FancyCoverFlow;
import cchao.org.mylibrary.fancycoverflow.FancyCoverFlowAdapter;
import cchao.org.weatherapp.R;
import cchao.org.weatherapp.WeatherApplication;
import cchao.org.weatherapp.utils.Cache;
import cchao.org.weatherapp.utils.Constant;

/**
 * Created by chenchao on 15/11/20.
 */
public class DailyFragnment extends Fragment{

    private Cache cache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);

        cache = new Cache(getActivity());
        int[] images = new int[6];
        for (int i = 0; i < 6; i++) {
            images[i] = getWeatherIconCode(cache.get(Constant.DAILY_COND_d) + String.valueOf(i + 2));
        }
        FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) rootView.findViewById(R.id.fancycoverflow);
        fancyCoverFlow.setAdapter(new ViewGroupExampleAdapter(images));
        return rootView;
    }

    private int getWeatherIconCode(String code) {
        int id = getResources().getIdentifier("w" + code, "drawable", "cchao.org.weatherapp");
        if(id != 0){
            return id;
        }
        return getResources().getIdentifier("w999", "drawable", "cchao.org.weatherapp");
    }


    private class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

        private int[] images = new int[6];

        public ViewGroupExampleAdapter(int[] images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Integer getItem(int i) {
            return images[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
            LayoutInflater inflater=(LayoutInflater) WeatherApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fragment_daily_fancycoverflow, null);
            view.setLayoutParams(new FancyCoverFlow.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT));
            return view;
        }
    }
}
