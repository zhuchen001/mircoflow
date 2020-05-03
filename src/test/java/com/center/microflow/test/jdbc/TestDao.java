package com.center.microflow.test.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

public class TestDao {

    private PlatformTransactionManager transactionManager;

    private JdbcTemplate jdbcTemplate;


    public List<TestBean> queryAll() {
        String sql = "select * from test";
        return this.jdbcTemplate.query(sql, new MyRowMapper());
    }

    public int addTest(TestBean bean) {
        String sql = "insert into test(id,name) values(?,?)";
        int count = this.jdbcTemplate.update(sql, bean.getId(),
                bean.getName());
        return count;
    }

    public int updateTest(TestBean bean) {
        String sql = "update test set name = ? where id = ?";
        int count = this.jdbcTemplate.update(sql,
                bean.getName(), bean.getId());
        return count;
    }

    public int deleteTest(int id) {
        String sql = "delete from test where id = ?";
        int count = this.jdbcTemplate.update(sql, id);
        return count;
    }

    public int deleteAll() {
        String sql = "delete from test";
        int count = this.jdbcTemplate.update(sql);
        return count;
    }

    public TestBean find(int id) {
        String sql = "select * from test where id = ?";
        List<TestBean> query = this.jdbcTemplate.query(sql, new MyRowMapper(), id);

        if (query.isEmpty()) {
            return null;
        }

        return query.get(0);
    }

    public void tran(final TestBean bean) {
        TransactionTemplate tt = new TransactionTemplate();

        tt.setTransactionManager(transactionManager);

        tt.execute((status) -> {
            updateTest(bean);
            addTest(bean);
            return null;
        });
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
