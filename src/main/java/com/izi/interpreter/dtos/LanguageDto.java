package com.izi.interpreter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    private String cmd;
    private String fileExtension;
    private String inlineOption;
}
