package com.example.roomwordssample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    //private member variable to hold a reference to the Repository.
    private WordRepository mRepository;
    // private LiveData member variable to cache the list of words.
    private LiveData<List<Word>> mAllWords;
    // constructor that gets a reference to the WordRepository
    // and gets the list of all words from the WordRepository.
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }
    //Add a "getter" method that gets all the words.
    // This completely hides the implementation from the UI.
    LiveData<List<Word>> getAllWords(){return mAllWords;}
    public void insert(Word word){mRepository.insert(word);}

    public void deleteAll() {
        mRepository.deleteAll();
    }
    public void deleteWord(Word word) {
        mRepository.deleteWord(word);
    }

}
