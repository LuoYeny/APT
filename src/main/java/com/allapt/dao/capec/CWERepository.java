package com.allapt.dao.capec;


import com.allapt.entity.CWE;
import com.allapt.entity.capec.Capec;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "CWE", path = "CWE")
public interface CWERepository extends Neo4jRepository<CWE, Long> {
    CWE findByCWE(@Param("CWE") String name);

}
