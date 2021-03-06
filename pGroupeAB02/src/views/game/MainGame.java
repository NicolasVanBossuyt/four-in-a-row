package views.game;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import models.Game;
import models.Player;
import models.message.*;
import models.messageloop.Notifiable;
import utils.Icon;
import utils.ShakeTransition;
import views.View;
import views.dialogs.YesNo;
import views.dialogs.YesNoDialog;
import views.widgets.*;

import static views.Layout.*;
import static views.Widget.iconButton;
import static views.Widget.panel;

public class MainGame extends View {
    private final Game game;
    private final Player player;

    private final Countdown countdown;
    private final ActualScore actualScore;
    private final MaxLevel maxLevel;
    private final ClueStack clueStack;
    private final AnswerField answer;

    private Notifiable onNewClueNotifier;
    private Notifiable onCountdownNotifier;
    private Notifiable onAnswerCorrect;
    private Notifiable onPlayerScoreChange;
    private Notifiable onAnswerIncorrect;
    private Notifiable onQuestionPassed;

    public MainGame(Game game, Player player) {
        super(true);

        this.game = game;
        this.player = player;

        Node quitButton = iconButton(Icon.CLOSE, event -> {
            if (new YesNoDialog("Quit the game", "Do you want to quit the game?\nAll progress will be lost!")
                    .show() == YesNo.YES) {
                game.shutdown();
            }
        });

        Node passButton = iconButton(Icon.SKIP_NEXT, event -> {
            game.pass();
        });

        countdown = new Countdown();
        actualScore = new ActualScore(player.getScore());
        maxLevel = new MaxLevel(player.getLevelMax());
        clueStack = new ClueStack();
        clueStack.setPadding(new Insets(32));
        answer = new AnswerField();
        answer.setOnAnswer(game::answer);

        Region statusPanel = panel(
                new StackPane(
                        fillWith(horizontallyCentered(countdown)),
                        horizontal(
                                32,
                                verticallyCentered(quitButton),
                                verticallyCentered(actualScore),
                                fillWith(spacer(0)),
                                maxLevel,
                                verticallyCentered(passButton)
                        ))
        );

        Region cluesAndAnswerPanel = vertical(
                0,
                statusPanel,
                fillWith(clueStack),
                horizontallyCentered(width(512, answer))
        );

        this.getChildren().add(cluesAndAnswerPanel);

    }

    public void onSwitchIn() {
        onNewClueNotifier = game.getMessageLoop().registerNotifier(OnNewClue.class, message -> {
            if (message.player().equals(player))
                clueStack.addClue(message.clue());
        });

        onCountdownNotifier = game.getMessageLoop().registerNotifier(OnCountDown.class, message -> {
            countdown.update(message.time());
        });

        onAnswerCorrect = game.getMessageLoop().registerNotifier(OnAnswerCorrect.class, message -> {
            if (message.player().equals(player)) {
                clueStack.clearClues();
                answer.clear();
            }
        });

        onPlayerScoreChange = game.getMessageLoop().registerNotifier(OnPlayerScoreChange.class, message -> {
            if (message.player().equals(player)) {
                System.out.println(player + " ?= " + message.player());
                actualScore.update(message.getScore());
                maxLevel.update(message.getLevel());
            }
        });

        onAnswerIncorrect = game.getMessageLoop().registerNotifier(OnAnswerIncorrect.class, message -> {
            if (message.player().equals(player)) {
                ShakeTransition shake = new ShakeTransition(answer, Duration.seconds(0.5), 16, 3);
                shake.play();
                maxLevel.update(game.getPlayer(0).getLevel());
            }
        });

        onQuestionPassed = game.getMessageLoop().registerNotifier(OnQuestionPassed.class, message -> {
            if (message.player().equals(player)) {
                clueStack.clearClues();
                answer.clear();
            }
        });
    }

    @Override
    public void onSwitchOut() {
        game.getMessageLoop().unregisterNotifier(onNewClueNotifier);
        game.getMessageLoop().unregisterNotifier(onCountdownNotifier);
        game.getMessageLoop().unregisterNotifier(onAnswerCorrect);
        game.getMessageLoop().unregisterNotifier(onPlayerScoreChange);
        game.getMessageLoop().unregisterNotifier(onAnswerIncorrect);
        game.getMessageLoop().unregisterNotifier(onQuestionPassed);
    }
}
