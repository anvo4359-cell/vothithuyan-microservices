import React from "react";
import { Link, Outlet } from "react-router-dom";

export default function AdminLayout() {
  return (
    <div style={{ minHeight: "100vh", display: "flex", background: "#f0f2f5", fontFamily: "Segoe UI, sans-serif" }}>
      {/* Sidebar */}
      <aside style={{ width: "240px", background: "#1f2937", color: "white", padding: "20px 0" }}>
        <div style={{ padding: "0 20px 20px", borderBottom: "1px solid #374151" }}>
          <h2 style={{ fontSize: "18px", margin: 0, color: "#60a5fa" }}>BobaTea Admin</h2>
          <span style={{ fontSize: "12px", color: "#9ca3af" }}>Hệ thống quản trị chuỗi</span>
        </div>
        <nav style={{ marginTop: "20px", display: "flex", flexDirection: "column" }}>
          <Link to="/admin" style={{ padding: "12px 20px", color: "white", textDecoration: "none" }}>📊 Dashboard</Link>
          <Link to="/" style={{ padding: "12px 20px", color: "#9ca3af", textDecoration: "none" }}>⬅️ Quay lại trang khách</Link>
        </nav>
      </aside>

      {/* Main Content */}
      <main style={{ flex: 1, padding: "24px" }}>
        <Outlet />
      </main>
    </div>
  );
}
