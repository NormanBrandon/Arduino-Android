#include <SoftwareSerial.h>

int ledPin = 13; // usamos un pin de salida al LED
char state = 0; // Variable lectrura serial

SoftwareSerial mySerial(10, 11);//conecta el tx del bluetooth al 10 y el rx al 11
void setup() {
    pinMode(ledPin, OUTPUT);   //Declara pin de Salida
    digitalWrite(ledPin, LOW); //Normalmente Apagado
    Serial.begin(9600);
    mySerial.begin(9600);
    delay(500);
    mySerial.println("Hello, world?");
}
 
void loop() {
 //si el modulo a manda dato, guardarlo en estado.
  if(Serial.available() > 0){
       state = mySerial.read();
       Serial.println(state);
 
  } // esta parte del código es para solo 1 Carácter o Unidad. 
 
 
 
}
