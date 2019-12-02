# WSO2 Class Mediator & External JNDI Datasource

A sample `WSO2 Class Mediator` implementation on defining and accessing an external JNDI Datasource.

## How To

### Define JNDI Datasource

Define the external JNDI datasource in `master-datasource.xml` which is placed inside the `<APIM>/repository/conf/datasource/` directory. You can use the following sample JNDI Datasource configuration

```xml
<datasource>
    <name>WSO2_EXTERNAL_DB</name>
    <description>The datasource used for registry and user manager</description>
    <jndiConfig>
        <name>jdbc/WSO2ExternalDB</name>
    </jndiConfig>
    <definition type="RDBMS">
        <configuration>
            <url>jdbc:mysql://localhost:3306/apimgt?useSSL=false</url>
            <username>root</username>
            <password>root</password>
            <driverClassName>com.mysql.jdbc.Driver</driverClassName>
            <maxActive>80</maxActive>
            <maxWait>60000</maxWait>
            <minIdle>5</minIdle>
            <testOnBorrow>true</testOnBorrow>
            <validationQuery>SELECT 1</validationQuery>
            <validationInterval>30000</validationInterval>
        </configuration>
    </definition>
</datasource>
```

### Build Project

Clone or download and the class mediator implementation and make the related chanages to the Mediation flow and build the project.

> NOTE: Don't forget to change the Datasource lookup name in the constructor

Use the following command to build the project

```sh
mvn clean package
```

After a successful build, copy the JAR artifact fromt the `/target` folder and place it inside the `<APIM>/repository/components/lib` directory.

### Design In-Sequence

Design an `in-sequence` flow to invoke the class mediator implementation to connect and communicate with the defined external datasource. Given below is a sample `in-sequence` ...

```xml
<sequence xmlns="http://ws.apache.org/ns/synapse" name="dasample">
    <log level="custom">
        <property name="TRACE" value="API Mediation Extension" />
    </log>

    <!-- defining the class mediator -->
    <class name="com.sample.mediator.ClassMediatorJNDIDatasource" />

    <!-- sample get-property -->
    <log level="custom">
        <property name="Database version from class mediator : " value="get-property('DatabaseResult')"/>
    </log>
</sequence>
```
