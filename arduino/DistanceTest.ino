#include <Servo.h> 

const int echoPin = 4;
const int trigPin = 6;
const int alphaServoPin = 8;
const int betaServoPin = 9;
const int inPin = 12;

const int stepDegreeAlpha = 2;
const int stepDegreeBeta = 2;
const int stepDelay = 100;

boolean reverse = false;

Servo alphaServo;
Servo betaServo;

void setup() {
  Serial.begin(9600);
  pinMode(inPin, INPUT);
  pinMode(echoPin, INPUT);
  pinMode(trigPin, OUTPUT);
  alphaServo.attach(alphaServoPin);
  betaServo.attach(betaServoPin);
}

void loop() {

  alphaServo.write(0);
  betaServo.write(0);

  if (digitalRead(inPin) == HIGH) {
    return;
  }
  
  Serial.println(measureDistanceCm());

}

long measureDistanceCm() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  long duration = pulseIn(echoPin, HIGH);
  return duration / 29 / 2;
}

void printMeasure(int alphaDegree, int betaDegree, long distanceCm) {
  Serial.print(alphaDegree);
  Serial.print(";");
  Serial.print(betaDegree);
  Serial.print(";");
  Serial.print(distanceCm);
  Serial.println();
}

