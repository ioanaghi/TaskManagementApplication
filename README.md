# Task Management Application

## 🚀 Welcome to the Task Management App!
Hey there! This is a **JavaFX-based Task Management Application** that helps you efficiently handle employees and their tasks. Whether you're tracking work hours, assigning tasks, or organizing complex projects, this app has got you covered!

## 🎯 What Can You Do?
- **Manage Employees** 👥
  - Add employees with unique IDs and track their tasks.
  - View the total hours worked.
  - See who has worked **more than 40 hours** in a week.

- **Handle Tasks Like a Pro** ✅
  - Add **simple and complex tasks**.
  - Assign tasks to employees and track progress.
  - Modify task statuses (**Completed / Not Completed**).
  - Remove tasks (including all subtasks if needed).

- **Keep Everything Saved** 💾
  - Your data is stored using **serialization**, so nothing gets lost when you close the app.

---

## 🏗 How the App Works (Behind the Scenes)
This app is built with **OOP principles** and follows the **MVC (Model-View-Controller) architecture**, which makes it super organized and easy to maintain.

### **🔹 Data Model (The Backbone)**
- `Employee.java` - Represents an employee.
- `Task.java` - The base class for tasks.
- `SimpleTask.java` - A task with a start and end time.
- `ComplexTask.java` - A task that contains subtasks.

### **🔹 Business Logic (The Brain)**
- `TaskManagement.java` - The main class that manages employees and tasks.
- `Utility.java` - Helper functions (like filtering employees by hours worked).

### **🔹 Persistence Layer (Keeps Your Data Safe)**
- `SerializationOperations.java` - Handles **saving and loading** data using files.

### **🔹 Presentation Layer (What You See)**
- `AppController.java` - The main controller handling user interactions.
- Individual controllers for **adding employees, assigning tasks, modifying tasks, etc.**
- `MainApplication.java` - The JavaFX entry point.

---

## 🛠 How to Run the App
### **What You Need**
- **Java 17+**
- **JavaFX 17+**
- **IntelliJ IDEA** (or another IDE with JavaFX support)

### **Get Started in 3 Steps**
1. **Clone the Repository** 📥
   ```sh
   git clone <repository-link>
   cd TaskManagementApplication
   ```
2. **Open the Project in IntelliJ IDEA** 🛠
3. **Run `MainApplication.java` and Enjoy!** 🎉

---

## 📂 Where Does Your Data Go?
The app saves everything in a **file called `project.dat`** so that when you reopen it, all your data is right where you left it!

---

## 🔮 Future Upgrades (Stay Tuned!)
- **💾 Switch to a Database** instead of saving in a file.
- **🚦 Task Prioritization** – Assign importance levels to tasks.
- **⏳ Deadline Tracking** – Get reminders for due dates.

---

## 🤝 Who Built This?
This app was created by **Ioana-Teodora Ghinet**, a passionate developer who loves making **efficient and user-friendly applications**. 🚀


