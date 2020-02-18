package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import utils.Main;

public class MainMenu extends StackPane {
    public MainMenu()
    {
        Pane singleplayerButton = Widgets.makeBigButton("assets/singleplayer.png", "Singleplayer" );
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane multiplayerButton = Widgets.makeBigButton( "assets/multiplayer.png","Multiplayer");
        multiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane orbScores = Widgets.makeOrbButton("assets/score.png");
        Pane orbSettings= Widgets.makeOrbButton("assets/settings.png");
        Pane orbQuit= Widgets.makeOrbButton("assets/close.png");

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.getChildren().addAll(
                Widgets.makeLogo(),
                singleplayerButton,
                multiplayerButton);
        menuContainer.setMaxWidth(436);

        HBox orbContainer = new HBox(16, orbScores, orbSettings, orbQuit);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48);
        StackPane.setAlignment(orbContainer, Pos.BOTTOM_CENTER);

        this.setAlignment(Pos.CENTER);
        this.setId("background");
        this.setPadding(new Insets(32));
        this.getChildren().addAll(menuContainer ,orbContainer);

        multiplayerButton.setOnMouseClicked(mouseEvent -> Main.switchScene(new MultiplayerSelect()));
        orbQuit.setOnMouseClicked(mouseEvent -> Main.quit());
    }
}
