package com.erzbir.numeron.core.entity.serviceimpl;

import com.erzbir.numeron.api.entity.GroupService;
import com.erzbir.numeron.core.entity.dao.GroupDao;
import com.erzbir.numeron.core.entity.daoimpl.GroupDaoImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:29
 */
public class GroupServiceImpl implements GroupService {
    private final Set<Long> group = new HashSet<>();
    private final GroupDao groupDao = GroupDaoImpl.INSTANCE;

    public GroupServiceImpl() {
        try {
            group.addAll(groupDao.getEnableGroups());
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Long> getEnableGroupList() {
        return group;
    }

    @Override
    public boolean exist(long qqId) {
        return group.contains(qqId);
    }

    @Override
    public boolean enableGroup(long groupId, long opId) {
        group.add(groupId);
        try {
            return groupDao.enableGroup(groupId, opId);
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean disableGroup(long groupId) {
        group.remove(groupId);
        try {
            return groupDao.disableGroup(groupId);
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            e.printStackTrace();
        }
        return false;
    }
}
