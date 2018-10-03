#include <ESP8266WiFi.h> 
#include <FirebaseArduino.h>
/*
 use ESP8266WiFI.h to initiate ESP8266
 use FirebaseArduin.h to connect arduino and Android
 */
// Set these to run example.
#define FIREBASE_HOST "UR_FB_HOST"
#define FIREBASE_AUTH "UR_FB_SECRET_CODE"

#define WIFI_SSID "UR_SSID"
#define WIFI_PASSWORD "UR_PW"
/*
  you NEED to DOUBLE CHECK the DATA 
 */
void setup() {
  Serial.begin(9600);
  pinMode(D1, OUTPUT);
  pinMode(D2, OUTPUT);
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.set("LED_STATUS", 0);
  Firebase.set("COFFEE_STATUS", 0);
}
int n = 0;
int i = 0;
// use n & i to get and post value
void loop() {
  // get value
  n = Firebase.getInt("LED_STATUS");
  i = Firebase.getInt("COFFEE_STATUS");
  // handle error
  if (n == 1) {
    Serial.println("LED ON");
    digitalWrite(D1, HIGH);
    //return;
    delay(1000);
  }
  else {
    Serial.println("LED OFF");
    digitalWrite(D1, LOW);
    //return;
  }
  if (i == 1) {
    Serial.println("COFFEE ON");
    digitalWrite(D2, HIGH);
    //return;
    delay(1000);
  }
  else {
    Serial.println("COFFEE OFF");
    digitalWrite(D2, LOW);
   // return;
  }
}
