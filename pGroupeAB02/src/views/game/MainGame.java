package views.game;

import views.widgets.*;
import views.dialogs.YesNo;
import views.dialogs.YesNoDialog;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.util.Duration;
import models.message.*;
import models.messageloop.Notifiable;
import models.Game;
import models.Player;
import utils.ShakeTransition;
import utils.Icon;
import views.View;

import static views.Widget.*;
import static views.Layout.*;

public class MainGame extends View {
    private final Game game;
    private final Player player;

    private final ClueStack clueStack;
    private final AnswerField answer;
    private final Countdown countdown;
    private final ActualScore actualScore;
    private final MaxLevel maxLevel;
    private Notifiable onNewClueNotifier;
    private Notifiable onCountdownNotifier;
    private Notifiable onAnswerCorrect;
    private Notifiable onAnswerIncorrect;
    private Notifiable onQuestionPassed;

    public MainGame(Game game, Player player) {
        super(false);

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
        actualScore = new ActualScore(game.getPlayer(0).getScore());
        maxLevel = new MaxLevel(game.getPlayer(0).getLevelMax());

        BorderPane sidebar = new BorderPane();

        sidebar.setId("sidebar");
        sidebar.setMinWidth(220);
        sidebar.setPadding(new Insets(16));

        sidebar.setLeft(new HBox(16, new StackPane(quitButton), new StackPane(passButton)));
        sidebar.setCenter(new HBox(new StackPane(actualScore), new StackPane(maxLevel)));
        sidebar.setRight(new StackPane(countdown));

        clueStack = new ClueStack();
        clueStack.setPadding(new Insets(32));

        answer = new AnswerField();
        answer.setOnAnswer(game::answer);
        VBox.setVgrow(answer, Priority.ALWAYS);

        BorderPane cluesAndAnswer = new BorderPane(clueStack, null, null, horizontallyCentered(width(512,answer)), null);

        VBox.setVgrow(cluesAndAnswer, Priority.ALWAYS);

        this.getChildren().add(new VBox(sidebar, cluesAndAnswer));

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
            if (message.player().equals(player))
            {
                clueStack.clearClues();
                answer.clear();
                actualScore.update(game.getPlayer(0).getScore());
                maxLevel.update(game.getPlayer(0).getLevelMax());
            }
        });

        onAnswerIncorrect = game.getMessageLoop().registerNotifier(OnAnswerIncorrect.class, message -> {
            if (message.player().equals(player))
            {
                ShakeTransition shake = new ShakeTransition(answer, Duration.seconds(0.5), 16, 3);
                shake.play();
                maxLevel.update(game.getPlayer(0).getLevelMax());
            }
        });

        onQuestionPassed = game.getMessageLoop().registerNotifier(OnQuestionPassed.class, message -> {
            if (message.player().equals(player))
            {
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
        game.getMessageLoop().unregisterNotifier(onAnswerIncorrect);
        game.getMessageLoop().unregisterNotifier(onQuestionPassed);
    }
}
