package com.example.tacademy.sampleaddressbook1;

import android.provider.BaseColumns;

/**
 * Created by Tacademy on 2016-02-16.
 */
public class DBContact {
    public interface AddressBook extends BaseColumns{
        public static final String TABLE_NAME = "addressBook";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_HOME = "home";
        public static final String COLUMN_OFFICE = "office";
    }
}
