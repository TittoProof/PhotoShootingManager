package com.aegidea.photoshootingmanager.repository;

import com.aegidea.photoshootingmanager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
public interface OrderRepository extends JpaRepository<Order, String>{

}
