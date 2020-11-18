package toto.kafka.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import toto.kafka.springboot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
