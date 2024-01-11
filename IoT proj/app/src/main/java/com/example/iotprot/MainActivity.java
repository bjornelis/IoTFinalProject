package com.example.iotprot;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {




    private static final String BROKER_URI = "tcp://192.168.12.181:1883";
    private static final String TOPIC = "proximity_topic";

    private MqttAndroidClient mqttAndroidClient;
    private TextView proximityTextView;

    private int prox_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proximityTextView = findViewById(R.id.proximityTextView);

        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), BROKER_URI, "android_client");

        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                String payload = new String(message.getPayload());
                updateUI(payload);
                if (Integer.parseInt(payload) > 3000){      //if the value is low, it turns on the lamp and sets the value
                    //runPythonScriptToTurnLightOn
                    setProx_value(Integer.parseInt(payload));
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        connectToBroker();
    }

    public int getProx_value(){         //Fetches the value for usage in the light part
        return prox_value;
    }

    private void setProx_value(int prox_value) {       //updates with the value from sensor
        this.prox_value = prox_value;
    }

    private void connectToBroker() {
        try {
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(TOPIC, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("MQTT", "connect succeed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   private void updateUI(String payload) {
       Log.d("MQTT", "Received payload: " + payload);
       runOnUiThread(() -> {
           Log.d("MQTT", "Updating UI with payload: " + payload);
           proximityTextView.setText(payload);

           if (Integer.parseInt(payload) > 3000){
               proximityTextView.setBackgroundColor(Color.RED);
           } else {
               proximityTextView.setBackgroundColor(Color.GREEN);
           }
       });
   }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
            try {
                mqttAndroidClient.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void kettleNavigate(View view){
        Intent intent = new Intent(this, kettleActivity.class);

        startActivity(intent);
    }

    public void lightNavigate(View view){
        Intent intent = new Intent(this, lightActivity.class);

        startActivity(intent);
    }
}

