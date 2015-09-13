package ru.getlect.evendate.evendate.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Dmitry on 03.09.2015.
 */
public class EvendateProvider extends ContentProvider {
    private static final String LOG_TAG = ContentProvider.class.getSimpleName();

    private static final int ORGANIZATIONS = 100;
    private static final int ORGANIZATION_ID = 101;
    private static final int TAGS = 200;
    private static final int TAG_ID = 201;
    private static final int EVENTS = 301;
    private static final int EVENT_ID = 302;
    private static final int EVENT_TAGS = 303;
    private static final int EVENT_FRIENDS = 305;
    private static final int USERS = 400;
    private static final int USER_ID = 401;

    private EvendateDBHelper mEvendateDBHelper;
    private final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate(){
        mEvendateDBHelper = new EvendateDBHelper(getContext());
        //mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
        //      EvendateContract.PATH_EVENTS, EVENTS);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_ORGANIZATIONS + "/#", ORGANIZATION_ID);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_ORGANIZATIONS, ORGANIZATIONS);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_TAGS, TAGS);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_TAGS + "/#", TAG_ID);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_EVENTS, EVENTS);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_EVENTS + "/#", EVENT_ID);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_EVENTS + "/#/" + EvendateContract.PATH_TAGS, EVENT_TAGS);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_EVENTS + "/#/" + EvendateContract.PATH_USERS, EVENT_FRIENDS);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_USERS, USERS);
        mUriMatcher.addURI(EvendateContract.CONTENT_AUTHORITY,
                EvendateContract.PATH_USERS + "/#", USER_ID);
        return true;
    }

    @Override
    public String getType(final Uri uri) {

        switch (mUriMatcher.match(uri)) {
            case ORGANIZATIONS:
                return EvendateContract.OrganizationEntry.CONTENT_TYPE;
            case ORGANIZATION_ID:
                return EvendateContract.OrganizationEntry.CONTENT_ITEM_TYPE;
            case TAGS:
                return EvendateContract.TagEntry.CONTENT_TYPE;
            case TAG_ID:
                return EvendateContract.TagEntry.CONTENT_ITEM_TYPE;
            case EVENTS:
                return EvendateContract.EventEntry.CONTENT_TYPE;
            case EVENT_ID:
                return EvendateContract.EventEntry.CONTENT_ITEM_TYPE;
            case USERS:
                return EvendateContract.UserEntry.CONTENT_TYPE;
            case USER_ID:
                return EvendateContract.UserEntry.CONTENT_ITEM_TYPE;
            case EVENT_TAGS:
                return EvendateContract.TagEntry.CONTENT_TYPE;
            case EVENT_FRIENDS:
                return EvendateContract.UserEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection,
                        final String[] selectionArgs, final String sortOrder){
        switch(mUriMatcher.match(uri)){
            case ORGANIZATIONS: {
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.OrganizationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.OrganizationEntry.CONTENT_URI);
                return cursor;
            }
            case TAGS: {
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.TagEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.TagEntry.CONTENT_URI);
                return cursor;
            }
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
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.EventEntry.CONTENT_URI);
                return cursor;
            }
            case USERS: {
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.UserEntry.CONTENT_URI);
                return cursor;
            }
            case EVENT_ID: {
                String[] args = {uri.getLastPathSegment()};
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.EventEntry.TABLE_NAME,
                        projection,
                        EvendateContract.EventEntry._ID + "=?",
                        args,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.EventEntry.CONTENT_URI);
                return cursor;
            }
            case USER_ID: {
                String[] args = {uri.getLastPathSegment()};
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.UserEntry.TABLE_NAME,
                        projection,
                        EvendateContract.UserEntry._ID + "=?",
                        args,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.UserEntry.CONTENT_URI);
                return cursor;
            }
            case TAG_ID: {
                String[] args = {uri.getLastPathSegment()};
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.TagEntry.TABLE_NAME,
                        projection,
                        EvendateContract.TagEntry._ID + "=?",
                        args,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.TagEntry.CONTENT_URI);
                return cursor;
            }
            case ORGANIZATION_ID: {
                String[] args = {uri.getLastPathSegment()};
                final Cursor cursor = mEvendateDBHelper.getReadableDatabase().query(
                        EvendateContract.OrganizationEntry.TABLE_NAME,
                        projection,
                        EvendateContract.OrganizationEntry._ID + "=?",
                        args,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.OrganizationEntry.CONTENT_URI);
                return cursor;
            }
            case EVENT_TAGS: {

                final SQLiteQueryBuilder sTagsByEventsQueryBuilder
                        = new SQLiteQueryBuilder();

                //This is an inner join which looks like
                //weather INNER JOIN location ON weather.location_id = location._id
                sTagsByEventsQueryBuilder.setTables(
                        EvendateContract.EventEntry.TABLE_NAME + " INNER JOIN " +
                                EvendateContract.EventTagEntry.TABLE_NAME +
                                " ON " + EvendateContract.EventEntry.TABLE_NAME +
                                "." + EvendateContract.EventEntry._ID +
                                " = " + EvendateContract.EventTagEntry.TABLE_NAME +
                                "." + EvendateContract.EventTagEntry._ID + " INNER JOIN " +
                                EvendateContract.TagEntry.TABLE_NAME +
                                " ON " + EvendateContract.TagEntry.TABLE_NAME +
                                "." + EvendateContract.TagEntry._ID +
                                " = " + EvendateContract.EventTagEntry.TABLE_NAME +
                                "." + EvendateContract.EventTagEntry._ID
                );

                //events/1/tags
                String[] args = {uri.getPathSegments().get(1)};
                final Cursor cursor = sTagsByEventsQueryBuilder.query(
                        mEvendateDBHelper.getReadableDatabase(),
                        projection,
                        EvendateContract.OrganizationEntry._ID + "=?",
                        args,
                        null,
                        null,
                        sortOrder
                );
                cursor.setNotificationUri(getContext().getContentResolver(),
                        EvendateContract.EventTagEntry.CONTENT_URI);
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
            case ORGANIZATIONS: {
                long id = db.insert(EvendateContract.OrganizationEntry.TABLE_NAME, null, values);
                if( id > 0 )
                    returnUri = ContentUris.
                            withAppendedId(EvendateContract.OrganizationEntry.CONTENT_URI, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TAGS: {
                long id = db.insert(EvendateContract.TagEntry.TABLE_NAME, null, values);
                if( id > 0 )
                    returnUri = ContentUris.withAppendedId(EvendateContract.TagEntry.CONTENT_URI, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case EVENTS: {
                long id = db.insert(EvendateContract.EventEntry.TABLE_NAME, null, values);
                if( id > 0 )
                    returnUri = ContentUris.withAppendedId(EvendateContract.EventEntry.CONTENT_URI, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case USERS: {
                long id = db.insert(EvendateContract.UserEntry.TABLE_NAME, null, values);
                if( id > 0 )
                    returnUri = ContentUris.withAppendedId(EvendateContract.UserEntry.CONTENT_URI, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            //TODO че делать с id???
            case EVENT_TAGS: {
                long id = db.insert(EvendateContract.EventTagEntry.TABLE_NAME, null, values);
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
    public int update(final Uri uri, final ContentValues values, final String selection,
                      final String[] selectionArgs){
        final SQLiteDatabase db = mEvendateDBHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int rowsUpdated;
        String[] args = {uri.getLastPathSegment()};
        switch (match) {
            case ORGANIZATION_ID:
                rowsUpdated = db.update(
                        EvendateContract.OrganizationEntry.TABLE_NAME, values,
                        EvendateContract.OrganizationEntry._ID + "=?", args);
                break;
            case TAG_ID:
                rowsUpdated = db.update(
                        EvendateContract.TagEntry.TABLE_NAME, values,
                        EvendateContract.TagEntry._ID + "=?", args);
                break;
            case EVENT_ID:
                rowsUpdated = db.update(
                        EvendateContract.EventEntry.TABLE_NAME, values,
                        EvendateContract.EventEntry._ID + "=?", args);
                break;
            case USER_ID:
                rowsUpdated = db.update(
                        EvendateContract.UserEntry.TABLE_NAME, values,
                        EvendateContract.UserEntry._ID + "=?", args);
                break;
            //case EVENT_TAGS:
            //    //events/1/tags
            //    String event_id = uri.getPathSegments().get(1);
            //    String tag_id = uri.getQueryParameter("tag_id");
            //    String[] eventsTagsArgs = {event_id, tag_id};
            //    rowsUpdated = db.update(
            //            EvendateContract.EventEntry.TABLE_NAME, values,
            //            EvendateContract.EventTagEntry.COLUMN_EVENT_ID + "=?" +
            //            EvendateContract.EventTagEntry.COLUMN_TAG_ID + "=?", eventsTagsArgs);
            //    break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        final SQLiteDatabase db = mEvendateDBHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int rowsDeleted;
        String[] args = {uri.getLastPathSegment()};
        Log.i(LOG_TAG, "in delete selection " + selection);
        for(String s : selectionArgs)
            Log.i(LOG_TAG, "in delete selectionArgs " + s);
        // this makes delete all rows return the number of rows deleted
        switch (match) {
            case ORGANIZATION_ID:
                rowsDeleted = db.delete(
                        EvendateContract.OrganizationEntry.TABLE_NAME,
                        EvendateContract.OrganizationEntry._ID + "=?", args);
                break;
            case TAG_ID:
                rowsDeleted = db.delete(
                        EvendateContract.TagEntry.TABLE_NAME,
                        EvendateContract.TagEntry._ID + "=?", args);
                break;
            case EVENT_ID:
                rowsDeleted = db.delete(
                        EvendateContract.TagEntry.TABLE_NAME,
                        EvendateContract.TagEntry._ID + "=?", args);
                break;
            case USER_ID:
                rowsDeleted = db.delete(
                        EvendateContract.UserEntry.TABLE_NAME,
                        EvendateContract.UserEntry._ID + "=?", args);
                break;
//            case EVENT_TAGS:
//                //events/1/tags
//                String event_id = uri.getPathSegments().get(1);
//                String tag_id = uri.getQueryParameter("tag_id");
//                String[] eventsTagsArgs = {event_id, tag_id};
//                rowsDeleted = db.delete(
//                        EvendateContract.EventTagEntry.TABLE_NAME,
//                        EvendateContract.EventTagEntry.COLUMN_EVENT_ID + "=?" +
//                        EvendateContract.EventTagEntry.COLUMN_TAG_ID + "=?", eventsTagsArgs);
//                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

}
