package com.example.abschlussprojekt.Model;

import com.example.abschlussprojekt.Controller.AddStudent_Controller;
import com.example.abschlussprojekt.Controller.ChangeStudent_Controller;
import com.example.abschlussprojekt.Controller.ExamView_Controller;
import com.example.abschlussprojekt.Controller.MainView_Controller;
import com.example.abschlussprojekt.HelloApplication;
import com.example.abschlussprojekt.ViewModel.AddStudent_ViewModel;
import com.example.abschlussprojekt.ViewModel.ChangeStudent_ViewModel;
import com.example.abschlussprojekt.ViewModel.ExamView_ViewModel;
import com.example.abschlussprojekt.ViewModel.MainView_ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Zentrale Verwaltungs und Koordinationsinstanz
 *
 * Die Context-Klasse fungiert als Single Source of Truth und übernimmt die Orchestrierung der Views.
 * Sie verwaltet das aktuell ausgewählte Student-Objekt aus der MainView TableView, sowie das Öffnen und Schließen
 * von Stage-Instanzen.
 *
 * Durch die zentrale Bereitstellung gemeinsamer Properties wird sichergestellt,
 * dass alle ViewModels auf den gleichen Anwendungszustand zugreifen.
 *
 * Außerdem übergibt Sie Abhängigkeiten über den Konstruktor
 *
 */


public class Context {
    private final Studentrepository repository = new Studentrepository();   //Erstellen der einzigen Instanz des Respositorys
    private MainView_ViewModel main_viewModel;
    private Stage examstage, addStudentstage, changeStudentstage;

    /**
     *erstellt Repository Instanz
     *fügt Dummy Student hinzu
     * startet MainView + Controller
     * @param stage
     * @throws IOException wenn Loader die Ressource nicht findet
     */
    public void start(Stage stage) throws IOException {

        //Initialisierung eines Dummy Students
        repository.add_student(new Student("Leon", "Valents", "Informatik", "1", "234", "leonvalenta@gmx.de"));

        //Mainviewmodel bekommt Repository (single source of truth) über Konstruktor Injektion
        main_viewModel = new MainView_ViewModel(repository);

        //laden des FXML
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/MainView_View.fxml"));

        Parent root = loader.load();
        //Controller über loader holen
        MainView_Controller controller = loader.getController();
        //controller bekommt viewmodel und context über Setter Injection, da Konstruktor ohne Parameter aufgerufen werden muss
        controller.setParams(main_viewModel, this);


        //Fenster anzeigen + dekorieren
        Scene scene = new Scene(root, 1191, 827);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Öffnet und initialisiert Fenster zum hinzufügen eines neuen Studenten
     * @throws IOException wenn Ressource nicht geladen werden kann
     */
    public void openAddStudentWindow() throws IOException {
        //Überprüfen ob Fenster schon existiert
        if(addStudentstage == null){
            AddStudent_ViewModel viewmodel = new AddStudent_ViewModel(repository);
            FXMLLoader laoder = new FXMLLoader(HelloApplication.class.getResource("views/AddStudent_View.fxml"));
            Parent root = laoder.load();
            AddStudent_Controller controller = laoder.getController();
            controller.setParams(viewmodel,this);

            Scene scene = new Scene(root, 200, 200);
            addStudentstage = new Stage();
            addStudentstage.setTitle("Add Student");
            addStudentstage.setScene(scene);
            addStudentstage.show();
        }
        //wenn Fenster exsistiert wieder in den Vordergund bringen --> nicht mehrere Fenster eines Typen
        else {
            if  (addStudentstage.isIconified()){
                addStudentstage.setIconified(false);
            }
            addStudentstage.show();
            addStudentstage.toFront();
            addStudentstage.requestFocus();
        }

    }

    /**
     * Öffnet und initialisiert Fenster zum Verändern eines ausgewählten Studenten
     * @throws IOException wenn Ressource nicht geladen werden kann
     */
    public void openChangeStudentWindow() throws IOException {
        //siehe openAddStudentWindow
        if (changeStudentstage == null){
            ChangeStudent_ViewModel viewModel = new ChangeStudent_ViewModel(repository,main_viewModel.selectedStudentProperty());
            FXMLLoader laoder = new FXMLLoader(HelloApplication.class.getResource("views/ChangeStudent_View.fxml"));
            Parent root = laoder.load();
            ChangeStudent_Controller controller = laoder.getController();
            controller.setParams(viewModel,this);

            Scene scene = new Scene(root, 600,400);
            changeStudentstage = new Stage();
            changeStudentstage.setTitle("Change Student");
            changeStudentstage.setScene(scene);
            changeStudentstage.show();
        }
        else {
            if  (changeStudentstage.isIconified()){
                changeStudentstage.setIconified(false);
            }
            changeStudentstage.show();
            changeStudentstage.toFront();
            changeStudentstage.requestFocus();
        }

    }

    /**
     * öffnet und initialisiert Fenster zur Notenübersicht für den ausgewählten Studenten
     * @throws IOException wenn Ressource nicht geladen werden kann
     */
    public void openExamViewWindow() throws IOException {
        //siehe openAddStudentWindow
        if (examstage == null ){
            ExamView_ViewModel viewModel = new ExamView_ViewModel(repository, main_viewModel.selectedStudentProperty());
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/ExamView.fxml"));
            Parent root = loader.load();
            ExamView_Controller controller = loader.getController();
            controller.setParams(viewModel, this);

            Scene scene = new Scene(root, 600, 634);
            examstage = new Stage();
            examstage.setResizable(false);
            examstage.setTitle("Exam  View");
            examstage.setScene(scene);
            examstage.show();
        }
        else {
            if  (examstage.isIconified()){
                examstage.setIconified(false);
            }
            examstage.show();
            examstage.toFront();
            examstage.requestFocus();
        }


    }

}
