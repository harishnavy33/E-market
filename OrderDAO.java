package com.harishmart.dao;

import com.harishmart.model.CartItem;
import com.harishmart.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OrderDAO {

    /**
     * Creates an order and its line items from the given cart items, decrements stock,
     * and clears the cart — all in a single transaction.
     */
    public long placeOrder(long userId, List<CartItem> cartItems) throws SQLException {
        BigDecimal total = cartItems.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            long orderId;
            String orderSql = "INSERT INTO orders (user_id, total, status) VALUES (?, ?, 'PLACED')";
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, userId);
                ps.setBigDecimal(2, total);
                ps.executeUpdate();
                var keys = ps.getGeneratedKeys();
                if (!keys.next()) throw new SQLException("Failed to create order");
                orderId = keys.getLong(1);
            }

            String itemSql = "INSERT INTO order_items (order_id, product_id, seller_id, quantity, price_at_purchase) " +
                    "SELECT ?, id, seller_id, ?, price FROM products WHERE id = ?";
            String stockSql = "UPDATE products SET stock = stock - ? WHERE id = ?";

            try (PreparedStatement itemPs = conn.prepareStatement(itemSql);
                 PreparedStatement stockPs = conn.prepareStatement(stockSql)) {
                for (CartItem item : cartItems) {
                    itemPs.setLong(1, orderId);
                    itemPs.setInt(2, item.getQuantity());
                    itemPs.setLong(3, item.getProductId());
                    itemPs.executeUpdate();

                    stockPs.setInt(1, item.getQuantity());
                    stockPs.setLong(2, item.getProductId());
                    stockPs.executeUpdate();
                }
            }

            String clearCartSql = "DELETE FROM cart_items WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(clearCartSql)) {
                ps.setLong(1, userId);
                ps.executeUpdate();
            }

            conn.commit();
            return orderId;

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}