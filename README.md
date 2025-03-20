# Pablo Escobanks

## Environment Variables Setup

This project uses environment variables to secure sensitive information such as database credentials. The environment variables are loaded from a `.env` file located in the `src/main/resources` directory.

### Setting up your environment

1. Copy the `.env.example` file to a new file called `.env` in the `src/main/resources` directory
2. Update the values in the `.env` file with your actual configuration
3. Make sure not to commit the `.env` file to source control (it's already added to `.gitignore`)

### Available Environment Variables

```
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/your_db_name
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Application Configuration
APP_NAME=pabloescobanks

# Hibernate Configuration
JPA_DDL_AUTO=update
JPA_SHOW_SQL=true
JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQL8Dialect

# Logging Configuration
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_SPRING_SECURITY=DEBUG
LOGGING_LEVEL_SPRING_WEB=DEBUG
LOGGING_LEVEL_SPRING_BOOT_AUTOCONFIGURE=DEBUG
LOGGING_LEVEL_HIBERNATE_SQL=DEBUG
LOGGING_LEVEL_HIBERNATE_TYPE=TRACE
```

### How it works

The application loads environment variables from the `.env` file using a custom property source loader. These environment variables are then used in the `application.properties` file to configure the application.

## Running the Application

To run the application with environment variables:

```
# On Windows
run.bat

# OR using Gradle directly
./gradlew bootRun
```

## Building the Application

To build the application:

```
./gradlew build
``` 