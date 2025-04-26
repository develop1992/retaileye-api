# RetailEye API

**RetailEye API** is a backend application designed to handle **employee safety**, **camera assignments**, **shift management**, and **incident tracking** in a retail store environment. The system integrates body camera usage with incident detection, video recording, and shift management. The application provides RESTful APIs to manage employees, camera assignments, shifts, and incidents.

## Features

- **Employee Management**: Create, update, and delete employees.
- **Shift Management**: Assign shifts to employees, track their shift details, and view shift history.
- **Body Camera Assignment**: Assign body cameras to employees during shifts, unassign them, and retrieve current assignments.
- **Incident Reporting**: Store flagged incidents ([RetailEye AI](https://github.com/develop1992/retaileye-ai)) detected during shifts, store and retrieve incident details.
- **Recording Management**: Store/remove video recordings associated with employee shifts and incidents.

## Folder Structure

```plaintext
retaileye-api/
├── gradle/
│   └── wrapper/            # Gradle wrapper files
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── edu/
│   │   │       └── ttu/
│   │   │           └── retaileye/
│   │   │               ├── controller/       # API controllers (Employee, Camera, Incident, etc.)
│   │   │               ├── dtos/             # Data transfer objects
│   │   │               ├── exceptions/       # Custom exception handling
│   │   │               ├── entities/         # Database models
│   │   │               ├── repositories/     # JPA repositories for data access
│   │   │               ├── services/         # Business logic and service classes
│   │   │               └── config/           # Config related classes
│   │   └── resources/
│   │       └── application.properties   # Configuration properties for the application
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

## Installation

### Prerequisites

- **Java 21** or higher
- **PostgreSQL** database running on port 5432
- **Gradle** for building and running the application

### Configuration

1. Clone the repository:

   ```bash
   git clone https://github.com/develop1992/retaileye-api.git
   cd retaileye-api
   ```

2. Set up the database:
   - Create a PostgreSQL database named **`retail_eye`**.
   - Update the database credentials in `src/main/resources/application.properties`:

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/retail_eye
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. Build and run the application:

   ```bash
   ./gradlew bootRun
   ```

4. The application will be available at `http://localhost:8080`.

## API Endpoints

### **Employee Management**

- **`POST /retaileye/employees`**: Create a new employee.
- **`GET /retaileye/employees`**: Get a list of all employees.
- **`GET /retaileye/employees/{id}`**: Get details of a specific employee.
- **`PUT /retaileye/employees/{id}`**: Update an employee's information.
- **`DELETE /retaileye/employees/{id}`**: Delete an employee.

### **Shift Management**

- **`POST /retaileye/employees-shifts`**: Assign a shift to an employee.
- **`GET /retaileye/employees-shifts`**: Get all employee shifts.
- **`GET /retaileye/employees-shifts/{id}`**: Get details of a specific shift.
- **`PUT /retaileye/employees-shifts/{id}`**: Update shift details.
- **`DELETE /retaileye/employees-shifts/{id}`**: Remove a shift assignment.

### **Body Camera Assignment**

- **`POST /retaileye/employees-shifts-cameras`**: Assign a body camera to an employee's shift.
- **`GET /retaileye/employees-shifts-cameras`**: Get all body camera assignments.
- **`GET /retaileye/employees-shifts-cameras/{id}`**: Get details of a specific body camera assignment.
- **`DELETE /retaileye/employees-shifts-cameras/{id}`**: Unassign a body camera from an employee's shift.

### **Incident Management**

- **`POST /retaileye/incidents`**: Create a new incident.
- **`GET /retaileye/incidents`**: Get a list of all incidents.
- **`GET /retaileye/incidents/{id}`**: Get details of a specific incident.
- **`DELETE /retaileye/incidents/{id}`**: Delete an incident.
- **`POST /retaileye/incidents/bulk`**: Create multiple incidents at once.
- **`DELETE /retaileye/incidents/all`**: Delete all incidents.

### **Recording Management**

- **`POST /retaileye/recordings`**: Create a new recording.
- **`GET /retaileye/recordings`**: Get a list of all recordings.
- **`GET /retaileye/recordings/{id}`**: Get details of a specific recording.
- **`PUT /retaileye/recordings/{id}`**: Update a recording's information.
- **`DELETE /retaileye/recordings/{id}`**: Delete an recording.
- **`GET /retaileye/recordings/view/{id}`**: Stream the video of a recording.

## Testing

Unit and integration tests are located in `src/test/java/edu/ttu/retaileye/`. To run the tests:

```bash
./gradlew test
```

## Technologies Used

- **Spring Boot**: For building the REST API.
- **PostgreSQL**: For storing data (employees, shifts, incidents, etc.).
- **JPA & Hibernate**: For ORM-based database interaction.
- **Gradle**: For dependency management and building the application.
- **Java 21**: For the latest features in Java development.

## Future Enhancements

- **Enhanced AI Incident Detection**:
    - The system can integrate more sophisticated **AI-based incident detection** for situations such as aggression, theft, or suspicious behavior using real-time video analysis from body cameras. AI models could be trained to identify abnormal human behavior or specific threats that require immediate attention.
    - **Integration with FastAPI**: The existing **FastAPI-based AI API** can be enhanced to provide more detailed analysis, such as detecting specific actions, not just motion, and sending alerts to the Spring backend for timely processing and notification.

- **User Authentication & Authorization**:
    - Incorporating **user authentication** and **role-based authorization** will ensure that sensitive actions, such as managing employees or reviewing incidents, are only accessible to the appropriate users (e.g., admins or managers).
    - This could include integrating **OAuth2**, **JWT**, or **Spring Security** for secure access control, ensuring that roles such as **admin**, **manager**, and **employee** can be enforced within the system.

- **Employee & Manager Dashboard**:
    - With the **[RetailEye UI](https://github.com/develop1992/retaileye-ui)** already in place, future enhancements could focus on improving the **manager dashboard**. Managers could review **incident history**, **employee shift data**, **camera assignments**, and flagged incidents in a comprehensive way. This dashboard could also provide actionable insights, such as trends in employee behavior or safety incidents.
    - **Advanced Reporting**: Create a system that generates detailed reports for managers, providing insights on camera usage, employee shift effectiveness, and the frequency of incidents.

- **Advanced Video Analytics**:
    - Use the data captured by body cameras and detected incidents to generate detailed **analytical reports** or analyze trends over time (e.g., most frequent incidents by employee or by shift). These reports can help managers identify patterns and improve security measures.
    - Integration with external analytics platforms could be explored for **data aggregation** and **visualization** to present actionable insights to management.

- **Real-time Notifications for Managers**:
    - Develop a **real-time notification system** for managers that alerts them when critical incidents are detected. These notifications could be integrated into the **UI** for immediate action, such as triggering an escalation protocol or notifying a security team.
    - Consider incorporating **push notifications** or **SMS alerts** to ensure rapid response times to incidents.

- **Integration with Third-party Security Systems**:
    - The system could be enhanced to work with third-party **security systems**, such as additional surveillance cameras, alarm systems, or biometric recognition systems. These integrations would provide a more robust safety solution.
    - This could include integrating **facial recognition**, **real-time location tracking**, or **alarms** triggered by incidents for comprehensive monitoring and escalation.