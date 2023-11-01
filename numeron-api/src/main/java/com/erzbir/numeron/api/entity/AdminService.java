package com.erzbir.numeron.api.entity;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:24
 */
public interface AdminService {

    List<Long> getAdmins(long botId, long groupId);

    boolean exist(long botId, long groupId, long id);

    boolean exist(long botId, long id);
}
