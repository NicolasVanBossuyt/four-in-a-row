package views.menu;

import javafx.scene.Node;
import utils.Icon;
import utils.SettingsManager;
import utils.StageManager;
import views.Layout;
import views.TextStyle;
import views.View;
import views.Widget;

import static views.Layout.*;
import static views.Widget.*;

public class Settings extends View {
    public Settings() {
        super(true);

        Node menu =
                vertical(
                        16,
                        horizontallyCentered(text("Settings", TextStyle.TITLE)),
                        horizontallyCentered(
                                horizontal(
                                        16,
                                        iconButton(
                                                () -> SettingsManager.get().getAudioMuted() ? Icon.VOLUME_OFF : Icon.VOLUME_UP,
                                                event -> SettingsManager.get().toggleAudioMuted()
                                        ),
                                        iconButton(
                                                () -> SettingsManager.get().isGraphicFullscreen() ? Icon.FULLSCREEN_EXIT : Icon.FULLSCREEN,
                                                event -> SettingsManager.get().toggleGraphicFullscreen()
                                        )
                                )
                        ),
                        text("Username", TextStyle.SUBTITLE),
                        textField(
                                () -> SettingsManager.get().getPlayerName(),
                                newName -> SettingsManager.get().setPlayerName(newName)
                        ),
                        text("Graphics", TextStyle.SUBTITLE),
                        vertical(
                                4,
                                text("Parallax", TextStyle.LABEL),
                                fillWith(
                                        slider(
                                                () -> SettingsManager.get().getGraphicParallax(),
                                                value -> SettingsManager.get().setGraphicParallax(value)
                                        )
                                )
                        ),
                        text("Audio", TextStyle.SUBTITLE),
                        vertical(
                                4,
                                text("Music", TextStyle.LABEL),
                                fillWith(
                                        slider(
                                                () -> SettingsManager.get().getAudioMusicVolume(),
                                                value -> SettingsManager.get().setAudioMusicVolume(value)
                                        )
                                )
                        ),
                        vertical(
                                4,
                                text("Effects", TextStyle.LABEL),
                                fillWith(
                                        slider(
                                                () -> SettingsManager.get().getAudioEffectVolume(),
                                                value -> SettingsManager.get().setAudioEffectVolume(value)
                                        )
                                )
                        )
                );

        this.getChildren().addAll(
                Layout.verticallyCentered(Layout.width(512, Widget.panel(menu))),
                backButton(actionEvent -> {
                    StageManager.goBackTo(new Main());
                    SettingsManager.save();
                })
        );
    }
}
