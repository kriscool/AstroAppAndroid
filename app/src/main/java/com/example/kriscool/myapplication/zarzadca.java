package com.example.kriscool.myapplication;
        import java.util.LinkedList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteDatabase.CursorFactory;
        import android.database.sqlite.SQLiteOpenHelper;

public class zarzadca extends SQLiteOpenHelper{

    public zarzadca(Context context) {
        super(context, "ulubione.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table ulubione(" +
                        "nr integer primary key autoincrement," +
                        "nazwa text;" + ""
                        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void dodajUlubione(Ulubione ul){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwa", ul.getNazwa());
        db.insertOrThrow("ulubione",null, wartosci);
    }

    public void kasujKontakt(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] argumenty={""+id};
        db.delete("ulubione", "nr=?", argumenty);
    }


    public void aktualizujKontakt(Ulubione kontakt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwa", kontakt.getNazwa());
        String args[]={kontakt.getId()+""};
        db.update("ulubione", wartosci,"nr=?",args);
    }

    public List<Ulubione> dajWszystkie(){
        List<Ulubione> kontakty = new LinkedList<Ulubione>();
        String[] kolumny={"nr","nazwa"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("ulubione",kolumny,null,null,null,null,null);
        while(kursor.moveToNext()){
            Ulubione kontakt = new Ulubione();
            kontakt.setId(kursor.getInt(0));
            kontakt.setNazwa(kursor.getString(1));
            kontakty.add(kontakt);
        }
        return kontakty;
    }

    public Ulubione dajKontakt(int nr){
        Ulubione kontakt=new Ulubione();
        SQLiteDatabase db = getReadableDatabase();
        String[] kolumny={"nr","nazwa"};
        String args[]={nr+""};
        Cursor kursor=db.query("ulubione",kolumny," nr=?",args,null,null,null,null);
        if(kursor!=null){
            kursor.moveToFirst();
            kontakt.setId(kursor.getInt(0));
            kontakt.setNazwa(kursor.getString(1));
        }
        return kontakt;
    }

    /*public List<Ulubione> dajPoNazwisku(String nazwisko){
        List<Ulubione> kontakty = new LinkedList<Ulubione>();
        String[] kolumny={"nr","nazwa"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.rawQuery("select nazwa"
                "' order by imie asc", null);
		/*Alternatywne wywoĹ‚anie metody rawQuery
		 *
		 * Cursor kursor =db.rawQuery
		 * ("select nr,imie,nazwisko,telefon from telefony where nazwisko=?	order by imie asc", nazwi);
		 *
        while(kursor.moveToNext()){
            Ulubione kontakt = new Ulubione();
            kontakt.setId(kursor.getInt(0));
            kontakt.setNazwa(kursor.getString(1));
            kontakty.add(kontakt);
        }
        return kontakty;
    }*/

}
