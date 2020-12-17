package com.allapt.dao.attack;


import com.allapt.entity.attack.DataSources;
import com.allapt.entity.attack.Detection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "Detection", path = "Detection")
public interface DetectionRepository extends Neo4jRepository<Detection, Long> {

}
