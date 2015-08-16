package ru.getlect.evendate.evendate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Spinner;

/**
 * Created by fj on 14.08.2015.
 */
public class AddEventActivity extends ActionBarActivity implements View.OnClickListener{

    TextView tv_add_event_time;
    LinearLayout ll_location;
    LinearLayout ll_photo;
    Spinner spinner;

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

        ll_location = (LinearLayout)findViewById(R.id.ll_location);
        ll_location.setOnClickListener(this);

        ll_photo = (LinearLayout)findViewById(R.id.ll_photo);
        ll_photo.setOnClickListener(this);

        spinner = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[5];
        int halfHour = 30;
        for(int i = 0; i <items.length; i++) {
            int time = halfHour * i;
            items[i] = "За " + String.valueOf(time) + " минут";}
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spinner.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_event_time:
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), "нажато что ль?", Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.ll_location:
                Intent intent_map_fragment = new Intent(this, MapsActivity.class);
                startActivity(intent_map_fragment);
                break;
            case R.id.ll_photo:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Complete action using"), 1);
                break;

        }

    }
}
