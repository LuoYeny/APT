package com.allapt.dao.attack;


import com.allapt.entity.CVE;
import com.allapt.entity.attack.AttackMitigations;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "CVE", path = "CVE")
public interface CVERepository extends Neo4jRepository<CVE, Long> {
    CVE findByCVE(@Param("CVE") String name);
}
