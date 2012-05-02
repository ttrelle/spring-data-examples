# Spring Data Blog Series

This projects holds the Java source examples for my blog post series on the Spring Data project:

* [Part 6: Spring Data Redis](http://blog.codecentric.de/en/2012/04/spring-data-redis/)
* [Part 5: Spring Data Neo4j](http://blog.codecentric.de/en/2012/02/spring-data-neo4j/)
* [Part 4: Geospatial Queries with Spring Data MongoDB](http://blog.codecentric.de/en/2012/02/spring-data-mongodb-geospatial-queries/)
* [Part 3: Spring Data MongoDB](http://blog.codecentric.de/en/2012/02/spring-data-mongodb/)
* [Part 2: Spring Data JPA](http://blog.codecentric.de/en/2012/01/spring-data-jpa/)
* [Part 1: Spring Data Commons](http://blog.codecentric.de/en/2011/12/spring-data-commons/)

## Usage

The projects are using a Maven based build. To run the tests on the command line use

	mvn clean test
   
If you want to use an IDE like Eclipse run

	mvn eclipse:eclipse
   
and set the Eclipse variable M2_REPO pointing to your local Maven repository. Or just import the project as an existing Maven project if you are using the m2 plugin.
 
