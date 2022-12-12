package com.epam.training.ticketservice.core.rooms;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Long Id;

    @Column(unique = true)
    private String name;

    private int maxRowNum;

    private int maxColNum;

    public Room(String name, int maxRowNum, int maxColNum) {
        this.name = name;
        this.maxRowNum = maxRowNum;
        this.maxColNum = maxColNum;
    }

    @Override
    public String toString(){
        return String.format("Room %s with %d seats, %d rows and %d columns",
                this.name,this.maxRowNum*maxColNum,this.maxRowNum,this.maxColNum);
    }
}
