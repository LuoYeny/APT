package com.allapt.dao.capec;


import com.allapt.entity.capec.Capec;
import com.allapt.entity.capec.Consequences;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "Consequences", path = "Consequences")
public interface ConsequencesRepository extends Neo4jRepository<Consequences, Long> {

}