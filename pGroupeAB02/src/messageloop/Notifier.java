package messageloop;

import javafx.application.Platform;

public abstract class Notifier<MessageType> implements Notifiable {
    Class<MessageType> type;

    public Notifier(Class<?> type) {
        this.type = (Class<MessageType>) type;
    }

    @Override
    public final boolean canAccept(Message message) {
        return type.isInstance(message);
    }

    @Override
    public final void handle(Message message) {
        Platform.runLater(() -> {
            handle((MessageType) message);
        });
    }

    public abstract void handle(MessageType message);
}