package com.aegidea.photoshootingmanager.repository;

import com.aegidea.photoshootingmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
public interface UserRepository extends JpaRepository<User, String>{

}
