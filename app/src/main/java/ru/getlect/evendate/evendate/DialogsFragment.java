package ru.getlect.evendate.evendate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;

/**
 * Created by fj on 17.08.2015.
 */
public class DialogsFragment extends Fragment implements View.OnClickListener{

    LinearLayout ll_location;
    LinearLayout ll_photo;
    LinearLayout ll_start_date;
    Spinner spinner;
    Toast toast;


    public static DialogsFragment newInstance() {
        DialogsFragment fragment = new DialogsFragment();
        return fragment;
    }

    private AddEventActivity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.add_event, container, false);



        ll_location = (LinearLayout) v.findViewById(R.id.ll_location);
        ll_location.setOnClickListener(this);

        ll_photo = (LinearLayout)v.findViewById(R.id.ll_photo);
        ll_photo.setOnClickListener(this);

        ll_start_date = (LinearLayout)v.findViewById(R.id.ll_start_date);
        ll_start_date.setOnClickListener(this);

//        spinner = (Spinner)v.findViewById(R.id.spinner);
//        String[] items = new String[5];
//        int halfHour = 30;
//        for(int i = 0; i <items.length; i++) {
//            int time = halfHour * i;
//            items[i] = "За " + String.valueOf(time) + " минут";}
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, items);
//        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
//        spinner.setAdapter(adapter);


        mActivity = (AddEventActivity)getActivity();

        return v;
    }


    @Override
    public void onClick(View v){
        Dialog.Builder builder = null;

        switch (v.getId()) {
            case R.id.ll_start_date :
            builder = new TimePickerDialog.Builder(R.style.SimpleDialogLight, 24, 00) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
                    Toast.makeText(mActivity, "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                    super.onPositiveActionClicked(fragment);
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();
                    super.onNegativeActionClicked(fragment);
                }
            } ;

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");



                toast.makeText(getActivity(), "НАЖАТО ЭТО ДЕРЬМО", Toast.LENGTH_SHORT).show();
                break;
        }

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(),null);



    }












}
