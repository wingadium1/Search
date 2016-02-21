package com.github.wingadium.search.ui.common;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.net.URISyntaxException;

public class FileUtils {

    private static final String STRING_CONTENT = "content";

    private static final String STRING_DATA = "_data";

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if (STRING_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {STRING_DATA};
            Cursor cursor;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = 0;
                if (cursor != null) {
                    column_index = cursor.getColumnIndexOrThrow(STRING_DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
