package ru.getlect.evendate.evendate.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.getlect.evendate.evendate.data.EvendateContract.EventEntry;
import ru.getlect.evendate.evendate.data.EvendateContract.OrganizationEntry;
import ru.getlect.evendate.evendate.data.EvendateContract.SubscriptionEntry;
import ru.getlect.evendate.evendate.data.EvendateContract.TagEntry;
import ru.getlect.evendate.evendate.data.EvendateContract.UserEntry;
import ru.getlect.evendate.evendate.data.EvendateContract.FavoriteEventEntry;
import ru.getlect.evendate.evendate.data.EvendateContract.EventTagEntry;
/**
 * Created by Dmitry on 02.09.2015.
 */
public class EvendateDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "evendate.db";
    public EvendateDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //TODO добавить уникальные ключи + ON CONFLICT REPLACE к ним
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_EVENTS_TABLE = "CREATE TABLE " + EventEntry.TABLE_NAME + " (" +
                EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EventEntry.COLUMN_TITLE + " LONGTEXT, " +
                EventEntry.COLUMN_DESCRIPTION + " LONGTEXT, " +
                EventEntry.COLUMN_LOCATION_TEXT + " LONGTEXT, " +
                EventEntry.COLUMN_LOCATION_URI + " LONGTEXT, " +
                EventEntry.COLUMN_START_DATE + " DATE NOT NULL, " +
                EventEntry.COLUMN_NOTIFICATIONS + " INTEGER, " +
                EventEntry.COLUMN_ORGANIZATION_ID + " INTEGER NOT NULL, " +
                EventEntry.COLUMN_LATITUDE + " REAL, " +
                EventEntry.COLUMN_LONGITUDE + " REAL, " +
                EventEntry.COLUMN_END_DATE + " DATE NOT NULL, " +
                EventEntry.COLUMN_IMAGE_VERTICAL_URL + " LONGTEXT, " +
                EventEntry.COLUMN_DETAIL_INFO_URL + " LONGTEXT, " +
                EventEntry.COLUMN_BEGIN_TIME + " TIME, " +
                EventEntry.COLUMN_END_TIME + " TIME, " +
                EventEntry.COLUMN_IMAGE_HORIZONTAL_URL + " LONGTEXT, " +
                EventEntry.COLUMN_LOCATION_JSON + " LONGTEXT, " +
                " FOREIGN KEY (" + EventEntry.COLUMN_ORGANIZATION_ID + ") REFERENCES " +
                OrganizationEntry.TABLE_NAME + " (" + OrganizationEntry._ID + ")" +
                " );";
        final String SQL_CREATE_EVENTS_TAGS_TABLE =
                "CREATE TABLE " + EventTagEntry.TABLE_NAME + " (" +
                EventTagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EventTagEntry.COLUMN_EVENT_ID + " INTEGER, " +
                EventTagEntry.COLUMN_TAG_ID + " INTEGER, " +
                " FOREIGN KEY (" + EventTagEntry.COLUMN_EVENT_ID + ") REFERENCES " +
                EventEntry.TABLE_NAME + " (" + EventEntry._ID + "), " +
                " FOREIGN KEY (" + EventTagEntry.COLUMN_TAG_ID + ") REFERENCES " +
                TagEntry.TABLE_NAME + " (" + TagEntry._ID + ")" +
                " );";
        final String SQL_CREATE_FAVORITE_EVENTS_TABLE =
                "CREATE TABLE " + FavoriteEventEntry.TABLE_NAME + " (" +
                FavoriteEventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteEventEntry.COLUMN_EVENT_ID + " INTEGER, " +
                FavoriteEventEntry.COLUMN_EVENT_DATE + " DATE NOT NULL, " +
                " FOREIGN KEY (" + FavoriteEventEntry.COLUMN_EVENT_ID + ") REFERENCES " +
                EventEntry.TABLE_NAME + " (" + EventEntry._ID + ")" +
                " );";
        final String SQL_CREATE_ORGANIZATIONS_TABLE =
                "CREATE TABLE " + OrganizationEntry.TABLE_NAME + " (" +
                        OrganizationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        OrganizationEntry.COLUMN_NAME + " VARCHAR(255), " +
                        OrganizationEntry.COLUMN_IMG_URL + " LONGTEXT, " +
                        OrganizationEntry.COLUMN_IMG + " VARCHAR(255), " +
                        OrganizationEntry.COLUMN_SHORT_NAME + " VARCHAR(255), " +
                        OrganizationEntry.COLUMN_BACKGROUND_IMG_URL + " LONGTEXT, " +
                        OrganizationEntry.COLUMN_DESCRIPTION + " LONGTEXT, " +
                        OrganizationEntry.COLUMN_TYPE_NAME + " VARCHAR(255), " +
                        OrganizationEntry.COLUMN_TYPE_ID + " INTEGER" +
                " );";
        final String SQL_CREATE_SUBSCRIPTIONS_TABLE =
                "CREATE TABLE " + SubscriptionEntry.TABLE_NAME + " (" +
                SubscriptionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SubscriptionEntry.COLUMN_ORGANIZATION_ID + " INTEGER, " +
                " FOREIGN KEY (" + SubscriptionEntry.COLUMN_ORGANIZATION_ID + ") REFERENCES " +
                OrganizationEntry.TABLE_NAME + " (" + OrganizationEntry._ID + ")" +
                " );";
        final String SQL_CREATE_TAGS_TABLE = "CREATE TABLE " + TagEntry.TABLE_NAME + " (" +
                TagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TagEntry.COLUMN_NAME + " VARCHAR(255)" +
                " );";
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserEntry.COLUMN_LAST_NAME + " VARCHAR(255), " +
                UserEntry.COLUMN_FIRST_NAME + " VARCHAR(255), " +
                UserEntry.COLUMN_MIDDLE_NAME + " VARCHAR(255), " +
                UserEntry.COLUMN_AVATAR_URL + " LONGTEXT" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_EVENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_EVENTS_TAGS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_EVENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ORGANIZATIONS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SUBSCRIPTIONS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TAGS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EvendateContract.EventEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EvendateContract.EventTagEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EvendateContract.FavoriteEventEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EvendateContract.OrganizationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EvendateContract.SubscriptionEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EvendateContract.TagEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EvendateContract.UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
