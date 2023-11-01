package com.erzbir.numeron.core.entity.dao;

import java.sql.SQLException;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 15:29
 */
public interface GroupDao {
    Set<Long> getEnableGroups() throws SQLException;

    boolean enableGroup(long groupId, long opId) throws SQLException;

    boolean disableGroup(long groupId) throws SQLException;
}
