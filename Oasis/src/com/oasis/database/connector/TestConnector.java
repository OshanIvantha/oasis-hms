package com.oasis.database.connector;

import com.mysql.jdbc.PreparedStatement;
import com.oasis.database.Connector;
import com.oasis.model.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TestConnector extends Connector {
    public HashMap<Integer, Test> getTestHashMap() {
        HashMap<Integer, Test> testHashMap = new HashMap<>();

        try {
            PreparedStatement preparedStatement = (PreparedStatement) getConnection().prepareStatement("SELECT * FROM test");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("test.id");
                String name = resultSet.getString("test.name");
                String description = resultSet.getString("test.description");
                double basePrice = resultSet.getDouble("test.base_price");

                Test test = new Test(id, name, description, basePrice);
                testHashMap.put(id, test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return testHashMap;
    }

    public void newTest(Test test) {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) getConnection().prepareStatement("INSERT INTO " +
                    "test(name, description, base_price) " +
                    "VALUES(?, ?, ?)");
            preparedStatement.setString(1, test.getName());
            preparedStatement.setString(2, test.getDescription());
            preparedStatement.setDouble(3, test.getBasePrice());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTest(Test test) {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) getConnection().prepareStatement("UPDATE test SET " +
                    "name = ?, " +
                    "description = ?, " +
                    "base_price = ? " +
                    "WHERE id = ?");
            preparedStatement.setString(1, test.getName());
            preparedStatement.setString(2, test.getDescription());
            preparedStatement.setDouble(3, test.getBasePrice());
            preparedStatement.setInt(4, test.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTest(Test test) {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) getConnection().prepareStatement("DELETE FROM test " +
                    "WHERE id = ?");
            preparedStatement.setInt(1, test.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
