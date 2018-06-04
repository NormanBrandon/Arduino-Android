float vcc=5.0,vm=0.0;
float LDR1,LDR2;
int MA1=9,MA2=10,MB1=11,MB2=12;
void setup(){
pinMode(MA1,OUTPUT);
pinMode(MA2,OUTPUT);
pinMode(MB1,OUTPUT);
pinMode(MB2,OUTPUT);
Serial.begin(9600);
}

void loop(){

LDRI=analogRead(1);
LDRD=analogRead(2);
Serial.print("LDR1: ");
Serial.println(LDR1);
Serial.print("LDR2: ");
Serial.println(LDR2);
delay(800);

 
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
