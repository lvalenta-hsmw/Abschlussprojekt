package com.example.abschlussprojekt.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Repository für Studentenobjekte.
 *
 * Zustädnig für die  Datenhaltung von Studenten und stellt
 * Methoden zur Verfügung, um Studenten hinzuzufügen und
 * zu löschen. Alle Änderungen werden
 * über ObservableLists bereitgestellt, um reaktive
 * Aktualisierungen in der UI zu ermöglichen.
 *
 * Das Repository ist unabhängig
 * von der Darstellung oder Benutzerinteraktionen.
 */
public class Studentrepository {


    private final ObservableList<Student> studentrepository = FXCollections.observableArrayList();


    /**
     *
     * @return = ObservableList für Studentenobjekte
     */
    public ObservableList<Student> getStudentlist (){
        return studentrepository;
    }

    /**
     * Methode zum Hinzufügen eines Studenten zum Repository
     * @param student = Studenten Objekt, welches hinzugefügt werden muss
     */
    public void add_student(Student student){
        studentrepository.add(student);
    }

    /**
     *
     * @param student = Studenten Objekt, welches entfernt werden muss
     */
    public void remove_student(Student student){
        studentrepository.remove(student);
    }




}
