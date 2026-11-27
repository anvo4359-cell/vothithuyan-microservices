import React from "react";
import { Link } from "react-router-dom";

export default function Header({ cartCount = 0 }) {
  return (
    <header style={{
      background: "linear-gradient(135deg, #6b4423, #a0522d)",
      color: "white",
      padding: "16px 32px",
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      boxShadow: "0 2px 8px rgba(0,0,0,0.15)",
      position: "sticky",
      top: 0,
      zIndex: 100
    }}>
      <div style={{ display: "flex", alignItems: "center", gap: "12px" }}>
        <span style={{ fontSize: "28px" }}>🧋</span>
        <Link to="/" style={{ color: "white", textDecoration: "none", fontSize: "20px", fontWeight: "bold" }}>
          BOBA TEA - Đặt Món Online
        </Link>
      </div>

      <nav style={{ display: "flex", gap: "24px", alignItems: "center" }}>
        <Link to="/" style={{ color: "white", textDecoration: "none", fontWeight: 500 }}>Trang chủ</Link>
        <Link to="/products" style={{ color: "white", textDecoration: "none", fontWeight: 500 }}>Thực đơn</Link>
        <Link to="/products" style={{
          background: "#ffcc00",
          color: "#333",
          padding: "8px 16px",
          borderRadius: "20px",
          textDecoration: "none",
          fontWeight: "bold",
          display: "flex",
          alignItems: "center",
          gap: "8px"
        }}>
          🛒 Giỏ hàng ({cartCount})
        </Link>
        <Link to="/admin" style={{ color: "#ffd2a0", textDecoration: "none", fontSize: "14px" }}>Quản trị (Admin)</Link>
      </nav>
    </header>
  );
}
