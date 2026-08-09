package com.harishmart.dao;

import com.harishmart.model.Seller;
import com.harishmart.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellerDAO {

    public Seller findByUserId(long userId) throws SQLException {
        String sql = "SELECT id, user_id, shop_name FROM sellers WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Seller(rs.getLong("id"), rs.getLong("user_id"), rs.getString("shop_name"));
                }
                return null;
            }
        }
    }

    public long createSeller(Seller seller) throws SQLException {
        String sql = "INSERT INTO sellers (user_id, shop_name) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, seller.getUserId());
            ps.setString(2, seller.getShopName());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
            return -1;
        }
    }
}