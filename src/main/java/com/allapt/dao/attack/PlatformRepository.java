package com.allapt.dao.attack;


import com.allapt.entity.attack.Platform;
import com.allapt.entity.attack.Software;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "Platform", path = "Platform")
public interface PlatformRepository extends Neo4jRepository<Platform, Long> {
    Platform findByPlatformName(@Param("platformName") String name);
}
