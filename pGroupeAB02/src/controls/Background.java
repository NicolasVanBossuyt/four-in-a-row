package controls;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import views.Animation;

public class Background extends StackPane {
    private boolean spinnerVisible;
    private double paralax = 1;

    private Pane gradiant;
    private Pane glitter;
    private Pane spinner;

    public Background(Boolean is_fancy) {
        gradiant = new Pane();
        gradiant.setId("background");
        gradiant.setScaleX(1.25);
        gradiant.setScaleY(1.25);

        getChildren().add(gradiant);

        if (is_fancy) {
            spinnerVisible = false;

            glitter = new Pane();
            glitter.setId("glitter");
            glitter.setScaleX(1.25);
            glitter.setScaleY(1.25);
            glitter.setOpacity(0);

            spinner = new Pane();
            spinner.setId("wheel");
            spinner.setMinHeight(350);
            spinner.setMinWidth(350);
            spinner.setMaxHeight(350);
            spinner.setMaxWidth(350);
            spinner.setScaleX(0);
            spinner.setScaleY(0);
            spinner.setOpacity(0.25);

            RotateTransition animation = new RotateTransition(Duration.seconds(16), spinner);
            animation.setByAngle(360);
            animation.setCycleCount(Transition.INDEFINITE);
            animation.setInterpolator(Interpolator.LINEAR);
            animation.play();

            getChildren().addAll(glitter, spinner);
        }
    }

    public void animate(double mouseX, double mouseY) {
        double x = (mouseX - getWidth() / 2) / getWidth();
        double y = (mouseY - getHeight() / 2) / getHeight();

        double offsetx = getWidth() * 0.25 * x * paralax;
        double offsety = getHeight() * 0.25 * y * paralax;

        gradiant.setTranslateX(-offsetx);
        gradiant.setTranslateY(-offsety);

        glitter.setTranslateX(-offsetx * 0.75);
        glitter.setTranslateY(-offsety * 0.75);

        spinner.setTranslateX(-offsetx * 0.5);
        spinner.setTranslateY(-offsety * 0.5);
    }

    public void showSpinner() {
        if (!spinnerVisible && spinner != null) {
            spinnerVisible = true;

            Animation.scale(spinner, 0, 10, 0.5);
            Animation.fade(glitter, 0, 0.25, 0.5);
        }
    }

    public void hideSpinner() {
        if (spinnerVisible && spinner != null) {
            spinnerVisible = false;

            Animation.scale(spinner, 10, 0, 0.5);
            Animation.fade(glitter, 0.25, 0, 0.5);
        }
    }

    public double getParalax() {
        return this.paralax;
    }

    public void setParalax(double paralax) {
        this.paralax = paralax;
    }
}