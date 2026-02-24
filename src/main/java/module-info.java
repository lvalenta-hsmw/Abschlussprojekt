module com.example.abschlussprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    //requires com.example.abschlussprojekt;

    // Öffnen für FXML Loader (Controller-Pakete)
    opens com.example.abschlussprojekt.Controller to javafx.fxml;

    // Ressourcen-Pakete müssen NICHT geöffnet werden
    // ViewModel und Model exportieren (für andere Module falls nötig)
    exports com.example.abschlussprojekt.ViewModel;
    exports com.example.abschlussprojekt.Model;

    // HelloApplication-Paket exportieren, damit Launcher Zugriff hat
    exports com.example.abschlussprojekt;
}
