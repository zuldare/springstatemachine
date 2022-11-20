package jh.jh.springstatemachine.repository;

import jh.jh.springstatemachine.domain.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
}
