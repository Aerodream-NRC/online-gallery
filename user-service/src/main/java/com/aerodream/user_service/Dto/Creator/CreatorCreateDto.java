package com.aerodream.user_service.Dto.Creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatorCreateDto {

    private Long userId;

    private String firstname;

    private String lastname;
}
