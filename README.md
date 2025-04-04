# This README file contains only the error log and instructions on how to resolve the issue.

## Cause -1
The tests failed in the GitHub Actions CI pipeline because 
the Spring Boot test context attempted to connect to a real PostgreSQL
database (localhost:5432), which doesn't exist in the CI environment. 
This caused a Connection refused error during application context loading.

### Current Test Configuration Used:
``` 
@ExtendWith(MockitoExtension.class)
```
✅ Using Mockito's JUnit 5 extension to write unit tests.

✅ This approach is independent of Spring’s full application context.

✅ However,  test class might still be annotated with @SpringBootTest, 
or some part of  test setup is triggering Spring context loading, 
which is not compatible with CI without a database.

## Solutions to Fix It
### Step 1: Remove Full Context Loading

✅ Avoid using @SpringBootTest if you're only testing services with mocks.

✅ Stick to @ExtendWith(MockitoExtension.class) and pure unit testing.

### Step 2: Create a src/test/resources/application-test.yml with H2 config
✅ Create a src/test/resources/application-test.yml with H2 config:
```
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.H2Dialect
```
Then in your test class:
```
@ActiveProfiles("test")
@SpringBootTest

```
### Step 3:  Use @DataJpaTest or @WebMvcTest When Appropriate
✅ Use @DataJpaTest for JPA-layer unit testing (with H2).

✅ Use @WebMvcTest for controller-layer testing without service/database dependencies.
****
