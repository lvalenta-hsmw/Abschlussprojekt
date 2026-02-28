package com.example.abschlussprojekt.ViewModel;

import com.example.abschlussprojekt.Model.Prüfungsleistung;
import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.Model.Studentrepository;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.LocalDate;

/**
 * ViewModel für die Prüfungsleistungsübersicht eines Studenten.
 *
 * ViewModel kapselt die Prüfungsleistungen des aktuell in der MainView ausgewählten Studenten
 * und stellt diese als bindbare Properties zur Verfügung.
 * Man kann sowohl Prüfungsleistungen Anzeigen, Ändern und hinzufügen
 *
 * Aufbau:
 * - student_mainview: ObjectProperty<Student> des aktuell ausgewählten Studenten über den Konstruktor aus dem Context
 * - prüfungsleistungen: bindbares ListProperty<Prüfungsleistung> für TableView.
 * - selectedPrüfungsleistung: aktuell ausgewählte Prüfungsleistung in der TableView.
 *
 * Change-Felder:
 * - fach_change, datum_change, note_change, versuch_change: Properties für das
 *   ändern einer ausgewählten Prüfungslestung
 * - BooleanBindings wie fach_change_okay prüfen die Gültigkeit.
 * - alles_change_okay zeigt das alle Eingaben validiert sind --> kann an Button gebunden werden
 *
 * Add-Felder:
 * - fach_add, datum_add, note_add: Properties für das Hinzufügen einer neuen
 *   Prüfungsleistung.
 * - BooleanBindings wie fach_add_okay prüfen die Eingaben --> fast gleiche Logik wie bei Change
 * - alles_add_okay zeigt das alle Eigaben auch hier validiert sind.
 *
 * Logik:
 * - Änderungen an student_mainview führen zu automatischem Binden/Entbinden der
 *   prüfungsleistungen-Liste über einen Listener
 * - Änderungen an selectedPrüfungsleistung aktualisieren automatisch die Change-Felder.
 *
 * Methoden:
 * - commit_change(): übernimmt die Änderungen in die ausgewählte Prüfungsleistung.
 * - commit_add(): erstellt eine neue Prüfungsleistung und fügt sie dem Studenten hinzu.
 * - deletePrüfungsleistung(): löscht die aktuell ausgewählte Prüfungsleistung
 * - requieres_versuch_confirmation(): prüft, ob ein Versuch herabgesetzt wird und
 *   eine Bestätigung erforderlich ist um Logik nicht in den Controller zu schreiben.
 *
 *
 */

public class ExamView_ViewModel {

    private ObjectProperty<Student> student_mainview = new SimpleObjectProperty<Student>();
    //kapselt die oberservable Array List und stellt bindbares Property zur Verfügung
    private final ListProperty<Prüfungsleistung> prüfungsleistungen = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObjectProperty<Prüfungsleistung>selectedPrüfungsleistung = new SimpleObjectProperty<Prüfungsleistung>();

    private Studentrepository repository;

    private final BooleanBinding fach_change_okay, datum_change_okay, note_change_okay,versuch_change_okay, alles_change_okay;
    private final BooleanBinding fach_add_okay, datum_add_okay, note_add_okay, alles_add_okay;


    //Drawfields Change
    private StringProperty fach_change =new SimpleStringProperty();
    private ObjectProperty<LocalDate> datum_change = new SimpleObjectProperty<>();
    private DoubleProperty note_change = new SimpleDoubleProperty();
    private IntegerProperty versuch_change = new SimpleIntegerProperty();
    //Drawfields Add
    private StringProperty fach_add =new SimpleStringProperty();
    private ObjectProperty<LocalDate> datum_add = new SimpleObjectProperty<>();
    private DoubleProperty note_add = new SimpleDoubleProperty();

    /**
     * Listener für den Wechsel der ausgewählen Prüfungsleistung in der TableView.
     * Listener für den Wechsel des ausgewählten Studenten (studentSimpleObjectProperty) --> Bindet Datenquelle (Prüfungsleistungen) der TableView an die Prüfungsleistungsproperty des ausgewählten Studenten
     * @param repository = = Injektion der Instanz des Studentenrepositorys aus dem Context
     * @param studentSimpleObjectProperty = ObjektProperty, welches reaktiv den in der MainView ausgewählten Studenten wiederspiegelt.
     */
    public ExamView_ViewModel(Studentrepository repository, ObjectProperty<Student> studentSimpleObjectProperty) {
        this.repository = repository;
        this.student_mainview.bind(studentSimpleObjectProperty);
        //Überwacht die ausgewählte Prüfungsleistung
        selectedPrüfungsleistung.addListener((obs, oldPrüfungsleistung, newPrüfungsleistung)->{

            drawfield(newPrüfungsleistung);

        });

        if(student_mainview.get() != null){
            //Bindet die Datenquelle (Prüfungsleistungen) der TableView an die Prüfungsleistungsproperty des ausgewählten Studenten
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
        /**
         * Boolean Binding zur Validierung der Eingaben in die Change Textfields
         */
        //BooleanBindings
        fach_change_okay = fach_change.isNotEmpty();
        datum_change_okay = datum_change.isNotNull();
        note_change_okay = note_change.greaterThanOrEqualTo(1.0).and(note_change.lessThanOrEqualTo(5.0));
        versuch_change_okay = versuch_change.greaterThanOrEqualTo(1).and(versuch_change.lessThanOrEqualTo(3));
        alles_change_okay = fach_change_okay.and(datum_change_okay).and(note_change_okay).and(versuch_change_okay);

        /**
         * Boolean Binding zur Validierung der Eingaben in die Add Textfields
         */
        fach_add_okay = fach_add.isNotEmpty();
        datum_add_okay = datum_add.isNotNull();
        note_add_okay = note_add.greaterThanOrEqualTo(1.0).and(note_add.lessThanOrEqualTo(5.0));
        alles_add_okay = fach_add_okay.and(datum_add_okay).and(note_add_okay);


    }

    /**
     * füllt die Propertys(Change) mit Daten der neuen Prüfungsleistung, damit sich die View / Textfields aktualisieren
     * @param newPrüfungsleistung = vom Listener (ausgewählte Prüfungsleistung) übergebener Parameter vom Typ Prüfungsleistung
     */
    private void drawfield(Prüfungsleistung newPrüfungsleistung) {
        if (newPrüfungsleistung != null){
        datum_change.set(newPrüfungsleistung.getDatum());
        note_change.set(newPrüfungsleistung.getNote());
        fach_change.set(newPrüfungsleistung.getFach());
        versuch_change.set(newPrüfungsleistung.getVersuch());
        }
        else {                      //Felder müssen zurückgesetzt werden denn beim Wechsel des Studenten in der Mainview ist danach kein Student selektiert und die alten Werte werden nicht überschrieben
            clearfield();
        }
    }

    /**
     * setzt die Propertys (Change) auf Default Werte
     */
    private void clearfield(){
        datum_change.set(null);
        note_change.set(0);            //Default Wert 0 denn sosnt hätte man ObjectProperty <Double> nutzen müssen um note.set(null) ausführen zu können --> Hier wird mit Noten gerechnet daher sollen Sie immer ein Wert haben.
        fach_change.set("");
        //versuch_change.set(0);
    }

    /**
     * übernimmt die Änderungen an einer bereits vorhandenen Prüfungsleistung in das Model
     */
    public void commit_change (){
        if (!alles_change_okay.get()){
            return;
        }
        selectedPrüfungsleistung.get().setDatum(datum_change.get());
        selectedPrüfungsleistung.get().setFach(fach_change.get());
        selectedPrüfungsleistung.get().setNote(note_change.get());
        selectedPrüfungsleistung.get().setVersuch(versuch_change.get());


    }

    /**
     * löscht die Prüfungsleistung aus dem Model und demnach auch aus der View
     */
    public void deletePrüfungsleistung() {
        if (selectedPrüfungsleistung != null){
            student_mainview.get().deletePrüfungsleistung(selectedPrüfungsleistung.get());      //löschen der Prüfungsleistung aus der Liste im Studenten
        }
    }

    /**
     * erstellt eine neue Prüfungsleistung und fügt sie der Notenliste bzw dem Student hinzu
     * Änderung im Model
     */
    public void commit_add(){

        if (student_mainview.get() != null){
            Prüfungsleistung prüfungsleistung =new Prüfungsleistung(fach_add.get(),note_add.get(),datum_add.get());     //Neues Prüfungsleistungsobjekt erstellen mit Werten aus Propertys
            student_mainview.get().addPrüfungsleistung(prüfungsleistung);
        }

    }

    /**
     *
     * @return Boolean, ob einen potenziell ungewollte Änderung durchgeführt wird
     */
    public boolean requieres_versuch_confirmation() {
        if(versuch_change.get() >= selectedPrüfungsleistung.get().getVersuch()){
            return false;
        }
        else {
            return true;
        }
    }


    //Getter

    /**
     * Getter für ListProperty<Prüfungsleistung> + ausgewählte Prüfungsleistung in der Prüfungsleistungs TableView
     *
     */
    public ListProperty<Prüfungsleistung> getPrüfungsleistungsProperty(){

        return prüfungsleistungen;
    }

    public ObjectProperty<Prüfungsleistung> SelectedPrüfungsleistungProperty(){
        return selectedPrüfungsleistung;
    }



    //Change Propertys

    /**
     * Getter für Propertys für Change Textfields
     *
     */
    public ObjectProperty<LocalDate> datum_changeProperty(){
        return datum_change;
    }

    public DoubleProperty note_changeProperty(){
        return note_change;
    }

    public StringProperty fach_changeProperty(){
        return fach_change;
    }

    public IntegerProperty versuch_changeProperty(){
        return versuch_change;
    }

    public BooleanBinding alles_change_okayProperty(){
        return alles_change_okay;

    }

    //Add Propertys

    /**
     * Getter für Propertys für Add Textfields
     *
     */

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
