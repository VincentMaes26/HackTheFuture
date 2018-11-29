import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WebsocketClient {
    private static Object waitLock = new Object();

    @OnMessage
    public void onMessage(String message){
        System.out.println("Received msg: " + message);
    }

    public static void main(String[] args) {
        WebSocketContainer container;
        Session session = null;

        try{
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WebsocketClient.class, URI.create("ws://34.242.133.196:8090/websocket/artifact"));
            wait4TerminateSignal();

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null){
                try{
                    session.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    private static void wait4TerminateSignal(){
        synchronized (waitLock){
            try{
                waitLock.wait();
            }catch (InterruptedException e){

            }
        }
    }
}
