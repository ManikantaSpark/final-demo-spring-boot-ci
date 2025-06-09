# Store Shopping Application

A complete Spring Boot web application for an online store with product listing, shopping cart, and checkout functionality. This application is built according to the provided OpenAPI 3.0 specification.

## Features

### Products Module
- **Product Listing**: View all products with filtering options
- **Product Details**: Get detailed information about specific products
- **Category Filtering**: Filter products by category (Electronics, Home & Kitchen, Sports, Books)
- **Availability Filtering**: Filter products by availability status

### Shopping Cart Module
- **Add to Cart**: Add products to shopping cart with specified quantities
- **View Cart**: Display current cart contents with total price and item count
- **Update Quantity**: Modify quantities of items in the cart
- **Remove Items**: Remove individual items from the cart

### Checkout Module
- **Order Placement**: Submit orders with shipping address and payment method
- **Order Confirmation**: Retrieve order details by order ID
- **Order Status**: Track order status

## API Endpoints

### Products
- `GET /api/products` - Get filtered product list
  - Query Parameters: `category` (string), `available` (boolean)
- `GET /api/products/{id}` - Get product details by ID

### Shopping Cart
- `GET /api/cart` - Get current cart contents
- `POST /api/cart` - Add item to cart
- `PUT /api/cart/{itemId}` - Update item quantity
- `DELETE /api/cart/{itemId}` - Remove item from cart

### Orders
- `POST /api/checkout` - Submit order
- `GET /api/orders/{orderId}` - Get order confirmation

## Technology Stack

- **Backend**: Spring Boot 3.5.0
- **Database**: H2 In-Memory Database
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Java Version**: 21
- **Frontend**: HTML, CSS, JavaScript

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6+

### Running the Application

1. **Clone and navigate to the project directory**:
   ```powershell
   cd "c:\Users\manik\OneDrive\Desktop\Final-Demo-Spring-Project"
   ```

2. **Compile the project**:
   ```powershell
   mvn clean compile
   ```

3. **Run the application**:
   ```powershell
   mvn spring-boot:run
   ```

4. **Access the application**:
   - Web Interface: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (leave empty)

## Sample Data

The application automatically initializes with sample products including:
- **Electronics**: Laptop, Smartphone, Headphones, Wireless Mouse
- **Home & Kitchen**: Coffee Maker
- **Sports**: Running Shoes, Water Bottle
- **Books**: Java Programming Book

## Database Schema

### Products Table
- `id` (VARCHAR) - Primary Key
- `name` (VARCHAR) - Product name
- `description` (VARCHAR) - Product description
- `price` (DOUBLE) - Product price
- `available` (BOOLEAN) - Availability status
- `image` (VARCHAR) - Image URL
- `category` (VARCHAR) - Product category

### Cart Items Table
- `id` (VARCHAR) - Primary Key
- `product_id` (VARCHAR) - Foreign Key to Product
- `quantity` (INTEGER) - Item quantity

### Orders Table
- `order_id` (VARCHAR) - Primary Key
- `cart_items` (VARCHAR) - JSON representation of cart items
- `total_price` (DOUBLE) - Order total
- `item_count` (INTEGER) - Total items count
- `shipping_address` (VARCHAR) - Delivery address
- `payment_method` (VARCHAR) - Payment method
- `order_status` (VARCHAR) - Order status

## Configuration

### Application Properties
```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# H2 Console (for development)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
```

## Project Structure

```
src/
├── main/
│   ├── java/com/example/Final/Demo/Spring/Project/
│   │   ├── FinalDemoSpringProjectApplication.java
│   │   ├── config/
│   │   │   └── DataInitializer.java
│   │   ├── controller/
│   │   │   ├── CartController.java
│   │   │   ├── OrderController.java
│   │   │   └── ProductController.java
│   │   ├── dto/
│   │   │   ├── CartItemRequest.java
│   │   │   └── OrderRequest.java
│   │   ├── model/
│   │   │   ├── Cart.java
│   │   │   ├── CartItem.java
│   │   │   ├── Order.java
│   │   │   └── Product.java
│   │   ├── repository/
│   │   │   ├── CartItemRepository.java
│   │   │   ├── OrderRepository.java
│   │   │   └── ProductRepository.java
│   │   └── service/
│   │       ├── CartService.java
│   │       ├── OrderService.java
│   │       └── ProductService.java
│   └── resources/
│       ├── application.properties
│       └── static/
│           └── index.html
└── test/
```

## Usage Examples

### Using the Web Interface
1. Open http://localhost:8080 in your browser
2. Browse products and filter by category or availability
3. Add products to your cart by selecting quantity and clicking "Add to Cart"
4. View your cart to see total price and item count
5. Proceed to checkout by providing shipping address and payment method

### Using the REST API

#### Get All Products
```bash
curl http://localhost:8080/api/products
```

#### Get Products by Category
```bash
curl "http://localhost:8080/api/products?category=Electronics"
```

#### Add Item to Cart
```bash
curl -X POST http://localhost:8080/api/cart \
  -H "Content-Type: application/json" \
  -d '{"productId": "PRODUCT_ID", "quantity": 2}'
```

#### Checkout
```bash
curl -X POST http://localhost:8080/api/checkout \
  -H "Content-Type: application/json" \
  -d '{"shippingAddress": "123 Main St", "paymentMethod": "Credit Card"}'
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.
