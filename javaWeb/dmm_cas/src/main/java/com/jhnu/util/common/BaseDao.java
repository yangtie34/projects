package com.jhnu.util.common;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
/**
 * @author Administrator 基础DAO实现类
 * 
 */
public class BaseDao {

    @NotNull
    private JdbcTemplate jdbcTemplate;

    @NotNull
    private DataSource dataSource;
    
    public BaseDao() {}  

    public final void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public final JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public final DataSource getDataSource() {
        return this.dataSource;
    }
}
