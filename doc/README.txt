Future Market
=============

== Requirements

* Maven 3
* link:http://www.linux.ime.usp.br/~cadu/apache-tomcat-7.0.25.baile.tar.bz2[Custom Apache Tomcat]. Modifications:
** Added some libs to reduce war file size
** Added user _tomcat_ with password _tomcat_ for manager app
* Environment variable +$CATALINA_HOME+ (e.g. +/home/user/apache-tomcat-7.0.25+)

== Building

=== Use cases

Each use case contains different numbers of shippers, supermarkets and portals. The most test use case is the third one.

IMPORTANT: You must create some configuration files. Use +src/main/resources/*.template+ and +webServices/useCases/3/config.properties.template+ as examples before proceeding.

.Third use case setup
----
cd webServices/useCases
./deploy_test.sh n 3
----

.Building
----
cd webServices
make
----

== Deploying

----
cd webServices
make deploy-registry
# After the web service is available:
make deploy-others
----

IMPORTANT: The current implementation does not persist data. If you redeploy the Registry service, all the services will be unregistered.

== Testing

.Unit tests
----
cd webServices
mvn test
----

To run others types of tests, we recommend Eclipse IDE. The +tests+ project has acceptance tests and suites that includes all the tests across the different projects. Therefore, it is enough to run the tests of the +tests+ project.

== Developing

=== Eclipse IDE

.Maven workspace configuration
----
$ mvn -Declipse.workspace=/path/to/your/workspace eclipse:configure-workspace
----

.Projects configuration
----
$ cd webServices
$ mvn -Declipse.workspace=/path/to/your/workspace eclipse:eclipse
----

Then you will be able to import all the projects in +webServices+ dir.

.Removing Eclipse configuration
----
$ cd webServices
$ mvn eclipse:clean
----

=== Conventions
Whether orch or chor are different or not, all web services have two endpoints as illustrated below:

* http://127.0.0.1:8080/registry/orchestration
* http://127.0.0.1:8080/registry/choreography

The package names are:

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
br.usp.ime.futuremarket.tests.acceptance::
  Acceptance tests. Require all web services deployed.
br.usp.ime.futuremarket.tests.integration::
  Verify the communication between web services. Require deployed web services.

IMPORTANT: Tomcat will instantiate only one object of each class no matter how many concurrent connections there are. If both orchestration and choreography versions refer to the same class (e.g. current Registry implementation), they will share the same object.

// vim: set syntax=asciidoc:
