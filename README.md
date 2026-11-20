# HỆ THỐNG QUẢN LÝ CHUỖI CỬA HÀNG TRÀ SỮA (ĐA CHI NHÁNH & TỰ ĐỘNG HÓA PHA CHẾ)

**Sinh viên thực hiện:** Võ Thị Thúy An  
**Học phần:** Lập trình Web 3 – Spring Boot & Microservices (Lab 02)

---

## 1. Cấu trúc thư mục dự án
```text
vothithuyan-microservices/
│
├── backend/
│   ├── menu-service/         # Service 1: Quản lý Menu & Công thức pha chế (MySQL - Port 8081)
│   ├── order-service/        # Service 2: Quản lý Đặt hàng & Giỏ hàng (SQL Server - Port 8082)
│   └── ... (các Service bổ sung: Kitchen, Employee, Delivery, Equipment)
│
├── frontend/
│   ├── admin/                # Giao diện Quản trị viên & Bếp
│   └── client/               # Giao diện Khách hàng đặt món Online
│
├── .gitignore
└── README.md
```

## 2. Danh sách Microservices
1. **Menu & Recipe Service** (Port `8081`, CSDL MySQL): Quản lý danh mục món, nguyên liệu, topping, size và thiết lập công thức/định lượng pha chế chuẩn.
2. **Order Service** (Port `8082`, CSDL SQL Server): Quản lý giỏ hàng, nhận đơn đặt món, phân bổ đơn về chi nhánh và theo dõi trạng thái đơn hàng.
3. **Kitchen / Preparation Service**: Hiển thị màn hình KDS cho quầy pha chế, nhận order và công thức chuẩn để pha chế.
4. **Employee Service**: Quản lý ca làm việc, nhân viên part-time/full-time, chấm công.
5. **Delivery Service**: Điều phối giao hàng cho đơn Online, theo dõi tiến trình shipper.
6. **Equipment & Branch Service**: Quản lý chi nhánh cửa hàng và thiết bị máy móc bảo trì.
