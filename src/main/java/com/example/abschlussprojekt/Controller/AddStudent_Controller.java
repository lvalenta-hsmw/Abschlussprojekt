package com.example.abschlussprojekt.Controller;

import com.example.abschlussprojekt.Model.Context;
import com.example.abschlussprojekt.ViewModel.AddStudent_ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddStudent_Controller {

    @FXML
    private TextField vorname_textfield, nachname_textfield, studiengang_textfield, fachsemester_textfield, matrikelnummer_textfield, email_textfield;

    @FXML
    private Button save_button;

    private AddStudent_ViewModel viewModel;

    //Übergabe nur durch Setter möglich, weil Konstruktor leer erstellt wird
    public void setParams(AddStudent_ViewModel viewModel, Context context){
        this.viewModel = viewModel;

        //binds
        vorname_textfield.textProperty().bindBidirectional(viewModel.vornameProperty());
        nachname_textfield.textProperty().bindBidirectional(viewModel.nachnameProperty());
        studiengang_textfield.textProperty().bindBidirectional(viewModel.studiengangProperty());
        fachsemester_textfield.textProperty().bindBidirectional(viewModel.fachsemesterProperty());
        matrikelnummer_textfield.textProperty().bindBidirectional(viewModel.matrikelnummerProperty());
        email_textfield.textProperty().bindBidirectional(viewModel.emailProperty());

        save_button.disableProperty().bind(viewModel.alles_okayProperty().not());


        save_button.setOnAction(e -> viewModel.commit());
    }
}
