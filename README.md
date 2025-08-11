# 📱 ABC Company Mobile App Automation

This repository contains **Appium + Java + Cucumber** automation scripts for testing the ABC Company mobile app.  
It follows a **Page Object Model (POM)** structure and supports **BDD** using Gherkin feature files.

---

## 📌 Features Covered

1. **Attendance Report Search**
    - Search attendance between specific dates.
    - Filter by **"On Leave"** status.
    - Capture screenshot of the results.

2. **Check-IN and Leave Application**
    - Perform a **Check-IN** with camera capture.
    - Submit a **Leave Application**.
    - Verify confirmation and take a screenshot.

---

## 📂 Project Structure

```
ExcelTechnology/
├── screenshots/                  # Captured screenshots
├── src
│   ├── main
│   │   └── java
│   └── test
│       ├── java
│       │   ├── pages             # Page Object Model classes
│       │   ├── stepDefinitions    # Cucumber step definition files
│       │   ├── utils              # Utility classes (Driver Manager)
│       │   └── TestRunner.java    # Cucumber test runner
│       └── resources
│           ├── features          # Gherkin feature files
│           │   ├── Attendance Report.feature
│           │   └── Leave Application.feature
│           ├── config.properties   # App & environment config
│           └── mobile-app.apk     # Application under test
├── target/                        # Test output
├── pom.xml
└── .gitignore
```

---

## ⚙️ Prerequisites

Before running the tests, make sure you have:

- **Java JDK 21** installed (`JAVA_HOME` configured)
- **Maven 3.8+**
- **Node.js** installed
- **Appium Server 2.x** installed globally:
  ```bash
  npm install -g appium
  ```
- **Appium Inspector** (optional)
- **Android SDK** installed & environment variables set:
  ```bash
  ANDROID_HOME=<path_to_android_sdk>
  PATH=$ANDROID_HOME/platform-tools:$PATH
  ```
- A connected Android device or emulator
- The `mobile-app.apk` in `src/test/resources/`

### 📦 Install Dependencies
```bash
  mvn clean install
```

### 🔧 Configure App & Device

In `src/test/java/utils/DriverManager.java` (or relevant driver setup class):

```java
caps.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/mobile-app.apk");
caps.setCapability("deviceName", "emulator-5554");
caps.setCapability("platformName", "Android");
caps.setCapability("automationName", "UiAutomator2");
```

You can store values like `deviceName` or `platformVersion` in `config.properties` for easier maintenance.

---

## ▶️ Running the Tests

- **Run all scenarios**
```bash
  mvn test
  ```

- **Run by tag**
  ```bash
  mvn test -Dcucumber.filter.tags="@Attendance-Report"
  mvn test -Dcucumber.filter.tags="@Leave-Application"
  ```

---

## 📊 Reports

- **HTML Report:** `target/cucumber-reports.html`

---

## 📷 Screenshots

Screenshots taken during execution are stored in the `screenshots/` folder.

---

## 🧩 Main Dependencies

From `pom.xml`:

- Selenium Java
- Appium Java Client
- Cucumber Java & JUnit
- Maven Surefire Plugin

---

## 🚀 Quick Start

1. **Clone the repo**
   ```bash
   git clone https://github.com/yourusername/ExcelTechnology.git
   cd ExcelTechnology
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Start Appium server**
   ```bash
   appium
   ```

4. **Run tests**
   ```bash
   mvn test
   ```

---
## Resources
- [Automation Video](docs/automation_video.mp4)
- [Bug Report and Test Cases](docs/exploratory_testing.pdf)


## 👤 Author

Md. Mijanur Rahman  
📧 mizan.csegub@gmail.com

---

If you want, I can also **add commands in the README to run your tests on a specific connected device from `config.properties`**, so you don’t have to edit Java files each time you switch devices. That way, your setup will be fully configurable.
