package service;

import dao.ArchiveDao;
import entity.Archive;

import java.util.List;

public class ArchiveService {
    public List<Archive> findAllArchives() {
        return ArchiveDao.findAllArchives();
    }

    public Archive findArchiveById(int id) {
        return ArchiveDao.findArchiveById(id);
    }

    public boolean updateArchive(Archive archive) {
        return ArchiveDao.updateArchive(archive);
    }

    public boolean addArchive(Archive archive) {
        return ArchiveDao.addArchive(archive);
    }

    public void deleteById(Integer id) {
        ArchiveDao.deleteById(id);
    }

}
