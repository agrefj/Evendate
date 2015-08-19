package ru.getlect.evendate.evendate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.rey.material.widget.Spinner;

/**
 * Created by fj on 14.08.2015.
 */
public class AddEventActivity extends AppCompatActivity {


    LinearLayout ll_location;
    LinearLayout ll_photo;
    LinearLayout ll_start_date;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogs_fragment);

        DialogsFragment dialogsFragment = new DialogsFragment();

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Новое");


//        spinner = (Spinner)findViewById(R.id.spinner);
//        String[] items = new String[5];
//        int halfHour = 30;
//        for(int i = 0; i <items.length; i++) {
//            int time = halfHour * i;
//            items[i] = "За " + String.valueOf(time) + " минут";}
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, items);
//        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
//        spinner.setAdapter(adapter);




    }

//
//    @Override
//    public void onClick(View v) {
//        Dialog.Builder builder = null;
//        switch (v.getId()) {
//            case R.id.ll_location:
//                Intent intent_map_fragment = new Intent(this, MapsActivity.class);
//                startActivity(intent_map_fragment);
//                break;
//            case R.id.ll_photo:
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,
//                        "Complete action using"), 1);
//                break;
//            case R.id.ll_start_date :
//                builder = new TimePickerDialog.Builder(R.style.SimpleDialogLight, 24, 00) {
//                    @Override
//                    public void onPositiveActionClicked(DialogFragment fragment) {
//                        TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
//                        super.onPositiveActionClicked(fragment);
//                    }
//
//                    @Override
//                    public void onNegativeActionClicked(DialogFragment fragment) {
//                        super.onNegativeActionClicked(fragment);
//                    }
//                } ;
//
//                builder.positiveAction("OK")
//                        .negativeAction("CANCEL");
//                break;
//
//
//
//        }
//
//        DialogFragment fragment = DialogFragment.newInstance(builder);
//        fragment.show(FragmentManager fragmentManager,);
//        fragment.show(getFragmentManager(), null);
//
//
//
//    }
}
