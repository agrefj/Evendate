package ru.getlect.evendate.evendate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment {

    Button btn_VK;




    public AuthFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);



//        VKSdk.login(AuthFragment, VKScopes.EMAIL);





        return textView;
    }







}
