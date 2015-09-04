package ru.getlect.evendate.evendate.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Dmitry on 03.09.2015.
 */
public class EvendateProvider extends ContentProvider {
    private static final String LOG_TAG = ContentProvider.class.getSimpleName();

    private static final int EVENTS = 0;

    private EvendateDBHelper mEvendateDBHelper;
    private final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate(){
        mEvendateDBHelper = new EvendateDBHelper(getContext());
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY, EvendateContract.PATH_EVENTS, EVENTS);
        return true;
    }


    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder){
        switch(mUriMatcher.match(uri)){
            case EVENTS: {
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.EventEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(), EvendateContract.EventEntry.CONTENT_URI);

                return cursor;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        final SQLiteDatabase db = mEvendateDBHelper.getWritableDatabase();
        Uri returnUri;
        switch (mUriMatcher.match(uri)){
            case EVENTS: {
                long id = db.insert(EvendateContract.EventEntry.TABLE_NAME, null, values);
                if( id > 0 )
                    returnUri = ContentUris.withAppendedId(EvendateContract.EventEntry.CONTENT_URI, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(final Uri uri) {

        switch (mUriMatcher.match(uri)) {
            case EVENTS:
                return EvendateContract.EventEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
}
