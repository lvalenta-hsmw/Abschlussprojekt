# Abschlussprojekt 

## 1. Projektübersicht 
### 1.1 Ziele
### 


Context 

- hält zentrale Instanz für Repository 
- Lifecycle Manager für Stages 
- zuständig für Dependency Injection für Repository und Property  
- Ersetzt komplexes Framework wie Spring Boot, Singelton oder Composition Root --> zu komplex für den Anwendungsfall
- Vorteile: Lose Kopplung, möglichkeit mock-up repositorys zu nutzen ohne alles umzubauen
- Nachteile: wenn zu groß + viel Logik --> Good Objekt + nicht mehr einfach austauschbar



Aufbau / Besonderheiten 

Mein Ziel war es eine Studentenverwaltung zu programmieren die bestmöglich auf die Prozesse eines Benutzers im Arbeitsalltag ausgelegt ist. 
Das Herzstück ist die Studententabelle. Sie präsentiert sowohl Information und ist gleichzeitig Selektor für weiterführende Menüs. 
Man spart viel Zeit wenn alle Menüs gleichzeitig aktiv nebeneinander anzeigt werden können und nicht explicit für jeden Studenten neu aufgerufen werden müssen. 
Daher sind alle studentenabhängigen Menüs reaktiv an den ausgewählten Studenten gebunden. 


Besonderheiten: 
Das System ist zu 90% deklarativ ausgelegt und basiert auf Propertys und Bindings. Dabei habe ich auf folgendes geachtet:
- eine source of truth 
- kein Student exsistiert wenn er nicht im repository ist
- Objekte statt verschachtelte Array Lists


Binding:

View --> Viewcontroller --> binden an -->Propertys im ViewModel --> prüfung / validierung --> Commit --> Model 

Modelpropertys --> werden gebunden --> Viewmodel --> stellt bereit --> Controller -->bindet an View -->zeigt an 

Folge: Modeldaten werden per Bindings "live" an die View gekoppelt während Vieweingaben im Viewmodel gepuffert werden und bestimmte Bedingungen erfüllen müssen 
System ist in synchronem Zustand, weil fast keine Komponente "benachrichtigt" werden muss, sondern ihre Abhängigkeit beobachtet und entsprechend reagiert 
--> wenig Boilercode, keine Updatemethoden, einfach zu erweitern oder auszutauschen


Boolean Binding

- besser als Listener um z.B. Textfelder zu validieren oder erst bei klick validieren 
- in großen Projekten übersichtlicher

