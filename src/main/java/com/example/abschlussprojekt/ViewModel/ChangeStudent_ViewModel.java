package com.example.abschlussprojekt.ViewModel;

import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.Model.Studentrepository;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private void drawfield(Student newStudent){
        vorname.set(newStudent.getVor_name());
        nachname.set(newStudent.getNach_name());
        studiengang.set(newStudent.getStudiengang());
        fachsemester.set(newStudent.getFachsemester());
        matrikelnummer.set(newStudent.get_matrikelnummer());
        email.set(newStudent.getEmail());
    }

    //Änderungen bestätigen
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

    public void cancel() {
        //Student kann gelöscht werden = null
        if (!student.isNull().get()) {
            vorname.set(student.get().getVor_name());
            nachname.set(student.get().getNach_name());
            studiengang.set(student.get().getStudiengang());
            fachsemester.set(student.get().getFachsemester());
            matrikelnummer.set(student.get().get_matrikelnummer());
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
