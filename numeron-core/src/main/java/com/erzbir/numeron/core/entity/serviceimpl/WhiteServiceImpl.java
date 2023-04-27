package com.erzbir.numeron.core.entity.serviceimpl;


import com.erzbir.numeron.api.model.WhiteService;
import com.erzbir.numeron.core.entity.dao.WhiteDao;
import com.erzbir.numeron.core.entity.daoimpl.WhiteDaoImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:29
 */
public class WhiteServiceImpl implements WhiteService {
    private final Set<Long> white = new HashSet<>();
    private final WhiteDao whiteDao = WhiteDaoImpl.INSTANCE;

    public WhiteServiceImpl() {
        try {
            white.addAll(whiteDao.getWhites());
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Long> getAdminList() {
        return white;
    }

    @Override
    public boolean exist(long qq) {
        return white.contains(qq);
    }

    @Override
    public boolean addWhite(long qq, long opId) {
        white.add(qq);
        try {
            return whiteDao.addWhite(qq, opId);
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeWhite(long qq) {
        return false;
    }
}
