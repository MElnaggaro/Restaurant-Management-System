# Restaurant Management System

Welcome to the **Restaurant Management System**! This Java-based application is designed to streamline and automate the core operations of a restaurant, including reservations, table management, notifications, and reporting. Whether you are a developer, a restaurant manager, or a curious learner, this project offers a robust foundation for modern restaurant operations.

---

## 🚀 Features

- **Reservation System**: Efficiently handle customer reservations using a Binary Search Tree (BST) for fast lookup and management.
- **Table Management**: Keep track of table availability and assignments.
- **Waiting List**: Manage customers waiting for tables with a priority queue.
- **Notification System**: Notify customers about their reservation status and table availability.
- **Report Generation**: Generate insightful reports for restaurant analytics.
- **Database Integration**: Easily connect to a database for persistent data storage (see `DatabaseConnection.java`).

---

## 📁 Project Structure

```
DATA FINAL PROJECT/
├── Mein Project.iml
└── src/
    ├── DatabaseConnection.java      # Handles database connectivity
    ├── Main.java                    # Entry point of the application
    ├── NotificationQueue.java       # Manages notification queue
    ├── NotificationSystem.java      # Sends notifications to users
    ├── ReportGenerator.java         # Generates reports
    ├── Reservation.java             # Reservation entity
    ├── ReservationBST.java          # BST for reservations
    ├── ReservationSystem.java       # Core reservation logic
    ├── Table.java                   # Table entity
    ├── WaitingList.java             # Waiting list logic
    └── WLPq.java                    # Priority queue for waiting list
```

---

## 🛠️ Getting Started

### Prerequisites
- Java 8 or higher
- (Optional) MySQL or any JDBC-compatible database

### How to Run
1. **Clone the repository:**
   ```bash
   git clone https://github.com/MElnaggaro/Restaurant-Management-System.git
   ```
2. **Navigate to the project directory:**
   ```bash
   cd Restaurant-Management-System/DATA\ FINAL\ PROJECT/src
   ```
3. **Compile the project:**
   ```bash
   javac *.java
   ```
4. **Run the application:**
   ```bash
   java Main
   ```

---

## 🧩 Main Components

- **ReservationBST**: Fast reservation search and management.
- **WaitingList & WLPq**: Efficiently manage and prioritize waiting customers.
- **NotificationSystem**: Keeps customers informed in real-time.
- **ReportGenerator**: Provides valuable business insights.

---

## 📊 Example Use Cases
- **Make a Reservation**: Add a new reservation and assign a table.
- **Handle Walk-ins**: Add customers to the waiting list and notify them when a table is ready.
- **Generate Reports**: View daily, weekly, or monthly analytics.

---

## 🤝 Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📄 License
This project is licensed under the MIT License.

---

## ✨ Credits
Developed by [MElnaggaro](https://github.com/MElnaggaro) and contributors.

---

## 💡 Inspiration
This project was created as a final project to demonstrate advanced Java programming, data structures, and real-world application design.

---

## 📬 Contact
For questions or suggestions, please open an issue or contact the maintainer via GitHub.
