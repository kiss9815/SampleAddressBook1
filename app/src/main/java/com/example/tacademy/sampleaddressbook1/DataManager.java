package com.example.tacademy.sampleaddressbook1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-02-16.
 */
public class DataManager extends SQLiteOpenHelper {

    private static DataManager instance;
    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    private static final String DB_NAME = "addressbook";
    private static final int DB_VERSION = 1;

    public DataManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + DBContact.AddressBook.TABLE_NAME +"(" +
                DBContact.AddressBook._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContact.AddressBook.COLUMN_NAME+" TEXT NOT NULL," +
                DBContact.AddressBook.COLUMN_PHONE+" TEXT," +
                DBContact.AddressBook.COLUMN_HOME+" TEXT," +
                DBContact.AddressBook.COLUMN_OFFICE+" TEXT" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor getAddressCursor(String keyword){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DBContact.AddressBook._ID,
                DBContact.AddressBook.COLUMN_NAME,
                DBContact.AddressBook.COLUMN_PHONE,
                DBContact.AddressBook.COLUMN_HOME,
                DBContact.AddressBook.COLUMN_OFFICE};

        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.isEmpty(keyword)) {
            selection = DBContact.AddressBook.COLUMN_NAME + " LIKE ? OR "+ DBContact.AddressBook.COLUMN_HOME+" LIKE ?";
            selectionArgs = new String[]{"%"+keyword+"%", "%" + keyword + "%"};
        }

        String groupBy = null;
        String having = null;
        String orderBy = DBContact.AddressBook.COLUMN_NAME+" COLLATE LOCALIZED ASC";
        Cursor c = db.query(DBContact.AddressBook.TABLE_NAME,columns,selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    public List<AddressData> getAddressList(String keyword){
        List<AddressData> list = new ArrayList<AddressData>();
        Cursor c = getAddressCursor(keyword);
        int idIndex = c.getColumnIndex(DBContact.AddressBook._ID);
        int nameIndex = c.getColumnIndex(DBContact.AddressBook.COLUMN_NAME);
        int phoneIndex = c.getColumnIndex(DBContact.AddressBook.COLUMN_PHONE);
        int homeIndex = c.getColumnIndex(DBContact.AddressBook.COLUMN_HOME);
        int officeIndex = c.getColumnIndex(DBContact.AddressBook.COLUMN_OFFICE);
        while(c.moveToNext()) {
            AddressData data = new AddressData();
            data._id = c.getLong(idIndex);
            data.name = c.getString(nameIndex);
            data.phone = c.getString(phoneIndex);
            data.home = c.getString(homeIndex);
            data.office = c.getString(officeIndex);
            list.add(data);
        }
        c.close();
        return list;
    }

    ContentValues values = new ContentValues();
    public void insertAddress(AddressData data) {
        if (data._id == AddressData.INVALID_ID) {
            SQLiteDatabase db = getWritableDatabase();
            values.clear();
            values.put(DBContact.AddressBook.COLUMN_NAME, data.name);
            values.put(DBContact.AddressBook.COLUMN_PHONE, data.phone);
            values.put(DBContact.AddressBook.COLUMN_HOME, data.home);
            values.put(DBContact.AddressBook.COLUMN_OFFICE, data.office);
            db.insert(DBContact.AddressBook.TABLE_NAME, null, values);
        } else {
            updateAddress(data);
        }
    }

    public void updateAddress(AddressData data) {
        if (data._id == AddressData.INVALID_ID) {
            insertAddress(data);
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContact.AddressBook.COLUMN_NAME, data.name);
        values.put(DBContact.AddressBook.COLUMN_PHONE, data.phone);
        values.put(DBContact.AddressBook.COLUMN_HOME, data.home);
        values.put(DBContact.AddressBook.COLUMN_OFFICE, data.office);

        String where = DBContact.AddressBook._ID  + " = ?";
        String[] args = new String[]{""+data._id};
        db.update(DBContact.AddressBook.TABLE_NAME, values, where,args);
    }

    public void deleteAddress(AddressData data) {
        if (data._id == AddressData.INVALID_ID) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        String where = DBContact.AddressBook._ID + " = ?";
        String[] args = new String[]{""+data._id};
        db.delete(DBContact.AddressBook.TABLE_NAME, where, args);
    }


}
