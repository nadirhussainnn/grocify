# 🛒 Grocify – Modern Grocery Management System

**Grocify** is a full-stack modern grocery management application that allows **store administrators** to manage products, orders, and customers, while **customers** can browse, search, and place orders for grocery items online.

---

## 🚀 Features

### 👤 Customer
- Register and login
- Browse and search for available products
- Place orders for products
- View personal order history

### 🛠️ Admin
- Manage product inventory (add, update, delete)
- View and manage customer orders
- View list of registered customers
- Mark orders as delivered

---

## 🧱 Tech Stack

### 🔹 Backend
- **Java 17**
- **Jakarta EE** (JAX-RS, JPA, CDI)
- **Hibernate** (JPA implementation)
- **PostgreSQL** (Database)
- **Maven** for project management
- **WildFly 30** for deployment

### 🔹 Frontend
- **Angular 19**
- **TypeScript**

### 🔹 Tools
- **Docker** for production build
- **Git** for Version control
- **Postman** for API Testing

---

## 📜 Setup Instructions

### 🔧 Local Development (Without Docker)

#### 📌 Prerequisites
- Java 17
- PostgreSQL (recommended version: 15+)
- Maven 3.8+
- WildFly 30

#### 🗄️ Database Setup

Use the following details to set up your PostgreSQL instance:

```
CREATE DATABASE grocify;
CREATE USER grocify_user WITH ENCRYPTED PASSWORD 'grocify_pass';
GRANT ALL PRIVILEGES ON DATABASE grocify TO grocify_user;
```

⚙️ Run the backend
```python
mvn clean install
cd target/server/bin
./standalone.sh
```

⚙️ Run the frontend
```python
npm install
ng serve
```

### 🔧 If Using Docker

⚙️ Make sure you are in root directory i.e where docker-compose.yml is located

then
```python
docker-compose up --build
```

In all cases, apps can be accessed at
- Frontend: http://localhost:4200
- Backend API: http://localhost:8080/api