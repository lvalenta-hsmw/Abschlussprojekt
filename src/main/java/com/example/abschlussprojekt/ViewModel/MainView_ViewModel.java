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



    public MainView_ViewModel(Studentrepository repository) {
        this.repository = repository;
        filteredList= new FilteredList<>(repository.getStudentlist(), s -> true);

        //Filtert List
        filteredList.predicateProperty().bind(Bindings.createObjectBinding(() ->
                        student -> {
                            boolean matchVorname = vorname.get() == null || vorname.get().isEmpty()
                                    || student.getVor_name().toLowerCase().contains(vorname.get().toLowerCase());
                            boolean matchNachname = nachname.get() == null || nachname.get().isEmpty()
                                    || student.getNach_name().toLowerCase().contains(nachname.get().toLowerCase());
                            boolean matchStudiengang = studiengang.get() == null || studiengang.get().isEmpty()
                                    || student.getStudiengang().toLowerCase().contains(studiengang.get().toLowerCase());
                            boolean matchFachsemester = fachsemester.get() == null || fachsemester.get().isEmpty()
                                    || student.getFachsemester().toLowerCase().contains(fachsemester.get().toLowerCase());
                            boolean matchMatrikelnummer = matrikelnummer.get() == null || matrikelnummer.get().isEmpty()
                                    || student.get_matrikelnummer().toLowerCase().contains(matrikelnummer.get().toLowerCase());
                            boolean matchEmail = email.get() == null || email.get().isEmpty()
                                    || student.getEmail().toLowerCase().contains(email.get().toLowerCase());
                            boolean matchDurchschnitt = durchschnitt.get() == null || durchschnitt.get().isEmpty()
                                    || String.valueOf(student.getDurchschnitt()).contains(durchschnitt.get());             //Könnte Erros geben wenn die Zahlen erst parsen müsste --> NumberFormatError

                            return matchVorname && matchNachname && matchStudiengang && matchFachsemester && matchMatrikelnummer && matchEmail && matchDurchschnitt;
                        },
                vorname, nachname, studiengang, fachsemester, matrikelnummer,email , durchschnitt // Abhängigkeiten
        ));



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


    public void delete_student() {

        repository.remove_student(selectedStudent.get());

    }
}
