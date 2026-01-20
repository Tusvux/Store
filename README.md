# API Backend Bán Hàng - Spring Boot

Dự án API Backend cho hệ thống bán hàng sử dụng Spring Boot và JSON.

## 📋 Tổng Quan

Dự án bao gồm 4 API chính:

### 1. **API Quản Lý Sản Phẩm** (`/api/products`)
- GET `/api/products` - Lấy tất cả sản phẩm
- GET `/api/products/{id}` - Lấy sản phẩm theo ID
- POST `/api/products` - Tạo sản phẩm mới
- PUT `/api/products/{id}` - Cập nhật sản phẩm
- DELETE `/api/products/{id}` - Xóa sản phẩm
- GET `/api/products/search?name={name}` - Tìm kiếm sản phẩm
- GET `/api/products/category/{category}` - Lọc theo danh mục

### 2. **API Quản Lý Khách Hàng** (`/api/customers`)
- GET `/api/customers` - Lấy tất cả khách hàng
- GET `/api/customers/{id}` - Lấy khách hàng theo ID
- POST `/api/customers` - Đăng ký khách hàng mới
- PUT `/api/customers/{id}` - Cập nhật thông tin khách hàng
- DELETE `/api/customers/{id}` - Xóa khách hàng
- GET `/api/customers/email/{email}` - Tìm khách hàng theo email

### 3. **API Quản Lý Đơn Hàng** (`/api/orders`)
- GET `/api/orders` - Lấy tất cả đơn hàng
- GET `/api/orders/{id}` - Lấy đơn hàng theo ID
- POST `/api/orders` - Tạo đơn hàng mới
- PATCH `/api/orders/{id}/status?status={status}` - Cập nhật trạng thái
- GET `/api/orders/customer/{customerId}` - Lấy đơn hàng theo khách hàng
- GET `/api/orders/status/{status}` - Lọc đơn hàng theo trạng thái
- DELETE `/api/orders/{id}` - Hủy đơn hàng

### 4. **API Quản Lý Giỏ Hàng** (`/api/cart`)
- GET `/api/cart/customer/{customerId}` - Xem giỏ hàng
- POST `/api/cart/customer/{customerId}/items` - Thêm sản phẩm vào giỏ
- PUT `/api/cart/customer/{customerId}/items/{productId}?quantity={quantity}` - Cập nhật số lượng
- DELETE `/api/cart/customer/{customerId}/items/{productId}` - Xóa sản phẩm khỏi giỏ
- DELETE `/api/cart/customer/{customerId}` - Xóa toàn bộ giỏ hàng
- GET `/api/cart/customer/{customerId}/total` - Tính tổng giá trị

## 🚀 Cài Đặt và Chạy

### Yêu Cầu
- Java 17 hoặc cao hơn
- Maven 3.6+

### Chạy Ứng Dụng

```bash
# Build project
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

### Truy Cập H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:salesdb`
- Username: `sa`
- Password: (để trống)

## 📝 Ví Dụ Request

### 1. Tạo Sản Phẩm
```json
POST /api/products
Content-Type: application/json

{
  "name": "Laptop Dell XPS 13",
  "description": "Laptop cao cấp, màn hình 13 inch",
  "price": 25000000.0,
  "stock": 50,
  "category": "Electronics",
  "imageUrl": "https://example.com/laptop.jpg"
}
```

### 2. Đăng Ký Khách Hàng
```json
POST /api/customers
Content-Type: application/json

{
  "fullName": "Nguyễn Văn A",
  "email": "nguyenvana@example.com",
  "phone": "0901234567",
  "address": "123 Đường ABC",
  "city": "Hà Nội",
  "country": "Việt Nam"
}
```

### 3. Tạo Đơn Hàng
```json
POST /api/orders
Content-Type: application/json

{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ],
  "shippingAddress": "123 Đường ABC, Hà Nội",
  "paymentMethod": "COD"
}
```

### 4. Thêm Sản Phẩm Vào Giỏ Hàng
```json
POST /api/cart/customer/1/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

## 🗄️ Cấu Trúc Database

### Products (Sản phẩm)
- id, name, description, price, stock, category, imageUrl

### Customers (Khách hàng)
- id, fullName, email, phone, address, city, country

### Orders (Đơn hàng)
- id, customerId, totalAmount, status, orderDate, shippingAddress, paymentMethod

### OrderItems (Chi tiết đơn hàng)
- id, orderId, productId, quantity, price

### Carts (Giỏ hàng)
- id, customerId

### CartItems (Sản phẩm trong giỏ)
- id, cartId, productId, quantity

## 📦 Công Nghệ Sử Dụng

- **Spring Boot 3.2.1** - Framework chính
- **Spring Data JPA** - ORM và database access
- **H2 Database** - In-memory database
- **Lombok** - Giảm boilerplate code
- **Maven** - Build tool

## 🔧 Tùy Chỉnh

Bạn có thể thay đổi cấu hình trong file `application.properties`:
- Thay đổi port: `server.port=8080`
- Cấu hình database khác thay vì H2
- Thêm validation, security, logging,...

## 📄 License

Dự án mẫu để học tập và phát triển.
