package com.example.abschlussprojekt.ViewModel;

import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.Model.Studentrepository;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel für das Ändern eines bestehenden Studenten.
 *
 * Nimmt die Eingabedaten für ein Change-Student-Formular auf und stellt
 * diese als bindbare JavaFX Properties zur Verfügung. Die Properties
 * entsprechen den aktuellen Eingaben des Benutzers und werden erst beim Commit
 * übertragen.
 *
 * Validierung:
 * Property hat ein BooleanBinding, das automatisch prüft,
 * ob die Eingabe gültig ist:
 * - vorname, nachname, studiengang: nur Buchstaben
 * - fachsemester, matrikelnummer: nur Zahlen
 * - email: muss '@' enthalten
 * -->ähnliche Logik wie in AddStudent_ViewModel
 *
 * BooleanBinding alles_okay zeigt an das alle Eingaben sichr sind.
 * Kann an commit btn gebunden werden
 *
 * Logik:
 * - Properties (StringProperty) werden bidirektional an TextFields gebunden.
 * - BooleanBindings werden an Buttons gebunden, um automatische
 *   Aktivierung/Deaktivierung zu steuern.
 * - commit() schreibt die Änderungen in das ausgewählte Student-Objekt.
 * - cancel() setzt die Propertys --> Felder auf die aktuellen Werte des Studenten zurück.
 *
 *
 */

public class ChangeStudent_ViewModel {


    private ObjectProperty<Student> student = new SimpleObjectProperty<Student>();

    //String Propertys
    private StringProperty vorname = new SimpleStringProperty();
    private StringProperty nachname = new SimpleStringProperty();
    private StringProperty studiengang = new SimpleStringProperty();
    private StringProperty fachsemester = new SimpleStringProperty();
    private StringProperty matrikelnummer = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();


    private final BooleanBinding vorname_okay, nachname_okay, studiengang_okay, fachsemester_okay, matrikelnummer_okay, email_okay, alles_okay;

    /**
     *
     * @param repository = Injektion der Instanz des Studentenrepositorys aus dem Context
     * @param studentSimpleObjectProperty = ObjektProperty, welches reaktiv den in der MainView ausgewählten Studenten wiederspiegelt
     */
    public ChangeStudent_ViewModel(Studentrepository repository, ObjectProperty<Student> studentSimpleObjectProperty) {

        //Binden des übergebenen ObjectProperty (aktiv ausgwählter Student aus der Tabelle) --> in der Klasse verfügbar machen
        this.student.bind(studentSimpleObjectProperty);
        drawfield(student.get());       //Initialisierung der Felder beim Start --> sonst leer
        //Listener der den ausgewählten Studenten überwacht

        this.student.addListener((obs, oldStudent, newStudent) -> {
            //neuer Student kann null werden --> Exception
            if (newStudent != null) {

                drawfield(newStudent);      //Felder füllen
            }
            else {
                cancel();       //zurücksetzen
            }
        });


        /**
         * BooleanBindings zur Validierung der Eingabe Eingaben in den gespiegelten Textfeldern aus dem Controller / View
         */
        //Boolean Bindings --> siehe AddStudent Viewmodel
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
                () -> fachsemester.get() != null && fachsemester.get().matches("\\d+"),
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

        //alle gleichzeitig erfüllt
        alles_okay = vorname_okay
                .and(nachname_okay)
                .and(studiengang_okay)
                .and(fachsemester_okay)
                .and(matrikelnummer_okay)
                .and(email_okay);


    }


    /**
     * Getter für BooleanBindings
     * Hinweiß: noch ungenutzt, aber für individuelle Eingabefeldkontrolle angedacht
     */
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

    /**
     *
     * Getter für Propertys  --> Bindung an Eingabefelder
     */
    public StringProperty vornameProperty() {
        return vorname;
    }

    public StringProperty nachnameProperty() {
        return nachname;
    }

    public StringProperty studiengangProperty() {
        return studiengang;
    }

    public StringProperty fachsemesterProperty() {
        return fachsemester;
    }

    public StringProperty matrikelnummerProperty() {
        return matrikelnummer;
    }

    public StringProperty emailProperty() {
        return email;
    }

    //Propertys setzen aus dem neuen Studenten

    /**
     * füllt die StringPropertys mit Daten des neuen Studenten, damit sich die View aktualisiert
     * @param newStudent = vom Listener (ausgewählter Student MainView) übergebener Parameter vom Typ Student
     */
    private void drawfield(Student newStudent){
        vorname.set(newStudent.getVor_name());
        nachname.set(newStudent.getNach_name());
        studiengang.set(newStudent.getStudiengang());
        fachsemester.set(newStudent.getFachsemester());
        matrikelnummer.set(newStudent.get_Matrikelnummer());
        email.set(newStudent.getEmail());
    }

    //Änderungen bestätigen

    /**
     * Ändert Eigenschaften des ausgewählten Studenten.
     * Änderung wird in das Modell übernommen.
     */
    public void commit (){
        if (!alles_okay.get()){
            return;
        }
        student.get().setVor_name(vorname.get());
        student.get().setNach_name(nachname.get());
        student.get().setStudiengang(studiengang.get());
        student.get().setFachsemester(fachsemester.get());
        student.get().setMatrikelnummer(matrikelnummer.get());
        student.get().setEmail(email.get());

    }

    /**
     * Wiederherstellen der ursprünglichen Daten des Studenten.
     * Zurücksetzen der Felder auf Ausweichwerte, wenn kein Student ausgewählt ist.
     */
    public void cancel() {
        //Student kann gelöscht werden = null
        if (!student.isNull().get()) {
            vorname.set(student.get().getVor_name());
            nachname.set(student.get().getNach_name());
            studiengang.set(student.get().getStudiengang());
            fachsemester.set(student.get().getFachsemester());
            matrikelnummer.set(student.get().get_Matrikelnummer());
            email.set(student.get().getEmail());
        } else {
            vorname.set(null);      //nullen der Felder, wenn kein Student ausgewählt ist
            nachname.set(null);
            studiengang.set(null);
            fachsemester.set(null);
            matrikelnummer.set(null);
            email.set(null);
        }

    }
}
