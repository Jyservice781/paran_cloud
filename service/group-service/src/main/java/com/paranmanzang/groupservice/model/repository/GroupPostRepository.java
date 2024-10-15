package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.entity.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupPostRepository extends JpaRepository<GroupPost, Long>, GroupPostRepositoryCustom {

}
