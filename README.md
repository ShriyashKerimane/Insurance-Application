# Insurance-Application

Description:
 - This application serves the purpose creation and maintaining of insurance policy that allows user to manage insurance, policies and claims.
 - MySQL(MariaDB) is used to store Data of clients, policies and claims with relational mapping.
 - This Restfull application programming interface use request body and request parameter as input and provides json output

Structure:
 - This application follows 3 layer of structured pattern which involves controler layer, service layer and repository layer.
 - These 3 layers folow up in all the endpoint data computation
 - Controller layer for mapping endpoints, Service layer performing desired operation and repository layer for database related operations.

Features:
 - Every Service(operations) with respect to client, policy and claim have corresponding CRUD operations.
 - Entity and DTO separate Data from internal and external data flow.
 - Every exception is featured with error code and brief description displayed to UI.

Techstack:
 - JDK                -> version 11
 - maven              -> Build tool
 - Spring boot        -> to develop application
 - Spring data Jpa    -> Repository support
 - Junit,Mockito      -> Testing framework
 - MySQL(MariaDB)     -> Database

Starting application:
 -> Install MariaDB and create a database named java.
 
 -> Install java in local system, run jar file cli present in target folder. "java -jar insurance-management-1.0.jar"
    or
 -> Install docker in local system, create docker image and run the image.
    sample code - docker build . -t insurance
                - docker run -p 8080:8080 insurance
