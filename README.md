# M295 - Kerem Telli
#### Welcome to my **Champions League** Spring Boot application. With this project, I aim to showcase what I have achieved in the past few days.

This project provides users with various functionalities, such as performing **CRUD** operations **(Create, Read, Update, and Delete)**.

The project was developed using the following resources:

* *Maven,
MySQL,
IntelliJ,
Spring Boot*

For users who want to dive into my code, I provide a detailed explanation and guide [Setup](https://github.com/Ketel28/m295-Kerem-Telli/edit/main/README.md#setup).

## Structure

#### Please note that you can find all those dependencies in (pom.xml) which you need for the project easily in the internet!
First I created the database and the tables and began to insert some raw data.

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/903be4e2-f975-40c3-b1c5-c30992dc68c2)

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/688c0da1-160d-497a-9f07-371d928c156f)

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/8c3a063a-63ad-4ce8-9362-11c0303b36a6)

In my case I used **jdbc** for connecting my database with java. To do that I created my **application.properties** file.

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/4c14134b-1a01-44ff-b80b-21ffd76da82a)

#### Please note that you have to modify the *application.properties* otherwise the connection to the database will not work!

The *Dao-Classes* are here for executing the **CRUD** methods

Its also important for you to **clean** and **install** the **restapi.yaml** for smooth excecution. You can find this .yaml file under ```src/main/resources```

## Requests

For testing some requests you need (in my case) **Postman**

Paste the **.yaml** file into **Swagger Editor** to see some example requests
[Swagger Editor](https:/https://editor.swagger.io///)

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/326afc78-1483-48a9-b2b6-7b00d216de71)

## GET REQUEST EXAMPLE

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/0727e57f-65c6-44f5-aa36-d38e7403c054)

As you can see it gives me some teams

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/7de49b20-8d3c-4ba4-823d-6e558467fed7)











## Setup

First and foremost, you will need an integrated development environment.
IntelliJ IDEA is an ideal choice for this.

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/386aeefc-98da-4c2b-85f9-2a6b48e5276e)

For cloning my repository you need to create a new Project from **Version Control**

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/f2e3030f-1ee5-4c2e-bf2e-e4f3f11ba3fb)

Put the link from the repository here in the url

**```git clone https://github.com/Ketel28/m295-Kerem-Telli.git```**

![image](https://github.com/Ketel28/m295-Kerem-Telli/assets/96288839/eaaae595-70f9-471a-82ad-7164c82b827e)

