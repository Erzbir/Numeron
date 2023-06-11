package com.erzbir.numeron.api.entity;

import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:29
 */
public interface GroupService {

    Set<Long> getEnableGroupList();

    boolean exist(long groupId);

    boolean enableGroup(long groupId, long opId);

    boolean disableGroup(long groupId);
}
