package com.etl.database;

import com.etl.Topic;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public enum DatabaseManager {

    INSTANCE;

    public Connection connectDatabase() {
        Connection connection = null;
        Properties props = new Properties();
        try {
            props.load(
                    new FileInputStream("C://Users/Jun/git/Upload/ETL_Project/src/com/etl/consumer/dbinfo.properties"));

            Class.forName(props.getProperty("driver"));
            connection = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"),
                    props.getProperty("pwd"));
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        } catch (SQLException e) {

        }
        return connection;
    }

    public void closeDatabase(Connection connnection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connnection != null)
                connnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int writeData(Topic topic) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        String sql = "insert into destination values(?,?,?,?)";
        try {
            connection = connectDatabase();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, topic.getEventId());
            preparedStatement.setString(2, topic.getEventTimeTamp());
            preparedStatement.setString(3, topic.getServiceCode());
            preparedStatement.setString(4, topic.getEventContext());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabase(connection, preparedStatement, resultSet);
        }
        return result;
    }
}
