package com.example.abschlussprojekt.Controller;

import com.example.abschlussprojekt.Model.Context;
import com.example.abschlussprojekt.Model.Prüfungsleistung;
import com.example.abschlussprojekt.ViewModel.ExamView_ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.DoubleStringConverter;

import java.time.LocalDate;
import java.util.Optional;

public class ExamView_Controller {

    @FXML
    private TableView<Prüfungsleistung> exam_table;

    @FXML
    private TableColumn<Prüfungsleistung,String> fach;
    @FXML
    private TableColumn<Prüfungsleistung,Double> note;
    @FXML
    private TableColumn<Prüfungsleistung, LocalDate> datum;
    @FXML
    private Button change_Prüfungsleistung, add_Prüfungsleistung, delete_Prüfungsleistung;
    @FXML
    private TextField PLFach_Textfield_change, PLNote_Textfield_change, PLFach_Textfield_add, PLNote_Textfield_add;
    @FXML
    private DatePicker PLDatum_Textfield_change, PLDatum_Textfield_add;



    private ExamView_ViewModel viewModel;
    private Context context;


    @FXML
    private void initialize(){

        //Für jede Spalte wird der Inhalt einer jeden Zeile dynamisch an das entsprechende Property des Prüfungsleistung gebunden
        //cellData ist dabei vom in den Generics angegebenen Typ (Prüfungslesitung) und vom entsprechend angegebenen Datentyp

        fach.setCellValueFactory(
                cellData -> cellData.getValue().getFachProperty()
        );
        //Generics stimmen nicht überein
        //TableColumn erwartet ObservableValue<Double> aber .getNoteProperty liefert ObservableValue<Numbers> --> .asObject wandelt um in ObservableValue<Double>
        note.setCellValueFactory(celldata ->celldata.getValue().getNoteProperty().asObject());
        datum.setCellValueFactory(cellData ->cellData.getValue().getDatumProperty());
    }




    public void setParams (ExamView_ViewModel viewmodel, Context context){
        this.viewModel = viewmodel;
        this.context = context;

        //Textfield Bindings Change
        PLDatum_Textfield_change.valueProperty().bindBidirectional(viewmodel.datum_changeProperty());
        PLFach_Textfield_change.textProperty().bindBidirectional(viewmodel.fach_changeProperty());
        //Textfield liefert String --> muss in Double umgewandelt werden
        TextFormatter<Double> formater = new TextFormatter<>(new DoubleStringConverter());
        //Textfield wird Formatter gesetzt
        PLNote_Textfield_change.setTextFormatter(formater);
        //formater liefert bindbares Property was an ViewModel gebunden werden kann
        //--> besser als Listener + parseDouble (nicht mehr reaktiv als Property)
        //Observables passen nicht zusammen --> .asObject
        formater.valueProperty().bindBidirectional(viewmodel.note_changeProperty().asObject());

        //Textfield Bindings Add

        PLDatum_Textfield_add.valueProperty().bindBidirectional(viewmodel.datum_addProperty());
        PLFach_Textfield_add.textProperty().bindBidirectional(viewmodel.fach_addProperty());
        TextFormatter<Double> formater_add = new TextFormatter<>(new DoubleStringConverter());
        PLNote_Textfield_add.setTextFormatter(formater_add);
        formater_add.valueProperty().bindBidirectional(viewmodel.note_addProperty().asObject());

        // Tableview Bindings
        //aktiv ausgewählte Prüfungsleistung wird als Property an das Viewmodel gebunden
        viewModel.SelectedPrüfungsleistungProperty().bind(exam_table.getSelectionModel().selectedItemProperty());
        //Hinterlegtes Datenmodell bleibt reaktiv austauschbar je nachdem welcher Student in der MainView ausgewählt ist
        exam_table.itemsProperty().bind(viewmodel.getPrüfungsleistungsProperty());

        //Buttons
        change_Prüfungsleistung.disableProperty().bind(viewModel.alles_change_okayProperty().not());
        change_Prüfungsleistung.setOnAction(e->viewModel.commit_change());
        add_Prüfungsleistung.disableProperty().bind(viewmodel.alles_add_okayProperty().not());
        add_Prüfungsleistung.setOnAction(e->viewModel.commit_add());
        //wenn keine Prüfungsleistung ausgewählt ist keine Löschung möglich
        delete_Prüfungsleistung.disableProperty().bind(viewmodel.SelectedPrüfungsleistungProperty().isNull());
        delete_Prüfungsleistung.setOnAction(e->{
            //Interne Controls Klasse zum Erzeugen einfacher Pop Ups
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bestätigung");
            alert.setHeaderText("Eintrag löschen");
            alert.setContentText("Sind sie sicher das sie die ausgewählte Note löschen wollen?");
            Optional<ButtonType> result = alert.showAndWait();
            //Ergebniss auswerten und wenn positiv Prüfungsleistung löschen lassen
            if (result.isPresent() && result.get() == ButtonType.OK){
                viewModel.deletePrüfungsleistung();
            }
        });








    }

}
