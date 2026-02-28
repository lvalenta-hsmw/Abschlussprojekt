package com.example.abschlussprojekt.Model;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Repräsentiert eine einzelne Prüfungsleistung eines Studenten.
 *
 * hat folgende Eigenschaften:
 * - Fach (String)
 * - Note (Double)
 * - Datum (LocalDate)
 * - Versuch (Integer)
 *
 * Jede Eigenschaft wird als JavaFX-Property bereitgestellt, um
 * reaktive Bindungen zu ermöglichen. Änderungen
 * an den Properties werden automatisch in allen gebundenen Views bzw anderen Objekten (Student)
 * geändert.
 *
 */

public class Prüfungsleistung {
    private final StringProperty Fach = new SimpleStringProperty();
    private final DoubleProperty Note = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> Datum = new SimpleObjectProperty();
    private final IntegerProperty Versuch = new SimpleIntegerProperty();

    /**
     *
     * @param Fach = Fach der Prüfungsleistung
     * @param Note = Note der Prüfungsleistung
     * @param Datum = Datum der Prüfungsleistung
     */
    public Prüfungsleistung(String Fach, Double Note, LocalDate Datum ) {
        this.Fach.set(Fach);
        this.Note.set(Note);
        this.Datum.set(Datum);
        this.Versuch.set(1);
    }


    /**
     * Getter Methoden für Propertys der Prüfungsleistung
     *
     */
    //Getter für Property
    public StringProperty getFachProperty(){
        return Fach;
    }
    public DoubleProperty getNoteProperty(){
        return Note;
    }
    public ObjectProperty<LocalDate> getDatumProperty(){
        return Datum;
    }

    public IntegerProperty getVersuchProperty(){
        return Versuch;
    }

    /**
     * Getter Methoden für Werte der Prüfungsleistung
     *
     */
    //Getter für Wert
    public String getFach(){
        return Fach.get();
    }

    public Double getNote(){
        return Note.get();
    }

    public LocalDate getDatum(){
        return Datum.get();
    }

    public Integer getVersuch(){
        return Versuch.get();
    }

    /**
     * Setter Methoden für Werte der Propertys der Prüfungsleistung
     *
     */
    //Setter für Werte
    public void setFach(String Fach){
        this.Fach.set(Fach);
    }

    public void setNote(Double Note){
        this.Note.set(Note);
    }

    public void setDatum(LocalDate Datum){
        this.Datum.set(Datum);
    }

    public void setVersuch(Integer Versuch){
        this.Versuch.set(Versuch);
    }

}
