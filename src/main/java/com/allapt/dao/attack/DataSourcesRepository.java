package com.allapt.dao.attack;


import com.allapt.entity.attack.AttackMitigations;
import com.allapt.entity.attack.DataSources;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "DataSources", path = "DataSources")
public interface DataSourcesRepository extends Neo4jRepository<DataSources, Long> {

}
