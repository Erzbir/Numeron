package com.erzbir.numeron.api.model;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/4/27 19:01
 */
public class ModelServiceLoader {
    static AdminService adminService;
    static BlackService blackService;
    static WhiteService whiteService;
    static GroupService groupService;

    static {
        ServiceLoader<AdminService> adminServices = ServiceLoader.load(AdminService.class, ModelServiceLoader.class.getClassLoader());
        Iterator<AdminService> iterator = adminServices.iterator();
        while (iterator.hasNext()) {
            try {
                adminService = (AdminService) iterator.next().getClass().getField("INSTANCE").get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ServiceLoader<BlackService> blackServices = ServiceLoader.load(BlackService.class, ModelServiceLoader.class.getClassLoader());
        Iterator<BlackService> iterator2 = blackServices.iterator();
        while (iterator2.hasNext()) {
            try {
                blackService = (BlackService) iterator2.next().getClass().getField("INSTANCE").get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ServiceLoader<WhiteService> whiteServices = ServiceLoader.load(WhiteService.class, ModelServiceLoader.class.getClassLoader());
        Iterator<WhiteService> iterator3 = whiteServices.iterator();
        while (iterator3.hasNext()) {
            try {
                whiteService = (WhiteService) iterator.next().getClass().getField("INSTANCE").get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ServiceLoader<GroupService> groupServices = ServiceLoader.load(GroupService.class, ModelServiceLoader.class.getClassLoader());
        Iterator<GroupService> iterator4 = groupServices.iterator();
        while (iterator4.hasNext()) {
            try {
                groupService = (GroupService) iterator4.next().getClass().getField("INSTANCE").get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
