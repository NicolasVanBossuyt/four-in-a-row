package views.screen;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import views.Animations;
import utils.AudioManager;
import utils.StageManager;
import views.View;
import views.menu.Main;

import static views.Layout.horizontallyCentered;
import static views.Layout.verticallyCentered;
import static views.Widget.*;

public class Splash extends View {
    public Splash() {
        super(false);

        Button button = button("Press any key to start...", event -> {
            goToMainMenu();
        });

        button.setOpacity(0);

        StackPane.setAlignment(button, Pos.BOTTOM_CENTER);

        Label label = new Label("Groupe AB02\nL. De Laet, C. Lepine, N. Van Bossuyt\n\nRelease 3");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setTextFill(new Color(1, 1, 1, 0.5));
        label.setOpacity(0);

        StackPane.setAlignment(label, Pos.BOTTOM_CENTER);

        StackPane black = new StackPane();
        black.setDisable(true);
        black.setStyle("-fx-background-color: black");

        black.getChildren().add(
                verticallyCentered(horizontallyCentered(buzzer()))
        );

        Node logo = logo();

        setOnKeyTyped(keyEvent -> {
            goToMainMenu();
        });

        Animations.fade(black, 1, 0, 0.25, 2);
        Animations.fade(logo, 0, 1, 0.25, 2);
        Animations.scale(logo, 4, 1, 0.25, 2, () -> StageManager.background().showSpinner());

        Animations.fade(button, 0, 1, 0.25, 3);
        Animations.fade(label, 0, 1, 0.30, 3);
        Animations.translateY(button, -100, -130, 0.25, 3);
        Animations.translateY(label, 0, -50, 0.30, 3);

        this.getChildren().addAll(logo, button, label, black);
    }

    @Override
    public void onAttach() {
        AudioManager.playLoopWithTransition("assets/musics/loop.wav", "assets/musics/intro.wav");

    }

    private void goToMainMenu() {
        StageManager.background().showSpinner();
        StageManager.switchView(new Main());
        AudioManager.playLoopWithTransition("assets/musics/loop2.wav", "assets/musics/transition.wav");
    }
}
