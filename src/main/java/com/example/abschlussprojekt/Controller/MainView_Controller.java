package com.example.abschlussprojekt.Controller;

import com.example.abschlussprojekt.Model.Context;
import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.ViewModel.MainView_ViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class MainView_Controller {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> vor_nameColumn, nach_nameColumn, studiengang_Column, fachsemester_Column, matrikelnummer_Column, email_Column, durchschnitt_Column;
    @FXML
    private Button add_btn, change_btn, exam_btn, delete_btn;
    @FXML
    private TextField vorname_textfield, nachname_textfield, studiengang_textfield, fachsemester_textfield, matrikelnummer_textfield, email_textfield,durchschnitt_textfield;


    private MainView_ViewModel viewModel;
    private Context context;




    @FXML
    private void initialize() {
        // nur TableView-Setup
        vor_nameColumn.setCellValueFactory(
                cellData -> cellData.getValue().getVorname_property()
        );
        nach_nameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nach_nameProperty()
        );
        studiengang_Column.setCellValueFactory(
                cellData -> cellData.getValue().studiengangProperty()
        );
        fachsemester_Column.setCellValueFactory(
                cellData -> cellData.getValue().fachsemesterProperty()
        );
        matrikelnummer_Column.setCellValueFactory(
                cellData -> cellData.getValue().Matrikelnummer_property()
        );
        email_Column.setCellValueFactory(
                cellData -> cellData.getValue().emailProperty()
        );
        //.getDurchschnittsProperty liefert readonly DoubleProperty --> falscher Datentyp
        durchschnitt_Column.setCellValueFactory(
                cellData -> cellData.getValue().getDurchschnittsProperty().asString()
        );
    }

    public void setParams(MainView_ViewModel viewModel, Context context) {
        //Initialisieren
        this.viewModel = viewModel;
        this.context = context;


        // Daten binden
        //Bei Initialsierung Tabelle mit aktuellen Daten befüllen
        studentTable.setItems(viewModel.getfilterdStudentlist());
        //aktuell ausgewählten Studenten an viewmodel weiterreichen
        viewModel.selectedStudentProperty()
                .bind(studentTable.getSelectionModel().selectedItemProperty());

        //Binds Textfields für Suche
        vorname_textfield.textProperty().bindBidirectional(viewModel.vornameProperty());
        nachname_textfield.textProperty().bindBidirectional(viewModel.nachnameProperty());
        studiengang_textfield.textProperty().bindBidirectional(viewModel.studiengangProperty());
        fachsemester_textfield.textProperty().bindBidirectional(viewModel.fachsemesterProperty());
        matrikelnummer_textfield.textProperty().bindBidirectional(viewModel.matrikelnummerProperty());
        email_textfield.textProperty().bindBidirectional(viewModel.emailProperty());
        durchschnitt_textfield.textProperty().bindBidirectional(viewModel.durchschnittProperty());

        //neue Stages / Fenster erzeugen

        //ADD
        add_btn.setOnAction(e -> {
            try {
                context.openAddStudentWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        //Change
        change_btn.setOnAction(e-> {
            try {
                context.openChangeStudentWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        change_btn.disableProperty().bind(viewModel.selectedStudentProperty().isNull());

        //Exam
        exam_btn.setOnAction(e-> {
            try {
                context.openExamViewWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        exam_btn.disableProperty().bind(viewModel.selectedStudentProperty().isNull());

        //delete
        delete_btn.setOnAction(e->{
            //Bestätigung des Löschvorgangs
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bestätigung");
            alert.setHeaderText("Eintrag löschen");
            //injection des Namens der zu löschenden Person, reaktiv nicht nötig, da so lange Alert Dialog offen kann kein anderer Student ausgewählt werden
            alert.setContentText("Sind sie sicher das sie " + viewModel.selectedStudentProperty().get().getNach_name() + " " + viewModel.selectedStudentProperty().get().getVor_name() + " löschen wollen?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                viewModel.delete_student();
            }
        });
        //nur möglich wenn Student ausgewählt
        delete_btn.disableProperty().bind(viewModel.selectedStudentProperty().isNull());


    }



}