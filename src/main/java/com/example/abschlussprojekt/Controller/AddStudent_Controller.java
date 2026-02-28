package com.example.abschlussprojekt.Controller;

import com.example.abschlussprojekt.Model.Context;
import com.example.abschlussprojekt.ViewModel.AddStudent_ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller für AddStudent
 *
 * Verwaltet die Bindungen zwischen den TextFields der View und dem ViewModel.
 * Überprüft die Eingaben automatisch über die
 * BooleanBindings des ViewModels und aktiviert/deaktiviert den commit-Button. -->Logik für BooleanBindings im ViewModel.
 *
 * Aufbau:
 * - TextFields: vorname, nachname, studiengang, fachsemester, matrikelnummer, email
 * - Button: save_button, reagiert nur, wenn alle Eingaben gültig sind
 *
 * Logik:
 * - setParams(AddStudent_ViewModel viewModel, Context context) --> Initialisiert die Bindungen zwischen View und ViewModel und setzt
 *   Event-Handler für den save-Button.
 *   Konstruktor leer, weil Controller nur leer erzeugt werden können auf die herkömmliche Weiße.
 */

public class AddStudent_Controller {

    @FXML
    private TextField vorname_textfield, nachname_textfield, studiengang_textfield, fachsemester_textfield, matrikelnummer_textfield, email_textfield;

    @FXML
    private Button save_button;

    private AddStudent_ViewModel viewModel;



    /**
     * Binden der Eingabefelder an Propertys aus dem Viewmodel
     * Klick Listener für Buttons
     * Disable Propert für Buttons
     * @param viewModel = Property Injection des Viewmodel und des Context über Methode
     * @param context = Property Injection des Viewmodel und des Context über Methode
     */
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
