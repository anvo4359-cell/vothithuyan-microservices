import React from "react";

export default function Cart({ items, onRemoveItem, onUpdateQuantity, onOrder, loading }) {
  const totalAmount = items.reduce((sum, item) => sum + (Number(item.price) * item.quantity), 0);

  return (
    <div style={{
      background: "white",
      padding: "20px",
      borderRadius: "12px",
      boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
      border: "1px solid #eee",
      position: "sticky",
      top: "90px"
    }}>
      <h3 style={{ margin: "0 0 16px 0", fontSize: "18px", borderBottom: "2px solid #f0f0f0", paddingBottom: "8px" }}>
        🛒 Giỏ hàng ({items.length})
      </h3>

      {items.length === 0 ? (
        <p style={{ color: "#888", textAlign: "center", padding: "20px 0" }}>Chưa có món nào trong giỏ hàng</p>
      ) : (
        <>
          <div style={{ maxHeight: "300px", overflowY: "auto", marginBottom: "16px" }}>
            {items.map((item, index) => (
              <div key={index} style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                padding: "8px 0",
                borderBottom: "1px dashed #eee"
              }}>
                <div style={{ flex: 1 }}>
                  <div style={{ fontWeight: "bold", fontSize: "14px" }}>{item.name}</div>
                  <div style={{ fontSize: "12px", color: "#666" }}>
                    Size {item.size} | {item.sugarLevel} đường | {item.iceLevel} đá
                  </div>
                  <div style={{ fontSize: "13px", color: "#d9534f" }}>
                    {Number(item.price).toLocaleString("vi-VN")} đ x {item.quantity}
                  </div>
                </div>

                <div style={{ display: "flex", alignItems: "center", gap: "6px" }}>
                  <button
                    onClick={() => onRemoveItem(index)}
                    style={{ background: "#ff4d4f", color: "white", border: "none", borderRadius: "4px", padding: "4px 8px", cursor: "pointer", fontSize: "12px" }}
                  >
                    🗑️
                  </button>
                </div>
              </div>
            ))}
          </div>

          <div style={{ borderTop: "2px solid #eee", paddingTop: "12px", marginBottom: "16px" }}>
            <div style={{ display: "flex", justifyContent: "space-between", fontSize: "16px", fontWeight: "bold" }}>
              <span>Tổng cộng:</span>
              <span style={{ color: "#d9534f" }}>{totalAmount.toLocaleString("vi-VN")} đ</span>
            </div>
          </div>

          <button
            onClick={onOrder}
            disabled={loading}
            style={{
              width: "100%",
              background: loading ? "#999" : "#ff9800",
              color: "white",
              border: "none",
              padding: "12px",
              borderRadius: "8px",
              fontSize: "16px",
              fontWeight: "bold",
              cursor: loading ? "not-allowed" : "pointer"
            }}
          >
            {loading ? "Đang xử lý..." : "💳 Đặt hàng ngay"}
          </button>
        </>
      )}
    </div>
  );
}
