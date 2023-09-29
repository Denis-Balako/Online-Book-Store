# Online BookStore API

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](http://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/built-with-grammas-recipe.svg)](http://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](http://forthebadge.com)

A ready-to-use **BookStore API** for online shops.

## Table of Contents

- [👨‍💻Tech stack](#technologies-and-tools)
- [⚡Quick start](#quick-start)
- [‍✈️Controllers](#controllers)
- [🧑‍🚀Postman Collection](#postman-collection)
- [🏁Conclusion](#conclusion)
- [📝License](#license)

## 👨‍💻Tech stack

Here's a brief high-level overview of the tech stack the **BookStore API** uses:

- [Spring Boot](https://spring.io/projects/spring-boot): provides a set of pre-built templates and conventions for creating stand-alone, production-grade Spring-based applications.
- [Hibernate](https://hibernate.org/): simplifies the interaction between Java applications and databases by mapping Java objects to database tables and vice versa.
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html): provides features like authentication, authorization, and protection against common security threats.
- [Spring Web](https://spring.io/projects/spring-ws#overview): includes tools for handling HTTP requests, managing sessions, and processing web-related tasks.
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/): provides a higher-level abstraction for working with databases and includes support for JPA (Java Persistence API).
- [Lombok](https://projectlombok.org/): helps reduce boilerplate code by automatically generating common code constructs (like getters, setters, constructors, etc.) during compile time.
- [Mapstruct](https://mapstruct.org/): generates mapping code based on annotations, reducing the need for manual, error-prone mapping code.
- [Liquibase](https://www.liquibase.org/): helps manage database schema changes over time, making it easier to track and deploy database updates.
- [Swagger](https://swagger.io/): provides a framework for generating interactive API documentation, allowing developers to understand, test, and use APIs more easily.
- [Docker](https://www.docker.com/): provides a consistent and reproducible way to deploy applications across different environments.
- [Postman](https://www.postman.com/): allows developers to create and send HTTP requests to test APIs, monitor responses, and automate testing workflows.

## ⚡️ Quick start

First, download a [repository][repo_url]. 
You can use git console command:

```bash
git clone https://github.com/Denis-Balako/Online-Book-Store.git
```

Build a project using **Maven**:
```bash
mvn clean install
```
Then, rise a **Docker** container of your app:
```bash
docker build -t {your-image-name}
docker-compose build
docker-compose up
```
That's all you need to know to start! 🎉

## ‍✈️Controllers

- [AuthenticationController](auth_controller): handles user registration and authorization.
- [BookController](book_controller): manages book operations, such as creation, updating, deleting and search.
- [CartController](cart_controller): manages shopping cart state, allows users to add, update, retrieve and delete cart items.
- [CategoryController](category_controller): manages categories, allows admins to create, update, retrieve and delete.
- [OrderController](order_controller): handles order management, creating, updating, deleting and retrieving order history.

## 🧑‍🚀Postman Collection

For easy test, I've created a Postman [collection](postman_collection) and [environment](postman_environment), that includes all user and admin requests.

## 🏁Conclusion

The **Bookstore API** offers a robust framework for constructing a platform dedicated to selling books. Whether you're initiating a fresh online bookstore or enhancing an already established one, this API enables you to efficiently handle books, orders, and customer information.

## 📝License

This project is licensed under the MIT license. Feel free to edit and distribute this template as you like.


<!-- Repository -->
[repo_url]: https://github.com/Denis-Balako/Online-Book-Store.git
[auth_controller]: https://github.com/Denis-Balako/Online-Book-Store/blob/master/src/main/java/com/balako/onlinebookstore/controller/AuthenticationController.java
[book_controller]: https://github.com/Denis-Balako/Online-Book-Store/blob/master/src/main/java/com/balako/onlinebookstore/controller/BookController.java
[cart_controller]: https://github.com/Denis-Balako/Online-Book-Store/blob/master/src/main/java/com/balako/onlinebookstore/controller/CartController.java
[category_controller]: https://github.com/Denis-Balako/Online-Book-Store/blob/master/src/main/java/com/balako/onlinebookstore/controller/CategoryController.java
[order_controller]: https://github.com/Denis-Balako/Online-Book-Store/blob/master/src/main/java/com/balako/onlinebookstore/controller/OrderController.java
[postman_collection]: https://github.com/Denis-Balako/Online-Book-Store/blob/master/Book%20Store%20API.postman_collection.json
[postman_environment]: https://github.com/Denis-Balako/Online-Book-Store/blob/master/Book%20store%20API.postman_environment.json