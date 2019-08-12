package com.nikdemo.notificationaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper  extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHelper";

    private static final String DB_NAME = "Database";
    private static final int DB_VERSION = 1;

    private static final String TBL_USER = "tbl_user";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PACKAGE = "package";

    private static final String TBL_CHAT = "tbl_chat";
    private static final String USER_ID = "pages_id";
    private static final String CHAT_TEXT = "text";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlBOOK = "CREATE TABLE "+TBL_USER+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE+" VARCHAR"+","
                +PACKAGE+" VARCHAR"+""
                + ");";

        String sqlPAGES = "CREATE TABLE "+TBL_CHAT+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +USER_ID+" VARCHAR,"
                +CHAT_TEXT+" VARCHAR)";

        db.execSQL(sqlBOOK);
        db.execSQL(sqlPAGES);

    }


    void addUser(String userName, String packageName){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE,userName);
        contentValues.put(PACKAGE,packageName);
        db.insert(TBL_USER, null,contentValues);
        db.close();
    }


    boolean checkUserIsExist(){
        SQLiteDatabase db =getReadableDatabase();

        String select = "Select * from "+TBL_USER+" Where("+TITLE+")";
        return true;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_CHAT);

        onCreate(db);
    }
}
