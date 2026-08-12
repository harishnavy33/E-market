package com.harishmart.servlet;

import com.harishmart.dao.CartDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * GET  /cart                        -> view cart
 * POST /cart                        -> add product to cart (params: productId, quantity)
 * GET  /cart?action=remove&id=X     -> remove a cart item
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CartServlet.class);
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = requireLogin(req, resp);
        if (userId == null) return;

        try {
            String action = req.getParameter("action");
            if ("remove".equals(action)) {
                long itemId = Long.parseLong(req.getParameter("id"));
                cartDAO.removeItem(itemId, userId);
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }

            req.setAttribute("cartItems", cartDAO.findByUserId(userId));
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error in CartServlet doGet", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = requireLogin(req, resp);
        if (userId == null) return;

        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            if (quantity < 1) quantity = 1;

            cartDAO.addOrIncrement(userId, productId, quantity);
            log.info("User {} added product {} (qty {}) to cart", userId, productId, quantity);
            resp.sendRedirect(req.getContextPath() + "/cart");

        } catch (Exception e) {
            log.error("Error in CartServlet doPost", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Long requireLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return null;
        }
        return (Long) session.getAttribute("userId");
    }
}