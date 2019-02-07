package com.izi.interpreter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class History {

    private String instructions;

    @Id
    private String sessionId;

    public void addInstruction(String instruction) {
        if (this.instructions.equals("")) this.instructions = instruction;
        else this.instructions += "; " + instruction;
    }
}
