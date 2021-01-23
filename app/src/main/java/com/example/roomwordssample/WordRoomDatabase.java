package com.example.roomwordssample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = {Word.class}, version = 2, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    //
    public abstract WordDao wordDao ();

    //Create the WordRoomDatabase as a singleton to prevent having multiple instances of
    // the database opened at the same time, which would be a bad thing
    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase (final Context context){
        if (INSTANCE==null){
           synchronized (WordRoomDatabase.class){
               if (INSTANCE==null){
                   // Create database here
                   INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           WordRoomDatabase.class, "word_database")
                   // Wipes and rebuilds instead of migrating if no Migration object.
                   // Migration is not part of this codelab.
                   .fallbackToDestructiveMigration()
                           .addCallback(sRoomDatabaseCallback)
                           .build();
               }
           }
        }
        return INSTANCE;
    }
    //To delete all content and repopulate the database
    // whenever the app is started, you create a RoomDatabase.Callback
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
            };
    /**
     * this is an inner class of WordRoomDatabase
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final WordDao mDao;
        String[] words =  {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(WordRoomDatabase db){
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
           // mDao.deleteAll();
            // If we have no words, then create the initial list of words
            if (mDao.getAnyWord().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }

            return null;
        }
    }
}
