package com.focusstudios.android.colouring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores_database";
    private static final String TABLE_NAME = "scores";

    private static final String KEY_ID = "id";
    private static final String KEY_SCORE = "score";
    private static final String KEY_GAMES = "games";
    private static final String KEY_MUSIC = "music";
    private static final String KEY_SFX = "sfx";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_SCORE + " TEXT DEFAULT '99:99', " + KEY_GAMES + " INTEGER DEFAULT 0, " + KEY_MUSIC + " INTEGER DEFAULT 1, "
                + KEY_SFX + " INTEGER DEFAULT 1)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //adding new scores
    public void addScores(Scores scores) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, scores.getScore());
        values.put(KEY_GAMES, scores.getGamesPlayed());
        values.put(KEY_MUSIC, scores.getMusic());
        values.put(KEY_SFX, scores.getSFX());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Scores getScores(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Scores contact = new Scores(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        cursor.close();
        return contact;
    }

    /*public List<Scores> getAllScores() {
        List<Scores> scoresList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Scores scores = new Scores();
                scores.setId(Integer.parseInt(cursor.getString(0)));
                scores.setScore(cursor.getString(1));
                scores.setGamesPlayed(cursor.getInt(2));
                scores.setMusic(cursor.getInt(3));
                scores.setSFX(cursor.getInt(4));
                scoresList.add(scores);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return scoresList;
    }*/

    public void updateScores(Scores scores) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, scores.getScore());
        values.put(KEY_GAMES, scores.getGamesPlayed());
        values.put(KEY_MUSIC, scores.getMusic());
        values.put(KEY_SFX, scores.getSFX());
        db.update(TABLE_NAME, values, null, null);
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}
