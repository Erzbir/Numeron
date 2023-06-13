package com.erzbir.numeron.core.entity.serviceimpl;

import com.erzbir.numeron.api.entity.BlackService;
import com.erzbir.numeron.core.entity.dao.BlackDao;
import com.erzbir.numeron.core.entity.daoimpl.BlackDaoImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:25
 */
public class BlackServiceImpl implements BlackService {
    private static final Set<Long> black = new HashSet<>();
    private static final BlackDao blackDao = BlackDaoImpl.INSTANCE;

    static {
        try {
            black.addAll(blackDao.getBlacks());
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Long> getBlacks() {
        return black;
    }

    @Override
    public boolean exist(long id) {
        return black.contains(id);
    }

    @Override
    public boolean addBlack(long qq, long opId) {
        black.add(qq);
        try {
            return blackDao.addBlack(qq, opId);
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeBlack(long qq) {
        black.remove(qq);
        try {
            return blackDao.removeBlack(qq);
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            e.printStackTrace();
        }
        return false;
    }
}
