package eu.happycoders.shop.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for {@link CartJpaEntity}.
 *
 * @author Sven Woltmann
 */
@Repository
public interface JpaCartSpringDataRepository extends JpaRepository<CartJpaEntity, Integer> {
    @Query("SELECT c FROM CartJpaEntity c LEFT JOIN FETCH c.lineItems WHERE c.customerId = ?1")
    Optional<CartJpaEntity> findByIdWithLineItems(int id);
}
