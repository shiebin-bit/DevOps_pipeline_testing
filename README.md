# My Book Collection DevOps Demo

My Book Collection is a simple 3-tier CRUD web application used to demonstrate a DevOps-based CI/CD workflow.

- Frontend: Angular with Angular Material
- Backend: Spring Boot
- Database: MySQL
- Local deployment: Docker Compose
- CI/CD: GitHub Actions
- Container registry: GitHub Container Registry (GHCR)
- Security scan: Snyk

## Project Architecture

```text
Angular Frontend -> Spring Boot REST API -> MySQL Database
                                      |
                                  Adminer
```

The application allows users to create, view, update, and delete books. It is used as the demo application for showing automated build, automated testing, static analysis, security scanning, containerization, and deployment.

## DevOps Workflow

This project follows a GitHub Actions CI/CD workflow inspired by a DevOps pipeline model.

```text
Developer push to GitHub
-> GitHub Actions pipeline starts
-> Backend unit tests and quality checks
-> Frontend Karma/Jasmine tests and build
-> Snyk dependency security scan
-> Docker image build
-> Push images to GitHub Container Registry
-> Local deployment using Docker Compose
```

Kubernetes is not included in this demo. Docker Compose is used as the deployment environment to keep the classroom demo simple and reliable.

## Tools Used

| DevOps Stage | Tool |
|---|---|
| Source Code Management | GitHub |
| CI/CD Automation | GitHub Actions |
| Backend Build | Gradle |
| Backend Testing | JUnit 5, Mockito |
| Backend Static Analysis | Checkstyle, PMD, SpotBugs |
| Backend Coverage | JaCoCo |
| Frontend Build | Angular CLI, npm |
| Frontend Testing | Karma, Jasmine, ChromeHeadless |
| Security Scan | Snyk |
| Containerization | Docker |
| Image Registry | GitHub Container Registry |
| Local Deployment | Docker Compose |
| Database Management | Adminer |

## GitHub Actions Pipeline

The main workflow is located at:

```text
.github/workflows/ci.yml
```

The pipeline contains four jobs:

1. Backend test, static analysis, coverage, and build
2. Frontend Karma/Jasmine test and Angular build
3. Snyk dependency security scan
4. Docker image build and GHCR publish

The Snyk scan requires a GitHub repository secret named:

```text
SNYK_TOKEN
```

Important: do not write the real Snyk API key inside this README or inside `ci.yml`. Store it only in GitHub Secrets.

To add the token in GitHub:

```text
Repository Settings
-> Secrets and variables
-> Actions
-> New repository secret
-> Name: SNYK_TOKEN
-> Secret: your Snyk API key
```

## Local Run

Install Docker Desktop first, then run:

```sh
docker compose up --build
```

Open the application:

```text
Frontend: http://localhost:4200
Backend:  http://localhost:8080
Adminer:  http://localhost:8081
```

Adminer database login:

| Setting | Value |
|---|---|
| Database system | MySQL |
| Server | db |
| User | root |
| Password | springCRUD |
| Database | bookDatabase |

## Backend Commands

Run backend tests:

```sh
./SpringCRUD/gradlew -p ./SpringCRUD test
```

Run backend quality checks:

```sh
./SpringCRUD/gradlew -p ./SpringCRUD check
```

Build backend:

```sh
./SpringCRUD/gradlew -p ./SpringCRUD build
```

## Frontend Commands

Install dependencies:

```sh
npm ci --prefix ./AngularCRUD
```

Run Karma/Jasmine tests in CI mode:

```sh
npm test --prefix ./AngularCRUD -- --watch=false --browsers=ChromeHeadless
```

Build frontend:

```sh
npm run build --prefix ./AngularCRUD
```

## Docker Images

On push to the `main` branch, GitHub Actions builds and publishes:

```text
ghcr.io/<owner>/<repo>/spring-service:latest
ghcr.io/<owner>/<repo>/angular-service:latest
```

Each image is also tagged with the commit SHA.

## Demo Script

1. Show the GitHub repository and project structure.
2. Explain the 3-tier architecture: Angular, Spring Boot, MySQL.
3. Push a change to GitHub or open the Actions tab.
4. Show the GitHub Actions workflow jobs.
5. Show backend JUnit/Mockito test and quality check results.
6. Show frontend Karma/Jasmine test and Angular build result.
7. Show Snyk dependency scan step.
8. Show Docker image build and GHCR publish step.
9. Run the application using Docker Compose.
10. Open `http://localhost:4200` and demonstrate CRUD operations.

