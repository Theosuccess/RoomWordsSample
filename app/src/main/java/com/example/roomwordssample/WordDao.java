package com.example.roomwordssample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(Word word);

        @Query("DELETE FROM word_table")
        void deleteAll();
    // this operation deletes a single row,
        @Delete
        void deleteWord(Word word);

    //In the WordDao interface, add a method to get any word:
    // method is called and returns an array containing one word.
    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    //update new word
    @Update
    void update (Word word);

    }
