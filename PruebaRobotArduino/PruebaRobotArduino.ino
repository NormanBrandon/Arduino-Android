int MA1=9,MA2=10,MB1=11,MB2=12;
void setup(){
pinMode(MA1,OUTPUT);
pinMode(MA2,OUTPUT);
pinMode(MB1,OUTPUT);
pinMode(MB2,OUTPUT);
}
void loop(){
avanza();
delay(1000);
derecha();
delay(1000);
izquierda();
delay(1000);
}

void avanza(){
digitalWrite(MA1,LOW);
digitalWrite(MA2,HIGH);
digitalWrite(MB1,LOW);
digitalWrite(MB2,HIGH);
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
