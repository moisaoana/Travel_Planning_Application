package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WishListDTO {

    @NotNull
    private LocationDisplayDTO location;

    @NotNull
    private String user;
}
