# RetailEye: Secure AI-Driven Superstore System

RetailEye is a monolithic Spring Boot application that integrates body camera usage tracking, real-time incident detection, and employee training workflows for superstores like Walmart or United. The system uses AI-based processing and smart device tracking to improve employee safety, accountability, and customer service quality.

---

## 🚀 Features

- ✅ **Body Camera Assignment**: Tracks which employee used which body camera during which shift.
- ✅ **Shift Management**: Handles employee shift records with full date/time tracking.
- ✅ **Camera Usage Tracking**: Ensures accountability with shared cameras across shifts.
- ✅ **Incident Simulation**: Flags tense or aggressive situations using AI-stubbed logic.
- ✅ **Recording Management**: Start/stop simulated recordings and associate them with employees.
- ✅ **Manager Dashboard (WIP)**: Review flagged incidents, mark for training, or escalate.

---

## 🧱 Tech Stack

| Layer          | Technology                                 |
|----------------|--------------------------------------------|
| Backend        | Java 21, Spring Boot 3                     |
| Database       | PostgreSQL                                 |
| ORM            | Spring Data JPA + Hibernate                |
| Frontend (TBD) | React.js / Angular (optional)              |
| AI Module      | Stubbed via service layer (Python planned) |
| Dev Tools      | IntelliJ, Git, Docker (optional)           |

---

## ⚙️ Getting Started

### 1. Prerequisites

- Java 21+
- PostgreSQL (running on default port `5432`)
- IntelliJ or any IDE of your choice

### 2. Create PostgreSQL Database

```sql
CREATE DATABASE retail_eye;
```