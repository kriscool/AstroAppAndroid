package com.example.kriscool.myapplication;

/**
 * Created by kriscool on 21.06.2017.
 */

public class Note {
    private Integer id;
    private String noteText;

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return noteText;
    }
}