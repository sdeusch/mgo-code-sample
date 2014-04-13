sun-code-sample
===============
<pre>

1. Summary
This application is a coding exercise demonstrating a REST web services in JSON using MongoDB. 
The functionality is following four endpoints:
* endpoint that authenticates a user based on a login/password passed in a JSON payload and
  verifies against a simple data structure (Mongo, MySQL, etc.)
* endpoint that returns all users in the database filtered by a URL parameter (gender, etc) 
  and groups them by (your choice) 
* endpoint that checks and returns the status of all components that it depends on (e.g. Is Mongo still up OK, etc.)
* endpoint that when calls returns the list of files in a given directory.

2. Requirements
* Java 6+
* Maven 3+
* git
* mongoDB, see http://docs.mongodb.org/manual/installation/
* curl 

3. Deployment
Check out the code from my public github repository:
git clone https://github.com/sdeusch/mgo-code-sample.git
cd mgo-code-sample

3.1 Build It
mvn clean install 

3.2 Run It
First start mongoDB on localhost with standard settings (in separate shell): mongod 
Then startup the web service using Spring Boot:
mvn spring-boot:run


4. Demonstration Walk Through

4.1 Initialization
On startup the mongoDB database is seeded with 4 users, see at http://localhost:8080/users
For instance there is a user of username 'Alice' with password 'secret'. 
See if we can log in with this information.

4.2. Authentication Endpoint 
The authentication endpoint is at http://localhost:8080/login 
You need to POST raw JSON with an HTTP header of 'Content-Type=application/json' to this URL, e.g. 
{"username":"Alice","password":"secret"}
should render "Welcome Alice!" on success, or "Access Denied - Status 403" on failure.
Tools like SoapUI or the Google Chrome plugin 'PostMan' can be used. 
If you have curl installed, you can even use this command line to test the happy case:

curl -X POST -H "Content-Type: application/json" -d '{"username":"Alice","password":"secret"}' \
     http://localhost:8080/login


4.3 Finding Users 
This is best shown by example (these URLs are case sensitive)
http://localhost:8080/users?lastName=Smith  ... shows all users with lastName='Smith'.
http://localhost:8080/users?lastName=Smith&start=0&n=1 ... with Pagination: starts at record 0, shows 1 element
http://localhost:8080/users?gender=Male     ... shows all male users
http://localhost:8080/users                 ... shows all users.
http://localhost:8080/users?start=0&n=3     ... with Pagination: starts at record 0, shows 3 


4.4 Healthcheck Endpoint 
This endpoint shows the status of each externally connected dependency individually as well as
the aggregate status of the service.
http://localhost:8080/health
The healthcheck page returns 'ALL-OK' if everything is alright. The 'ALL-OK' signal can be grepped by monitoring jobs.

4.5 File listing Endpoint 
This endpoint lists everything in a given directory as specified by the request parameter dir. For example  
http://localhost:8080/v1/list?dir=/Users
lists everything in the /Users/ directory on my MacBook. The returned JSON document in my case looks like this:
{"dir":"/Users","files":[".localized","captain","deuscs01","Shared"]}


5. Technology Discussion
The technology stack used tries to minimize the amount of code as much as possible in Java. 
The technologies used are 
* Spring core
* Spring Boot
* Spring Data
* Spring MVC
* Junit 4
* Mockito
* MongoDB 
Spring and its child frameworks provide a lot of convenience of the box. Spring Boot has a Tomcat server built in 
and allows deployment without copying a war file to a servlet engine. This speeds up deployment and simplifies tremendously. 
Spring Data has rich, typed data repository classes for MongoDB. Very little code is needed. 
Spring MVC REST has built in (un)/marshalling from JSON payloads to POJOs.
MongoDB is a document noSQL database which runs in its own process. It is very fast and a natural fit for documents in JSON format. 

5.1 Web service endpoint versioning
The File Listing endpoint, http://localhost:8080/v1/list?dir=/Users, has a /v1/ component in the URL for version 1. 
This is set at the controller level. It could be injected from a system wide runtime parameter for all controllers. 


</pre>














