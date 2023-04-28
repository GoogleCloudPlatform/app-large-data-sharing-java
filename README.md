# app-large-data-sharing-java

## Google Cloud Stack
1. Cloud Run
2. Cloud Storage
3. Cloud Load Balancing
4. Firestore

## Install dependencies
```bash
cd api/
mvn clean install
```

## Quick testing
```bash
# [Unit Test]
mvn test -Punit-test

# [Integration Test]
# Please set up your cloud infrastructure before running the integration test
# 1. Cloud Storage and Firestore database must be enabled first
# 2. Set up gcloud cli tools on your environment
# 3. Set up default credential with 'gcloud auth application-default login'
mvn test -Pintegration-test
```

## Style linting
```bash
mvn clean checkstyle:check
```

## Dockerize the application
```bash
export LDS_IMAGE_NAME = <image name of the application, eg. gcr.io/my-project/my-application>
mvn compile com.google.cloud.tools:jib-maven-plugin:3.3.1:build
```

## REST server variables
```bash
LDS_BUCKET = <bucket name of Cloud Storage>
LDS_LB_URL = <url of the load balancer>
LDS_REST_PORT = <port of the application server>
```


# Frontend

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 15.2.0.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

