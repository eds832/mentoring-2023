**Authentication and Authorization Task**

Necessary Tools

Java Development Kit 11+

Apache Maven 3.6.0+

Git 2.24+

Spring 5

Spring Boot 2

##Task

(1 star)

1. Create Spring Boot MVC project.

2. Create REST endpoint "GET /info" that provide random stats (ex: "MVC application"). Test this endpoint.

3. Add Spring Security module to your project and configure it for authenticated access to all resources. Use email/password combination for it.

(2 starts)

4. Use a non-embedded DB to store users.

5. Use salt and hashing to store user passwords.

6. Create additional REST endpoint "GET /about" and configure non-authenticated access to it.

(3-4 starts)

7. Create one more REST endpoint "GET /admin".

8. Now you need to add authorised access to "GET /info" and "GET /admin", add "VIEW_INFO", "VIEW_ADMIN" permissions for it. Create 3 users with different combination of permissions.

9. Create new Login/Logout pages and configure Spring Security to use new Login/Logout.

10. Add Brute Force protector. BLock user email for 5 minute on 3 unsuccessful login.

11. Create an endpoint to show blocked users

(5 start)

12. Implement a new Spring Boot MVC application called "Secret providers". Application should provide page with text form. After sending a secret, application must generate uniq link address, to provide one-time access to secret information. After this information must be removed from application.

13. User (sender and recipient) must be authorized and have "STANDARD" permission.

14. Use docker containers to implement solution.

Create pull requests to your mentor with implemented application (-s).

**When practical task is implemented and ready for verification please upload an archive (jar, zip) with resources to the Result field and move your assignment to "Needs review" status.**


**Secret providers Task**

( 12. - 14.): are done here

Application requires Docker installed

Run the following scripts in the root path:

* cd docker
* docker system prune -a
* y
* docker volume prune
* y
* run.bat

App runs on http://localhost:8081/

Use credentials:

* user1@epam.com / user1Password -> has access to the Info and the Secret page (http://localhost:8081/secret)
* user2@epam.com / user2Password -> has access to the Info and the Secret page (http://localhost:8081/secret)
* admin@epam.com / adminPassword -> has access to the Admin page
* full@epam.com / fullPassword -> has access to the Info, the Secret page and the Admin page

Using the Secret Page (http://localhost:8081/secret), user1@epam.com and user2@epam.com with "STANDARD" authority can create and view one-time secret messages that can be accessed through their links.
