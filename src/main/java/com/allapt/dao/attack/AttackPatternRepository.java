package com.allapt.dao.attack;


import com.allapt.entity.attack.AttackPattern;
import com.allapt.entity.capec.Capec;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "AttackPattern", path = "AttackPattern")
public interface AttackPatternRepository extends Neo4jRepository<AttackPattern, Long> {

}
