package com.example.abschlussprojekt.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Studentrepository {

    private final ObservableList<Student> studentrepository = FXCollections.observableArrayList();


    //getter
    public ObservableList<Student> getStudentlist (){
        return studentrepository;
    }


    public void add_student(Student student){
        studentrepository.add(student);
    }

    public void remove_student(Student student){
        studentrepository.remove(student);
    }




}
