
## Docker setup
### Build Docker images:
#### Setup account DockerHub:
 - Open folder .m2 with path  `C:\Users\<username>\.m2`
 - Open 'settings.xml'. Check dockerhub account set or not. If not, insert this code
    ```
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.xsd">
        <servers>
            <server>
                <id>registry.hub.docker.com</id>
                <username>Enter Username of DockerHub</username>
                <password>Enter Password of DockerHub</password>
            </server>
        </servers>
        <pluginGroups xmlns="http://maven.apache.org/SETTINGS/1.1.0">
            <pluginGroup>org.apache.maven.plugins</pluginGroup>
            <pluginGroup>org.codehaus.mojo</pluginGroup>
        </pluginGroups>
    </settings>
    ```
 - Make sure the account in settings.xml match with the repository of jib-maven-plugin in project pom.xml.
#### Run maven command for build and push to dockerhub:

 - `mvn clean install` 
 - `mvn jib:build -pl '!common,!clients'`
### Docker Compose:
#### Download docker images:

- Run `docker compose up -d` to download all image.

#### Setup grafana:

- login with account (admin-password) in "localhost:3000"
- Connect DataSource:
    + Click Data Sources in Homepage
    + Choose Prometheus.
    + Enter for Name -> "Prometheus Microservices" and Connection -> "http://prometheus:9090".
    + Save and Test.
- Create Dashboard.
    + Navigate to "Home/Dashboard" and click "Create Dashboard"
    + Click on "Import dashboard"
    + Import dashboard config file -- ".\jobnet-ipj\jobnet-server\grafana\Grafana_Dashboard.json"
    + Select Prometheus DataSource - "Prometheus Microservices"
    + Click Import.