
package com.harishmart.servlet;

import com.harishmart.dao.CartDAO;
import com.harishmart.dao.OrderDAO;
import com.harishmart.model.CartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * GET  /checkout  -> show order summary before placing
 * POST /checkout  -> place the order from current cart
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CheckoutServlet.class);
    private final CartDAO cartDAO = new CartDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = requireLogin(req, resp);
        if (userId == null) return;

        try {
            List<CartItem> items = cartDAO.findByUserId(userId);
            if (items.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            req.setAttribute("cartItems", items);
            req.getRequestDispatcher("/checkout.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error in CheckoutServlet doGet", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = requireLogin(req, resp);
        if (userId == null) return;

        try {
            List<CartItem> items = cartDAO.findByUserId(userId);
            if (items.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            long orderId = orderDAO.placeOrder(userId, items);
            log.info("Order {} placed by user {}", orderId, userId);
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/order-confirmation.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error placing order", e);
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