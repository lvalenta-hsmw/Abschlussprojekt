package com.example.abschlussprojekt;

import com.example.abschlussprojekt.Controller.MainView_Controller;
import com.example.abschlussprojekt.Model.Context;
import com.example.abschlussprojekt.Model.Student;
import com.example.abschlussprojekt.Model.Studentrepository;
import com.example.abschlussprojekt.ViewModel.MainView_ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Startpunkt der Anwendung.
 *
 * Diese Klasse startet die JavaFX-Anwendung + initialisiert die Context-Klasse
 *
 * Start:
 * 1. Erstellung der Context-Instanz
 * 2. Start der MainView über den Context
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Erstellen der Context Klasse welche als Window Manager / Service Schicht / Dependency Injector
        Context context = new Context();
        //Starten der MainView --> start der GUI
        context.start(stage);
    }
}
