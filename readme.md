# NAB E-Commerce REST API Doc.
NAB Ecommerce Backend APIs

Back-end: https://github.com/DangQuangITus/Ecommerce-Rest-API

#### Download resource:
> <b>git clone</b> https://github.com/DangQuangITus/Ecommerce-Rest-API.git.

> <b>cd</b> ```Ecommerce-Rest-API/```

<p>This is a Maven Project. Ensure, Maven is installed on your system.</p>
<p>It is Recommended that you use Linux Based OS.</p>


### Database Design
> https://dbdiagram.io/d/60a38f43b29a09603d155dc4

### Basic Analysis & System Design
> https://cloud.smartdraw.com/share.aspx/?pubDocShare=4496FAA86E66F0C49B16702315DD1438C5E

### How to run in local
<p>1. Go to parent folder including pom.xml file </p>
<p>2. Run command line </p> 

> mvn clean install -DskipTests && java -jar target/ecommerce-0.0.1-SNAPSHOT.jar --spring.config.location=./conf/application.yml

```Note: H2 Database: ```
<p>username: <b>admin</b> </p>
<p>password: </p>

> http://localhost:9000/h2/ 


### The MEAN Stack
- Knowledge in Core Java 
- Coding skills
- Java Development Kit (JDK)
- IntelliJ IDEA 
- H2 Database
- Spring Boot

### Project Hierarchy
![img_1.png](img_1.png)

#### config 
Configure Spring security to restrict access to protected resources.
Configure Role-based Authorization to protect resources on the server.

#### controller
REST API for admins and customers

#### exception
Handle specials exception

#### models
Database entities

#### payload
Authen dto, api request - response

#### security
Handle JWT token and Current User

### Example: Curl for test API
#### Init data
<p>Insert example data, admin user account, product, category...</p>

> curl --location --request POST 'http://localhost:9001/api/admin/init'
#### Register new user
<p>Signup a new account</p>

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
<p>Login by admin user</p>

> curl --location --request POST 'http://localhost:9001/api/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
"usernameOrEmail": "admin",
"password": "admin"
}'

#### API for admin add product
<p>API for admin to add new product </p>

> curl --location --request POST 'http://localhost:9001/api/admin/product/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxNDQ1MDQyLCJleHAiOjE2MjIwNDk4NDJ9.9PkZ_hh-eGFOlqFyuFNK-KA-RlnHuxikNmzwjTu7QPI' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Cam",
"price": 1500,
"description": "Cam ne",
"stock": 1000,
"color": "orange",
"categoryId": "1",
"brandId": "1"
}'


#### API for admin update/delete product
> curl --location --request POST 'http://localhost:9001/api/admin/product/update/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxNDQ1MDQyLCJleHAiOjE2MjIwNDk4NDJ9.9PkZ_hh-eGFOlqFyuFNK-KA-RlnHuxikNmzwjTu7QPI' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Cam",
"price": 1500,
"description": "Cam ne",
"stock": 1000,
"color": "orange",
"categoryId": "1",
"brandId": "1"
}'

#### API for customer get all product
> curl --location --request GET 'http://localhost:9001/api/product' \
--header 'Content-Type: application/json' \
--data-raw '{
"metadataRequest": {
"page" : "0",
"size" : "10",
"sort" : "ASC",
"sortValue" : "id"
}
}'

#### API for customer search product
>curl --location --request GET 'http://localhost:9001/api/product/search' \
--header 'Content-Type: application/json' \
--data-raw '{
"price" : 1500,
"name": "Cam",
"metadataRequest": {
"page" : "0",
"size" : "10",
"sort" : "ASC",
"sortValue" : "id"
}
}'
#### API for customer order cart

> curl --location --request POST 'http://localhost:9001/api/order/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName" : "Dang Quang",
    "email" : "DangQuang@gmail.com",
    "phone" : "0395423567",
    "address" : "123 Nguyen Trai",
    "orderItemsDtos": [
        {
            "price": 1200,
            "quantity": 10,
            "productId": 1
        },
         {
            "price": 1400,
            "quantity": 10,
            "productId": 1
        }
    ]
}'
