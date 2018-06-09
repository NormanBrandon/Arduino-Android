
int MA1=9,MA2=10,MB1=11,MB2=12,distancia,tiempo,echo,trigger;
void setup(){
pinMode(MA1,OUTPUT);
pinMode(MA2,OUTPUT);
pinMode(MB1,OUTPUT);
pinMode(MB2,OUTPUT);
pinMode(3,INPUT);
pinMode(5,INPUT);
pinMode(7,INPUT);
pinMode(4,OUTPUT);
pinMode(6,OUTPUT);
pinMode(8,OUTPUT);
 randomSeed(analogRead(5));
Serial.begin(9600);
}

void loop(){
  Serial.println("Nueva Lectura");
Serial.print(leer("center"));
Serial.println("  Delantero");
Serial.print(leer("left"));
Serial.println("  Izquierdo");
 
  if(leer("center")>15 && leer("left")>15)
 {   avanza();
      delay(50);
 }
 else if(leer("center")>15 && leer("left")<15)
    {apagar();
    delay(200);
    reversa();
    delay(100);
    izquierda();
    delay(random(100, 200));  
}
  else if(leer("center")<15 && leer("left")>15)
    {apagar();
    delay(200);
    reversa();
    delay(100);
    derecha();
    delay(random(100, 200));  
}
  else
  apagar();
  

}
int leer(String s){
    if(s=="left"){
    trigger=4;
    echo=3;}
    else if(s=="center"){
    trigger=6;
    echo=5;}
    else if(s=="right"){
    trigger=8;
    echo=7;}
  
  digitalWrite(trigger, LOW);
  delayMicroseconds(2);
  digitalWrite(trigger, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigger, LOW);
  tiempo = pulseIn(echo, HIGH);
  distancia = tiempo / 58;

  return distancia;
}
void apagar(){
digitalWrite(MA1,LOW);
digitalWrite(MA2,LOW);
digitalWrite(MB1,LOW);
digitalWrite(MB2,LOW);
}
void avanza(){
digitalWrite(MA1,LOW);
digitalWrite(MA2,HIGH);
digitalWrite(MB1,LOW);
digitalWrite(MB2,HIGH);
}
void reversa(){
digitalWrite(MA1,HIGH);
digitalWrite(MA2,LOW);
digitalWrite(MB1,HIGH);
digitalWrite(MB2,LOW);
}
void izquierda(){
digitalWrite(MA1,HIGH);
digitalWrite(MA2,LOW);
digitalWrite(MB1,LOW);
digitalWrite(MB2,HIGH);
}
void derecha(){
digitalWrite(MA1,LOW);
digitalWrite(MA2,HIGH);
digitalWrite(MB1,HIGH);
digitalWrite(MB2,LOW);
}
