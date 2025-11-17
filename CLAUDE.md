# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a JSP (Java Server Pages) and Servlet learning project containing two main modules:
- **ch08**: Demonstrates session management with cookies, member login/logout functionality
- **ch09**: Demonstrates CRUD operations with JSP pages for list, insert, view, and write operations

Both modules are Maven-based WAR (Web Application Archive) projects using Jakarta Servlet API 6.1.0 with Java 17.

## Building and Running

### Build Commands
```bash
# Build a specific module
mvn clean package -f ch08/pom.xml
mvn clean package -f ch09/pom.xml

# Or use Maven wrapper
./ch08/mvnw clean package
./ch09/mvnw clean package
```

### Running on a Server
The projects generate WAR files in `target/` that can be deployed to a Jakarta-compatible servlet container (Tomcat 10.1+, Jetty 11+, etc.).

## Project Structure

### ch08 - Session & Authentication
- **Java Servlets**: `src/main/java/org/example/ch08/HelloServlet.java`
- **JSP Pages**: `src/main/webapp/`
  - `8-1.jsp`: Login/logout UI that checks userId cookie
  - `8-2.jsp`: Handles login form submission
  - `8-3.jsp`: Handles logout
  - `member/`: Contains member join and login forms

### ch09 - CRUD Operations
- **Java Servlets**: `src/main/java/org/example/ch09/HelloServlet.java`
- **JSP Pages**: `src/main/webapp/`
  - `list.jsp`: Displays records
  - `write.jsp`: Form for creating new records
  - `insert.jsp`: Handles insert operations
  - `view.jsp`: Displays single record details

## Key Dependencies
- **Jakarta Servlet API 6.1.0**: Modern Jakarta EE servlet support (replaces javax.servlet)
- **JUnit 5.13.2**: Testing framework

## Build Configuration
- **Java Version**: 17 (source and target)
- **Encoding**: UTF-8
- **WAR Plugin**: Maven 3.4.0 for building web archives
- Each module has Maven wrapper scripts for dependency-free builds

## Development Notes
- The web.xml files are minimal (Jakarta EE 6.0 defaults to annotation-based configuration)
- Servlets use `@WebServlet` annotation for mapping instead of web.xml configuration
- JSP pages use Korean language comments and labels (educational content for Korean students)