package bsuir.vlad.universityshooter.game.windows;

import bsuir.vlad.universityshooter.game.Difficulty;
import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.game.profile.ProfileFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

class LeadersTableWindow {
    LeadersTableWindow(Stage stage) {
        TableView<Profile> scoreTable = new TableView<>();

        TableColumn<Profile, String> name = new TableColumn<>("Profile name");
        TableColumn<Profile, Difficulty> difficulty = new TableColumn<>("Difficulty");
        TableColumn<Profile, Long> score = new TableColumn<>("Score");

        scoreTable.getColumns().addAll(name, difficulty, score);

        for (TableColumn tc : scoreTable.getColumns()) {
            double minWidth = 200;
            tc.setMinWidth(minWidth);
        }

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        String filePath = "resources/profiles.xml";
        ProfileFile profileFile = new ProfileFile(filePath);

        List<Profile> profileList = profileFile.load();
        ObservableList<Profile> profileObservableList = FXCollections.observableList(profileList);

        scoreTable.setItems(profileObservableList);

        double tableBottom = 45;
        double tableRightLeftTop = 0;
        StackPane.setMargin(scoreTable, new Insets(
                tableRightLeftTop, tableRightLeftTop, tableBottom, tableRightLeftTop
        ));
        StackPane.setAlignment(scoreTable, Pos.TOP_CENTER);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelEvent -> {
            stage.close();
            new MainMenuWindow(stage);
        });

        double buttonBottom = 10;
        double buttonRightLeftTop = 0;
        StackPane.setMargin(cancelButton, new Insets(
                buttonRightLeftTop, buttonRightLeftTop, buttonBottom, buttonRightLeftTop
        ));
        StackPane.setAlignment(cancelButton, Pos.BOTTOM_CENTER);

        StackPane pane = new StackPane(scoreTable, cancelButton);

        Scene leadersTableScene = new Scene(pane);

        stage.setScene(leadersTableScene);
        stage.show();
    }
}
