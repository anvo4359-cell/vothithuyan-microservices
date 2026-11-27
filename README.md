# HỆ THỐNG QUẢN LÝ CHUỖI CỬA HÀNG TRÀ SỮA (ĐA CHI NHÁNH & TỰ ĐỘNG HÓA PHA CHẾ)

**Sinh viên thực hiện:** Võ Thị Thúy An  
**Mã số sinh viên:** 2124110097  
**STT:** 02  
**Học phần:** Lập trình Web 3 – Spring Boot & Microservices (Lab 02)

---

## 1. Cấu trúc thư mục đồ án
```text
VoThiThuyAn-Microservice/
│
├── backend/
│   ├── menu-service/         # Service 1: Quản lý Menu & Công thức pha chế (MySQL - Port 8081)
│   ├── order-service/        # Service 2: Quản lý Đặt hàng & Giỏ hàng (SQL Server - Port 8082)
│   ├── kitchen-service/      # Service 3: Quản lý Màn hình bếp KDS & Pha chế (MySQL - Port 8083)
│   ├── employee-service/     # Service 4: Quản lý Nhân sự, Ca làm việc & Chấm công (MySQL - Port 8084)
│   ├── delivery-service/     # Service 5: Quản lý Vận chuyển & Theo dõi Shipper (MySQL - Port 8085)
│   └── branch-service/       # Service 6: Quản lý Chi nhánh & Trang thiết bị (MySQL - Port 8086)
│
├── frontend/
│   ├── admin/                # Giao diện Quản trị viên & Bếp KDS
│   └── client/               # Giao diện Khách hàng đặt món Online
│
├── .gitignore
├── 2_VoThiThuyAn_2124110097.docx # Báo cáo thực hành Lab 02
└── README.md
```

## 2. Danh sách đầy đủ 6 Business Microservices đã hoàn thành
1. **Menu & Recipe Service** (Port `8081`, CSDL MySQL `menu_db`): Quản lý danh mục món, nguyên liệu, topping, size và thiết lập công thức/định lượng pha chế chuẩn.
2. **Order Service** (Port `8082`, CSDL SQL Server `order_db`): Quản lý giỏ hàng, nhận đơn đặt món online/offline, phân bổ đơn về chi nhánh và theo dõi trạng thái đơn hàng.
3. **Kitchen / Preparation Service** (Port `8083`, CSDL MySQL `kitchen_db`): Quản lý màn hình KDS cho quầy pha chế, nhận order tự động kèm công thức pha chế chi tiết và cập nhật trạng thái pha chế.
4. **Employee Service** (Port `8084`, CSDL MySQL `employee_db`): Quản lý hồ sơ nhân sự, phân loại Full-time / Part-time, phân ca làm việc, chấm công check-in/check-out.
5. **Delivery Service** (Port `8085`, CSDL MySQL `delivery_db`): Quản lý giao vận đơn online, gán shipper, ghi nhận log hành trình và trạng thái vận chuyển theo thời gian thực.
6. **Branch & Equipment Service** (Port `8086`, CSDL MySQL `branch_db`): Quản lý thông tin chuỗi chi nhánh và danh mục trang thiết bị, máy móc pha chế, lịch bảo trì bảo dưỡng định kỳ.
