# NAB E-Commerce Doc
NAB Ecommerce Backend APIs

Back-end: https://github.com/DangQuangITus/Ecommerce-Rest-API

#### Download resource:
1. ```git clone https://github.com/DangQuangITus/Ecommerce-Rest-API.git```
2. ```cd Ecommerce-Rest-API/```
3. ```git checkout develop```

- This is a Maven Project. Ensure, Maven is installed on your system.
- It is Recommended that you use Linux Based OS.

### Database Design
https://dbdiagram.io/d/60a38f43b29a09603d155dc4

### Basic System Design
https://cloud.smartdraw.com/share.aspx/?pubDocShare=4496FAA86E66F0C49B16702315DD1438C5E

### How to run in local
1. Go to parent folder including pom.xml file
2. Run command line 
   ```mvn clean install -DskipTests && java -jar target/ecommerce-0.0.1-SNAPSHOT.jar --spring.config.location=./conf/application.yml```

### The MEAN Stack
- Knowledge in Core Java 
- Coding skills
- Java Development Kit (JDK)
- IntelliJ IDEA 
- H2 Database
- Spring Boot

### Project Hierarchy
![img.png](img.png)

#### config 
Configure Spring security to restrict access to protected resources.
Configure Role-based Authorization to protect resources on the server.

#### controller
REST API for admins and customers

#### dto 
Main business request entity

#### exception
Handle specials exception

#### models
Database entities

#### payload
authen dto, api request - response

#### security
Handle JWT token and Current User

### Curl for test API
#### Init data
> curl --location --request POST 'http://localhost:9001/api/admin/init' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzNTMiLCJpYXQiOjE2MjEzMDkwODYsImV4cCI6MTYyMTkxMzg4Nn0.5-G5yRTAoNWBRMlNkmVFd8OZcob4hHds7KPoUHmNWCY'

#### Register new user
> curl --location --request POST 'http://localhost:9001/api/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
"firstName": "Quangnd2",
"lastName": "123456",
"username": "Quangnd2",
"email": "Quangnd2@gmail.com",
"password": "123456",
"phoneNo": "0396424816" }'

#### Login for get token
> curl --location --request POST 'http://localhost:9001/api/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
"usernameOrEmail": "admin",
"password": "admin"
}'

#### API for admin add product
> curl --location --request POST 'http://localhost:9000/api/admin/product/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzNTMiLCJpYXQiOjE2MjEzMjI5NDMsImV4cCI6MTYyMTkyNzc0M30.PDO0cuHZk4horcgOV1d29GM8WdfqJRYKZFE817K6vmk' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Cam",
"price": "1500",
"description": "Cam ne",
"color": "orange",
"categoryId": "1",
"brandId": "1"
}'


#### API for admin update/delete product
> curl --location --request POST 'http://localhost:9000/api/admin/product/update/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxMzYwMTA2LCJleHAiOjE2MjE5NjQ5MDZ9.Od0gvDVQZTpQXnRhIcg7XvssM38sa3ZlXuCpJrVOv1I' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Cam",
"price": "1500",
"description": "Cam ne",
"color": "orange",
"categoryId": "1",
"brandId": "1"
}'

#### API for customer get all product
> curl --location --request GET 'http://localhost:9000/api/product' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxMzU5Mzg3LCJleHAiOjE2MjE5NjQxODd9.6at_38uvEHuTsQxgaY_Cx1fPAFKuC0vxyHLF0fqTnVY' \
--header 'Content-Type: application/json' \
--data-raw '{
"searchRequest": {
"page" : "0",
"size" : "10",
"sort" : "ASC",
"sortValue" : "id"
}
}'

#### API for customer search product
>curl --location --request GET 'http://localhost:9000/api/product/search' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxMzU5Mzg3LCJleHAiOjE2MjE5NjQxODd9.6at_38uvEHuTsQxgaY_Cx1fPAFKuC0vxyHLF0fqTnVY' \
--header 'Content-Type: application/json' \
--data-raw '{
"price" : 1500,
"name": "Cam",
"searchRequest": {
"page" : "0",
"size" : "10",
"sort" : "ASC",
"sortValue" : "id"
}
}'
#### API for customer order cart

>curl --location --request POST 'http://localhost:9000/api/order/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxMzU5Mzg3LCJleHAiOjE2MjE5NjQxODd9.6at_38uvEHuTsQxgaY_Cx1fPAFKuC0vxyHLF0fqTnVY' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName" : "Dang Quang",
    "email" : "DangQuang@gmail.com",
    "phone" : "0395423567",
    "address" : "123 Nguyen Trai"
}'

