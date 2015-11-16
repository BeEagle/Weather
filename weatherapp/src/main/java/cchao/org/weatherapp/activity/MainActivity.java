package cchao.org.weatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cchao.org.weatherapp.R;
import cchao.org.weatherapp.utils.HttpUtil;

/**
 * Created by chenchao on 15/11/13.
 */
public class MainActivity extends AppCompatActivity{

    private Toolbar mToolbar;

    private FloatingActionButton mFloatingActionButton;

    private HttpUtil httpUtil;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Weather");

        //final Animation floating_animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.floating_out);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpUtil = new HttpUtil(MainActivity.this, "CN101020300");
                httpUtil.getWeather();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_main_location) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
