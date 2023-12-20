package com.center.microflow.test.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyRowMapper implements RowMapper<TestBean> {
    @Override
    public TestBean mapRow(ResultSet resultSet, int i) throws SQLException {
        TestBean bean = new TestBean();
        bean.setId(resultSet.getInt("id"));
        bean.setName(resultSet.getString("name"));
        return bean;
    }
}
