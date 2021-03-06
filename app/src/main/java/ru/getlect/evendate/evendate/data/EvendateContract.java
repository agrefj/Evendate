package ru.getlect.evendate.evendate.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dmitry on 02.09.2015.
 */
public class EvendateContract {
    public static final String CONTENT_AUTHORITY = "ru.getlect.evendate.evendate";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_EVENTS = "events";
    public static final String PATH_ORGANIZATIONS = "organizations";
    public static final String PATH_TAGS = "tags";
    public static final String PATH_USERS = "users";

    public static final class EventEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENTS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENTS;

        public static final String TABLE_NAME = "events";
        public static final String COLUMN_EVENT_ID = "event_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LOCATION_TEXT = "location";
        public static final String COLUMN_LOCATION_URI = "location_uri";
        public static final String COLUMN_LOCATION_JSON = "location_object";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_START_DATE = "event_start_date";
        public static final String COLUMN_END_DATE = "event_end_date";
        public static final String COLUMN_NOTIFICATIONS = "notifications_schema_json";
        public static final String COLUMN_ORGANIZATION_ID = "organization_id";
        public static final String COLUMN_IMAGE_VERTICAL_URL = "image_vertical_url";
        public static final String COLUMN_IMAGE_HORIZONTAL_URL = "image_horizontal_url";
        public static final String COLUMN_DETAIL_INFO_URL = "detail_info_url";
        public static final String COLUMN_BEGIN_TIME = "begin_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_CAN_EDIT = "can_edit";
        public static final String COLUMN_EVENT_TYPE = "event_type_latin_name";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";
    }
    public static final class EventTagEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENTS).appendPath(PATH_TAGS).build();
        public static final String TABLE_NAME = "events_tags";
        public static final String COLUMN_EVENT_ID = "event_id";
        public static final String COLUMN_TAG_ID = "tag_id";
    }
    public static final class OrganizationEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ORGANIZATIONS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/organizations";
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/organization";

        public static final String TABLE_NAME = "organizations";
        public static final String COLUMN_ORGANIZATION_ID = "organization_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMG_URL = "img_url";
        public static final String COLUMN_SHORT_NAME = "short_name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TYPE_NAME = "type_name";
        public static final String COLUMN_SUBSCRIBED = "subscribed";
    }
    public static final class TagEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAGS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/tags";
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/tag";

        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_TAG_ID = "tag_id";
        public static final String COLUMN_NAME = "name";
    }
    public static final class UserEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/users";
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/user";

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_MIDDLE_NAME = "middle_name";
        public static final String COLUMN_AVATAR_URL = "avatar_url";
        public static final String COLUMN_FRIEND_UID = "friend_uid";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_LINK = "link";
    }
    public static final class UserEventEntry implements BaseColumns{
        public static final String TABLE_NAME = "events_users";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_EVENT_ID = "event_id";
    }
}
