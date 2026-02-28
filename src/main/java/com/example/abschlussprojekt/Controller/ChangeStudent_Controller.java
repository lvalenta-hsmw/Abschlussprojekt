package com.example.abschlussprojekt.Controller;

import com.example.abschlussprojekt.Model.Context;
import com.example.abschlussprojekt.ViewModel.ChangeStudent_ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller für ChangeStudent
 *
 * Verwaltet die Bindungen zwischen den TextFields der View und den Properties
 * des ChangeStudent_ViewModel.
 * Überprüft die Eingaben automatisch über die
 * BooleanBindings des ViewModels und aktiviert/deaktiviert den commit-Button.
 *
 * Struktur:
 * - TextFields: vorname, nachname, studiengang, fachsemester, matrikelnummer, email
 * - Buttons:
 *      - save_button: löst Commit-Methode im ViewModel aus.
 *      - cancel_button: löst Cancel-Methode im ViewModel aus.
 *
 */

public class ChangeStudent_Controller {
    @FXML
    private TextField vorname_textfield, nachname_textfield, studiengang_textfield, fachsemester_textfield, matrikelnummer_textfield, email_textfield;

    @FXML
    private Button save_button, cancel_button;



    private ChangeStudent_ViewModel viewModel;
    private Context context;

    /**
     *Binding der Eingabefelder an Propertys aus dem Viewmodel
     * Klick Listener für Buttons
     * Disable Property für Buttons
     * @param viewModel = Property Injection des Viewmodel und des Context über Methode
     * @param context = zentrale Context Instant zur Fensterverwaltung (siehe Context)
     */
    public void setParams(ChangeStudent_ViewModel viewModel, Context context){
        this.viewModel = viewModel;
        this.context = context;

        //binds --> halten View und ViewModel (bzw Model) synchron
        vorname_textfield.textProperty().bindBidirectional(viewModel.vornameProperty());
        nachname_textfield.textProperty().bindBidirectional(viewModel.nachnameProperty());
        studiengang_textfield.textProperty().bindBidirectional(viewModel.studiengangProperty());
        fachsemester_textfield.textProperty().bindBidirectional(viewModel.fachsemesterProperty());
        matrikelnummer_textfield.textProperty().bindBidirectional(viewModel.matrikelnummerProperty());
        email_textfield.textProperty().bindBidirectional(viewModel.emailProperty());
        //Button wird nur anklickbar wenn alles_okayProperty aus dem Viewmodel auch okay ist
        save_button.disableProperty().bind(viewModel.alles_okayProperty().not());
        //übernehmen der Änderungen ins Model
        save_button.setOnAction(e->viewModel.commit());
        //zurücksetzten
        cancel_button.setOnAction(e->viewModel.cancel());
    }





}
