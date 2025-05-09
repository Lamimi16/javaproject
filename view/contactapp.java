package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.contact;
import dao.contactDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class contactapp extends Application {

    private List<contact> contacts = new ArrayList<>();
    private ListView<contact> listView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        contacts = contactDAO.getAllContacts();
        listView.getItems().addAll(contacts);

        TextField nomField = new TextField(); nomField.setPromptText("Nom");
        TextField telField = new TextField(); telField.setPromptText("Téléphone");
        TextField emailField = new TextField(); emailField.setPromptText("Email");

        Button ajouterBtn = new Button("Ajouter");
        Button supprimerBtn = new Button("Supprimer");
        TextField rechercherField = new TextField(); rechercherField.setPromptText("Rechercher");

        ajouterBtn.setOnAction(e -> {
            contact c = new contact(nomField.getText(), telField.getText(), emailField.getText());
            contactDAO.ajouter(c);
            contacts.add(c);
            listView.getItems().add(c);

            nomField.clear(); telField.clear(); emailField.clear();
        });

        supprimerBtn.setOnAction(e -> {
            contact selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                contactDAO.supprimer(selected);
                contacts.remove(selected);
                listView.getItems().remove(selected);
            }
        });

        rechercherField.textProperty().addListener((obs, oldVal, newVal) -> {
            listView.getItems().setAll(
                contacts.stream()
                        .filter(c -> c.getNom().toLowerCase().contains(newVal.toLowerCase()))
                        .collect(Collectors.toList())
            );
        });

        VBox form = new VBox(5, nomField, telField, emailField, ajouterBtn, supprimerBtn, rechercherField);
        HBox root = new HBox(10, form, listView);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Carnet de Contacts (MySQL)");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
