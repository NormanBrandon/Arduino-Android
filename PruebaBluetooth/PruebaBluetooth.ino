void setup()  
{
  Serial.begin(9600);
  pinMode(13,OUTPUT);
  pinMode(A0,OUTPUT);
}

void loop() 
{
  if (Serial.available())
  {
    char dato=Serial.read();
    digitalWrite(13,HIGH);
    digitalWrite(A0,HIGH);
   
    delay(200);
    Serial.println('a');
  }
  digitalWrite(13,LOW);
  digitalWrite(A0,LOW);

}
