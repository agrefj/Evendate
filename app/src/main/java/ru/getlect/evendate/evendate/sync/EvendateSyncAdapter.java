package ru.getlect.evendate.evendate.sync;

/**
 * Created by Dmitry on 08.09.2015.
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import ru.getlect.evendate.evendate.R;
import ru.getlect.evendate.evendate.data.EvendateContract;

public class EvendateSyncAdapter extends AbstractThreadedSyncAdapter {
    String LOG_TAG = EvendateSyncAdapter.class.getSimpleName();
    // Global variables
    // Define a variable to contain a content resolver instance
    //ContentResolver mContentResolver;
    private static final String[] ORGANIZATION_PROJECTION = new String[] {
            EvendateContract.OrganizationEntry._ID,
            EvendateContract.OrganizationEntry.COLUMN_ORGANIZATION_ID,
            EvendateContract.OrganizationEntry.COLUMN_NAME,
            EvendateContract.OrganizationEntry.COLUMN_IMG_URL,
            EvendateContract.OrganizationEntry.COLUMN_SHORT_NAME,
            EvendateContract.OrganizationEntry.COLUMN_DESCRIPTION,
            EvendateContract.OrganizationEntry.COLUMN_TYPE_NAME,
            EvendateContract.OrganizationEntry.COLUMN_SUBSCRIBED_COUNT
    };
    // Constants representing column positions from PROJECTION.
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_ORGANIZATION_ID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_IMG_URL = 3;
    public static final int COLUMN_SHORT_NAME = 4;
    public static final int COLUMN_DESCRIPTION = 5;
    public static final int COLUMN_TYPE_NAME = 6;
    public static final int COLUMN_SUBSCRIBED_COUNT = 7;

    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public EvendateSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public EvendateSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }
    /*
     * Specify the code you want to run in the sync adapter. The entire
     * sync adapter runs in a background thread, so you don't have to set
     * up your own background processing.
     */
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {

        //token here
        String basicAuth = "";
        //getEvents(basicAuth);
        updateOrganization(basicAuth);
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }
    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }
    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        //SunshineSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        //ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }
    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    private void getEvents(String token){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String eventsJsonStr = null;

        try {
            URL url = new URL("http://evendate.ru/api/events");

            // Create the request and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.connect();

            //Read the input stream inso String eventsJsonStr
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }

            eventsJsonStr = buffer.toString();
            Log.w(LOG_TAG, eventsJsonStr);
            //TODO parsing

        }

        catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attemping
            // to parse it.
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

    }

    private void updateOrganization(String token){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JsonString = null;

        try {
            URL url = new URL("http://evendate.ru/api/organizations");

            // Create the request and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.connect();

            //Read the input stream inso String eventsJsonStr
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }

            JsonString = buffer.toString();
            Log.w(LOG_TAG, JsonString);
            ArrayList<OrganizationEntry> organizationsList = getOrganizationDataFromJson(JsonString);
            mergeOrganizationData(organizationsList);
        }

        catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attemping
            // to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }  finally {
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
    }

    public static class OrganizationEntry {
        public final int organization_id;
        public final String name;
        public final String img_url;
        public final String short_name;
        public final String description;
        public final String type_name;
        public final int subscribed_count;

        OrganizationEntry(int organization_id, String name, String img_url, String short_name,
                          String description, String type_name, int subscribed_count) {
            this.organization_id = organization_id;
            this.name = name;
            this.img_url = img_url;
            this.short_name = short_name;
            this.description = description;
            this.type_name = type_name;
            this.subscribed_count = subscribed_count;
        }
    }
    private ArrayList<OrganizationEntry> getOrganizationDataFromJson(String JsonStr) throws JSONException {


        ArrayList<OrganizationEntry> cVList = new ArrayList<OrganizationEntry>();

        // Location information
        final String DATA = "data";
        final String ORGANIZATION_ID = "id";
        final String DESCRIPTION = "description";
        final String NAME = "name";
        final String IMG_URL = "img_url";
        final String SHORT_NAME = "short_name";
        final String TYPE_NAME = "type_name";
        final String SUBSCRIBED_COUNT = "subscribed_count";

        try {
            JSONObject organizationsJson = new JSONObject(JsonStr);
            JSONArray organizationsArray = organizationsJson.getJSONArray(DATA);

            for(int i = 0; i < organizationsArray.length(); i++) {

                JSONObject organizationJson = organizationsArray.getJSONObject(i);

                int organization_id = organizationJson.getInt(ORGANIZATION_ID);
                String description = organizationJson.getString(DESCRIPTION);
                String name = organizationJson.getString(NAME);
                String img_url = organizationJson.getString(IMG_URL);
                String type_name = organizationJson.getString(TYPE_NAME);
                String short_name = organizationJson.getString(SHORT_NAME);
                int subscribed_count = organizationJson.getInt(SUBSCRIBED_COUNT);

                OrganizationEntry organizationEntry = new OrganizationEntry(
                        organization_id, name, img_url, short_name, description, type_name, subscribed_count
                );
                cVList.add(organizationEntry);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return cVList;
    }
    private void mergeOrganizationData(ArrayList<OrganizationEntry> organizationsList){
        final ContentResolver contentResolver = getContext().getContentResolver();

        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        // Build hash table of incoming entries
        HashMap<Integer, OrganizationEntry> organizationMap = new HashMap<>();
        for (OrganizationEntry e : organizationsList) {
            organizationMap.put(e.organization_id, e);
        }

        // Get list of all items
        Log.i(LOG_TAG, "Fetching local entries for merge");
        Uri uri = EvendateContract.OrganizationEntry.CONTENT_URI; // Get all entries
        Cursor c = contentResolver.query(uri, ORGANIZATION_PROJECTION, null, null, null);
        assert c != null;
        Log.i(LOG_TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");

        // Find stale data
        int id;
        int organizationId;
        String name;
        String img_url;
        String short_name;
        String description;
        String type_name;
        int subscribed_count;
        while (c.moveToNext()) {
            id = c.getInt(COLUMN_ID);
            organizationId = c.getInt(COLUMN_ORGANIZATION_ID);
            name = c.getString(COLUMN_NAME);
            img_url = c.getString(COLUMN_IMG_URL);
            short_name = c.getString(COLUMN_SHORT_NAME);
            description = c.getString(COLUMN_DESCRIPTION);
            type_name = c.getString(COLUMN_TYPE_NAME);
            subscribed_count = c.getInt(COLUMN_SUBSCRIBED_COUNT);
            OrganizationEntry match = organizationMap.get(organizationId);
            if (match != null) {
                // Entry exists. Remove from entry map to prevent insert later.
                organizationMap.remove(organizationId);
                // Check to see if the entry needs to be updated
                Uri existingUri = EvendateContract.OrganizationEntry.CONTENT_URI.buildUpon()
                        .appendPath(Integer.toString(id)).build();
                if ((match.name != null && !match.name.equals(name)) ||
                        (match.img_url != null && !match.img_url.equals(img_url)) ||
                        (match.short_name != null && !match.short_name.equals(short_name)) ||
                        (match.type_name != null && !match.type_name.equals(type_name)) ||
                        (match.description != null && !match.description.equals(description)) ||
                        (match.subscribed_count != subscribed_count)) {
                    // Update existing record
                    Log.i(LOG_TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(EvendateContract.OrganizationEntry.COLUMN_NAME, match.name)
                            .withValue(EvendateContract.OrganizationEntry.COLUMN_IMG_URL, match.img_url)
                            .withValue(EvendateContract.OrganizationEntry.COLUMN_SHORT_NAME, match.short_name)
                            .withValue(EvendateContract.OrganizationEntry.COLUMN_DESCRIPTION, match.description)
                            .withValue(EvendateContract.OrganizationEntry.COLUMN_TYPE_NAME, match.type_name)
                            .withValue(EvendateContract.OrganizationEntry.COLUMN_SUBSCRIBED_COUNT, match.subscribed_count)
                            .build());
                } else {
                    Log.i(LOG_TAG, "No action: " + existingUri);
                }
            } else {
                // Entry doesn't exist. Remove it from the database.
                Uri deleteUri = EvendateContract.OrganizationEntry.CONTENT_URI.buildUpon()
                        .appendPath(Integer.toString(id)).build();
                Log.i(LOG_TAG, "Scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();

        // Add new items
        for (OrganizationEntry e : organizationMap.values()) {
            Log.i(LOG_TAG, "Scheduling insert: organization_id=" + e.organization_id);
            batch.add(ContentProviderOperation.newInsert(EvendateContract.OrganizationEntry.CONTENT_URI)
                    .withValue(EvendateContract.OrganizationEntry.COLUMN_ORGANIZATION_ID, e.organization_id)
                    .withValue(EvendateContract.OrganizationEntry.COLUMN_NAME, e.name)
                    .withValue(EvendateContract.OrganizationEntry.COLUMN_IMG_URL, e.img_url)
                    .withValue(EvendateContract.OrganizationEntry.COLUMN_SHORT_NAME, e.short_name)
                    .withValue(EvendateContract.OrganizationEntry.COLUMN_DESCRIPTION, e.description)
                    .withValue(EvendateContract.OrganizationEntry.COLUMN_TYPE_NAME, e.type_name)
                    .withValue(EvendateContract.OrganizationEntry.COLUMN_SUBSCRIBED_COUNT, e.subscribed_count)
                    .build());
        }
        Log.i(LOG_TAG, "Merge solution ready. Applying batch update");
        try {
            mContentResolver.applyBatch(EvendateContract.CONTENT_AUTHORITY, batch);
        }catch (Exception e){
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            return;
        }
        mContentResolver.notifyChange(
                EvendateContract.OrganizationEntry.CONTENT_URI, // URI where data was modified
                null,                           // No local observer
                false);                         // IMPORTANT: Do not sync to network
        // This sample doesn't support uploads, but if *your* code does, make sure you set
        // syncToNetwork=false in the line above to prevent duplicate syncs.
        Log.i(LOG_TAG, "Batch update done");
    }

}
