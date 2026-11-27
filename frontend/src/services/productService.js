import { productApi } from "./api";

export const productService = {
  getAll: async (params) => {
    const response = await productApi.get("/products", { params });
    return response.data;
  },
  getById: async (id) => {
    const response = await productApi.get(`/products/${id}`);
    return response.data;
  },
  getCategories: async () => {
    const response = await productApi.get("/categories");
    return response.data;
  }
};
