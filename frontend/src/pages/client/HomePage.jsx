import React from "react";
import { Link } from "react-router-dom";

export default function HomePage() {
  return (
    <div style={{ textAlign: "center", padding: "40px 0" }}>
      <div style={{
        background: "linear-gradient(135deg, #fef3c7, #fed7aa)",
        borderRadius: "16px",
        padding: "48px 24px",
        marginBottom: "36px"
      }}>
        <h1 style={{ fontSize: "36px", color: "#78350f", margin: "0 0 16px 0" }}>
          Trà Sữa Thơm Ngon - Tươi Mát Mỗi Ngày
        </h1>
        <p style={{ fontSize: "18px", color: "#92400e", maxWidth: "600px", margin: "0 auto 24px auto" }}>
          Đặt món trực tuyến với hệ thống Microservices hiện đại, pha chế tự động theo công thức chuẩn cho từng ly trà sữa của bạn.
        </p>
        <Link to="/products" style={{
          background: "#b45309",
          color: "white",
          padding: "14px 28px",
          borderRadius: "30px",
          textDecoration: "none",
          fontSize: "18px",
          fontWeight: "bold",
          display: "inline-block",
          boxShadow: "0 4px 12px rgba(180, 83, 9, 0.3)"
        }}>
          Xem Menu & Đặt Hàng Ngay 🧋
        </Link>
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))", gap: "24px" }}>
        <div style={{ background: "white", padding: "24px", borderRadius: "12px", boxShadow: "0 2px 8px rgba(0,0,0,0.06)" }}>
          <div style={{ fontSize: "32px", marginBottom: "8px" }}>🍃</div>
          <h3 style={{ margin: "0 0 8px 0" }}>Trà Tươi Nguyên Bản</h3>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>Ủ lạnh và pha chế mỗi ngày từ lá trà Bảo Lộc thượng hạng.</p>
        </div>
        <div style={{ background: "white", padding: "24px", borderRadius: "12px", boxShadow: "0 2px 8px rgba(0,0,0,0.06)" }}>
          <div style={{ fontSize: "32px", marginBottom: "8px" }}>⚡</div>
          <h3 style={{ margin: "0 0 8px 0" }}>Pha Chế Chuẩn Công Thức</h3>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>Tự động hóa công thức và điều phối trực tiếp xuống quầy bếp KDS.</p>
        </div>
        <div style={{ background: "white", padding: "24px", borderRadius: "12px", boxShadow: "0 2px 8px rgba(0,0,0,0.06)" }}>
          <div style={{ fontSize: "32px", marginBottom: "8px" }}>🚀</div>
          <h3 style={{ margin: "0 0 8px 0" }}>Giao Hàng Siêu Tốc</h3>
          <p style={{ color: "#666", fontSize: "14px", margin: 0 }}>Kết nối hệ thống điều phối shipper giao tận nơi trong 30 phút.</p>
        </div>
      </div>
    </div>
  );
}
