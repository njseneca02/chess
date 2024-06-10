package network;

import com.google.gson.Gson;
import exception.ResponseException;
import ui.NotificationHandler;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketCommunicator extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;

    public WebsocketCommunicator(NotificationHandler notificationHandler, String url) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch(serverMessage.getServerMessageType()){
                        case NOTIFICATION -> notificationHandler.notify(new Gson().fromJson(message, NotificationMessage.class));
                        case ERROR -> notificationHandler.notify(new Gson().fromJson(message, ErrorMessage.class));
                        case LOAD_GAME -> notificationHandler.notify(new Gson().fromJson(message, LoadGameMessage.class));
                    }
                    //notificationHandler.notify(serverMessage);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void send(String msg) throws Exception {this.session.getBasicRemote().sendText(msg);}

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
