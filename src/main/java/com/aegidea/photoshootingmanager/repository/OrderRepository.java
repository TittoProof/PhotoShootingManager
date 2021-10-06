package com.aegidea.photoshootingmanager.repository;

import com.aegidea.photoshootingmanager.entity.Order;
import com.aegidea.photoshootingmanager.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
public interface OrderRepository extends JpaRepository<Order, String>{
    
    @Modifying
    @Query("UPDATE Order as o SET o.status=:status WHERE o.id=:id")
    public int updateOrderStatus(@Param("id") String id, @Param("status") OrderStatus status);

}
