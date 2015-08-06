package ru.getlect.evendate.evendate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by fj on 06.08.2015.
 */
public class AuthActivity extends FragmentActivity {

    private static final String[] sMyScope = new String[]{
            VKScope.EMAIL
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        LoginFragment loginFragment = new LoginFragment();
    }



    public static class LoginFragment extends android.support.v4.app.Fragment {
        public LoginFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.activity_login, container, false);
            v.findViewById(R.id.btn_auth).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    VKSdk.login(getActivity(), sMyScope);

                }
            });

            return v;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                Toast.makeText(AuthActivity.this, "onResult", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
                Toast.makeText(AuthActivity.this, "onError", Toast.LENGTH_SHORT);
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback) ) {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }






}
