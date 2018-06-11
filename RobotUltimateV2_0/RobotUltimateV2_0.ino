#include <math.h> //para valores flotantes y double se ocupó esta librería básicamente

//Valores constantes del circuito
  float rAux = 780.0;
  float vcc = 4.9;        //Diferencia de potencial de entrada
  float beta = 11177.0;   
  float temp0 = 298.0;    //Temperatura inicial en Kelvin (298K = 24°C)
  float r0 = 1000.0;      //Valor nominal de la resistencia del termistor

//Variables usadas en el cálculo
  float vm = 0.0;
  float rntc = 0.0;
  float temperaturaK = 0.0;
  int LDRI,LDRD,sensibilidad=110;
  int buzzer=A0,led=13,dmax=15;   //dmax es la distancia máxima, está en [cm]
  double Do=1,Dob=2,Re=3, Reb=4, Mi=5, Fa=6, Fab=7, Sol=8, Solb=9, La=10, Lab=11, Si=12;

  int MA1=9,MA2=10,MB1=11,MB2=12; //motores MA=motor lado izquierdo, MB=motor lado derecho. Sus respectivos 1 y 2 es la polaridad de éstos. 
  int distancia,tiempo,echo,trigger;
  char dato,control;
  String level;
  int time90=125;

//Ahora sí viene lo chido
// Declaración de todos los pines usados, como de costumbre:

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

  Serial.begin(9600);  //tiempo de siempre
}


//Ahora la programación del robotcito bb. Powered by Proteco36 Gen. 

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
        while(dato!='R'){
            automatico();
            recibir();
            if(dato=='K'){
                recibir();
                dmax=(int)dato;
            }
        }
    }
  
    
    if(dato=='S'){
        while(dato!='R'){
            seguidor();
            recibir();  
            if(dato=='K'){
                delay(10);
                recibir();
                sensibilidad=((int)dato)*10;
            }
       }  
    }
    
    if(dato=='L'){
        while(dato!='R'){
            enviartrama();
        }
    }

  
}    
/////////////
//FIN LOOP///
/////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////
//INICIO REMOTO//
////////////////
void remoto(){
  recibir();
  
  switch(dato){     //switch de los casos que le manda la aplicación (caracteres)
    case 'A': avanza();   //boton flecha adelante
    break;  
    
    case 'B': reversa();  //boton flecha abajo
    break;  
    
    case 'C': izquierda();  //boton flecha izq
    break;  
    
    case 'D': derecha();    //boton flecha derecha
    break;  
    
    case 'E':             //boton del claxon
        digitalWrite(led,HIGH);
        tone(buzzer,frecuencia(Do,4),500);
        delay(200);
        digitalWrite(led,LOW);
    break;  
    
    case 'F':             //claxon 2
        digitalWrite(led,HIGH);
        tone(buzzer,frecuencia(Re,4),500);
        delay(200);
        digitalWrite(led,LOW);
    break; 
    
    case 'G':             //claxon 3
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
////////////////
//FIN REMOTO////
////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////
//INICIO RECIBIR//
//////////////////
void recibir(){
  if(Serial.available()>0){   //revisa si existe la conexón
    dato = Serial.read();     //si hay conexión, entonces lee la información que manda ek transmisor
  }
}
///////////////
//FIN RECIBIR//
///////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
double frecuencia(double nota, double octava) 
  {
     return (440.0 * exp(((octava-4)+(nota-10)/12.0)*log(2)));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void enviartrama(){
    if(dato=='J'){
        delay(70);
        Serial.print("#");                      //dato de inicio del paquete de datos
        Serial.print("Nivel de Luz S1 : ");
        Serial.println(luminosidad(1));         //manda dato de sensor de luz a la app
        Serial.print("Nivel de luz S2 : ");
        Serial.println(luminosidad(2));
        Serial.print("Distancia Frontal : ");
        Serial.print(leer("center"));           //dato de sensor de distancia ojitos centrales
        Serial.println(" cm");
        Serial.print("Distancia Izquierda : ");
        Serial.print(leer("left"));             //ojos izquierdos checan distancia
        Serial.println(" cm");
        Serial.print("Distancia Derecha : ");
        Serial.print(leer("right"));            //ojos derechos checan distancia
        Serial.println(" cm");
        Serial.print("Temperatura : ");
        Serial.print(temperatura());            //dato del sensor de Temperatura
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
        if((LDRD-LDRI)>32){
            digitalWrite(MA1,LOW);
            digitalWrite(MA2,HIGH);
        }
        else if((LDRI-LDRD)>32){
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
  LDRI=analogRead(1);   //lee la intensidad que marca el sensor izquierdo
  LDRD=analogRead(2);   //lee intensidad que marca el sensor derecho
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void automatico(){

    if(leer("center")>dmax && leer("left")>dmax && leer("right")>dmax){ //caso ideal sin obstáculos en la vida, sin límite sin freno alv, compa. Fierro.
    avanza();
    delay(100);
    }
    else if(leer("center")>dmax && leer("left")<dmax && leer("right")<dmax){//un estrecho
    derecha();
    delay(time90*4);
    }
    else if(leer("center")>dmax && leer("left")>dmax && leer("right")<dmax){//obstaculo a un lado no ta de frente
    derecha();
    delay(time90);
    }
    else if(leer("center")>dmax && leer("left")<dmax && leer("right")>dmax){//obstaculo a un lado no ta de frente
    izquierda();
    delay(time90);
    }
    else if(leer("center")<dmax && leer("left")<dmax && leer("right")>dmax){//obstaculo a un lado    
    izquierda();
    delay(time90*2);
    }
    else if(leer("center")<dmax && leer("left")>dmax && leer("right")<dmax){//obstaculo al otro lado
    derecha();
    delay(time90*2);
    }
    else if(leer("center")<dmax && leer("left")<dmax && leer("right")<dmax){//encruzijada
    derecha();
    delay(time90*4);
    }
    else if(leer("center")<dmax && leer("left")>dmax && leer("right")>dmax){//encruzijada
    izquierda();
    delay(time90*4);
    }
    else
    {avanza();
    delay(100);
  }
    
    
    apagar();


}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


int temperatura(){
    vm=(vcc / 1024)*( (analogRead(3)-1024)*-1 );                //Calcular tensión en la entrada
    rntc = (vm*rAux)/(vcc-vm);  //rAux / ((vcc/vm)-1);          //Calcular la resistencia de la NTC
    temperaturaK = beta/(log(rntc/r0)+(beta/temp0));            //Calcular la temperatura en Kelvin
    
    //Restar 273 para pasar a grados Celsius
    return temperaturaK - 273;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////
//INICIO LECTURA DE DISTANCIA CON OJITOS//
//////////////////////////////////////////
int leer(String s){
    if(s=="left"){
        trigger=4;
        echo=3;
    }
    else if(s=="center"){
        trigger=6;
        echo=5;
    }
    else if(s=="right"){
        trigger=8;
        echo=7;
    }
  
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
//////////////////////////////////////////
//FIN LECTURA DE DISTANCIA CON OJITOS/////
//////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
//INICIO CONTROLES DE FUNCIONES BÁSICAS DEL ROBOT BB (APAGAR, AVANZAR, IR A LA DERECHA, IZQUIERDA, ETC)
/////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void apagar(){
  digitalWrite(MA1,LOW);  //se apagan los motores, todos LOW, todos tranqui
  digitalWrite(MA2,LOW);
  digitalWrite(MB1,LOW);
  digitalWrite(MB2,LOW);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void avanza(){
  digitalWrite(MA1,LOW);    //Motor izquierdo "dirección" para atrás se apaga
  digitalWrite(MA2,HIGH);   //Se enciende dirección de giro del motor izquierdo para adelante
  digitalWrite(MB1,LOW);    //Motor derecho dirección para atrás se apaga
  digitalWrite(MB2,HIGH);   //Se enciende dirección para adelante como en el otro
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void reversa(){
  digitalWrite(MA1,HIGH);   //Se enciende la "dirección del motor" izquierdo para atrás
  digitalWrite(MA2,LOW);    
  digitalWrite(MB1,HIGH);   //motor derecho pa' 'trás
  digitalWrite(MB2,LOW);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//para girar a la izquierda, los motores deben estar invertidos, el izquierdo para atrás y el derecho para adelante
void izquierda(){
  digitalWrite(MA1,HIGH);  //motor izquierdo para atrás
  digitalWrite(MA2,LOW);
  digitalWrite(MB1,LOW);
  digitalWrite(MB2,HIGH);   //motor derecho adelante
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void derecha(){
  digitalWrite(MA1,LOW);
  digitalWrite(MA2,HIGH);   //motor izquierdo para adelante
  digitalWrite(MB1,HIGH);   //motro derecho para atrás
  digitalWrite(MB2,LOW);  
}
