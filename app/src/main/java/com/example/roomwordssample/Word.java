package com.example.roomwordssample;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

//@Entity class represents an entity in a table
@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word (@NonNull String word){this.mWord=word;}
    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */
    @Ignore
    public Word(int id, @NonNull String word){
        this.id = id;
        this.mWord= word;
    }
    public String getWord(){return this.mWord;}

    public int getId(){return id;}

    public void setId(int id){this.id=id;}
}
