package com.example.abschlussprojekt.Model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Modellklasse zur Repräsentation eines Studenten.
 *
 * Ein Student besitzt personenbezogene Daten (Vorname, Nachname, Studiengang, Fachsemester, Matrikelnummer, Email, Durchschnitt), sowie eine
 * Liste von Prüfungsleistungen. Die Prüfungsleistungen werden
 * als ObservableList verwaltet und über ein ListProperty bindbar gemacht.
 *
 * Der Durchschnitt der Noten wird reaktiv berechnet und
 * über ein bindbares readonl Property bereitgestellt.
 *
 * Es handelt sich um eine Model Klasse
 */

public class Student {

    //Eigenschaften des Student Objekt als Property um Bindung zu ermöglichen
    private final StringProperty vor_name = new SimpleStringProperty();
    private final StringProperty nach_name = new SimpleStringProperty();
    private final StringProperty studiengang = new SimpleStringProperty();
    private final StringProperty fachsemester = new SimpleStringProperty();
    private final StringProperty matrikelnummer = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final ReadOnlyDoubleWrapper durchschnitt = new ReadOnlyDoubleWrapper();




    //Übersichtsheitshalber aufgesplittete Observable List die Dank Exstractor auch auf Propertyänderungen reagiert -->readme Extraktor
    ObservableList<Prüfungsleistung> oberservableListe =
            FXCollections.observableArrayList(
                    pl -> new Observable[]{
                            pl.getDatumProperty(), pl.getFachProperty(), pl.getNoteProperty(), pl.getVersuchProperty() }
            );

    private final ListProperty<Prüfungsleistung> prüfungsleistungen= new SimpleListProperty<>(oberservableListe);


    /**
     * Student bekommt Dummy Prüfungsleistung
     * Durchschnitt wird per reaktivem Binding errechnet
     * @param vor_name
     * @param nach_name
     * @param studiengang
     * @param fachsemester
     * @param matrikelnummer
     * @param email
     */
    public Student(String vor_name, String nach_name, String studiengang, String fachsemester, String matrikelnummer, String email ) {
        this.vor_name.set(vor_name);
        this.nach_name.set(nach_name);
        this.matrikelnummer.set(matrikelnummer);
        this.studiengang.set(studiengang);
        this.fachsemester.set(fachsemester);
        this.email.set(email);

        //Dummy Prüfungsleistung die jeder Student hat
        prüfungsleistungen.add(new Prüfungsleistung("Math",2.0, LocalDate.of(2024, 6, 15)));

        /*
        ()-> = keine Parameter
        {...} = Berechnung des Durchschnittes
                - empty Fall abdecken (return 0.0)
                - .stream = Pipelineverarbetung
                - .maptoDouble (wandelt jeden Streameintrag in double Wert) --> DoubleStream ("Liste" aus den Noten)
                - .average = Durchschnitt
                - orElse = OptionalDouble könnte leer sein --> Auffangfall = 0.0

        , prüfungsleistung = Observable List mit Extractor (siehe oben) --> ändert sich die Liste wird {...} neu berechnet


         */
        durchschnitt.bind(Bindings.createDoubleBinding(
                () -> {
                    if (prüfungsleistungen.isEmpty()) return 0.0;
                    return prüfungsleistungen.stream()
                            .mapToDouble(pl -> pl.getNote())
                            .average()
                            .orElse(0.0);
                },
                prüfungsleistungen
        ));



    }

    //getter

    /**
     * Getter Methoden für Propertys des Studenten und dazugehörigen Werten für simple Eigenschaften
     *
     */
    public String getVor_name(){
        return vor_name.get();
    }

    public StringProperty Vorname_property(){
        return vor_name;
    }

    public String get_Matrikelnummer(){
        return matrikelnummer.get();
    }

    public StringProperty Matrikelnummer_property(){
        return matrikelnummer;
    }

    public String getNach_name() {
        return nach_name.get();
    }

    public StringProperty Nach_nameProperty() {
        return nach_name;
    }

    public String getStudiengang() {
        return studiengang.get();
    }

    public StringProperty StudiengangProperty() {
        return studiengang;
    }

    public String getFachsemester() {
        return fachsemester.get();
    }

    public StringProperty FachsemesterProperty() {
        return fachsemester;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty EmailProperty() {
        return email;
    }

    /**
     * Getter Methoden für Prüfungsleistung und Durchschnitt
     *
     */
    //Prüfungsleistung
    public ListProperty<Prüfungsleistung> getPrüfungsleistungsProperty(){
        return prüfungsleistungen;
    }
    public ObservableList<Prüfungsleistung> getPrüfungsleitung(){
        return prüfungsleistungen.get();
    }
    public ReadOnlyDoubleProperty getDurchschnittsProperty(){
        return durchschnitt;
    }
    public double getDurchschnitt() {
        return durchschnitt.get();
    }

    /**
     *
     * @param prüfungsleistung = Objekt vom Typ Prüfungsleistung
     */
    public void addPrüfungsleistung(Prüfungsleistung prüfungsleistung){
        prüfungsleistungen.add(prüfungsleistung);
    }

    /**
     *
     * @param prüfungsleistung = Objekt vom Typ Prüfungsleistung
     */
    public void deletePrüfungsleistung(Prüfungsleistung prüfungsleistung){
        prüfungsleistungen.remove(prüfungsleistung);
    }

    //Setter

    /**
     *
     * Setter für Werte der Propertys
     */
    public void setVor_name(String vor_name){
        this.vor_name.set(vor_name);
    }

    public void setNach_name(String nach_name){
        this.nach_name.set(nach_name);
    }

    public void setMatrikelnummer(String matrikelnummer){
        this.matrikelnummer.set(matrikelnummer);
    }

    public void setStudiengang(String studiengang){
        this.studiengang.set(studiengang);
    }

    public void setFachsemester(String fachsemester){
        this.fachsemester.set(fachsemester);
    }

    public void setEmail(String email){
        this.email.set(email);
    }








}
