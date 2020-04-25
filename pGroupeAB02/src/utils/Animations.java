package utils;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {
    private Animations() {
    }

    public static Node fade(double from, double to, double duration, Node node) {
        return fade(from, to, duration, 0, null, node);
    }

    public static Node fade(Node node, double from, double to, double duration, double delay) {
        return fade(from, to, duration, delay, null, node);
    }

    public static Node fade(double from, double to, double duration, Runnable then, Node node) {
        return fade(from, to, duration, 0, then, node);
    }

    public static Node fade(double from, double to, double duration, double delay, Runnable then, Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(duration), node);

        fade.setDelay(Duration.seconds(delay));
        fade.setFromValue(from);
        fade.setToValue(to);

        if (then != null) {
            fade.setOnFinished(even -> then.run());
        }

        fade.play();

        return node;
    }

    public static Node scale(Node node, double from, double to, double duration) {
        return scale(node, from, to, duration, 0, null);
    }

    public static Node scale(Node node, double from, double to, double duration, double delay) {
        return scale(node, from, to, duration, delay, null);
    }

    public static Node scale(Node node, double from, double to, double duration, Runnable then) {
        return scale(node, from, to, duration, 0, then);
    }

    public static Node scale(Node node, double from, double to, double duration, double delay, Runnable then) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(duration), node);

        scale.setDelay(Duration.seconds(delay));
        scale.setFromX(from);
        scale.setToX(to);
        scale.setFromY(from);
        scale.setToY(to);

        scale.setOnFinished(even -> {
            if (then != null) {
                then.run();
            }
        });

        scale.play();

        return node;
    }

    public static Node translateY(double from, double to, double duration, Node node) {
        return translateY(from, to, duration, 0, null, node);
    }

    public static Node translateY(Node node, double from, double to, double duration, double delay) {
        return translateY(from, to, duration, delay, null, node);
    }

    public static Node translateY(double from, double to, double duration, Runnable then, Node node) {
        return translateY(from, to, duration, 0, then, node);
    }

    public static Node translateY(double from, double to, double duration, double delay, Runnable then, Node node) {
        TranslateTransition offsetY = new TranslateTransition(Duration.seconds(duration), node);

        offsetY.setDelay(Duration.seconds(delay));
        offsetY.setFromY(from);
        offsetY.setToY(to);

        if (then != null) {
            offsetY.setOnFinished(even -> then.run());
        }

        offsetY.play();

        return node;
    }


    public static Node translateX(Node node, double from, double to, double duration) {
        return translateX(node, from, to, duration, 0, null);
    }

    public static Node translateX(Node node, double from, double to, double duration, double delay) {
        return translateX(node, from, to, duration, delay, null);
    }

    public static Node translateX( Node node, double from, double to, double duration, Runnable then) {
        return translateX(node, from, to, duration, 0, then);
    }

    public static Node translateX(Node node, double from, double to, double duration, double delay, Runnable then) {
        TranslateTransition offsetX = new TranslateTransition(Duration.seconds(duration), node);

        offsetX.setDelay(Duration.seconds(delay));
        offsetX.setFromX(from);
        offsetX.setToX(to);
        offsetX.setInterpolator(Interpolator.EASE_BOTH);

        if (then != null) {
            offsetX.setOnFinished(even -> then.run());
        }

        offsetX.play();

        return node;
    }
}