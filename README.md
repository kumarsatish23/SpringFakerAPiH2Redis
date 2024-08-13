# Redis Installation and Spring Configuration Guide
____
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Fedora](https://img.shields.io/badge/Fedora-294172?style=for-the-badge&logo=fedora&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

## Overview

This guide walks you through the **installation of Redis on Fedora**, configuring Redis, and integrating it with a **Spring Boot** application for caching.

## Prerequisites

- **Operating System**: Fedora
- **Required Knowledge**: Basic understanding of Redis, Fedora, and the Spring Framework
- **Java Version**: 17 (as specified in the Maven [`pom.xml`](./pom.xml))

## Installation

### Installing Redis on Fedora

1. **Update your system:**

    ```bash
    sudo dnf update
    ```

2. **Install Redis:**

    ```bash
    sudo dnf install redis
    ```

3. **Start and enable the Redis service:**

    ```bash
    sudo systemctl start redis
    sudo systemctl enable redis
    ```

4. **Verify Redis is running:**

    ```bash
    sudo systemctl status redis
    ```

### Configuring Redis

1. **Locate the Redis configuration file:**

   - **Search for the Redis configuration file** using the `find` command:
      ```bash
      sudo find / -name "redis.conf"
      ```

      This will search your entire filesystem for any file named `redis.conf`.

   - **Check typical directories** if the above command doesn’t yield results. Redis configuration files are often located in:

       - `/etc/redis/redis.conf`
       - `/etc/redis.conf`
       - `/usr/local/etc/redis/redis.conf`
       - `/etc/redis/`


2. **Edit the `redis.conf` file:**

    ```bash
    sudo nano /etc/redis/redis.conf
    ```

3. **Modify the following settings:**

    - **Disable protected mode:**

      Find and change:

      ```plaintext
      protected-mode yes
      ```

      to:

      ```plaintext
      protected-mode no
      ```

    - **Bind to all network interfaces:**

      Find and change:

      ```plaintext
      bind 127.0.0.1 ::1
      ```

      to:

      ```plaintext
      bind 0.0.0.0
      ```

4. **Restart Redis to apply changes:**

    ```bash
    sudo systemctl restart redis
    ```

## Spring Boot Integration

### Maven Dependencies

Here’s a breakdown of key dependencies from the `pom.xml`:

- **[`spring-boot-starter-data-redis`](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis)**: Simplifies Redis data access in Spring applications.
- **[`spring-boot-starter-cache`](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-cache)**: Enables caching in your Spring Boot application.
- **[`spring-boot-starter-data-jpa`](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)**: Adds support for JPA to interact with databases.
- **[`spring-boot-starter-web`](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)**: Facilitates building web applications, including RESTful services.
- **[`spring-boot-devtools`](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)**: Offers development tools like automatic restarts.
- **[`h2`](https://mvnrepository.com/artifact/com.h2database/h2)**: In-memory database for development/testing.
- **[`lombok`](https://mvnrepository.com/artifact/org.projectlombok/lombok)**: Reduces boilerplate code in Java.
- **[`javafaker`](https://mvnrepository.com/artifact/com.github.javafaker/javafaker)**: Generates fake data for testing.
- **[`springdoc-openapi-starter-webmvc-ui`](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui)**: Integrates Springdoc OpenAPI for API documentation.
- **[`jackson-datatype-jsr310`](https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310)**: Adds support for Java 8 date/time types in Jackson.

### Code Configuration

1. **Spring Boot Application Class**

   Your main application class should be annotated with `@SpringBootApplication` and `@EnableCaching` to enable caching:

    ```java
    @SpringBootApplication
    @EnableCaching
    public class SpringFakerAPiSwaggerApplication {
        public static void main(String[] args) {
            SpringApplication.run(SpringFakerAPiSwaggerApplication.class, args);
        }
    }
    ```

2. **MovieService Class**

    - **`@CachePut`**: Updates the cache with the result of the method execution.
    - **`@Cacheable`**: Caches the result of the method if not already present.
    - **`@CacheEvict`**: Removes an entry from the cache.

   Example:

    ```java
    @Service
    public class MovieService {

        @Autowired
        private MovieRepository movieRepository;

        @CachePut(value = "movies", key = "#result.id")
        public Movie createMovie() {
            // Method implementation
        }

        @Cacheable(value = "movies")
        public List<Movie> getAllMovies() {
            // Method implementation
        }

        @Cacheable(value = "movies", key = "#id")
        public Optional<Movie> getMovieById(Long id) {
            // Method implementation
        }

        @CachePut(value = "movies", key = "#id")
        public Movie updateMovie(Long id, Movie movieDetails) {
            // Method implementation
        }

        @CacheEvict(value = "movies", key = "#id")
        public void deleteMovie(Long id) {
            // Method implementation
        }
    }
    ```

3. **Spring Boot Configuration**

   Add the following properties to `application.properties` or `application.yml` to configure Redis:

    - **`spring.cache.type=redis`**: Enables Redis as the cache provider.
    - **`spring.data.redis.host=localhost`**: Specifies the Redis server host.
    - **`spring.data.redis.port=6379`**: Specifies the Redis server port.
    - **`spring.cache.redis.time-to-live=6000`**: Sets the cache entry time-to-live in seconds.

   Example `application.properties`:

    ```properties
    spring.application.name=springFakerAPiSwagger
    spring.cache.type=redis
    spring.data.redis.host=172.26.18.1
    spring.data.redis.port=6379
    spring.cache.redis.time-to-live=6000
    ```

## Conclusion

You've successfully installed and configured Redis on Fedora and integrated it with your Spring Boot application for caching. Redis caching is now set up, enhancing your application's performance by optimizing data retrieval.

Feel free to tweak the configuration and code examples according to your specific needs.

---

If you need further assistance or customization, feel free to reach out!

---