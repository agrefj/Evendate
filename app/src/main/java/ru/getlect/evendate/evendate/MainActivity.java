package ru.getlect.evendate.evendate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnDateChangedListener, View.OnClickListener {


    private NavigationDrawerFragment mNavigationDrawerFragment;
    private TextView tv_bottom;
    private CharSequence mTitle;
    private android.support.v7.app.ActionBarDrawerToggle toggle;

    public int[] arrayID = new int[100];
    public String[] arrayTitle = new String[20];
    public String[] arrayDescription = new String[20];
    public String[] arrayEventStartDate = new String[20];
    public String[] arrayEventEndDate = new String[20];
    public String[] arrayOrganizationName = new String[20];

    // Will contain the raw JSON response as a string.
    public String eventsJsonStr = null;

    Button btn_VK;
    Button btn_add_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        btn_VK = (Button)findViewById(R.id.btn_VK);
        btn_VK.setOnClickListener(this);

        btn_add_event = (Button)findViewById(R.id.btn_add_event);
        btn_add_event.setOnClickListener(this);



        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);



        tv_bottom = (TextView)findViewById(R.id.tv_bottom);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

//         Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        MaterialCalendarView widget = (MaterialCalendarView) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);

        widget.addDecorator(new DisableAllDaysDecorator());
//        widget.addDecorator(new EnableOneToTenDecorator());

        FetchEventsTask fetchEventsTask = new FetchEventsTask();
        fetchEventsTask.execute();


    }

    @Override
    public void onDateChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
        String stringDay = String.valueOf(calendarDay);
        Toast.makeText(this, stringDay, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_VK:
                intent = new Intent(this,WebAuthActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add_event:
                intent = new Intent(this,DialogsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private static class DisableAllDaysDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.getDay() <=31;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }

//        private static boolean[] PRIME_TABLE = {
//                false,  // 0?
//                true,
//                false,// 2
//                false,// 3
//                false,
//                false,// 5
//                false,
//                false,// 7
//                false,
//                false,
//                false,
//                false,// 11
//                false,
//                false,// 13
//                false,
//                false,
//                false,
//                false,// 17
//                false,
//                false,// 19
//                false,
//                false,
//                false,
//                true,// 23
//                true,
//                true,
//                true,
//                true,
//                true,
//                true,// 29
//                false,
//                false,// 31
//                false,
//                false,
//                false, //PADDING
//        };
    }

//    private static class EnableOneToTenDecorator implements DayViewDecorator {
//
//        @Override
//        public boolean shouldDecorate(CalendarDay day) {
//            return day.getDay() <= 10;
//        }
//
//        @Override
//        public void decorate(DayViewFacade view) {
//            view.setDaysDisabled(false);
//        }
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                
                break;
            case 2:

                break;
            case 3:

                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class FetchEventsTask extends AsyncTask<Void,Void,String> {
        private final String LOG_TAG = FetchEventsTask.class.getSimpleName();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        @Override
        protected String doInBackground(Void...params){
            try {
                URL url = new URL("http://evendate.ru/all.json");

                // Create the request and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream inso String eventsJsonStr
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                eventsJsonStr = buffer.toString();

            }

            catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return eventsJsonStr;

        }


        @Override
        protected void onPostExecute(String eventsJsonStr){
        super.onPostExecute(eventsJsonStr);



            JSONObject eventsJSONObject = null;

            //for parsing
            final String DATA = "data";
            final String ID = "id";
            final String TITLE = "title";
            final String DESCRIPTION = "description";
            final String EVENT_START = "event_start_date";
            final String EVENT_END = "event_end_date";

            //for saving
            Integer data_id = null;
            String data_title = "";
            String data_description ="";
            String data_event_start = "";
            String data_event_end = "";


            try {
                eventsJSONObject = new JSONObject(eventsJsonStr);
                JSONArray eventsArray = eventsJSONObject.getJSONArray(DATA);

                JSONObject first_event = eventsArray.getJSONObject(0);
                data_id = first_event.getInt(ID);
                data_title = first_event.getString(TITLE);
                data_description = first_event.getString(DESCRIPTION);
                data_event_start = first_event.getString(EVENT_START);
                data_event_end = first_event.getString(EVENT_END);



                Log.e(LOG_TAG, "FIRST ID: " + data_id);
                Log.e(LOG_TAG, "FIRST TITLE: " + data_title);
                Log.e(LOG_TAG, "FIRST DESCRIPTION: " + data_description);
                Log.e(LOG_TAG, "FIRST EVENT START: " + data_event_start);
                Log.e(LOG_TAG, "FIRST EVENT END: " + data_event_end);







//                for(int i=0; i<4; i++){
//
//
//                }


            }

            catch (JSONException e){
                e.printStackTrace();
            }

            catch (NullPointerException e){
                e.printStackTrace();
            }


        }



    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
