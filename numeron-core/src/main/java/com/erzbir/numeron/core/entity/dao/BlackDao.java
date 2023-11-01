package com.erzbir.numeron.core.entity.dao;

import java.sql.SQLException;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 15:28
 */
public interface BlackDao {
    Set<Long> getBlacks() throws SQLException;

    boolean addBlack(long qq, long opId) throws SQLException;

    boolean removeBlack(long qq) throws SQLException;
}
