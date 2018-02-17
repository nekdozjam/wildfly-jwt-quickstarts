# Wildfly JWT authentication quickstarts

The quickstarts demonstrate securing WildFly applications using Elytron subsystem and JWT authentication. They provide small examples, that can be used as a reference for your own project.

Prior to running the quickstarts you should read this entire document and have WildFly server set up.

* [Start and configure the WildFly Server](#wildfly-setup)

Use of WILDFLY_HOME Variable
-----------------------------------------

The quickstart README files use the replaceable value WILDFLY_HOME to denote the path to the WildFly installation. When you encounter this value in a README file, make sure you replace it with the actual path to your WildFly installation.


System requirements
-----------------------------------------
The applications these projects produce are designed to be run on WildFly Application Server 12 or later.


Run the Quickstarts
-----------------------------------------
The root folder of each individual quickstart contains a README file with specific details on how to build and run the example. In most cases you do the following:

<a id="wildfly-setup"></a>Start the WildFly Server
-----------------------------------------

1. Open a terminal and navigate to the root of the JBoss EAP server directory.
2. Use the following command to start the JBoss EAP server:
   ````
   For Linux:   WILDFLY_HOME/bin/standalone.sh
   For Windows: WILDFLY_HOME\bin\standalone.bat
   ````

Build and Deploy the Quickstarts
-----------------------------------------

See the README file in each individual quickstart folder for specific details and information on how to run and access the example.
###Build the Quickstart Archive

In most cases, you can use the following steps to build the application to test for compile errors or to view the contents of the archive. See the specific quickstart README file for complete details.

1. Open a terminal and navigate to the root directory of the quickstart you want to build.

2. Use the following command if you only want to build the archive, but not deploy it.

    
    $ mvn clean install

###Build and Deploy the Quickstart

This section describes the basic steps to build and deploy an application. See the specific instructions in each quickstart README file for any variations to this process.

1. Make sure you start the WildFly server as described in the quickstart README file.

2. Open a terminal and navigate to the root directory of the quickstart you want to run.

3. Use the following command to build and deploy the archive.

    
    $ mvn clean install wildfly:deploy

###Undeploy an Archive

Use the following command to undeploy the quickstart.

    $ mvn wildfly:undeploy

