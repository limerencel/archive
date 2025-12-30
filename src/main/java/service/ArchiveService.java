package service;

import dao.ArchiveDao;
import entity.Archive;

import java.util.List;

public class ArchiveService {
    public List<Archive> findAllArchives() {
        return ArchiveDao.findAllArchives();
    }
}
