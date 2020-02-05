package com.example.publics;

//import com.lichfaker.log.Logger;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.greenrobot.eventbus.EventBus;
import static com.example.publics.Contants.Connect;
import static com.example.publics.Contants.ConnextFailed;
import static com.example.publics.Contants.DisConnect;
import static com.example.publics.Contants.PublicFailed;
import static com.example.publics.Contants.PublicSuccess;
import static com.example.publics.Contants.ReceData;
 
public class MqttManager {
 
    private static MqttManager mInstance = null;
 
    private MqttCallback mCallback;
 
    private MqttClient client;
    private MqttConnectOptions conOpt;
    private boolean clean = true;

    private MqttManager() {
        mCallback = new MqttCallbackBus();
    }

    public static MqttManager getInstance() {
        if (null == mInstance) {
            mInstance = new MqttManager();
        }
        return mInstance;
    }

    /**
     * 閲婃斁鍗曚緥, 鍙婂叾鎵�寮曠敤鐨勮祫婧�
     */
    public static void release() {
        try {
            if (mInstance != null) {
                mInstance.disConnect();
                mInstance = null;
            }
        } catch (Exception e) {
//            Logger.e(e.getMessage());
        }
    }


//    public static final String SubTopic1 = "clinetB";
//    public static final String SubTopic2 = "/public/TEST/windows";

    /**
     * 鍒涘缓Mqtt 杩炴帴
     *
     * @param brokerUrl Mqtt鏈嶅姟鍣ㄥ湴鍧�(tcp://xxxx:1863)
     * @param userName  鐢ㄦ埛鍚�
     * @param password  瀵嗙爜
     * @param clientId  clientId
     * @return
     */
    public boolean creatConnect(String brokerUrl, String userName, String password, String clientId) {
        boolean flag = false;
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

        try {
            // Construct the connection options object that contains connection parameters
            // such as cleanSession and LWT
            conOpt = new MqttConnectOptions();
            conOpt.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            conOpt.setCleanSession(clean);//璁㈤槄闈炴寔涔�  non-durable
            conOpt.setKeepAliveInterval(60);
//            conOpt.set

            if (password != null) {
                conOpt.setPassword(password.toCharArray());
            }
            if (userName != null) {
                conOpt.setUserName(userName);
            }

            // Construct an MQTT blocking mode client
            client = new MqttClient(brokerUrl, clientId, dataStore);
//            conOpt.setWill( topic,"close".getBytes(),2,true);
            client.setCallback(mCallback);
            client.setTimeToWait(10 * 1000);

            flag = doConnect();

//            client.
        } catch (MqttException e) {
//            Logger.e(e.getMessage());
        }

        if (flag) {
            EventBus.getDefault().post(new MyMsg("", Connect));
        } else {
            EventBus.getDefault().post(new MyMsg("", DisConnect));
        }
        return flag;
    }

    /**
     * 寤虹珛杩炴帴
     *
     * @return
     */
    public boolean doConnect() {
        boolean flag = false;
        if (client != null) {
            try {
                client.connect(conOpt);
//                Logger.d("Connected to " + client.getServerURI() + " with client ID " + client.getClientId());
                flag = true;
            } catch (Exception e) {
//                Logger.e(e.getMessage());
            }
        }
        return flag;
    }

    /**
     * Publish / send a message to an MQTT server
     *
     * @param topicName the name of the topic to publish to
     * @param qos       the quality of service to delivery the message at (0,1,2)
     * @param payload   the set of bytes to send to the MQTT server
     * @return boolean
     */
    public boolean publish(String topicName, int qos, byte[] payload) {

        boolean flag = false;

        if (client != null && client.isConnected()) {

//            Logger.d("Publishing to topic \"" + topicName + "\" qos " + qos);

            // Create and configure a message
            MqttMessage message = new MqttMessage(payload);
            message.setQos(qos);

            // Send the message to the server, control is not returned until
            // it has been delivered to the server meeting the specified
            // quality of service.
            try {
                client.publish(topicName, message);
                flag = true;
            } catch (MqttException e) {
//                Logger.e(e.getMessage());
            }

        }

        return flag;
    }

    /**
     * Subscribe to a topic on an MQTT server
     * Once subscribed this method waits for the messages to arrive from the server
     * that match the subscription. It continues listening for messages until the enter key is
     * pressed.
     *
     * @param topicName to subscribe to (can be wild carded)
     * @param qos       the maximum quality of service to receive messages at for this subscription
     * @return boolean
     */
    public boolean subscribe(String topicName, int qos) {

        boolean flag = false;

        if (client != null && client.isConnected()) {
            // Subscribe to the requested topic
            // The QoS specified is the maximum level that messages will be sent to the client at.
            // For instance if QoS 1 is specified, any messages originally published at QoS 2 will
            // be downgraded to 1 when delivering to the client but messages published at 1 and 0
            // will be received at the same level they were published at.
//            Logger.d("Subscribing to topic \"" + topicName + "\" qos " + qos);
            try {
                client.subscribe(topicName, qos);
                flag = true;
            } catch (MqttException e) {
//                Logger.e(e.getMessage());
            }
        }

        return flag;

    }

    /**
     * 鍙栨秷杩炴帴
     *
     * @throws MqttException
     */
    public void disConnect() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
        }
    }
}
