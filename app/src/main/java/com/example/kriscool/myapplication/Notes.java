package com.example.kriscool.myapplication;

/**
 * Created by kriscool on 21.06.2017.
 */
public interface Notes {
    String TABLE_NAME = "notes";

    interface Columns {
        String NOTE_ID = "_id";
        String NOTE_TEXT = "note_text";
    }
}