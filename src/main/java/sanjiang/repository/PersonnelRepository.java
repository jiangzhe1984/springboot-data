package sanjiang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sanjiang.domain.Personnel;

/**
 * Created by jiangzhe on 15-11-10.
 */
public interface PersonnelRepository extends JpaRepository<Personnel,Long>, JpaSpecificationExecutor<Personnel> {
}
