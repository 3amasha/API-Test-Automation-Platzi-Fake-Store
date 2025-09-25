
# 🚀 API Testing Automation Project

![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-4.0-blue)
![TestNG](https://img.shields.io/badge/TestNG-Testing-lightgrey)
![RestAssured](https://img.shields.io/badge/RestAssured-API-green)
![Postman](https://img.shields.io/badge/Postman-Collection-orange)
![Allure](https://img.shields.io/badge/Allure-Report-pink)
![Status](https://img.shields.io/badge/Status-Active-brightgreen)

> **Goal:** Build a complete **API testing project** progressing from **Manual Testing → Postman → Automation with Rest Assured** following industry best practices.
> The project tests endpoints from the [EscuelaJS API](https://api.escuelajs.co/docs).

---

## 📖 Table of Contents

* [Project Overview](#-project-overview)
* [Tech Stack](#-tech-stack)
* [Modules Tested](#-modules-tested)
* [Project Structure](#-project-structure)
* [Sample Test Scenarios](#-sample-test-scenarios)
* [Future Enhancements](#-future-enhancements)

---

## 🌟 Project Overview

This project demonstrates **end-to-end API testing**, starting with manual verification, then Postman scripts, and finally a **scalable automation framework** using Rest Assured.

### Workflow:

1. **Manual Testing**

   * Analyze API documentation.
   * Validate endpoints using Postman.
   * Document test case & scenarios in **Excel**.

2. **Postman Automation**

   * Dynamic variables like `{{currentIsoTimestamp}}` for unique test data.
   * **pm.test** scripts for validation.
   * Data-driven testing with JSON/CSV.

3. **Automated Testing**

   * Built using **Java, TestNG, Maven, Rest Assured**.
   * Clean architecture with **API Client classes**, **POJOs**, and utilities.
   * Integrated **Allure Reports** for rich visual test reports.

---

## 🛠 Tech Stack

| Tool             | Purpose                         |
| ---------------- | ------------------------------- |
| **Java 21**      | Core programming language       |
| **Maven**        | Build & dependency management   |
| **TestNG**       | Test management & annotations   |
| **Rest Assured** | API testing framework           |
| **Allure**       | Test reporting & visualization  |
| **Postman**      | Manual & pre-automation testing |
| **excel**        | Test case documenting           |

---

## 📦 Modules Tested

| Module         | Endpoints Tested                           |
| -------------- | ------------------------------------------ |
| **Categories** | Create, Get All, Get by ID, Update, Delete |
| **Products**   | Create, Get All, Get by ID, Update, Delete |
| **Users**      | Create, Get All, Get by ID, Update         |
| **Auth**       | Login, Get Profile, Refresh Tokens         |

---

## 📂 Project Structure

```
api-testing-automation/
│
├── src
│   ├── main/java
│   │   ├── auth/                # Authentication & TokenManager
│   │   ├── base/                # Request/Response Specifications logic & Enum for all endpoint paths
│   │   ├── clients/             # API clients (Categories, Products, Users)
│   │   ├── models/              # POJOs for request/response
│   │   └── config/              # Configurations & Loads values from config.properties
│   │
│   └── test/java
│       ├── base/                # Base classes for setup
│       └── tests/               # Test classes
│
├── pom.xml                       # Maven dependencies
├── resources                     # Base URL, credentials, etc.
├── target/allure-results/        # Raw Allure files
└── README.md                     # Project documentation
```

---

## 📑 Sample Test Scenarios

| Test Case ID | Endpoint        | Scenario                       | Method | Expected Result  |
| ------------ | --------------- | ------------------------------ | ------ | ---------------- |
| CAT-01       | `/categories`   | Create category (valid data)   | POST   | 201 Created      |
| CAT-03       | `/categories`   | Missing name field             | POST   | 400 Bad Request  |
| USR-01       | `/users`        | Create user (valid data)       | POST   | 201 Created      |
| USR-05       | `/users/{id}`   | Update user with invalid email | PUT    | 400 Bad Request  |
| AUTH-01      | `/auth/login`   | Login with valid credentials   | POST   | 200 + Tokens     |
| AUTH-03      | `/auth/profile` | Access profile without token   | GET    | 401 Unauthorized |

---

## 🚀 Future Enhancements

* **Dockerize** the test framework.
* Integrate with **Jenkins** for CI/CD pipelines.
* Add performance testing with **JMeter/Gatling**.
* Extend reporting with HTML dashboards.

---

## 👤 Author

**Amr Amasha**
Passionate Software Testing Engineer specializing in **manual testing**. **API testing** and **test automation**.
[LinkedIn](https://www.linkedin.com/in/amr-amasha-919b79215/) | [GitHub](https://github.com/3amasha) 
| [Resume](https://drive.google.com/file/d/1xxxq2QPXRtskyLWUY2ZKfH2DMT8dvkl3/view?usp=drive_link)


