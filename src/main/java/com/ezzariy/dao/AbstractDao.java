package com.ezzariy.dao;

import java.sql.Connection;

public class AbstractDao {
    protected final Connection connection = ConnectionFactory.getConnection();
}
