
# 🚀 API Testing Automation Project

![Java](://img.shields.io/badge/Java-17-orange)
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
* [Setup & Installation](#️-setup--installation)
* [Test Execution](#-test-execution)
* [Allure Reporting](#-allure-reporting)
* [Postman Integration](#-postman-integration)
* [Sample Test Scenarios](#-sample-test-scenarios)
* [Future Enhancements](#-future-enhancements)

---

## 🌟 Project Overview

This project demonstrates **end-to-end API testing**, starting with manual verification, then Postman scripts, and finally a **scalable automation framework** using Rest Assured.

### Workflow:

1. **Manual Testing**

   * Analyze API documentation.
   * Validate endpoints using Postman.
   * Document scenarios in **Excel** & **Zephyr**.

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
| **Java 17**      | Core programming language       |
| **Maven**        | Build & dependency management   |
| **TestNG**       | Test management & annotations   |
| **Rest Assured** | API testing framework           |
| **Allure**       | Test reporting & visualization  |
| **Postman**      | Manual & pre-automation testing |
| **Zephyr**       | Test case management            |

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
│   │   ├── clients/             # API clients (Categories, Products, Users)
│   │   ├── models/              # POJOs for request/response
│   │   └── utils/               # Configurations & helpers
│   │
│   └── test/java
│       ├── base/                # Base classes for setup
│       └── tests/               # Test classes
│
├── pom.xml                       # Maven dependencies
├── postman_collection.json       # Postman collection
├── target/allure-results/        # Raw Allure files
└── README.md                     # Project documentation
```

---

## ⚙️ Setup & Installation

### **1. Clone the Repository**

```bash
git clone https://github.com/your-username/api-testing-automation.git
cd api-testing-automation
```

### **2. Install Dependencies**

```bash
mvn clean install
```

### **3. Configure Environment**

* Update `config.properties` with:

  * `baseUrl`
  * API credentials (email/password)

---

## 🧪 Test Execution

### Run All Tests:

```bash
mvn clean test
```

### Run a Specific Test Class:

```bash
mvn -Dtest=CategoriesTests test
```

### Run With testng.xml Suite:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

---

## 📊 Allure Reporting

Generate and view Allure reports:

```bash
allure serve target/allure-results
```

> **Note:**
> A `@BeforeSuite` TestNG hook clears `target/allure-results` before each run to prevent old results.

**Allure Features:**

* Pass/Fail trends
* Request & Response logs
* Visual step breakdown

---

## 🔹 Postman Integration

The provided Postman collection:

* Uses **dynamic variables** for unique data:

  * `{{currentIsoTimestamp}}` for unique names/emails.
* Stores access tokens dynamically after login.

**Example Body With Variables:**

```json
{
  "name": "Postman Category {{currentIsoTimestamp}}",
  "image": "https://placehold.co/600x400"
}
```

Run with external JSON/CSV files for data-driven testing.

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

## 🤝 Contributing

Pull requests are welcome!
Please open an issue first to discuss proposed changes.

---

## 📜 License

This project is licensed under the MIT License.

---

## 👤 Author

**Amr Amasha**
Passionate QA Engineer specializing in **API testing**, **test automation**, and **framework design**.
[LinkedIn](https://www.linkedin.com/) | [GitHub](https://github.com/)

---

Would you like me to include **GitHub badges for build status and test coverage** to make it even more professional?
