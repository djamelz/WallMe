#include <Servo.h> 

const int echoPin = 4;
const int trigPin = 6;
const int alphaServoPin = 8;
const int betaServoPin = 9;
const int inPin = 12;

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

  Serial.print("Distance Cm : ");
  Serial.print(measureDistanceCm());
  Serial.println(" Cm");
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
