package com.example.roomwordssample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>>mAllWords;
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    WordRepository(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Word>>getAllWords(){
        return mAllWords;
    }
    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Word word){
        new insertAsyncTask(mWordDao).execute(word);
    }
    public void deleteAll(){
        new insertAsyncTask.deleteAllWordsAsyncTask(mWordDao).execute();
    }

    // Need to run off main thread
    public void deleteWord(Word word) {
        new insertAsyncTask.deleteWordAsyncTask(mWordDao).execute(word);
    }
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

        /**
         * Delete all words from the database (does not delete the table)
         */
        public static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
            private WordDao mAsyncTaskDao;

            deleteAllWordsAsyncTask(WordDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                mAsyncTaskDao.deleteAll();
                return null;
            }
        }

        /**
         * Delete a single word from the database.
         */
        private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
            private WordDao mAsyncTaskDao;

            deleteWordAsyncTask(WordDao dao) {
                mAsyncTaskDao = dao;
            }
            @Override
            protected Void doInBackground(Word... params) {
                mAsyncTaskDao.deleteWord(params[0]);
                return null;
            }
        }
    }
}
