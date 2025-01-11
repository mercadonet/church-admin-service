# Getting Started

## Prerequisites

Before you begin, ensure you have the following installed:
- Java 17
- Gradle
- PostgreSQL (if using a PostgreSQL database)

## Running the Application

1. **Clone the repository**:
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Set up the database**:
    - If using PostgreSQL, create a database and update the `application.properties` file with your database credentials.

3. **Build the project**:
    ```sh
    ./gradlew build
    ```

4. **Run the application**:
    ```sh
    ./gradlew bootRun
    ```

5. **Access the application**:
    - The application will be running at `http://localhost:8080`.

## Testing the Application

To run the tests, use the following command:
```sh
./gradlew test