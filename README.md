# camel-amq-poc


## Installation on OpenShift

_This assumes that the **Red Hat Streams for Apache Kafka** operator is already installed_

Go to the Administrator perspective

![img.png](images/img.png)

Select the Operators

![img_6.png](images/img_6.png)


Click Kafka
![img_7.png](images/img_7.png)

Deploy a Kafka cluster

Select _Current namespaces only_
![img_8.png](images/img_8.png)

Then "Create Kafka"
![img_9.png](images/img_9.png)

Keep all the settings as default and click "Create" on the bottom

This will take a couple minutes to properly initialize

![img_10.png](images/img_10.png)


Scroll to the right and click _Kafka Topic_ 

![img_11.png](images/img_11.png)


Click the _Create KafkaTopic_ button

![img_12.png](images/img_12.png)

Keep all settings as default and click _Create_ at bottom

Return to the _Developer_ perspective

![img_13.png](images/img_13.png)


Click _+Add_ button

![img_14.png](images/img_14.png)


Select _Import from Git_

![img_15.png](images/img_15.png)


Enter the URL: https://github.com/mike4263/camel-amq-poc.git
![img.png](images/gitUrl.png)

It should auto detect the Builder Image

_NOTE: OpenShift 4.13 and older may specify a builder image that does not work properly with quarkus. ([bug](https://github.com/jboss-container-images/openjdk/pull/358))_
![img_3.png](images/img_3.png)

Create a new _VistA_ application group
![img_16.png](images/img_16.png)


Enter Vista as the of the application group and _vista-app_ as the name of the resources:

![img_17.png](images/img_17.png)

Select "Deployment" as the Resource Type
![img_2.png](images/img_2.png)

Scroll down to the bottom.  Click _Deployment_ and enter the environment variables:

| Env Variable | Name |
|--------------|------|
| BOOTSTRAP_URL             | my-cluster-kafka-bootstrap:9092     |
| QUARKUS_PROFILE             | prod     |

Click _Create_

Click on the lower left circle to watch the build process
![img_5.png](images/img_5.png)

The build will take a couple minutes.  After the build is complete, click _Topology_ on the left to return to the topology overview.

Click the blue ring to pull up the side pane.

![img.png](images/img_18.png)

Click _View logs_

![img_1.png](images/img_19.png)

If everything worked properly, you should see the following in the logs

![img_20.png](images/img_20.png)




