package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.Share;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Scope(SCOPE_SINGLETON)
public interface ShareRepository extends PagingAndSortingRepository<Share, String> {

    @Query("Select s FROM Share s WHERE lower(s.name) like lower(concat('%', :name, '%') ) ")
    Iterable<Share> findAllNameContains(@Param("name") String name);
}
