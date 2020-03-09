package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import multiplayer.Multiplayer;
import utils.StageManager;

public class MainMenu extends View {
    public MainMenu() {
        Pane singleplayerButton = Widgets.makeBigButton(Icon.PERSON, "Singleplayer");
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));
        singleplayerButton.setOnMouseClicked(event -> StageManager.switchView(new SelectTheme(null, 3)));

        Pane joinMultiplayerButton = Widgets.makeBigButton(Icon.GROUP_ADD, "Join Multiplayer");
        joinMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));
        joinMultiplayerButton.setOnMouseClicked(mouseEvent -> StageManager.switchView(new JoinMultiplayer()));

        Pane hostMultiplayerButton = Widgets.makeBigButton(Icon.GROUP, "Host Multiplayer");
        hostMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));
        hostMultiplayerButton.setOnMouseClicked(mouseEvent -> Multiplayer.host(Multiplayer.DEFAULT_PORT));

        Pane orbEditor = Widgets.makeOrbButton(Icon.EDIT);
        Pane orbScores = Widgets.makeOrbButton(Icon.EMOJI_EVENTS);
        Pane orbSettings = Widgets.makeOrbButton(Icon.SETTINGS);
        Pane orbQuit = Widgets.makeOrbButton(Icon.CLOSE);

        HBox orbContainer = new HBox(16, orbEditor, orbScores, orbSettings, orbQuit);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48);
        StackPane.setAlignment(orbContainer, Pos.BOTTOM_CENTER);

        VBox menuContainer = new VBox(16, Widgets.makeLogo(), singleplayerButton, joinMultiplayerButton,
                hostMultiplayerButton, orbContainer) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        };

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(menuContainer);

        orbQuit.setOnMouseClicked(mouseEvent -> {
            StageManager.switchView(new EndScreen());
        });

        orbSettings.setOnMouseClicked(mouseEvent -> StageManager.switchView((new Settings())));
    }
}
