import React, { useState, useEffect } from "react";
import { orderService } from "../../services/orderService";

export default function DashboardPage() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadOrders();
  }, []);

  const loadOrders = async () => {
    try {
      setLoading(true);
      const data = await orderService.getAll();
      setOrders(data);
    } catch (err) {
      console.error("Lỗi khi tải danh sách đơn:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1 style={{ fontSize: "24px", margin: "0 0 16px 0", color: "#111827" }}>Tổng quan Quản trị (Admin Dashboard)</h1>
      <p style={{ color: "#6b7280", marginBottom: "24px" }}>Theo dõi các đơn hàng phát sinh từ quy trình đặt món liên Microservices.</p>

      {/* Thẻ thống kê */}
      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))", gap: "16px", marginBottom: "24px" }}>
        <div style={{ background: "white", padding: "16px", borderRadius: "8px", border: "1px solid #e5e7eb" }}>
          <span style={{ fontSize: "12px", color: "#6b7280" }}>Tổng số đơn hàng</span>
          <div style={{ fontSize: "24px", fontWeight: "bold", color: "#1f2937" }}>{orders.length}</div>
        </div>
        <div style={{ background: "white", padding: "16px", borderRadius: "8px", border: "1px solid #e5e7eb" }}>
          <span style={{ fontSize: "12px", color: "#6b7280" }}>Doanh số tạm tính</span>
          <div style={{ fontSize: "24px", fontWeight: "bold", color: "#059669" }}>
            {orders.reduce((sum, o) => sum + Number(o.totalAmount || 0), 0).toLocaleString("vi-VN")} đ
          </div>
        </div>
      </div>

      {/* Danh sách đơn hàng */}
      <div style={{ background: "white", borderRadius: "8px", border: "1px solid #e5e7eb", overflow: "hidden" }}>
        <div style={{ padding: "16px", borderBottom: "1px solid #e5e7eb", fontWeight: "bold" }}>Danh sách đơn hàng mới nhất</div>
        {loading ? (
          <p style={{ padding: "20px", textAlign: "center" }}>Đang tải...</p>
        ) : orders.length === 0 ? (
          <p style={{ padding: "20px", textAlign: "center", color: "#888" }}>Chưa có đơn hàng nào.</p>
        ) : (
          <table style={{ width: "100%", borderCollapse: "collapse", fontSize: "14px", textAlign: "left" }}>
            <thead style={{ background: "#f9fafb", color: "#4b5563" }}>
              <tr>
                <th style={{ padding: "10px 16px" }}>Mã đơn</th>
                <th style={{ padding: "10px 16px" }}>Khách hàng</th>
                <th style={{ padding: "10px 16px" }}>Số ĐT</th>
                <th style={{ padding: "10px 16px" }}>Tổng tiền</th>
                <th style={{ padding: "10px 16px" }}>Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((o) => (
                <tr key={o.id} style={{ borderBottom: "1px solid #f3f4f6" }}>
                  <td style={{ padding: "10px 16px", fontWeight: "bold", color: "#2563eb" }}>{o.orderCode}</td>
                  <td style={{ padding: "10px 16px" }}>{o.customerName}</td>
                  <td style={{ padding: "10px 16px" }}>{o.customerPhone}</td>
                  <td style={{ padding: "10px 16px", color: "#dc2626", fontWeight: "bold" }}>
                    {Number(o.totalAmount).toLocaleString("vi-VN")} đ
                  </td>
                  <td style={{ padding: "10px 16px" }}>
                    <span style={{ background: "#e0f2fe", color: "#0369a1", padding: "2px 8px", borderRadius: "4px", fontSize: "12px" }}>
                      {o.status}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
