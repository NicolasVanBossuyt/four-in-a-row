package views.menu;

import views.widgets.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import models.Player;
import utils.StageManager;
import views.View;
import utils.Widgets;

public class Score extends View {
    TableView tableView = new TableView();

    public Score() {
        setPadding(new Insets(32));
        tableView.setMaxWidth(512);
        tableView.setMaxHeight(480);

        TableColumn<String, Player> column1 = new TableColumn<>("Position");
        column1.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<String, Player> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Player> column3 = new TableColumn<>("Score");
        column3.setCellValueFactory(new PropertyValueFactory<>("score"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Title("Score"), tableView, backButton);
    }
}