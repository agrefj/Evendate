package ru.getlect.evendate.evendate;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fj on 14.08.2015.
 */
public class AddEventActivity extends ActionBarActivity implements View.OnClickListener{

    TextView tv_add_event_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Новое");

        tv_add_event_time = (TextView)findViewById(R.id.tv_add_event_time);
        tv_add_event_time.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_event_time:

                Toast toast;
                toast = Toast.makeText(getApplicationContext(), "нажато что ль?", Toast.LENGTH_LONG);
                toast.show();



                break;

        }

    }
}
