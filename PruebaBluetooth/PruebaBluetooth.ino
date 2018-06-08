
int ledPin = 13; // usamos un pin de salida al LED
char state; // Variable lectrura serial

 void setup()  {
    Serial.begin(9600);
}
 
void loop() {
 //si el modulo a manda dato, guardarlo en estado.

  if(Serial.available() > 0){
       Serial.println(Serial.read());
 
  } // esta parte del código es para solo 1 Carácter o Unidad. 
 
 
 
}
