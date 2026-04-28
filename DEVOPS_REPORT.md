# DevOps-Based CI/CD Methodology Report

## Title

Adapting a DevOps-Based CI/CD Methodology for Software Quality Improvement in a Web Application

## 1. Introduction

This report presents the implementation of a DevOps-based CI/CD methodology using an existing web application as the testing subject. The selected application is a three-tier CRUD system consisting of an Angular frontend, a Spring Boot backend, and a MySQL database. The application itself is not the main contribution of this project. Instead, it is used as a practical demo system to test and demonstrate DevOps practices.

The focus of this project is on improving the software delivery process through automation. The DevOps workflow includes source code management, automated build, automated testing, static code analysis, dependency security scanning, containerization, container image publishing, and local deployment.

This implementation adapts the DevOps concepts from the selected research article into a more accessible environment using GitHub, GitHub Actions, Docker, GitHub Container Registry, Snyk, and Docker Compose. Kubernetes is not included in this demo because the purpose of the demonstration is to show a clear and reliable CI/CD workflow rather than a complex production deployment environment.

## 2. Project Scope

The scope of this project is limited to the DevOps implementation and demonstration workflow.

The existing Angular and Spring Boot application is used only as a sample system for testing the DevOps pipeline. Therefore, this report does not focus on explaining the internal frontend components, backend services, or business logic of the application.

The project focuses on:

- Creating an automated GitHub Actions CI/CD pipeline
- Running backend automated tests using JUnit and Mockito
- Running frontend automated tests using Karma and Jasmine
- Running backend static analysis using Checkstyle, PMD, and SpotBugs
- Generating code coverage using JaCoCo
- Scanning dependencies using Snyk
- Building Docker images for frontend and backend
- Publishing Docker images to GitHub Container Registry
- Running the complete application locally using Docker Compose

## 3. DevOps Methodology Adaptation

The selected article proposes a DevOps-based software engineering model that includes source code management, static code analysis, automated build, automated testing, automated deployment, and continuous monitoring. In this project, the same general methodology is adapted using different tools.

| DevOps Practice | Original Concept | Adapted Tool in This Project |
|---|---|---|
| Source Code Management | Centralized source repository | GitHub |
| CI/CD Automation | Automated pipeline | GitHub Actions |
| Automated Build | Build application artifacts | Gradle, npm, Angular CLI |
| Automated Testing | Unit and integration tests | JUnit, Mockito, Karma, Jasmine |
| Static Code Analysis | Code quality checking | Checkstyle, PMD, SpotBugs |
| Security Checking | Vulnerability detection | Snyk |
| Containerization | Docker image creation | Docker |
| Artifact Registry | Container image storage | GitHub Container Registry |
| Deployment | Cloud/container deployment | Docker Compose local deployment |

This adaptation keeps the core DevOps idea from the article but replaces Azure-based services with GitHub-based and Docker-based tools. This makes the implementation easier to demonstrate in a classroom environment.

## 4. Tools Used

### 4.1 GitHub

GitHub is used for source code management. It stores the project files, tracks code changes, and acts as the trigger point for the CI/CD pipeline. When a developer pushes code to the main branch or opens a pull request, GitHub Actions automatically starts the workflow.

### 4.2 GitHub Actions

GitHub Actions is used as the CI/CD automation tool. The workflow file is located at:

```text
.github/workflows/ci.yml
```

The pipeline is divided into four main jobs:

1. Backend test, quality check, and build
2. Frontend Karma/Jasmine test and build
3. Snyk dependency security scan
4. Docker build and GitHub Container Registry publish

### 4.3 Gradle

Gradle is used to build and test the Spring Boot backend. The backend build has been upgraded to use Gradle 8.5 and Spring Boot 2.7.18 so that it can run correctly in the current Java environment while still targeting Java 17.

### 4.4 JUnit and Mockito

JUnit 5 is used as the backend testing framework. Mockito is used to create mock objects for testing backend logic without depending on real external services. These tools support automated backend testing in the CI pipeline.

### 4.5 Checkstyle, PMD, SpotBugs, and JaCoCo

Checkstyle, PMD, and SpotBugs are used for backend static code analysis. They help detect coding standard issues, possible defects, bad practices, and maintainability problems.

JaCoCo is used to generate backend code coverage reports. This helps show how much of the backend code is covered by automated tests.

### 4.6 npm, Angular CLI, Karma, and Jasmine

npm and Angular CLI are used to install dependencies, run tests, and build the Angular frontend.

Karma is used as the frontend test runner, while Jasmine is used as the frontend testing framework. In the CI pipeline, the frontend tests run using ChromeHeadless so that the tests can execute automatically without opening a visible browser.

### 4.7 Snyk

Snyk is used to scan backend and frontend dependencies for known security vulnerabilities. The Snyk API key is stored securely as a GitHub Actions secret named:

```text
SNYK_TOKEN
```

The real API key must not be committed into the repository or written directly inside the workflow file.

### 4.8 Docker and GitHub Container Registry

Docker is used to package the frontend and backend into container images. After the CI checks pass, GitHub Actions builds Docker images and pushes them to GitHub Container Registry.

The image names follow this structure:

```text
ghcr.io/<owner>/<repo>/spring-service:latest
ghcr.io/<owner>/<repo>/angular-service:latest
```

Each image is also tagged using the Git commit SHA for traceability.

### 4.9 Docker Compose

Docker Compose is used for local deployment. It starts the complete system, including the frontend, backend, MySQL database, and Adminer database management tool.

Kubernetes is excluded from this implementation to keep the demo focused, simple, and easier to reproduce.

## 5. CI/CD Pipeline Flow

The CI/CD workflow begins when code is pushed to GitHub.

```text
Developer pushes code
-> GitHub Actions is triggered
-> Backend tests are executed
-> Backend static analysis and coverage checks are executed
-> Backend artifact is built
-> Frontend dependencies are installed
-> Frontend Karma/Jasmine tests are executed
-> Frontend production build is created
-> Snyk scans backend and frontend dependencies
-> Docker images are built
-> Docker images are pushed to GitHub Container Registry
```

This flow demonstrates the DevOps principle of continuous integration because every code change is automatically validated by build, test, and quality checks.

It also supports continuous delivery because container images are automatically created and published after successful validation. These images can then be used for deployment.

## 6. CI Workflow Code Explanation

The CI/CD workflow is defined in `.github/workflows/ci.yml`. This file tells GitHub Actions when to run the pipeline, what environment to prepare, and what commands to execute.

### 6.1 Workflow Trigger

```yaml
on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main
```

This section means the workflow will run when code is pushed to the `main` branch or when a pull request targets the `main` branch. This supports continuous integration because every new code change is automatically validated.

### 6.2 Workflow Permissions

```yaml
permissions:
  contents: read
  packages: write
  checks: write
  pull-requests: write
```

These permissions allow GitHub Actions to read the repository, publish test check results, and push Docker images to GitHub Container Registry. The `packages: write` permission is required for publishing container images to GHCR.

### 6.3 Environment Variables

```yaml
env:
  JAVA_VERSION: '17'
  NODE_VERSION: '16'
  SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}
```

This section defines common values used across the workflow. Java 17 is used for the Spring Boot backend, and Node.js 16 is used for the Angular frontend. The Snyk token is loaded from GitHub Secrets so that the real API key is not exposed in the repository.

### 6.4 Backend Job

```yaml
backend:
  name: Backend test, quality check, and build
  runs-on: ubuntu-latest
```

The backend job runs on an Ubuntu GitHub Actions runner. It is responsible for validating the Spring Boot backend.

```yaml
- name: Set up JDK
  uses: actions/setup-java@v4
  with:
    distribution: temurin
    java-version: ${{ env.JAVA_VERSION }}
    cache: gradle
```

This step installs Java 17 and enables Gradle caching. Caching helps speed up repeated workflow runs by reusing downloaded Gradle dependencies.

```yaml
- name: Run backend unit tests
  run: ./SpringCRUD/gradlew -p ./SpringCRUD test
```

This command runs backend automated tests using JUnit and Mockito.

```yaml
- name: Run backend static analysis and coverage checks
  run: ./SpringCRUD/gradlew -p ./SpringCRUD check
```

This command runs backend quality checks, including Checkstyle, PMD, SpotBugs, and JaCoCo coverage verification.

```yaml
- name: Build backend artifact
  run: ./SpringCRUD/gradlew -p ./SpringCRUD build
```

This command builds the Spring Boot backend artifact after tests and quality checks are completed.

### 6.5 Frontend Job

```yaml
frontend:
  name: Frontend Karma test and build
  runs-on: ubuntu-latest
```

The frontend job validates the Angular frontend.

```yaml
- name: Set up Node.js
  uses: actions/setup-node@v4
  with:
    node-version: ${{ env.NODE_VERSION }}
    cache: npm
    cache-dependency-path: AngularCRUD/package-lock.json
```

This step installs Node.js 16 and enables npm dependency caching.

```yaml
- name: Install frontend dependencies
  working-directory: AngularCRUD
  run: npm ci
```

This command installs frontend dependencies based on `package-lock.json`. `npm ci` is used because it gives a clean and reproducible installation in CI.

```yaml
- name: Run frontend Karma tests
  working-directory: AngularCRUD
  run: npm test -- --watch=false --browsers=ChromeHeadless
```

This command runs frontend automated tests. Angular CLI starts Karma as the test runner, Jasmine runs the test cases, and ChromeHeadless allows tests to run automatically without a visible browser.

```yaml
- name: Build frontend
  working-directory: AngularCRUD
  run: npm run build
```

This command builds the Angular frontend and checks that the frontend can be compiled successfully.

### 6.6 Snyk Security Job

```yaml
security:
  name: Snyk dependency scan
  needs:
    - backend
    - frontend
```

The security job runs after the backend and frontend jobs. This ensures that dependency scanning is performed after the main test and build validation.

```yaml
- name: Skip Snyk scan when token is not configured
  if: env.SNYK_TOKEN == ''
  run: echo "SNYK_TOKEN is not configured..."
```

This step prevents the workflow from failing if the Snyk token has not been added yet. For the actual demo, the token should be added as a GitHub repository secret.

```yaml
- name: Scan backend dependencies with Snyk
  if: env.SNYK_TOKEN != ''
  continue-on-error: true
  working-directory: SpringCRUD
  run: snyk test --severity-threshold=high
```

This command scans backend dependencies and reports high-severity vulnerabilities. `continue-on-error: true` is used so that the scan result can be shown during the demo without stopping the whole pipeline unexpectedly.

```yaml
- name: Scan frontend dependencies with Snyk
  if: env.SNYK_TOKEN != ''
  continue-on-error: true
  working-directory: AngularCRUD
  run: snyk test --severity-threshold=high
```

This command scans frontend dependencies for known vulnerabilities.

### 6.7 Docker and GHCR Job

```yaml
docker:
  name: Docker build and GHCR publish
  needs:
    - backend
    - frontend
  if: github.event_name == 'push'
```

The Docker job runs only after the backend and frontend jobs pass. It only runs on push events, not pull requests, because publishing container images should happen only after code is pushed to the repository.

```yaml
- name: Prepare lowercase GHCR image names
  run: |
    echo "IMAGE_PREFIX=ghcr.io/${GITHUB_REPOSITORY,,}" >> "$GITHUB_ENV"
```

This step prepares the image name for GitHub Container Registry. GHCR image names must be lowercase, so the repository name is converted to lowercase.

```yaml
- name: Log in to GitHub Container Registry
  uses: docker/login-action@v3
  with:
    registry: ghcr.io
    username: ${{ github.actor }}
    password: ${{ secrets.GITHUB_TOKEN }}
```

This step logs in to GHCR using the built-in GitHub token.

```yaml
- name: Build backend Docker image
  run: |
    docker build \
      --tag "$IMAGE_PREFIX/spring-service:latest" \
      --tag "$IMAGE_PREFIX/spring-service:${{ github.sha }}" \
      ./SpringCRUD
```

This command builds the backend Docker image. It creates two tags: `latest` and the commit SHA. The SHA tag improves traceability because it links the image to a specific code version.

```yaml
- name: Build frontend Docker image
  run: |
    docker build \
      --tag "$IMAGE_PREFIX/angular-service:latest" \
      --tag "$IMAGE_PREFIX/angular-service:${{ github.sha }}" \
      ./AngularCRUD
```

This command builds the frontend Docker image using the Angular Dockerfile.

```yaml
- name: Push backend Docker image
  run: |
    docker push "$IMAGE_PREFIX/spring-service:latest"
    docker push "$IMAGE_PREFIX/spring-service:${{ github.sha }}"
```

This command pushes the backend image to GitHub Container Registry.

```yaml
- name: Push frontend Docker image
  run: |
    docker push "$IMAGE_PREFIX/angular-service:latest"
    docker push "$IMAGE_PREFIX/angular-service:${{ github.sha }}"
```

This command pushes the frontend image to GitHub Container Registry.

## 7. Automated Testing

Automated testing is a key part of this DevOps workflow because it helps detect defects early.

### 7.1 Backend Testing

The backend test command is:

```sh
./SpringCRUD/gradlew -p ./SpringCRUD test
```

This command runs the Spring Boot backend test suite using JUnit and Mockito. The test results are published in GitHub Actions so that the team can inspect failures directly from the pipeline page.

### 7.2 Frontend Testing

The frontend test command is:

```sh
npm test --prefix ./AngularCRUD -- --watch=false --browsers=ChromeHeadless
```

This command runs Angular tests in CI mode. Karma starts the browser test environment, Jasmine runs the test cases, and ChromeHeadless allows the tests to run automatically in GitHub Actions.

## 8. Static Analysis and Security Scan

Static analysis is performed on the backend using:

- Checkstyle
- PMD
- SpotBugs

The command used is:

```sh
./SpringCRUD/gradlew -p ./SpringCRUD check
```

This command runs automated quality checks and JaCoCo coverage verification.

Security scanning is performed using Snyk. Snyk checks project dependencies and reports known vulnerabilities. In this demo, Snyk is configured as a reporting step so that vulnerability results can be shown during the demonstration without blocking the entire pipeline unexpectedly.

## 9. Containerization and Deployment

After testing and quality checks are completed, Docker is used to build container images for the backend and frontend.

The backend and frontend images are pushed to GitHub Container Registry. This provides a centralized place to store build artifacts and makes the images traceable by commit SHA.

For deployment, Docker Compose is used locally:

```sh
docker compose up --build
```

The application can then be accessed through:

```text
Frontend: http://localhost:4200
Backend:  http://localhost:8080
Adminer:  http://localhost:8081
```

This deployment method is suitable for a classroom demo because it is easier to set up than Kubernetes while still showing container-based deployment.

## 10. Expected Demo Output

During the demo, the following evidence can be shown:

1. GitHub repository with source code
2. GitHub Actions workflow file
3. Successful backend test job
4. Successful backend static analysis and build job
5. Successful frontend Karma/Jasmine test and build job
6. Snyk security scan output
7. Docker image build logs
8. Published images in GitHub Container Registry
9. Running application using Docker Compose
10. CRUD operation demonstration in the browser

## 11. Benefits of the DevOps Implementation

This DevOps workflow improves software quality in several ways.

First, automation reduces manual work. Developers do not need to manually run every test and build step before checking whether the system is valid.

Second, defects can be detected earlier. If a code change breaks backend tests, frontend tests, or static analysis rules, the GitHub Actions pipeline reports the failure immediately.

Third, security risks can be discovered earlier through dependency scanning. Snyk helps identify vulnerable libraries before the application is deployed.

Fourth, Docker improves deployment consistency. The application can run in containers, reducing environment mismatch between development and deployment.

Finally, GitHub Container Registry improves traceability. Each Docker image can be connected to a specific commit, making it easier to understand which version of the system is being tested or deployed.

## 12. Limitations

This demo does not include Kubernetes, autoscaling, cloud infrastructure provisioning, or advanced production monitoring. These features are outside the current scope because the goal is to demonstrate a clear CI/CD workflow using accessible tools.

The Snyk scan may report vulnerabilities because the demo application uses older dependencies. In this implementation, Snyk is used to show security visibility rather than to fully enforce a strict production security gate.

The application itself is an existing demo system and is not the main development contribution of this project.

## 13. Conclusion

This project demonstrates how DevOps practices can be applied to an existing web application using GitHub Actions, automated testing, static analysis, Snyk, Docker, GitHub Container Registry, and Docker Compose.

Although the application code is used mainly as a test subject, the project successfully shows how a manual software delivery process can be improved through automation. The CI/CD pipeline validates code changes, checks software quality, scans dependencies, builds Docker images, and prepares the system for container-based deployment.

The implementation proves that DevOps practices can be adapted from a cloud-based research model into a practical and reproducible classroom demo environment.
