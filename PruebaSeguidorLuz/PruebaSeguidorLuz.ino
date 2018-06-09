float vcc=5.0,vm=0.0;
int LDRI,LDRD,sensibilidad=110;
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
Serial.println(LDRI);
Serial.print("LDR2: ");
Serial.println(LDRD);
  if(LDRI>sensibilidad&&LDRD>sensibilidad){
   if(LDRI<LDRD){
   digitalWrite(MA1,LOW);
   digitalWrite(MA2,HIGH);
   }
   else if(LDRD<LDRI){
  digitalWrite(MB1,LOW);
  digitalWrite(MB2,HIGH);
 }
   else
   avanza();
  delay(100);
  }
  apagar();

 
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
