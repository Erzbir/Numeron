package com.erzbir.numeron.plugin.openai.config;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.plugin.openai.Role;
import com.erzbir.numeron.utils.ConfigReadException;
import com.erzbir.numeron.utils.ConfigWriteException;
import com.erzbir.numeron.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/17 02:09
 * <p>人设</p>
 */
public class RoleConfig {
    private static final String configFile = NumeronImpl.INSTANCE.getPluginWorkDir() + "chatgpt/config/role.json";
    private static final Object key = new Object();
    private static volatile RoleConfig INSTANCE;
    List<Role> roles = new ArrayList<>();
    private int default_role = 0;

    private RoleConfig() {

    }

    public static RoleConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = JsonUtil.load(configFile, RoleConfig.class);
                    } catch (ConfigReadException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new RoleConfig();
                    try {
                        JsonUtil.dump(configFile, INSTANCE, RoleConfig.class);
                    } catch (ConfigWriteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return INSTANCE;
    }

    public Role getRole(int index) {
        return roles.get(index);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRole(List<Role> roles) {
        this.roles = roles;
    }

    public int getDefaultRole() {
        return default_role;
    }

    public void setDefaultRole(int default_role) {
        this.default_role = default_role;
    }
}
