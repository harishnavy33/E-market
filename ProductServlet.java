package com.harishmart.servlet;

import com.harishmart.dao.ProductDAO;
import com.harishmart.dao.SellerDAO;
import com.harishmart.model.Product;
import com.harishmart.model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Handles browsing all products (buyers) and CRUD for a seller's own products.
 * GET  /products              -> list all products (public)
 * GET  /products?action=new   -> show add-product form (seller only)
 * POST /products               -> create product (seller only)
 * GET  /products?action=edit&id=X -> show edit form (seller only)
 * POST /products?action=update -> update product (seller only)
 * GET  /products?action=delete&id=X -> delete product (seller only)
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ProductServlet.class);
    private final ProductDAO productDAO = new ProductDAO();
    private final SellerDAO sellerDAO = new SellerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) {
                requireSellerSession(req, resp);
                req.getRequestDispatcher("/product-form.jsp").forward(req, resp);
                return;
            }

            if ("edit".equals(action)) {
                Seller seller = requireSellerSession(req, resp);
                if (seller == null) return;
                long id = Long.parseLong(req.getParameter("id"));
                Product product = productDAO.findById(id);
                if (product == null || product.getSellerId() != seller.getId()) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Not your product");
                    return;
                }
                req.setAttribute("product", product);
                req.getRequestDispatcher("/product-form.jsp").forward(req, resp);
                return;
            }

            if ("delete".equals(action)) {
                Seller seller = requireSellerSession(req, resp);
                if (seller == null) return;
                long id = Long.parseLong(req.getParameter("id"));
                productDAO.deleteProduct(id, seller.getId());
                resp.sendRedirect(req.getContextPath() + "/products");
                return;
            }

            // default: list all products
            req.setAttribute("products", productDAO.findAll());
            req.getRequestDispatcher("/products.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error in ProductServlet doGet", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Seller seller = requireSellerSession(req, resp);
            if (seller == null) return;

            String action = req.getParameter("action");
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));

            if ("update".equals(action)) {
                long id = Long.parseLong(req.getParameter("id"));
                Product p = new Product(id, seller.getId(), name, description, price, stock);
                productDAO.updateProduct(p);
                log.info("Product {} updated by seller {}", id, seller.getId());
            } else {
                Product p = new Product(0, seller.getId(), name, description, price, stock);
                productDAO.createProduct(p);
                log.info("Product '{}' created by seller {}", name, seller.getId());
            }

            resp.sendRedirect(req.getContextPath() + "/products");

        } catch (Exception e) {
            log.error("Error in ProductServlet doPost", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Confirms the logged-in user has a seller profile, auto-creating one on first use.
     * Redirects to login and returns null if there's no session.
     */
    private Seller requireSellerSession(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, java.sql.SQLException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return null;
        }
        long userId = (long) session.getAttribute("userId");
        Seller seller = sellerDAO.findByUserId(userId);
        if (seller == null) {
            String shopName = session.getAttribute("userName") + "'s Shop";
            long newId = sellerDAO.createSeller(new Seller(0, userId, shopName));
            seller = new Seller(newId, userId, shopName);
        }
        return seller;
    }
}