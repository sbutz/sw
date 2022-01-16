package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.Share;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.PagingAndSortingRepository;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Scope(SCOPE_SINGLETON)
public interface ShareRepository extends PagingAndSortingRepository<Share, String> {
}
