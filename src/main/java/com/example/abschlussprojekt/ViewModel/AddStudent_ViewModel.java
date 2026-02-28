package com.example.abschlussprojekt.ViewModel;

import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.Model.Studentrepository;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel für die Hinzufügung von Studenten.
 *
 * Enthält die Eingabedaten für das Add-Student-Formular und stellt
 * diese als bindbare JavaFX Properties zur Verfügung. Die Properties
 * spiegeln die aktuellen Eingaben des Benutzers wider, werden aber erst beim Commit in das Modell übertragen
 *
 * Validierung:
 * Für jede Property existiert ein BooleanBinding, das automatisch
 * prüft, ob die Eingabe korrekt ist:
 * - vorname, nachname, studiengang: nur Buchstaben
 * - fachsemester, matrikelnummer: nur Zahlen
 * - email: muss '@' enthalten
 *
 * Ein kombinierter BooleanBinding 'alles_okay' zeigt an, ob alle
 * Eingaben gültig sind und stellt Getter dafür bereit
 *
 * Logik dahinter:
 * - Properties (StringProperty) werden bidirektional an TextFields gebunden.
 * - BooleanBindings werden an Buttons gebunden, um automatische
 *   Aktivierung/Deaktivierung zu steuern.
 * - commit() erzeugt einen neuen Studenten und fügt ihn dem Repository hinzu,
 *   anschließend werden die Eingaben zurückgesetzt.
 */
public class AddStudent_ViewModel {

    private final Studentrepository repository;

    private final StringProperty vorname = new SimpleStringProperty("");
    private final StringProperty nachname = new SimpleStringProperty("");
    private final StringProperty studiengang = new SimpleStringProperty("");
    private final StringProperty fachsemester = new SimpleStringProperty("");
    private final StringProperty matrikelnummer = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");

    private final BooleanBinding vorname_okay, nachname_okay, studiengang_okay, fachsemester_okay, matrikelnummer_okay, email_okay, alles_okay;


    /**
     * Konstruktor
     * Anlegen von BooleanBindings zur Validierung der Propertys
     *
     * @param repository = Injektion der Instanz des Studentenrepositorys aus dem Context
     */
    public AddStudent_ViewModel(Studentrepository repository) {
        this.repository = repository;

        //Boolean Binding reagiert automatisch auf Änderungen der Propertys und passt den Boolean an
        vorname_okay = Bindings.createBooleanBinding(
                () -> vorname.get() != null && vorname.get().matches("[a-zA-Z]+"),
                vorname
        );
        nachname_okay = Bindings.createBooleanBinding(
                () -> nachname.get() != null && nachname.get().matches("[a-zA-Z]+"),
                nachname
        );

        studiengang_okay = Bindings.createBooleanBinding(
                () -> studiengang.get() != null && studiengang.get().matches("[a-zA-Z]+"),
                studiengang
        );

        fachsemester_okay = Bindings.createBooleanBinding(
                () -> fachsemester.get() != null && fachsemester.get().matches("\\d+"), //regex \\d+ = nur Zahlen
                fachsemester
        );

        matrikelnummer_okay = Bindings.createBooleanBinding(
                () -> matrikelnummer.get() != null && matrikelnummer.get().matches("\\d+"),
                matrikelnummer
        );

        email_okay = Bindings.createBooleanBinding(
                () -> email.get() != null && email.get().contains("@"),
                email
        );

        //Alles  okay
        alles_okay = vorname_okay
                .and(nachname_okay)
                .and(studiengang_okay)
                .and(fachsemester_okay)
                .and(matrikelnummer_okay)
                .and(email_okay);




    }


    //BooleanBindings um disable Property reaktiv zu de/aktivieren
    public BooleanBinding vorname_okayProperty() {
        return vorname_okay;
    }

    public BooleanBinding nachname_okayProperty() {
        return nachname_okay;
    }

    public BooleanBinding studiengang_okayProperty() {
        return studiengang_okay;
    }

    public BooleanBinding fachsemester_okayProperty() {
        return fachsemester_okay;
    }

    public BooleanBinding matrikelnummer_okayProperty() {
        return matrikelnummer_okay;
    }

    public BooleanBinding email_okayProperty() {
        return email_okay;
    }

    public BooleanBinding alles_okayProperty(){
        return alles_okay;
    }


    //Getter für StringPropertys als "Zwischenspeicher" und Spiegel der Eingaben bis zum Commit
    public StringProperty vornameProperty(){
        return vorname;
    }

    public StringProperty nachnameProperty(){
        return nachname;
    }

    public StringProperty studiengangProperty(){
        return studiengang;
    }

    public StringProperty fachsemesterProperty(){
        return fachsemester;
    }

    public StringProperty matrikelnummerProperty(){
        return matrikelnummer;
    }

    public StringProperty emailProperty(){
        return email;
    }


    /**
     * Hinzufügen des neuen Studenten zum Model --> Aufruf aus Controller
     */
    //Hinzufügen des neuen Studenten zum Modell --> löst View Aktualierung automatisch aus, da sich Repository ändert
    public void commit (){

        Student s = new Student(vorname.get(), nachname.get(),studiengang.get(),fachsemester.get(),matrikelnummer.get(),email.get()); //Erzeugen neuer Student
        repository.add_student(s);      //hinzufügen des Student zum Repository (aus Konstruktor Injection)
        vorname.set(null);                //zurücksetzten aller Eingabefelder
        nachname.set(null);
        studiengang.set(null);
        fachsemester.set(null);
        matrikelnummer.set(null);
        email.set(null);
    }

}
