import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import Header from "../components/Header";
import Footer from "../components/Footer";

export default function ClientLayout() {
  const [cartCount, setCartCount] = useState(0);

  return (
    <div style={{ minHeight: "100vh", display: "flex", flexDirection: "column", background: "#f8f9fa", fontFamily: "Segoe UI, sans-serif" }}>
      <Header cartCount={cartCount} />
      <main style={{ flex: 1, padding: "24px 32px", maxWidth: "1280px", margin: "0 auto", width: "100%", boxSizing: "border-box" }}>
        <Outlet context={{ cartCount, setCartCount }} />
      </main>
      <Footer />
    </div>
  );
}
