# camel-amq-poc

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/camel-amq-poc-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Camel Core ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/core.html)): Camel core functionality and basic Camel languages: Constant, ExchangeProperty, Header, Ref, Simple and Tokenize
- Camel MLLP ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/mllp.html)): Communicate with external systems using the MLLP protocol
- Camel Kafka ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/kafka.html)): Sent and receive messages to/from an Apache Kafka broker
- Camel Netty ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/netty.html)): Socket level networking using TCP or UDP with Netty 4.x



## Deployment on OpenShift
1. Deploy Kafka on OCP
- Install AMQ Streams Operator


Operators > Installed Operators


- 
- Deploy a Kafka cluster
- 
  Goto Kafka > Create Kafka

- Deploy a Kafka topic

Goto Kafka > Create Kafka Topic

Create my-topic & test



2. Create S2I build / deployment

Developer Perspective
+Add
Import from Git



### workaround
```shell script
oc newapp registry.access.redhat.com/ubi8/openjdk-21~https://github.com/mike4263/camel-amq-poc vista
```
3. Edit Deployment with env variables

    a. Get Bootstrap URI
```shell script 
bash-4.4 ~ $ oc get svc my-cluster-kafka-bootstrap
NAME                         TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)                      AGE
my-cluster-kafka-bootstrap   ClusterIP   172.30.153.123   <none>        9091/TCP,9092/TCP,9093/TCP   85m
```
