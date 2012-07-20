Future Market
=============

== Requirements

* Maven 3
* link:http://www.linux.ime.usp.br/~cadu/apache-tomcat-7.0.25.baile.tar.bz2[Custom Apache Tomcat]. Modifications:
** Added libs to reduce war file size
** Added user _tomcat_ with password _tomcat_ for manager app
* Environment variable +$CATALINA_HOME+ (e.g. +/home/user/apache-tomcat-7.0.25+)

== Eclipse IDE

.Maven workspace configuration
----
$ mvn -Declipse.workspace=/path/to/your/workspace eclipse:configure-workspace
----

.Projects configuration
----
$ cd webServices
$ mvn -Declipse.workspace=/path/to/your/workspace eclipse:configure-workspace eclipse:eclipse
----

Then you will be able to import the existing projects.

.Removing Eclipse configuration
----
$ cd webServices
$ mvn eclipse:clean
----

== Conventions
All web services have two endpoints as illustrated below:

* http://127.0.0.1:8080/registry/orchestration
* http://127.0.0.1:8080/registry/choreography

It doesn't matter if they are different or identical. The package names are:

br.usp.ime.futuremarket::
  Common code between orchestration and choreography.
br.usp.ime.futuremarket.orchestration::
  Specific orchestration code.
br.usp.ime.futuremarket.choreography::
  Specific choreography code.
br.usp.ime.futuremarket.tests.unit::
  Unit tests.
br.usp.ime.futuremarket.tests.functional::
  Functional tests. Require a deployed web service.
br.usp.ime.futuremarket.tests.integration::
  Tests that verify the communication between web services. Require deployed web services.

IMPORTANT: Tomcat will instantiate only one object of each class no matter how many concurrent connections there are. If both orchestration and choreography versions refer to the same class (e.g. current Registry implementation), they will share the same object.

== Building

=== Configuration templates

You must create configuration files in some projects. Use +src/main/resources/*.template+ as base.

.All projects
----
cd webServices
mvn install
----

.Single project
----
cd webServices
mvn -pl projectName -am install
----

The +-am+ option will build dependencies.

== Deploying and Undeploying

There are Makefiles in the +webServices+ dir (deploys all projects) and inside each deployable project dir (deal with a single project). See +make help+ for more information.

IMPORTANT: The current implementation does not persist data. If you redeploy the Registry service, for instance, all stored data will be lost!

== Testing

Unit tests can be run by +mvn test+ and the others, in Eclipse IDE. Except for unit tests, you must create propertie files based on +src/test/java/*.template+.

// vim: set syntax=asciidoc:
