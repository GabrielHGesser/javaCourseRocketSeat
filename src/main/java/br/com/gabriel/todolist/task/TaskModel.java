package br.com.gabriel.todolist.task;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


/*
 *
 * ID
 * User (ID_USER)
 * Description
 * Title
 * Initial Date
 * End Date
 * Priority
 *
 * */


@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private LocalDateTime initialDate;
    private LocalDateTime endDate;
    private String priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;




}
