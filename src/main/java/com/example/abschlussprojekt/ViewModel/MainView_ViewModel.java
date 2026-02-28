package com.example.abschlussprojekt.ViewModel;

import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.Model.Studentrepository;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * ViewModel für die MainView der Studentenverwaltung
 *
 *  ViewModel zuständig für die gesamte Logik zur Anzeige, Filterung und Auswahl
 * von Studenten in der MainView.
 * Es stellt bindbare Properties für die Suchfelder bereit.
 *
 * Aufbau:
 * - repository: Das zugrundeliegende Datenmodell mit allen Studenten.
 * - filteredList: FilteredList<Student> wird als Quelle für die TableView bereit gestellt und
 * ändert ihr Predicate (Filter) automatisch, abhängig von den Suchfeldern.
 * - selectedStudent: Aktuell ausgewählter Student in der TableView.
 *
 * Filter:
 * - Bindet die PredicateProperty der FilteredList an die Eingabefelder (vorname,
 *   nachname, studiengang, fachsemester, matrikelnummer, email, durchschnitt).
 * - sobald sich eines der Textfeld Propertys ändert, wird das Predicate neu berechnet und die filtered List auch.
 *
 *
 * Funktionen:
 * - getStudentlist(): liefert die komplette ObservableList der Studenten.
 * - getfilterdStudentlist(): liefert die gefilterte ObservableList für die TableView.
 * - delete_student(): entfernt den aktuell ausgewählten Studenten aus dem Repository.
 *
 */

public class MainView_ViewModel {

    private final Studentrepository repository;
    private FilteredList<Student> filteredList;



    private final ObjectProperty<Student> selectedStudent =
            new SimpleObjectProperty<>();


    //String Propertys
    private StringProperty vorname = new SimpleStringProperty();
    private StringProperty nachname = new SimpleStringProperty();
    private StringProperty studiengang = new SimpleStringProperty();
    private StringProperty fachsemester = new SimpleStringProperty();
    private StringProperty matrikelnummer = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty durchschnitt = new SimpleStringProperty();


    /**
     * Anlegen einer FilteredList<Student>, welche reaktiv an die Studentenliste aus dem Repository gebunden ist.
     * filtern der Liste nach reaktiven an Stringpropertys gebundene Filterkriterien
     * @param repository = Injektion der Instanz des Studentenrepositorys aus dem Context
     */
    public MainView_ViewModel(Studentrepository repository) {
        this.repository = repository;
        filteredList= new FilteredList<>(repository.getStudentlist(), s -> true);

        /*
        äußere Lambda: keine Parameter, wird einmal je Änderung des Bindings aufgerufen, liefert das aktuelle Prädikat
        innere Lambda: nimmt Parameter (student) vom Typ zu dem die filtered List angelegt wurde
                       wird für jeden Studenten aufgerufen, true = anzeigen, false = verwerfen

         */

        //Filtert List
        filteredList.predicateProperty().bind(Bindings.createObjectBinding(() ->
                        student -> {
                            boolean matchVorname = vorname.get() == null || vorname.get().isEmpty()                 //wenn Suchfeld leer --> jeder Student passt
                                    || student.getVor_name().toLowerCase().contains(vorname.get().toLowerCase());   //case sensitiv überprüfen ob der Name des Studenten den Text aus dem Suchfeld enthält
                            boolean matchNachname = nachname.get() == null || nachname.get().isEmpty()
                                    || student.getNach_name().toLowerCase().contains(nachname.get().toLowerCase());
                            boolean matchStudiengang = studiengang.get() == null || studiengang.get().isEmpty()
                                    || student.getStudiengang().toLowerCase().contains(studiengang.get().toLowerCase());
                            boolean matchFachsemester = fachsemester.get() == null || fachsemester.get().isEmpty()
                                    || student.getFachsemester().toLowerCase().contains(fachsemester.get().toLowerCase());
                            boolean matchMatrikelnummer = matrikelnummer.get() == null || matrikelnummer.get().isEmpty()
                                    || student.get_Matrikelnummer().toLowerCase().contains(matrikelnummer.get().toLowerCase());
                            boolean matchEmail = email.get() == null || email.get().isEmpty()
                                    || student.getEmail().toLowerCase().contains(email.get().toLowerCase());
                            boolean matchDurchschnitt = durchschnitt.get() == null || durchschnitt.get().isEmpty()
                                    || String.valueOf(student.getDurchschnitt()).contains(durchschnitt.get());             //Könnte Erros geben wenn die Zahlen erst parsen müsste --> NumberFormatError

                            return matchVorname && matchNachname && matchStudiengang && matchFachsemester && matchMatrikelnummer && matchEmail && matchDurchschnitt;
                        },
                vorname, nachname, studiengang, fachsemester, matrikelnummer,email , durchschnitt // Abhängigkeiten
        ));

    }

    /**
     * löscht Student aus dem Repository
     */
    public void delete_student() {
        repository.remove_student(selectedStudent.get());
    }


    public ObservableList<Student> getStudentlist() {
        return repository.getStudentlist();
    }

    public FilteredList<Student> getfilterdStudentlist(){
        return filteredList;
    }

    public ObjectProperty<Student> selectedStudentProperty() {
        return selectedStudent;
    }

    //Getter

    /**
     *
     * Getter für Propertys die Such / Filter Kriterien wiederspiegeln
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

    public StringProperty durchschnittProperty(){return durchschnitt;}



}
