# example-jaxrs-jwt: JAX-RS secured using JSON Web Tokens (JWTs)
Level: Beginner
Technologies: JavaEE
Summary: JWT protected JAX-RS Service with simple token issuer

What is it?
-------------------
This quickstart demonstrates how to secure JAX-RS service with JWTs using WildFly Elytron subsystem.

There are 4 resource endpoints + 1 for generating JWTs:
* `/rest/public` - requires no authentication
* `/rest/customer` - can be accessed by users with `customer` role
* `/rest/admin` - can be accessed by users with `admin` role
* `/rest/claims` - can be accessed by any authenticated user and demonstrates how token claims can be extracted


* `/rest/token` - POST endpoint for generating tokens from provided credentials

System Requirements
-------------------
You need to have WildFly Application Server 12 running.

ll you need to build this project is Java 8.0 (Java SDK 1.8) or later and Maven 3.1.1 or later.

Configure the Server
------------------------------------
Do not forget to backup your `WILDFLY_HOME/configuration/standalone.xml` configuration file! You will need it to restore
the server to the original state.

Before you deploy the quickstart you need to configure Elytron subsystem. For your convenience, this
quickstart batches the commands into a `configure-elytron.cli` script provided in the root directory of this quickstart.
You can configure the server by running following command from the root directory of this quickstart:

    $ WILDFLY_HOME/bin/jboss_cli.sh -c --file=configure-elytron.cli

Note: For Windows, use the `WILDFLY_HOME\bin\jbocc_cli.bat` script

Note: The script contains placeholder PEM public key to make the deployment easy. DO NOT use the same key for anything
but testing purposes! You want to generate your own key pair.

Generate a keypair for token signing / validating
--------------------------------------------------
Elytron uses RS256 (SHA256withRSA), RS384 (SHA384withRSA) and RS512 (SHA512withRSA) asymmetric keys for signing JWTs.

The keys must be in PKCS#8 format.

You can generate your own RS256 keypair by running `generateKeys.sh` script.

Note: The private key implementation in `JwtRestClient.java` is for demonstration purposes only. Never leave your private
keys in plaintext and use a keystore or other protected store instead!

Build and Deploy the Quickstart
--------------------------------
1. Open a terminal and navigate to the root directory of this quickstart.
2. The following shows the command to deploy the quickstart:
    ````
    $ mvn install wildfly:deploy
    ````
    
Run the client app
------------------
Before you run the client, make sure you have already successfully deployed the REST application to the server and that
your terminal is still in the same folder.

Type the following command to execute the client.

    $ mvn exec:java


Undeploy the Quickstart
--------------------------------
After you are done working with the quickstart you can run the following command from the root directory of this quickstart
to undeploy it from the running server.

    $ mvn wildfly:undeploy