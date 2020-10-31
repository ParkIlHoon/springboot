package org.hoon.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface Neo4JRepository extends Neo4jRepository<Account, Long> {
}
