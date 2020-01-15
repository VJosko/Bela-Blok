package com.example.belablok;

import com.example.belablok.klase.Upis;

import java.util.ArrayList;
import java.util.List;

public class UpisStorage {
    private List<Upis> upisi;
    private UpisStorage(){
        upisi = new ArrayList<>();
    }

    static  private UpisStorage instance;
    public static UpisStorage getInstance(){
        if(instance == null) {
            instance = new UpisStorage();
        }
        return instance;
    }

    public List<Upis> getUpise() {
        return upisi;
    }

    public void setUpise(List<Upis> upisi) {
        this.upisi = upisi;
    }

    public void addUpis(Upis upis){
        upisi.add(upis);

    }

    public List<Upis> readUpise()
    {
        return upisi;
    }
}
