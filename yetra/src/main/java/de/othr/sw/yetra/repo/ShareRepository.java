package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Share;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShareRepository extends PagingAndSortingRepository<Share, String> {
}
