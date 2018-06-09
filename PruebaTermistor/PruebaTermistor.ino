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


void setup() {
  Serial.begin(9600); 
}

void loop() {

  //Bloque de cálculo
  vm=(vcc / 1024)*( (analogRead(3)-1024)*-1 );                //Calcular tensión en la entrada
  rntc = (vm*rAux)/(vcc-vm);  //rAux / ((vcc/vm)-1);  //Calcular la resistencia de la NTC
  temperaturaK = beta/(log(rntc/r0)+(beta/temp0));  //Calcular la temperatura en Kelvin

  //Restar 273 para pasar a grados celsus
  Serial.println(rntc);
  Serial.print(temperaturaK - 273);
  Serial.println(" Grados celsius");
  delay(1000); 
}
