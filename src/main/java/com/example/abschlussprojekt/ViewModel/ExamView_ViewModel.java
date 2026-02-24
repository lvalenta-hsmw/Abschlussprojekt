package com.example.abschlussprojekt.ViewModel;

import com.example.abschlussprojekt.Model.Prüfungsleistung;
import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.Model.Studentrepository;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.LocalDate;

public class ExamView_ViewModel {

    private ObjectProperty<Student> student_mainview = new SimpleObjectProperty<Student>();
    //kapselt die oberservable Array List und stellt bindbares Property zur Verfügung
    private final ListProperty<Prüfungsleistung> prüfungsleistungen = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObjectProperty<Prüfungsleistung>selectedPrüfungsleistung = new SimpleObjectProperty<Prüfungsleistung>();

    private Studentrepository repository;

    private final BooleanBinding fach_change_okay, datum_change_okay, note_change_okay, alles_change_okay;
    private final BooleanBinding fach_add_okay, datum_add_okay, note_add_okay, alles_add_okay;


    //Drawfields Change
    private StringProperty fach_change =new SimpleStringProperty();
    private ObjectProperty<LocalDate> datum_change = new SimpleObjectProperty<>();
    private DoubleProperty note_change = new SimpleDoubleProperty();
    //Drawfields Add
    private StringProperty fach_add =new SimpleStringProperty();
    private ObjectProperty<LocalDate> datum_add = new SimpleObjectProperty<>();
    private DoubleProperty note_add = new SimpleDoubleProperty();

    public ExamView_ViewModel(Studentrepository repository, ObjectProperty<Student> studentSimpleObjectProperty) {
        this.repository = repository;
        this.student_mainview.bind(studentSimpleObjectProperty);
        //Überwacht die ausgewählte Prüfungsleistung
        selectedPrüfungsleistung.addListener((obs, oldPrüfungsleistung, newPrüfungsleistung)->{

            drawfield(newPrüfungsleistung);

        });

        if(student_mainview.get() != null){
            //Bindet die Datenquelle (prüfungsleistungen) der TableView an die Prüfungsleistungsproperty des ausgewählten Studenten
            // .get() --> statisch und muss neu gebunden / entbunden werden, wenn sich ausgewählter Student ändert
            prüfungsleistungen.bind(student_mainview.get().getPrüfungsleistungsProperty());
        }
        //überwacht Änderung am ausgewählten Studenten in der Mainview
        this.student_mainview.addListener((obs, oldStudent, newStudent) -> {
            if (oldStudent != null){
                prüfungsleistungen.unbind();
            }
            if (newStudent != null){
                prüfungsleistungen.bind(newStudent.getPrüfungsleistungsProperty());
            }
            //Rückfall --> leeres Array
            else{
                prüfungsleistungen.set(FXCollections.observableArrayList());
            }

        });

        //BooleanBindings
        fach_change_okay = fach_change.isNotEmpty();
        datum_change_okay = datum_change.isNotNull();
        note_change_okay = note_change.greaterThanOrEqualTo(1.0).and(note_change.lessThanOrEqualTo(5.0));
        alles_change_okay = fach_change_okay.and(datum_change_okay).and(note_change_okay);

        fach_add_okay = fach_add.isNotEmpty();
        datum_add_okay = datum_add.isNotNull();
        note_add_okay = note_add.greaterThanOrEqualTo(1.0).and(note_add.lessThanOrEqualTo(5.0));
        alles_add_okay = fach_add_okay.and(datum_add_okay).and(note_add_okay);


    }

    private void drawfield(Prüfungsleistung selectedPrüfungsleistung) {
        if (selectedPrüfungsleistung != null){
        datum_change.set(selectedPrüfungsleistung.getDatum());
        note_change.set(selectedPrüfungsleistung.getNote());
        fach_change.set(selectedPrüfungsleistung.getFach());
        }
        else {                      //Felder müssen zurückgesetzt werden denn beim Wechsel des Studenten in der Mainview ist danach kein Student selektiert und die alten Werte werden nicht überschrieben
            clearfield();
        }
    }

    private void clearfield(){
        datum_change.set(null);
        note_change.set(0);            //Default Wert 0 denn sosnt hätte man ObjectProperty <Double> nutzen müssen um note.set(null) ausführen zu können --> Hier wird mit Noten gerechnet daher sollen Sie immer ein Wert haben.
        fach_change.set("");
    }


    public void commit_change (){
        if (!alles_change_okay.get()){
            return;
        }
        selectedPrüfungsleistung.get().setDatum(datum_change.get());
        selectedPrüfungsleistung.get().setFach(fach_change.get());
        selectedPrüfungsleistung.get().setNote(note_change.get());

    }

    public void deletePrüfungsleistung() {
        if (selectedPrüfungsleistung != null){
            student_mainview.get().deletePrüfungsleistung(selectedPrüfungsleistung.get());      //löschen der Prüfungsleistung aus der Liste im Studenten
        }
    }


    public void commit_add(){

        if (student_mainview.get() != null){
            Prüfungsleistung prüfungsleistung =new Prüfungsleistung(fach_add.get(),note_add.get(),datum_add.get());     //Neues Prüfungsleistungsobjekt erstellen mit Werten aus Propertys
            student_mainview.get().addPrüfungsleistung(prüfungsleistung);
        }

    }


    //Getter


    public ListProperty<Prüfungsleistung> getPrüfungsleistungsProperty(){

        return prüfungsleistungen;
    }

    public ObjectProperty<Prüfungsleistung> SelectedPrüfungsleistungProperty(){
        return selectedPrüfungsleistung;
    }



    //Change Propertys
    public ObjectProperty<LocalDate> datum_changeProperty(){
        return datum_change;
    }

    public DoubleProperty note_changeProperty(){
        return note_change;
    }

    public StringProperty fach_changeProperty(){
        return fach_change;
    }

    public BooleanBinding alles_change_okayProperty(){
        return alles_change_okay;

    }

    //Add Propertys

    public ObjectProperty<LocalDate> datum_addProperty(){
        return datum_add;
    }

    public DoubleProperty note_addProperty(){
        return note_add;
    }

    public StringProperty fach_addProperty(){
        return fach_add;
    }

    public BooleanBinding alles_add_okayProperty(){
        return alles_add_okay;

    }



}
