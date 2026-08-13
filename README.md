#  AgroPredict

An end-to-end agriculture assistance platform that combines a **Spring Boot web application**, **Python machine-learning services**, **MySQL**, and **external APIs** to provide crop recommendations, harvest decisions, and interactive assistance.

##  Overview

AgroPredict is a college project developed as a web-based agriculture application.

The main application is built with **Spring Boot**, while the machine-learning models are developed in **Python** and exposed through a **Flask API**. The application also uses **MySQL** for data storage and integrates an AI chatbot using the **OpenRouter API**.

The project currently provides:

-  Crop recommendation based on soil and environmental parameters
-  Harvest / Do Not Harvest prediction
-  AI chatbot integration
-  MySQL database integration using Spring Data JPA and Hibernate
-  Email functionality
-  Server-rendered pages using Thymeleaf

---

##  System Architecture

```text
                         ┌──────────────────────┐
                         │        User          │
                         │      Browser         │
                         └──────────┬───────────┘
                                    │
                              HTML / CSS / JS
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │     Spring Boot      │
                         │      Backend         │
                         │                      │
                         │ Controllers / JPA    │
                         │ Application Logic    │
                         │ Thymeleaf            │
                         └───────┬───────┬──────┘
                                 │       │
                     ┌───────────┘       └──────────────┐
                     │                                  │
                     ▼                                  ▼
             ┌────────────────┐                 ┌────────────────┐
             │      MySQL     │                 │   Flask API    │
             │  agroPredictDB │                 │    Python      │
             └────────────────┘                 └───────┬────────┘
                                                        │
                                                ┌───────┴────────┐
                                                │                │
                                                ▼                ▼
                                      cropPredModel.pkl   harvestModel.pkl
                                      Random Forest       Isolation Forest
                                                        +
                                                   MinMaxScaler
```

### Request flow

For crop prediction:

```text
User
  ↓
Spring Boot /predict
  ↓
HTTP POST → Flask /predict
  ↓
RandomForestClassifier
  ↓
Prediction
  ↓
Spring Boot
  ↓
CropPredResult.html
```

For harvest prediction:

```text
User
  ↓
Spring Boot /harvestPredict
  ↓
HTTP POST → Flask /harvestPredict
  ↓
IsolationForest + MinMaxScaler
  ↓
Harvest / Do Not Harvest
  ↓
Spring Boot
  ↓
harvest_result.html
```

---

##  Main Features

###  Crop Recommendation

The application accepts:

- Nitrogen
- Phosphorus
- Potassium
- pH
- Rainfall
- Temperature
- Soil color

The Spring Boot backend sends these parameters to the Python Flask API. The Flask service loads the trained Random Forest model and returns the predicted crop.

###  Harvest Prediction

The application accepts:

- Brix
- Pol
- Purity

The Python service applies the stored scaler and uses an Isolation Forest model to determine whether the crop should be harvested.

###  AI Chatbot

The application includes chatbot functionality integrated with the **OpenRouter API**.

The real API key is not included in this repository. A placeholder is used instead.

For a production version, the API key should be handled server-side or through secure secret management rather than being exposed in frontend JavaScript.

###  Database

The application uses:

- **MySQL**
- **Spring Data JPA**
- **Hibernate**

The main database used by the application is:

```text
agroPredictDB
```

The main soil-data table is:

```text
soildata_table
```

###  Email

Spring Mail is used for application email functionality through SMTP.

Credentials are supplied through environment variables rather than being stored directly in source code.

---

##  Technology Stack

### Backend

- Java 17
- Spring Boot 3.3.6
- Spring Web
- Spring Data JPA
- Hibernate
- Thymeleaf
- Spring Mail
- Maven

### Machine Learning / Python

- Python 3.10
- Flask
- Pandas
- Joblib
- Scikit-learn
- NumPy
- SciPy

### Frontend

- HTML
- CSS
- JavaScript
- Thymeleaf

### Database

- MySQL
- MySQL Workbench

### External Services

- OpenRouter API
- Gmail SMTP

---

## 📂 Project Structure

```text
AgroPredict/
│
├── ML/
│   ├── app.py
│   ├── cropPredModel.pkl
│   ├── harvestModel.pkl
│   └── requirements.txt
│
├── springboot/
│   └── backend/
│       ├── pom.xml
│       ├── mvnw
│       ├── mvnw.cmd
│       │
│       └── src/
│           ├── main/
│           │   ├── java/
│           │   │   └── com/agroPredict/demo/
│           │   │       ├── DemoApplication.java
│           │   │       ├── modelConnections.java
│           │   │       ├── SoilTable.java
│           │   │       ├── SoilDataRepository.java
│           │   │       ├── DBConnectionLogic.java
│           │   │       ├── EmailService.java
│           │   │       ├── ReminderScheduler.java
│           │   │       ├── normalEstimate.java
│           │   │       └── pageController.java
│           │   │
│           │   └── resources/
│           │       ├── static/
│           │       │   ├── css/
│           │       │   └── js/
│           │       ├── templates/
│           │       ├── application.properties
│           │       └── application-example.properties
│           │
│           └── test/
│
├── .gitignore
└── README.md
```

---

##  Prerequisites

Install the following before running the project:

- Java 17
- IntelliJ IDEA or another Java IDE
- MySQL Server
- MySQL Workbench
- Miniconda / Anaconda
- Python 3.10

---

##  Setup and Run

### 1. Clone the repository

```bash
git clone https://github.com/dattarajkadam793-cyber/AgroPredict.git
cd AgroPredict
```

### 2. Create the MySQL database

Open MySQL Workbench and run:

```sql
CREATE DATABASE agroPredictDB;
```

The application is configured to connect to:

```text
localhost:3306/agroPredictDB
```

Hibernate is configured with:

```properties
spring.jpa.hibernate.ddl-auto=update
```

so the required database tables can be created/updated from the JPA entities when the application starts.

The main soil-data table is:

```text
soildata_table
```

To inspect saved soil records:

```sql
SELECT * FROM soildata_table;
```

### 3. Configure Spring Boot credentials

The application uses environment variables for sensitive configuration.

Configure:

```text
MAIL_USERNAME
MAIL_PASSWORD
DB_USERNAME
DB_PASSWORD
```

An example configuration file is included:

```text
application-example.properties
```

Do not commit actual database passwords, mail credentials, or other secrets.

### 4. Set up the Python ML service

Activate the Python environment:

```bash
conda activate ml_env
```

Go to the ML directory:

```bash
cd ML
```

Install the required packages:

```bash
pip install -r requirements.txt
```

Start the Flask service:

```bash
python app.py
```

The ML service runs on:

```text
http://127.0.0.1:5000
```

Available endpoints:

```text
POST /predict
POST /harvestPredict
```

### 5. Start the Spring Boot application

Open:

```text
springboot/backend
```

in IntelliJ IDEA.

Run:

```text
DemoApplication
```

The Spring Boot application runs on:

```text
http://localhost:8080
```

The Flask service should be running at the same time because Spring Boot sends prediction requests to the Flask API.

---

##  API Communication

### Crop Prediction

Spring Boot sends:

```text
POST http://localhost:5000/predict
```

Example request:

```json
{
  "Nitrogen": 98,
  "Phosphorus": 99,
  "Potassium": 96,
  "pH": 7.8,
  "Rainfall": 888,
  "Temperature": 886,
  "soilColor": "Black"
}
```

Example response:

```json
{
  "prediction": "Sugarcane"
}
```

### Harvest Prediction

Spring Boot sends:

```text
POST http://localhost:5000/harvestPredict
```

Example input:

```json
{
  "Brix": 18.5,
  "Pol": 14.2,
  "Purity": 76.8
}
```

Example response:

```json
{
  "prediction": "Harvest",
  "score": 0.42
}
```

---

##  OpenRouter Chatbot Setup

The chatbot uses the OpenRouter API.

The real API key is intentionally not included in this repository.

The code contains:

```text
YOUR_OPENROUTER_API_KEY
```

as a placeholder.

To use the chatbot locally:

1. Create your own OpenRouter API key.
2. Replace `YOUR_OPENROUTER_API_KEY` with your own key.
3. Do not commit the real key to GitHub.

For a future version, the API request can be moved to the Spring Boot backend so that the API key is not exposed in frontend JavaScript.

---

##  Machine Learning Models

### Crop Recommendation

```text
Model: RandomForestClassifier

Inputs:
- Nitrogen
- Phosphorus
- Potassium
- pH
- Rainfall
- Temperature
- Soil color

Output:
Recommended crop
```

### Harvest Decision

```text
Model: IsolationForest
Preprocessing: MinMaxScaler

Inputs:
- Brix
- Pol
- Purity

Output:
Harvest / Do Not Harvest
```

The trained `.pkl` models are included in the repository so the application can perform inference without retraining.

The original training datasets and Jupyter notebooks are not included in this repository.

---

##  Application Flow

```text
                    Browser
                       │
                       ▼
                Spring Boot :8080
                       │
             ┌─────────┴─────────┐
             │                   │
             ▼                   ▼
          MySQL            Flask API :5000
                                 │
                        ┌────────┴────────┐
                        │                 │
                        ▼                 ▼
                  Crop Model        Harvest Model
                 RandomForest      IsolationForest
                                      +
                                  MinMaxScaler
```

---

##  Security

Sensitive credentials are intentionally excluded from the repository.

Examples include:

- MySQL passwords
- Gmail app passwords
- OpenRouter API keys

Spring Boot database and mail credentials are read through environment variables.

The OpenRouter key included in the repository is only a placeholder:

```text
YOUR_OPENROUTER_API_KEY
```

---

##  Project Scope

This repository contains the main application code and the trained ML models used for inference.

The following are intentionally not included:

- Original training datasets
- Jupyter notebooks
- Miniconda environment
- Local MySQL database files
- Passwords
- API keys

The project is structured so that the Spring Boot application and the Python ML service can be run locally as separate components.

---

##  Project

**AgroPredict**

Built using Java, Spring Boot, Python, Flask, Scikit-learn, MySQL, HTML, CSS and JavaScript.

##  Author

Dattaraj Kadam