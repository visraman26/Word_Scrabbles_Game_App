package com.example.vishalraman.new_game;

/**
 * Created by apicard on 9/16/15.
 */
public interface GhostDictionary {
    public final static int MIN_WORD_LENGTH = 1;
    boolean isWord(String word);
    String getAnyWordStartingWith(String prefix);
    String getGoodWordStartingWith(String prefix);
}
