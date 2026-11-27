import React, { useState } from "react";

export default function ProductCard({ product, onAddToCart }) {
  const [quantity, setQuantity] = useState(1);
  const [size, setSize] = useState("M");
  const [sugarLevel, setSugarLevel] = useState("100%");
  const [iceLevel, setIceLevel] = useState("100%");

  const handleAdd = () => {
    onAddToCart({
      id: product.id,
      name: product.name,
      price: product.price,
      quantity: Number(quantity),
      size,
      sugarLevel,
      iceLevel,
      imageUrl: product.imageUrl
    });
    setQuantity(1);
  };

  return (
    <div style={{
      background: "white",
      borderRadius: "12px",
      overflow: "hidden",
      boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
      display: "flex",
      flexDirection: "column",
      border: "1px solid #eee",
      transition: "transform 0.2s"
    }}>
      <img
        src={product.imageUrl || "https://images.unsplash.com/photo-1558857563-b37cf52db0f1?w=400"}
        alt={product.name}
        style={{ width: "100%", height: "200px", objectFit: "cover" }}
      />
      <div style={{ padding: "16px", display: "flex", flexDirection: "column", flex: 1 }}>
        <h3 style={{ margin: "0 0 8px 0", fontSize: "18px", color: "#333" }}>{product.name}</h3>
        <p style={{ margin: "0 0 12px 0", color: "#666", fontSize: "13px", flex: 1 }}>
          {product.description || "Hương vị trà thơm ngon kết hợp sữa thanh mát"}
        </p>

        <div style={{ fontSize: "18px", fontWeight: "bold", color: "#d9534f", marginBottom: "12px" }}>
          {Number(product.price).toLocaleString("vi-VN")} đ
        </div>

        {/* Tuỳ chọn size, đường, đá */}
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "8px", marginBottom: "12px", fontSize: "13px" }}>
          <div>
            <label style={{ display: "block", color: "#777", marginBottom: "2px" }}>Size:</label>
            <select value={size} onChange={(e) => setSize(e.target.value)} style={{ width: "100%", padding: "4px 8px", borderRadius: "6px", border: "1px solid #ccc" }}>
              <option value="M">Size M (Vừa)</option>
              <option value="L">Size L (Lớn +5k)</option>
            </select>
          </div>
          <div>
            <label style={{ display: "block", color: "#777", marginBottom: "2px" }}>Đường:</label>
            <select value={sugarLevel} onChange={(e) => setSugarLevel(e.target.value)} style={{ width: "100%", padding: "4px 8px", borderRadius: "6px", border: "1px solid #ccc" }}>
              <option value="100%">100% đường</option>
              <option value="70%">70% đường</option>
              <option value="50%">50% đường</option>
              <option value="30%">30% đường</option>
              <option value="0%">0% đường</option>
            </select>
          </div>
        </div>

        {/* Nhập số lượng và nút Thêm */}
        <div style={{ display: "flex", gap: "8px", alignItems: "center" }}>
          <input
            type="number"
            min="1"
            max="99"
            value={quantity}
            onChange={(e) => setQuantity(Math.max(1, parseInt(e.target.value) || 1))}
            style={{ width: "60px", padding: "8px", textAlign: "center", borderRadius: "6px", border: "1px solid #ccc" }}
          />
          <button
            onClick={handleAdd}
            style={{
              flex: 1,
              background: "#2e7d32",
              color: "white",
              border: "none",
              padding: "10px",
              borderRadius: "6px",
              fontWeight: "bold",
              cursor: "pointer"
            }}
          >
            🛒 Thêm vào giỏ
          </button>
        </div>
      </div>
    </div>
  );
}
