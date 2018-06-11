  #include <math.h>

//Valores fijos del circuito
float rAux = 780.0;
float vcc = 4.9;
float beta = 11177.0;
float temp0 = 298.0;
float r0 = 1000.0;

//Variables usadas en el cálculo
float vm = 0.0;
float rntc = 0.0;
float temperaturaK = 0.0;
int LDRI,LDRD,sensibilidad=110;
int buzzer=A0,led=13,dmax=15;
double Do=1,Dob=2,Re=3, Reb=4, Mi=5, Fa=6, Fab=7, Sol=8, Solb=9, La=10, Lab=11, Si=12;

int MA1=9,MA2=10,MB1=11,MB2=12,distancia,tiempo,echo,trigger;
char dato,control;
String level;
void setup(){
pinMode(led,OUTPUT);
pinMode(buzzer,OUTPUT);
pinMode(4,OUTPUT);
pinMode(6,OUTPUT);
pinMode(8,OUTPUT);
pinMode(3,INPUT);
pinMode(5,INPUT);
pinMode(7,INPUT);

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
  recibir();
  digitalWrite(led,HIGH);
  delay(400);
  digitalWrite(led,LOW);
  delay(400);
  if(dato=='X'){
    while(dato!='R'){
    remoto();
    }
  }
  
  if(dato=='U'){
  while(dato!='R')
  {automatico();
  recibir();
  if(dato=='K'){
  recibir();
  dmax=(int)dato;
  }}
  }
  if(dato=='S'){
   while(dato!='R')
  {seguidor();
  recibir();  
  if(dato=='K'){
    delay(10);
  recibir();
  sensibilidad=((int)dato)*20;
  }}  
}
  if(dato=='L'){
  while(dato!='R'){
  enviartrama();
  }}

  

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void remoto(){

recibir();
switch(dato){
  case 'A':
  avanza();
  break;  
  case 'B':
  reversa();
  break;  
  case 'C':
  izquierda();
  break;  
  case 'D':
  derecha();
  break;  
  case 'E':
  digitalWrite(led,HIGH);
  tone(buzzer,frecuencia(Do,4),500);
  delay(200);
  digitalWrite(led,LOW);
  break;  
  case 'F':
  digitalWrite(led,HIGH);
  tone(buzzer,frecuencia(Re,4),500);
  delay(200);
  digitalWrite(led,LOW);
  break; 
  case 'G':
  digitalWrite(led,HIGH);
  tone(buzzer,frecuencia(Mi,4),500);
  delay(200);
  digitalWrite(led,LOW);
  break;  
}
  delay(80);
  apagar();
  dato='N';



}




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void recibir(){
if(Serial.available()>0){
dato = Serial.read();
}
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
double frecuencia(double nota, double octava) 
  {
     return (440.0 * exp(((octava-4)+(nota-10)/12.0)*log(2)));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void enviartrama(){

if(dato=='J'){
delay(70);
Serial.print("#");//dato de inicio del paquete de datos
Serial.print("Nivel de Luz S1 : ");
Serial.println(luminosidad(1));
Serial.print("Nivel de luz S2 : ");
Serial.println(luminosidad(2));
Serial.print("Distancia Frontal : ");
Serial.print(leer("center"));
Serial.println(" cm");
Serial.print("Distancia Izquierda : ");
Serial.print(leer("left"));
Serial.println(" cm");
Serial.print("Distancia Derecha : ");
Serial.print(leer("right"));
Serial.println(" cm");
Serial.print("Temperatura : ");
Serial.print(temperatura());
Serial.println(" Celsius");
Serial.print("Sensibilidad Luz : ");
Serial.println(sensibilidad);
Serial.print("Distancia Maxima : ");
Serial.println(dmax);
Serial.print("$");//dato para indicar final de paquete de datos
}
dato='N';
recibir();

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

String luminosidad(int i){
  medirluz();
  if(i==1){
    if(LDRI>=0 && LDRI<=205)
      level="Muy bajo";
    else if(LDRI>205 && LDRI<=410)
      level="Bajo";
    else if(LDRI>410 && LDRI<=615)
      level="Medio";
    else if(LDRI>615 && LDRI<=820)
      level="Alto";
    else if(LDRI>820 && LDRI<=1024)
      level="Muy Alto";
      
  }
  else if(i==2){
    if(LDRD>=0 && LDRD<=205)
      level="Muy bajo";
    else if(LDRD>205 && LDRD<=410)
      level="Bajo";
    else if(LDRD>410 && LDRD<=615)
      level="Medio";
    else if(LDRD>615 && LDRD<=820)
      level="Alto";
    else if(LDRD>820 && LDRD<=1024)
      level="Muy Alto";
 
  }
  return level;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void seguidor(){
  medirluz();
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void medirluz(){
LDRI=analogRead(1);
LDRD=analogRead(2);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void automatico(){
  if(leer("center")>dmax && leer("left")>dmax)
 {   avanza();
      delay(50);
 }
 else if(leer("center")>dmax && leer("left")<dmax)
    {apagar();
    delay(200);
    reversa();
    delay(100);
    izquierda();
    delay(random(100, 200));  
}
  else if(leer("center")<dmax && leer("left")>dmax)
    {apagar();
    delay(200);
    reversa();
    delay(100);
    derecha();
    delay(random(100, 200));  
}

  apagar();


}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


int temperatura(){
    vm=(vcc / 1024)*( (analogRead(3)-1024)*-1 );                //Calcular tensión en la entrada
  rntc = (vm*rAux)/(vcc-vm);  //rAux / ((vcc/vm)-1);  //Calcular la resistencia de la NTC
  temperaturaK = beta/(log(rntc/r0)+(beta/temp0));  //Calcular la temperatura en Kelvin

  //Restar 273 para pasar a grados celsus
return temperaturaK - 273;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
  if(distancia<0)
  distancia*=-1;


  return distancia;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void apagar(){
digitalWrite(MA1,LOW);
digitalWrite(MA2,LOW);
digitalWrite(MB1,LOW);
digitalWrite(MB2,LOW);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void avanza(){
digitalWrite(MA1,LOW);
digitalWrite(MA2,HIGH);
digitalWrite(MB1,LOW);
digitalWrite(MB2,HIGH);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void reversa(){
digitalWrite(MA1,HIGH);
digitalWrite(MA2,LOW);
digitalWrite(MB1,HIGH);
digitalWrite(MB2,LOW);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void izquierda(){
digitalWrite(MA1,HIGH);
digitalWrite(MA2,LOW);
digitalWrite(MB1,LOW);
digitalWrite(MB2,HIGH);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void derecha(){
digitalWrite(MA1,LOW);
digitalWrite(MA2,HIGH);
digitalWrite(MB1,HIGH);
digitalWrite(MB2,LOW);
}
