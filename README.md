# TRM4J

## Description

We will make a copy of TRM with Java.
TRM has 2 central concepts: projects and time registrations.
Each project represents a certain amount of "purchased" hours of labour and has a unique identifier.
Time registrations represent a certain amount of hours of performed labour and the day on which they were performed.
A project can be associated with multiple time registrations, but the sum of the amount of hours performed must always be less than or equal to the amount of purchased hours (make sure this business rule holds as an invariant).

Start by implementing these entities.
Make sure that hours (both for projects and time registrations) are strictly positive.

Build a UI using web technology.
Create a page that shows an overview of the available projects (id, purchased hours and total amount of hours not performed yet).
Also provide a form that allows you to add a new project by specifying the amount of hours.

Create a second page that contains nothing but a form to add a time registration.
The form should allow you to select a project and enter a date and the amount of performed hours.
Verify that the amount of hours is a strictly positive number.
Make sure that the amount of total hours available to the project is not exceeded!

Use templating to ensure that both pages have a similar look-and-feel: a nav bar on top that allows you to switch between pages and a footer on the bottom with some contact info (your name, a link to the real realdolmen website, a link to a "contact us" email…).
(Bonus: use the inetum colors for extra flair).

Now add security into the mix.
There should be two types of users: consultants and managers.
A manager can manage (create, delete...) projects, a consultant can see projects but only create time registrations.
Adapt the time registration entity so that there is an association to the user that entered it (a string column with the consultant id is enough)
You may use in-memory users (bonus: create a user entity, ensure the password is not stored plain-text).

Now add a login page (form-based).
Provide a way for users to log out, too.
Make sure that both pages are protected.
Consultants and managers can view both pages, but only managers can create new projects (either hide the form for consultants + add a POST security constraint, or move the form to a separate page).

Extend the time registrations page so it lists existing time registrations (Bonus: make this look like a calendar).
Make sure that only registrations from the currently logged in user are shown.
Adding a time registration should automatically associate it to the current user.
Test this by creating (at least) 2 accounts and using the application as both (you can do this in 2 separate sessions or with 2 private browser windows in parallel).
Verify that you can not see anyone else's time registrations.
(Bonus: provide a project detail page only accessible to managers where all time registrations for that project are listed.)

As a final hurdle, protect yourself versus concurrency issues.
(If you do a double submit by clicking really fast, or just add a small delay (e.g., using Thread.sleep) in the service to add a time registration to a project, you might be able to enter time registrations totalling more hours than are available for a project.
This can only be prevented by proper concurrency management, either on the database (transaction isolation) or on the application (locking) level.

## MySQL

### Docker

[On docker hub](https://hub.docker.com/_/mysql/)

Command:

    docker run --name mysql4j -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_USER=acaddemicts -e MYSQL_PASSWORD=acaddemicts -e MYSQL_DATABASE=trm4j -p "3306:3306" mysql:8

### Maven

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.26</version>
    <scope>test</scope>
</dependency>
```

### JDBC information

https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-connect-drivermanager.html

Driver:

    com.mysql.cj.jdbc.Driver

Connection string:

    jdbc:mysql://localhost:3333/trm4j

### Glassfish setup

https://blog.payara.fish/using-mysql-with-payara

Remove all the default properties.
Also add `useSSL` with value `false` **and** `allowPublicKeyRetrieval` with value `true`!

### Alternative (embedded) setup

Add this to `web.xml`:

```xml
<data-source>
    <name>java:app/ds/trm4j</name>
    <class-name>com.mysql.cj.jdbc.Driver</class-name>
    <url>jdbc:mysql://localhost:3306/trm4j</url>
    <user>acaddemicts</user>
    <password>acaddemicts</password>
    <property>
        <name>useSSL</name>
        <value>false</value>
    </property>
    <property>
        <name>allowPublicKeyRetrieval</name>
        <value>true</value>
    </property>
</data-source>
```

### Possible GUIs

* https://dbeaver.io/download/