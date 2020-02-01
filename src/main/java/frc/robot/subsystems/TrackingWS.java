
package frc.robot.subsystems;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import javax.xml.crypto.URIReferenceException;

import org.java_websocket.drafts.Draft;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TrackingWS {
    private String targetData;
    private WebSocketClient dataWS;

    public TrackingWS() throws URIReferenceException, URISyntaxException {
            dataWS = new WebSocketClient(new URI("ws://10.26.1.74:8080/tracking/ws"), new Draft_6455()){
    
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Socket opened");
            }
        
            @Override
            public void onMessage(String message) {
                targetData = message;
            }
        
            @Override
            public void onError(Exception ex) {
                System.out.println("ERROR" + ex);
                dataWS.close();
            }
        
            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("socket closed");
            }
        };
    }

    public String getTargetData() {
        return targetData;
    }

    public void connect(){
        dataWS.connect();
    }
    
}