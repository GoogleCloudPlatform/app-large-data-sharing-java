# app-large-data-sharing-java

## Google Cloud Stack
1. Cloud Run
2. Cloud Storage
3. Cloud Load Balancing
4. Firestore

## Dockerized a Spring Boot application
```bash
export LDS_IMAGE_NAME = <image name of the application, eg. gcr.io/my-project/my-application>
mvn compile com.google.cloud.tools:jib-maven-plugin:3.3.1:build
```

## Style Linting
```bash
mvn clean checkstyle:check
```

### REST Server Env.
```bash
LDS_BUCKET = <bucket name of Cloud Storage>
LDS_LB_URL = <url of the load balancer>
LDS_REST_PORT = <port of the application server>
```
