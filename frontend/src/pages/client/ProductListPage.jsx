import React, { useState, useEffect } from "react";
import { useNavigate, useOutletContext } from "react-router-dom";
import { productService } from "../../services/productService";
import { orderService } from "../../services/orderService";
import ProductCard from "../../components/ProductCard";
import Cart from "../../components/Cart";

export default function ProductListPage() {
  const [products, setProducts] = useState([]);
  const [selectedProducts, setSelectedProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [fetching, setFetching] = useState(true);
  const [errorMsg, setErrorMsg] = useState("");
  
  // Thông tin giao hàng
  const [customerName, setCustomerName] = useState("Võ Thị Thúy An");
  const [customerPhone, setCustomerPhone] = useState("0912345678");
  const [deliveryAddress, setDeliveryAddress] = useState("123 Nguyễn Văn Cừ, Quận 5, TP.HCM");

  const navigate = useNavigate();
  const outletContext = useOutletContext();

  useEffect(() => {
    loadProducts();
  }, []);

  useEffect(() => {
    if (outletContext && outletContext.setCartCount) {
      outletContext.setCartCount(selectedProducts.length);
    }
  }, [selectedProducts]);

  const loadProducts = async () => {
    try {
      setFetching(true);
      setErrorMsg("");
      const data = await productService.getAll();
      setProducts(data);
    } catch (err) {
      console.error("Lỗi khi tải sản phẩm:", err);
      setErrorMsg("Không thể kết nối đến Menu Service (Port 8081). Vui lòng đảm bảo Backend đang chạy!");
    } finally {
      setFetching(false);
    }
  };

  const handleAddToCart = (item) => {
    setSelectedProducts((prev) => [...prev, item]);
  };

  const handleRemoveFromCart = (index) => {
    setSelectedProducts((prev) => prev.filter((_, idx) => idx !== index));
  };

  // Hàm xử lý đặt hàng liên Microservice (Trang 21 của tài liệu Lab 03)
  const handleCreateOrder = async () => {
    if (selectedProducts.length === 0) {
      alert("Vui lòng chọn sản phẩm vào giỏ hàng trước!");
      return;
    }

    const orderData = {
      customerName,
      customerPhone,
      deliveryAddress,
      branchId: 1,
      paymentMethod: "COD",
      items: selectedProducts.map((p) => ({
        productId: p.id,
        quantity: Number(p.quantity),
        size: p.size,
        sugarLevel: p.sugarLevel,
        iceLevel: p.iceLevel,
        toppings: "Trân châu hoàng kim"
      }))
    };

    try {
      setLoading(true);
      // Gọi qua Order Service (Port 8082), Order Service sẽ gọi Menu Service qua RestClient để lấy giá và tạo đơn
      const result = await orderService.create(orderData);
      console.log("Đặt hàng thành công:", result);
      setSelectedProducts([]);
      navigate("/order-success", { state: { order: result } });
    } catch (error) {
      console.error("Lỗi đặt hàng:", error);
      alert(error.response?.data?.message || "Không thể đặt hàng! Vui lòng kiểm tra lại dịch vụ Backend.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div style={{ marginBottom: "24px" }}>
        <h2 style={{ fontSize: "24px", color: "#333", margin: "0 0 8px 0" }}>Thực Đơn Trà Sữa Tươi Ngon</h2>
        <p style={{ color: "#666", margin: 0 }}>Chọn món và tùy chỉnh mức đường, mức đá theo sở thích của bạn.</p>
      </div>

      {errorMsg && (
        <div style={{ background: "#fee2e2", color: "#b91c1c", padding: "12px 16px", borderRadius: "8px", marginBottom: "20px" }}>
          ⚠️ {errorMsg}
        </div>
      )}

      {fetching ? (
        <p style={{ textAlign: "center", padding: "40px" }}>Đang tải danh sách đồ uống...</p>
      ) : (
        <div style={{ display: "grid", gridTemplateColumns: "1fr 340px", gap: "28px", alignItems: "start" }}>
          {/* Cột danh sách món */}
          <div>
            <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(240px, 1fr))", gap: "20px" }}>
              {products.map((product) => (
                <ProductCard key={product.id} product={product} onAddToCart={handleAddToCart} />
              ))}
            </div>
          </div>

          {/* Cột giỏ hàng & thông tin giao hàng */}
          <div>
            {/* Form người nhận */}
            <div style={{ background: "white", padding: "16px", borderRadius: "12px", border: "1px solid #eee", marginBottom: "16px" }}>
              <h4 style={{ margin: "0 0 12px 0", fontSize: "15px", color: "#555" }}>📍 Thông tin nhận hàng</h4>
              <input
                type="text"
                placeholder="Họ tên người nhận"
                value={customerName}
                onChange={(e) => setCustomerName(e.target.value)}
                style={{ width: "100%", padding: "8px", marginBottom: "8px", borderRadius: "6px", border: "1px solid #ccc", boxSizing: "border-box" }}
              />
              <input
                type="text"
                placeholder="Số điện thoại"
                value={customerPhone}
                onChange={(e) => setCustomerPhone(e.target.value)}
                style={{ width: "100%", padding: "8px", marginBottom: "8px", borderRadius: "6px", border: "1px solid #ccc", boxSizing: "border-box" }}
              />
              <input
                type="text"
                placeholder="Địa chỉ giao hàng"
                value={deliveryAddress}
                onChange={(e) => setDeliveryAddress(e.target.value)}
                style={{ width: "100%", padding: "8px", borderRadius: "6px", border: "1px solid #ccc", boxSizing: "border-box" }}
              />
            </div>

            {/* Giỏ hàng Component */}
            <Cart
              items={selectedProducts}
              onRemoveItem={handleRemoveFromCart}
              onOrder={handleCreateOrder}
              loading={loading}
            />
          </div>
        </div>
      )}
    </div>
  );
}
