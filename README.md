# Test Automation Framework

## Overview

This repository contains a professional-grade test automation framework developed for hybrid Web and API testing. The
architecture is built on modern Java standards and follows Behavior-Driven Development (BDD) principles to ensure
scalability, maintainability, and readability.

## Technology Stack

* **Programming Language:** Java 25 (OpenJDK)
* **Integrated Development Environment:** IntelliJ IDEA 2025.2.5 (Community Edition)
* **Build Management:** Apache Maven 3.9.14
* **Test Execution:** TestNG
* **BDD Implementation:** Cucumber
* **Browser & Mobile Automation:** Selenium WebDriver and Appium
* **Reporting Engine:** Allure Reports
* **Continuous Integration:** Jenkins (Pipeline as Code via Jenkinsfile)

## Technical Architecture and Features

* **Design Pattern:** Implementation of Page Object Model (POM) to ensure structural separation between UI components
  and test logic.
* **BDD Methodology:** Utilization of Gherkin syntax (.feature files) to facilitate collaboration between technical and
  non-technical stakeholders.
* **Hybrid Testing Support:** Integrated solution for verifying both Graphical User Interfaces (Web) and RESTful API
  endpoints.
* **Code Optimization:** Integration of Project Lombok to minimize boilerplate code and enhance readability.
* **Configuration Management:** Centralized environment management utilizing .properties, .json, and SQLite (.db) data
  sources.

## Prerequisites

* **Java Development Kit:** JDK 25
* **Build Tool:** Apache Maven 3.9.14 or higher
* **Operating System:** macOS (Optimized for Apple Silicon / ARM64)
* **Reporting Tools:** Allure Commandline Interface (Installable via `brew install allure`)
* **Web Drivers:** ChromeDriver and GeckoDriver compatible with local browser versions

## Integrated Development Environment (IDE) Configuration

The following IntelliJ IDEA plugins are utilized within this framework to optimize development:

* **Cucumber for Java / Gherkin:** Support for feature file navigation and step definition binding.
* **Lombok:** Annotation processing for automated code generation.
* **GitHub Copilot:** AI-assisted development integration.
* **YAML & TOML:** Comprehensive support for specialized configuration formats.

## Execution Guide

### 1. Test Execution via Maven

To execute the automation suite using the defined TestNG configuration and specific Cucumber tags, run:

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml -Dcucumber.filter.tags="@general"