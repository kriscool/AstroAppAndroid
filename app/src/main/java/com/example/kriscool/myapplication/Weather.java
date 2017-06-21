package com.example.kriscool.myapplication;

/**
 * Created by kriscool on 19.06.2017.
 */

public class Weather {
    String opis;
    String kraj;
    String kierunekWiatru;
    String cisnienie;
    String wilgotnosc;
    String widocznosc;
    String miasto;
    String dlugosc;
    String szerokosc;
    String temperatura;
    String silaWiatru;


    public String getAll(){
        return getKraj() + getKierunekWiatru() +getCisnienie() + getWilgotnosc() + getWidocznosc() + getMiasto() + getDlugosc() + getSzerokosc() +getTemperatura() + getSilaWiatru();
    }
    public void setCisnienie(String cisnienie) {
        this.cisnienie = cisnienie;
    }
    public String getCisnienie() {
        return cisnienie;
    }

    public String getKierunekWiatru() {
        return kierunekWiatru;
    }

    public String getWilgotnosc() {
        return wilgotnosc;
    }

    public String getWidocznosc() {
        return widocznosc;
    }

    public void setKierunekWiatru(String kierunekWiatru) {
        this.kierunekWiatru = kierunekWiatru;
    }

    public void setWilgotnosc(String wilgotnosc) {
        this.wilgotnosc = wilgotnosc;
    }

    public void setWidocznosc(String widocznosc) {
        this.widocznosc = widocznosc;
    }

    public String getOpis() {
        return opis;
    }

    public String getKraj() {
        return kraj;
    }

    public String getMiasto() {
        return miasto;
    }

    public String getDlugosc() {
        return dlugosc;
    }

    public String getSzerokosc() {
        return szerokosc;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getSilaWiatru() {
        return silaWiatru;
    }


    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public void setDlugosc(String dlugosc) {
        this.dlugosc = dlugosc;
    }

    public void setSzerokosc(String szerokosc) {
        this.szerokosc = szerokosc;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public void setSilaWiatru(String silaWiatru) {
        this.silaWiatru = silaWiatru;
    }

}
