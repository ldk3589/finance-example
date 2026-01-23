package com.example.financemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 
 * </p>
 *
 * @author dk
 * @since 2026-01-22
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private LocalDateTime createTime;

}
