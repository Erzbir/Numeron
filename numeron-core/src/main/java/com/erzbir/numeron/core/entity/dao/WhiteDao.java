package com.erzbir.numeron.core.entity.dao;

import java.sql.SQLException;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 15:29
 */
public interface WhiteDao {
    Set<Long> getWhites() throws SQLException;

    boolean addWhite(long qq, long opId) throws SQLException;

    boolean removeWhite(long qq) throws SQLException;
}
