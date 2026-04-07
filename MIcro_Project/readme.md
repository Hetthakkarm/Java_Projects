# 🚀 Vending Machine Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge\&logo=java\&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00758F?style=for-the-badge\&logo=mysql\&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Database-blue?style=for-the-badge)

---

## 📖 Overview

The **Vending Machine Management System** is a Java-based console application designed to simulate real-world vending operations using **role-based access control**.

It enables:

* 🧍 Buyers to purchase items
* 👨‍🔧 Operators to manage inventory
* 👨‍💼 Managers to control machines and users

> This project demonstrates practical implementation of **JDBC, SQL, and Object-Oriented Programming (OOP)** concepts.

---

## 🎯 Objectives

* ✅ Provide seamless purchasing experience
* ✅ Enable inventory management for operators
* ✅ Allow managers to control machines and users
* ✅ Maintain transaction and reporting system

---

## 🏗️ System Architecture

```plaintext
Main (Controller)
   ↓
Service Layer
   ├── LoginService
   ├── ManagerService
   ├── OperatorService
   ├── MachineService
   ↓
DBConnection (JDBC Layer)
   ↓
MySQL Database
```

---

## 👥 User Roles

### 🧍 Buyer

* Select vending machine
* View items
* Add items to cart
* Checkout and purchase

---

### 👨‍🔧 Operator

* Login authentication
* View assigned machines
* View inventory
* Add items
* Update stock

---

### 👨‍💼 Manager

* Login authentication
* Add machines
* Create operators
* Assign machines
* View daily sales report

---

## ⚙️ Features

* 🔐 Role-based authentication
* 🛒 Cart system using HashMap
* 📦 Inventory management
* 🔄 Real-time stock updates
* 📊 Daily sales report
* 🔗 Machine-operator mapping
* 🧠 Clean modular design

---

## 🗄️ Database Schema

### 📌 Users Table                 

| Field    | Type    |
| -------- | ------- |
| user_id  | INT     |
| username | VARCHAR |
| password | VARCHAR |
| role     | VARCHAR |

---

### 📌 Machines Table

| Field      | Type    |
| ---------- | ------- |
| machine_id | INT     |
| name       | VARCHAR |
| location   | VARCHAR |

---

### 📌 Items Table

| Field   | Type    |
| ------- | ------- |
| item_id | INT     |
| name    | VARCHAR |
| price   | DOUBLE  |

---

### 📌 Machine Inventory

| Field      | Type |
| ---------- | ---- |
| machine_id | INT  |
| item_id    | INT  |
| quantity   | INT  |

---

### 📌 Operator Machine Mapping

| Field       | Type |
| ----------- | ---- |
| operator_id | INT  |
| machine_id  | INT  |

---

### 📌 Orders Table

| Field        | Type      |
| ------------ | --------- |
| order_id     | INT       |
| machine_id   | INT       |
| total_amount | DOUBLE    |
| created_at   | TIMESTAMP |

---

## 🔄 Workflow

### 🧍 Buyer Flow

```plaintext
Select Machine → View Items → Add to Cart → Checkout → Stock Updated
```

---

### 👨‍🔧 Operator Flow

```plaintext
Login → View Machines → Manage Inventory
```

---

### 👨‍💼 Manager Flow

```plaintext
Login → Manage System → View Reports
```

---

## 🧠 Key Concepts Used

* 🔹 JDBC (Java Database Connectivity)
* 🔹 PreparedStatement (Secure SQL Execution)
* 🔹 ResultSet (Data Retrieval)
* 🔹 HashMap (Cart Implementation)
* 🔹 SQL JOIN Operations
* 🔹 Aggregation (SUM, COUNT)
* 🔹 Role-Based Access Control

---

## 🚀 How to Run

### 🔧 Step 1: Clone Repository

```bash
git clone https://github.com/your-username/vending-system.git
cd vending-system
```

---

### 🗄️ Step 2: Setup Database

```sql
CREATE DATABASE vending_db;
```

---

### 📦 Step 3: Add MySQL Connector

Place the `.jar` file inside `lib/` folder.

---

### ⚙️ Step 4: Compile

```bash
javac -cp ".;lib\mysql-connector-j-9.6.0.jar" database\DBConnection.java manager\*.java operator\*.java machine\*.java auth\*.java Main.java
```

---

### ▶️ Step 5: Run

```bash
java -cp ".;lib\mysql-connector-j-9.6.0.jar" Main
```

---

## 🐞 Challenges Faced

* Handling JDBC connection setup
* Implementing cart logic correctly
* Managing role-based access
* Ensuring correct stock updates

---

## 🔧 Solutions

* Used JDBC with proper classpath
* Implemented HashMap for cart tracking
* Designed modular service-based architecture
* Used SQL joins for data consistency

---

## 📈 Future Enhancements

* 🎨 GUI (Java Swing / JavaFX)
* 💳 Online payment integration
* 📊 Advanced analytics dashboard
* 📱 Mobile app version
* ⚠️ Low stock alerts

---

## 🎯 Conclusion

This project successfully demonstrates a **real-world vending machine system** with proper backend logic, database integration, and role-based control.

It provides a strong foundation for building scalable enterprise applications.

---

## 👨‍💻 Author

**Het Thakkar**

---
