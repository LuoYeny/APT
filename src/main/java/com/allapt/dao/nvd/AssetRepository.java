package com.allapt.dao.nvd;


import com.allapt.entity.capec.Consequences;
import com.allapt.entity.nvd.Asset;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/26 21:51
 */
@RepositoryRestResource(collectionResourceRel = "Asset", path = "Asset")
public interface AssetRepository extends Neo4jRepository<Asset, Long> {
    Asset findAssetByProduct(@Param("product") String name);
}
