int LDR1,LDR2,THERM;

void setup(){

  Serial.begin(9600);

}
void loop(){
  LDR1 = analogRead(A1);
  LDR2 = analogRead(A2);
  THERM = analogRead(A3);
Serial.println("Nueva Lectura");

Serial.print(LDR1);
Serial.println(" LDR1");
Serial.print(LDR2);
Serial.println(" LDR2");
Serial.print(THERM);
Serial.println(" THERM");
delay(1000);


}
