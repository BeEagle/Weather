package cchao.org.weatherapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rey.material.widget.TextView;

import java.util.ArrayList;

import cchao.org.weatherapp.R;
import cchao.org.weatherapp.utils.WeatherIconUtil;

/**
 * Created by chenchao on 15/11/27.
 */
public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.ViewHolder>{

    private ArrayList<String> mDataTime;
    private ArrayList<String> mDataTmp;
    private ArrayList<String> mDataCondText;
    private ArrayList<String> mDataCondImage;

    public void setmData(ArrayList<String> mDataTime
            , ArrayList<String> mDataCondImage
            , ArrayList<String> mDataCondText
            , ArrayList<String> mDataTmp) {
        this.mDataTime = mDataTime;
        this.mDataCondImage = mDataCondImage;
        this.mDataCondText = mDataCondText;
        this.mDataTmp = mDataTmp;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_daily_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        viewHolder.mTimeTextView.setText(mDataTime.get(i));
        viewHolder.mTmpTextView.setText(mDataTmp.get(i));
        viewHolder.mCondTextView.setText(mDataCondText.get(i));
        viewHolder.mCondImageView.setImageDrawable(WeatherIconUtil.getWeatherIcon(mDataCondImage.get(i)));
    }

    @Override
    public int getItemCount() {
        return mDataTime.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTimeTextView, mTmpTextView, mCondTextView;
        public ImageView mCondImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTimeTextView = (TextView) itemView.findViewById(R.id.recycler_daily_item_time);
            mTmpTextView = (TextView) itemView.findViewById(R.id.recycler_daily_item_tmp);
            mCondTextView = (TextView) itemView.findViewById(R.id.recycler_daily_item_cond_text);
            mCondImageView = (ImageView) itemView.findViewById(R.id.recycler_daily_item_cond_image);
        }
    }
}
