package cchao.org.weatherapp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rey.material.widget.TextView;

import java.util.ArrayList;

import cchao.org.weatherapp.R;
import cchao.org.weatherapp.WeatherApplication;

/**
 * Created by chenchao on 15/11/27.
 */
public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.ViewHolder>{

    private ArrayList<String> mDataTime;
    private ArrayList<String> mDataTmp;
    private ArrayList<String> mDataCondText;
    private ArrayList<String> mDataCondImage;

    public void setmDataTime(ArrayList<String> mDataTime) {
        this.mDataTime = mDataTime;
    }

    public void setmDataCondImage(ArrayList<String> mDataCondImage) {
        this.mDataCondImage = mDataCondImage;
    }

    public void setmDataCondText(ArrayList<String> mDataCondText) {
        this.mDataCondText = mDataCondText;
    }

    public void setmDataTmp(ArrayList<String> mDataTmp) {
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
        viewHolder.mCondImageView.setImageDrawable(getImage(mDataCondImage.get(i)));
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

    /**
     * 获取天气图标
     * @param imageName 天气名称(数字)
     * @return 天气图标drawable对象
     */
    private Drawable getImage(String imageName) {
        int id = WeatherApplication.getInstance().getResources().getIdentifier("w" + imageName, "drawable", "cchao.org.weatherapp");
        if(id != 0){
            return WeatherApplication.getInstance().getResources().getDrawable(id);
        }
        return WeatherApplication.getInstance().getResources().getDrawable(WeatherApplication.getInstance().getResources().getIdentifier("w999", "drawable", "cchao.org.weatherapp"));
    }
}
