package fr.iutmindfuck.qcmiutlyon1.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.iutmindfuck.qcmiutlyon1.databasehandlers.UserSQLHandler;

public class SQLServices extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "IUT-BG-QCM";

    public SQLServices(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserLinkedTables(db);
        // createMCQLinkedTables();
    }
    private void createUserLinkedTables(SQLiteDatabase db) {
        db.execSQL(UserSQLHandler.getSQLForTableCreation());

        // Insert Basic ID for test (will not exists in final version)
        ContentValues user = UserSQLHandler.getUserDBEntry();
        ContentValues teacher = UserSQLHandler.getTeacherDBEntry();

        db.insertWithOnConflict("User", null, user, SQLiteDatabase.CONFLICT_REPLACE);
        db.insertWithOnConflict("User", null, teacher, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserSQLHandler.getSQLForTableSupression());
        onCreate(db);
    }

    public boolean isResultsMatching(String table, String[] select,
                                      String where, String[] whereValues) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table, select,
                where, whereValues,
                null, null, null);

        cursor.moveToFirst();
        boolean isResult = ! cursor.isAfterLast();
        cursor.close();

        return isResult;
    }

}
