# BÁO CÁO THỰC HÀNH LAB 02 – SPRING BOOT & MICROSERVICES
## XÂY DỰNG DATABASE VÀ RESTFUL API CRUD CHO 2 MICROSERVICES

- **Họ và tên:** Võ Thị Thúy An  
- **MSSV:** *(Điền MSSV)*  
- **STT:** *(Điền STT)*  
- **Học phần:** Lập trình Web 3 – Spring Boot & Microservices  
- **Tên đề tài:** QUẢN LÝ CHUỖI CỬA HÀNG TRÀ SỮA (ĐA CHI NHÁNH & TỰ ĐỘNG HÓA PHA CHẾ)  
- **Link GitHub Repository:** [https://github.com/anvo4359-cell/vothithuyan-microservices](https://github.com/anvo4359-cell/vothithuyan-microservices)

---

## CÂU A. LỰA CHỌN ĐỀ TÀI VÀ XÁC ĐỊNH CÁC MICROSERVICES

### A./ Xác định các chức năng của từng nhóm giao diện

#### 1. Admin – Trang quản trị (Dành cho Quản lý / Nhân viên chi nhánh)
| STT | Nhóm chức năng | Chức năng dự kiến |
| :---: | :--- | :--- |
| 1 | **Quản lý Chuỗi & Chi nhánh (Branch)** | Tạo mới/quản lý danh sách chi nhánh, quản lý thông tin cửa hàng, gán nhân sự và kho riêng cho từng chi nhánh. |
| 2 | **Quản lý Thực đơn & Định lượng (Menu & Recipe)** | Quản lý danh mục món, tùy chọn (size, đường, đá, topping), cấu hình công thức pha chế/định lượng chuẩn cho từng món (dùng để báo xuống bếp). |
| 3 | **Quản lý Nhân sự (Employee)** | Quản lý thông tin nhân viên, phân loại Full-time / Part-time, quản lý ca làm việc, chấm công và phân quyền theo chi nhánh. |
| 4 | **Quản lý Trang thiết bị (Equipment)** | Quản lý danh mục thiết bị (máy dập nắp, máy pha trà, tủ đông...), lịch bảo trì/bảo dưỡng, trạng thái hoạt động tại từng chi nhánh. |
| 5 | **Màn hình Bếp / Pha chế (Kitchen/Preparation)** | Hiển thị màn hình KDS (Kitchen Display System): Tự động nhận đơn hàng kèm công thức pha chế chi tiết (size, % đường, % đá, topping, định lượng) ngay khi đơn được tạo. |
| 6 | **Quản lý Đơn hàng & Vận chuyển (Order & Delivery)** | Xem danh sách đơn hàng online/offline, điều phối vận chuyển đơn online, theo dõi shipper, cập nhật trạng thái giao hàng. |

#### 2. Client – Trang khách hàng (Dành cho khách hàng đặt hàng Online)
| STT | Nhóm chức năng | Chức năng dự kiến |
| :---: | :--- | :--- |
| 1 | **Chọn chi nhánh & Thực đơn** | Định vị/chọn chi nhánh gần nhất, xem danh sách món ăn/đồ uống khả dụng tại chi nhánh đó. |
| 2 | **Tùy chỉnh món & Công thức cá nhân** | Chọn size, mức đường, mức đá, danh sách topping đi kèm cho từng ly. |
| 3 | **Giỏ hàng & Đặt hàng Online** | Thêm món, chọn địa chỉ giao hàng, áp mã giảm giá, xác nhận đơn hàng. |
| 4 | **Thanh toán Online** | Chọn phương thức thanh toán (COD, VNPAY, MoMo, Ví điện tử), xem kết quả thanh toán. |
| 5 | **Theo dõi Vận chuyển (Live Tracking)** | Theo dõi tiến độ đơn hàng: Đã tiếp nhận → Bếp đang pha chế theo công thức → Đang vận chuyển (Shipper) → Đã giao thành công. |

---

### B./ Xác định các quy trình nghiệp vụ
| STT | Quy trình nghiệp vụ | Đối tượng thực hiện | Service liên quan |
| :---: | :--- | :--- | :--- |
| 1 | Quản lý chi nhánh & Phân công nhân sự (Full/Parttime) | Admin / Quản lý | Branch, Employee Service |
| 2 | Quản lý thực đơn & Thiết lập công thức pha chế | Admin | Menu Service |
| 3 | Khách đặt đơn Online & Chọn chi nhánh xử lý | Client | Order, Branch, Menu Service |
| 4 | Tự động chuyển đơn & Công thức pha chế xuống Bếp | Hệ thống | Order, Kitchen/Recipe Service |
| 5 | Xử lý pha chế tại bếp & Cập nhật trạng thái hoàn thành | Nhân viên Bếp | Kitchen, Order Service |
| 6 | Phân công & Theo dõi vận chuyển đơn hàng Online | Admin / Shipper | Order, Delivery Service |
| 7 | Theo dõi & Quản lý bảo trì trang thiết bị chi nhánh | Admin / Kỹ thuật | Equipment, Branch Service |
| 8 | Quản lý ca làm việc & Chấm công nhân viên Parttime/Fulltime | Quản lý chi nhánh | Employee Service |

---

### C./ Xác định các Business Microservices sẽ xây dựng
| STT | Tên Service | Chức năng phụ trách | Service cần giao tiếp |
| :---: | :--- | :--- | :--- |
| 1 | **Menu & Recipe Service**<br>*(Service 1 - MySQL, Port 8081)* | Quản lý danh mục món, tùy chỉnh (size, đường, đá) và thiết lập công thức/định lượng pha chế chi tiết. | Order, Kitchen Service |
| 2 | **Order Service**<br>*(Service 2 - SQL Server, Port 8082)* | Quản lý giỏ hàng, tiếp nhận đơn online/offline, gắn đơn với chi nhánh xử lý và theo dõi trạng thái đơn. | Menu, Branch, Kitchen, Delivery Service |
| 3 | **Kitchen / Preparation Service** | Quản lý màn hình bếp tại từng chi nhánh, tiếp nhận đơn từ Order Service, hiển thị công thức pha chế tương ứng và thông báo hoàn thành. | Order, Menu Service |
| 4 | **Employee Service** | Quản lý hồ sơ nhân sự, phân loại Full-time / Part-time, chia ca làm việc và chấm công theo chi nhánh. | Branch Service |
| 5 | **Delivery Service** | Quản lý quy trình vận chuyển đơn hàng online, phân công đơn cho đơn vị/nhân viên giao hàng, theo dõi hành trình giao. | Order Service |
| 6 | **Equipment & Branch Service** | Quản lý thông tin các chi nhánh và danh mục/trạng thái/lịch bảo trì trang thiết bị của từng chi nhánh. | Branch, Employee Service |

---

## CÂU B. KHỞI TẠO CẤU TRÚC ĐỒ ÁN VÀ QUẢN LÝ GITHUB

### 1. Cấu trúc thư mục đồ án chuẩn
```text
vothithuyan-microservices/
│
├── backend/
│   ├── menu-service/         # Service 1: MySQL (Port 8081)
│   └── order-service/        # Service 2: SQL Server (Port 8082)
│
├── frontend/
│   ├── admin/                # Giao diện Quản trị viên & Bếp KDS
│   └── client/               # Giao diện Khách hàng đặt món
│
├── .gitignore               # Đã loại trừ: target/, .idea/, .vscode/, .env, *.log
├── README.md                # Giới thiệu tổng quan hệ thống
└── STT_VoThiThuyAn_MSSV.docx # File báo cáo Word hoàn chỉnh theo mẫu
```

### 2. Cấu hình Spring Boot Services
- Spring Boot version: `3.3.4`, Java `21`, công cụ Maven.
- Dependencies: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`, `spring-dotenv` (4.0.0), `lombok`, MySQL & SQL Server JDBC Drivers.
- Lệnh biên dịch: `mvn clean compile` → **BUILD SUCCESS**.

### 3. Lịch sử Commit & Push GitHub (Lần 1 - Câu B)
- **Commit:** `b34e909 Khoi tao do an Microservices`
- Đã liên kết và push lên GitHub branch `main`.

---

## CÂU C & D. DATABASE & RESTFUL API SERVICE 1 (MENU SERVICE - MYSQL)

### 1. Cấu hình CSDL MySQL
- **Database:** `menu_db`
- **Port:** `8081`
- **Cơ chế Hibernate/JPA:** `spring.jpa.hibernate.ddl-auto=update`
- **Bảo mật:** Mật khẩu lưu trữ qua `.env` (`MYSQL_PASSWORD`) và nạp tự động bằng `spring-dotenv`.

### 2. Danh sách thực thể (Entity) & Bảng CSDL
- `Category` (`categories`): Quản lý nhóm món (Trà sữa, Trà trái cây...).
- `Product` (`products`): Món trà sữa (Tên món, giá cơ sở `base_price` dạng `BigDecimal`, mô tả, ảnh, quan hệ `@ManyToOne` với `Category`).
- `RecipeItem` (`recipe_items`): Công thức định lượng nguyên liệu chuẩn (Cốt trà, bột béo, đường mía...) để tự động báo xuống màn hình Bếp pha chế.
- `Topping` (`toppings`): Danh sách topping gọi kèm (Trân châu hoàng kim, thạch củ năng, kem cheese...).

### 3. Danh sách RESTful API CRUD đã xây dựng
| Nhóm API | Method | Endpoint | Mô tả |
| :--- | :---: | :--- | :--- |
| **Categories** | `GET` | `/api/categories` | Lấy danh sách danh mục (hỗ trợ `?activeOnly=true`) |
| **Categories** | `GET` | `/api/categories/{id}` | Xem chi tiết danh mục theo ID |
| **Categories** | `POST` | `/api/categories` | Thêm mới danh mục món |
| **Categories** | `PUT` | `/api/categories/{id}` | Cập nhật thông tin danh mục |
| **Categories** | `DELETE` | `/api/categories/{id}` | Xóa danh mục |
| **Products** | `GET` | `/api/products` | Lấy danh sách món (hỗ trợ lọc `?categoryId=...` hoặc tìm kiếm `?search=...`) |
| **Products** | `GET` | `/api/products/{id}` | Xem thông tin chi tiết một món |
| **Products** | `POST` | `/api/products` | Thêm món mới vào menu |
| **Products** | `PUT` | `/api/products/{id}` | Cập nhật thông tin món |
| **Products** | `DELETE` | `/api/products/{id}` | Xóa món khỏi menu |
| **Recipes** | `GET` | `/api/recipes` | Xem công thức pha chế (hỗ trợ `?productId=...`) |
| **Recipes** | `GET` | `/api/recipes/{id}` | Xem chi tiết nguyên liệu theo ID |
| **Recipes** | `POST` | `/api/recipes` | Thêm nguyên liệu định lượng cho món |
| **Recipes** | `PUT` | `/api/recipes/{id}` | Cập nhật định lượng nguyên liệu |
| **Recipes** | `DELETE` | `/api/recipes/{id}` | Xóa nguyên liệu khỏi công thức |
| **Toppings** | `GET` | `/api/toppings` | Lấy danh sách topping (hỗ trợ `?inStockOnly=true`) |
| **Toppings** | `GET` | `/api/toppings/{id}` | Xem chi tiết topping theo ID |
| **Toppings** | `POST` | `/api/toppings` | Thêm topping mới |
| **Toppings** | `PUT` | `/api/toppings/{id}` | Cập nhật topping |
| **Toppings** | `DELETE` | `/api/toppings/{id}` | Xóa topping |

### 4. Lịch sử Commit & Push GitHub (Lần 2 - Câu D)
- **Commit:** `c5f1494 Hoan thanh Cau C va D: Xay dung Database, Entity, Repository, Service, Controller cho Service 1 (Menu Service)`

---

## CÂU E. DATABASE & RESTFUL API SERVICE 2 (ORDER SERVICE - SQL SERVER)

### 1. Cấu hình CSDL SQL Server
- **Database:** `order_db`
- **Port:** `8082`
- **Cơ chế Hibernate/JPA:** `spring.jpa.hibernate.ddl-auto=update`
- **Bảo mật:** Mật khẩu lưu trữ qua `.env` (`SQLSERVER_PASSWORD`).

### 2. Danh sách thực thể (Entity) & Bảng CSDL
- `Order` (`orders`): Đơn hàng khách hàng (Mã đơn `orderCode` tự động sinh dạng `ORD-yyyyMMddHHmmss-xxx`, tên khách, số điện thoại, chi nhánh xử lý, tổng tiền `BigDecimal`, trạng thái, phương thức thanh toán).
- `OrderItem` (`order_items`): Chi tiết từng ly trà sữa trong đơn (Món, size, % đường, % đá, topping kèm theo, số lượng, đơn giá, thành tiền `subtotal`).
- `Cart` (`carts`): Giỏ hàng online của khách hàng (gắn với `sessionId`).
- `CartItem` (`cart_items`): Chi tiết các ly trà sữa trong giỏ hàng.

### 3. Danh sách RESTful API CRUD đã xây dựng
| Nhóm API | Method | Endpoint | Mô tả |
| :--- | :---: | :--- | :--- |
| **Orders** | `GET` | `/api/orders` | Lấy toàn bộ đơn hàng (hỗ trợ lọc `?status=...`, `?branchId=...`, `?phone=...`) |
| **Orders** | `GET` | `/api/orders/{id}` | Xem chi tiết đơn hàng theo ID |
| **Orders** | `GET` | `/api/orders/code/{orderCode}` | Tra cứu nhanh đơn hàng theo mã đơn |
| **Orders** | `POST` | `/api/orders` | Tạo mới đơn hàng (tự động tính tổng tiền & subtotal) |
| **Orders** | `PUT` | `/api/orders/{id}` | Cập nhật thông tin đơn hàng |
| **Orders** | `PATCH` | `/api/orders/{id}/status` | Cập nhật trạng thái đơn (PENDING, PREPARING, DELIVERING, COMPLETED, CANCELLED) |
| **Orders** | `DELETE` | `/api/orders/{id}` | Xóa đơn hàng |
| **Carts** | `GET` | `/api/carts` | Xem danh sách giỏ hàng |
| **Carts** | `GET` | `/api/carts/session/{sessionId}` | Lấy hoặc khởi tạo giỏ hàng theo Session ID |
| **Carts** | `POST` | `/api/carts/session/{sessionId}/items` | Thêm món trà sữa (kèm tùy chỉnh) vào giỏ hàng |
| **Carts** | `DELETE` | `/api/carts/items/{itemId}` | Xóa một món khỏi giỏ hàng |
| **Carts** | `DELETE` | `/api/carts/session/{sessionId}/clear` | Làm rỗng giỏ hàng |

### 4. Lịch sử Commit & Push GitHub (Lần 3 - Câu E)
- **Commit:** `18042d8 Hoan thanh Cau E: Xay dung Database, Entity, Repository, Service, Controller cho Service 2 (Order Service - SQL Server)`

---

## KẾT LUẬN
- Đồ án đã hoàn thiện trọn vẹn 100% các yêu cầu từ Câu A đến Câu E theo đúng chuẩn kiến trúc Spring Boot Microservices.
- Hai dịch vụ hoạt động độc lập với 2 hệ quản trị CSDL khác biệt (MySQL và SQL Server).
- Cung cấp sẵn cơ chế nạp dữ liệu mẫu ban đầu (`DataInitializer`) và API CRUD đầy đủ, sẵn sàng cho việc kiểm thử và mở rộng các Service tiếp theo.
