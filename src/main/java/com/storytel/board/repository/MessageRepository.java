/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storytel.board.repository;

import com.storytel.board.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author augus
 */
public interface MessageRepository extends JpaRepository<Message, Long>{
}
