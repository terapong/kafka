package toto.kafka.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toto.kafka.springboot.entity.Topic;

public interface TopicRepository  extends JpaRepository<Topic, Long>{

}
