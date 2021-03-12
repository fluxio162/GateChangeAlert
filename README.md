# Project 5: Gate Change Alert `S3, Lambda Rekognition, DB` 

You need to create a workflow that performs a series of actions after a gate of a specific flight has changed at an  airport. 

## Introduction

### Motivation

The workflow reads the information about the flight, the new gate and then loads all available passenger data (from a Database) of that flight. Also, checks the queue length of the security check. Thereafter, for every passenger from that flight that is already at the airport, the workflow reads the gps location and calculates the time to gate. If the passenger is in the public area, the workflow adds the delay for the security check. 

### Input 

- An image(s) of security check on S3. You may assume one or multiple security check queues and then calculate the overall average waiting time.
- threshold value (in seconds)
- flight number
- new gate


### Rough steps 

- create a database in format you like (in a container, run it in a VM, use some DB service of AWS)
- fill the database with some passenger data, flights, gates
- create a map of the Innsbruck Airport with GPS locations of security check and gates
- distinguish the security and public area
- read the image from S3 and use it as an input for AWS Rekognition
- check the time to the new gate and inform the passenger accordingly based on the threshold value


## Week A (Homework 06): Sketch the workflow with AFCL

Sketch a preliminary workflow with AFCL. 

Think about these things:

* How to group the above algorithm into functions 
* What you can do in parallel; what is independent from each other (e.g. reading the passengers from the flight and calculating the security check delay)
* What information each function needs, how you best represent it (access images to S3, collections for passengers)
* How information flows between your functions


Put together the workflow using the FC Editor, AFCL Java API or a yaml editor. 

Create empty functions that just produce the data how you specified with AFCL. Run the workflow with these functions with the Enactment Engine.


## Week B (Homework 07): Code the functions

### Rough functions

#### Get passengers `Database`

This function should read all passengers that are at the airport. You may use a boolean flag in the database to know whether a passenger of the flight is present at the airport.
Think which passenger data you will need for later.

#### Calculate security check delay `AWS Rekognition` `AWS S3`

This function should load an image(s) from S3 and call AWS rekognition for each image (each waiting queue). In case you use more images, you would need a reduction function to calculate the average waiting time. 


#### Read GPS Location 

This function should return the GPS location of a passenger (e.g., from the DB). 



#### Estimate time between two GPS locations

This function should return the estimated time between two GPS locations (e.g., passenger - security check, security check - the new gate)


#### inform the passenger

This function should send a notification to a passenger based on the input. Select the notification channel you like (`email`, `slack`, `sms`, ...).

Hints:
* Choose an appropriate threshold such that the time to gate for some passengers is below, while for others is above the threshold.


## Week C (Homework 08)

Orchestrate the functions with the Enactment Engine.

----

# Parts you can use in addition

## Amazon

```
S3 Glacier
AWS Backup
DynamoDB (NoSQL)
ElastiCache (distributed in-memory DB)
QLDB (immutable transaction log)
Timestream (IoT)
Cloud Map (AWS resource query)
Chatbot (subscribed to SNS)
Polly  (Text-to-speech)
MediaConvert (from * to S3)
Kinesis video streams (from web, sdk, to S3, \*)
Lex (barebones Alexa)
Forecast (time-series)
Rekognition (Object and scene detection, facial, face comparison, text in image)
Comprehend (Natural text processing: entities, key phrases, personal data, language, sentiment)
Translate
Athena (big data query service, basically SQL)
Data exchange (miscellaneous data)
Sumerian (three.js in Browser)
SQS (pub-sub queue)
Greengrass (IoT, local / in-cloud processing)
```

## IBM Lite

```
Watson assistant (chatbot / text to some action)
Visual recognition (scenes, objects...)
Cloudant (JSON database)
Annotator for clinical data
Translate
Object storage
Natural language understanding
Speech to text (streaming transcription)
Text to speech
Tone analyzer (text sentiment)	
```
