package cchao.org.weatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import cchao.org.weatherapp.R;

/**
 * Created by chenchao on 15/11/13.
 */
public class MainActivity extends AppCompatActivity{

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.activity_main);

        final Animation floating_animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.floating_out);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionButton.setVisibility(View.GONE);
                mFloatingActionButton.startAnimation(floating_animation);
                //startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

}
