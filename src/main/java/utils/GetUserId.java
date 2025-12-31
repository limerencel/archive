package utils;

import dao.ArchiveDao;

public class GetUserId {

    public static Integer findUserId(String username) {
        return ArchiveDao.findUserId(username);
    }
}
