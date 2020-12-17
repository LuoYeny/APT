package com.allapt.dao.nvd;


import com.allapt.entity.nvd.Asset;
import com.allapt.entity.nvd.CVSS;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "CVSS", path = "CVSS")
public interface CVSSRepository extends Neo4jRepository<CVSS, Long> {


}
