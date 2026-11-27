import React from "react";
import { useLocation, Link } from "react-router-dom";

export default function OrderSuccessPage() {
  const location = useLocation();
  const order = location.state?.order;

  return (
    <div style={{ maxWidth: "600px", margin: "40px auto", textAlign: "center", background: "white", padding: "36px", borderRadius: "16px", boxShadow: "0 4px 16px rgba(0,0,0,0.08)" }}>
      <div style={{ fontSize: "60px", marginBottom: "12px" }}>🎉</div>
      <h2 style={{ color: "#2e7d32", margin: "0 0 12px 0" }}>Đặt Hàng Thành Công!</h2>
      <p style={{ color: "#666", marginBottom: "24px" }}>
        Đơn hàng của bạn đã được ghi nhận trên hệ thống Microservices và tự động điều phối xuống Bếp pha chế.
      </p>

      {order && (
        <div style={{ background: "#f9fafb", padding: "20px", borderRadius: "12px", textAlign: "left", marginBottom: "24px", border: "1px solid #eee" }}>
          <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "8px" }}>
            <span style={{ color: "#666" }}>Mã đơn hàng:</span>
            <strong style={{ color: "#b45309" }}>{order.orderCode}</strong>
          </div>
          <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "8px" }}>
            <span style={{ color: "#666" }}>Khách hàng:</span>
            <strong>{order.customerName} ({order.customerPhone})</strong>
          </div>
          <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "8px" }}>
            <span style={{ color: "#666" }}>Trạng thái:</span>
            <span style={{ background: "#dcfce7", color: "#166534", padding: "2px 8px", borderRadius: "4px", fontSize: "12px", fontWeight: "bold" }}>
              {order.status}
            </span>
          </div>
          <div style={{ display: "flex", justifyContent: "space-between", marginTop: "12px", paddingTop: "12px", borderTop: "1px dashed #ddd" }}>
            <span style={{ fontSize: "16px", fontWeight: "bold" }}>Tổng tiền thanh toán:</span>
            <strong style={{ fontSize: "18px", color: "#d9534f" }}>
              {Number(order.totalAmount).toLocaleString("vi-VN")} đ
            </strong>
          </div>
        </div>
      )}

      <Link
        to="/products"
        style={{
          background: "#6b4423",
          color: "white",
          padding: "12px 24px",
          borderRadius: "8px",
          textDecoration: "none",
          fontWeight: "bold",
          display: "inline-block"
        }}
      >
        Tiếp Tục Đặt Thêm Món 🧋
      </Link>
    </div>
  );
}
