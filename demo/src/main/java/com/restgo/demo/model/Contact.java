package com.restgo.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private Integer id;
    @NotBlank
    @Size(min = 5, max = 20, message = "Name should be between 5 and 20")
    private String name;
    @NotNull
    @Range(min =0, max =100, message = "You can have max 100")
    private Double quantity;
    @NotNull
    @Range(min =0, max =100, message = "You can have max 100")
    private Double size;

    @NotBlank
    @Size(min =5, max =20, message = "Brand name must be between 5 and 20")
    private String brand;
    @NotBlank
    @Size(min =10, max =100, message = "Description must be between 10 character at least")
    private String description;

    @NotNull
    @Range(min =0, max =1000, message = "Selling Price must be between 5 and 20")
    private Double selling_price;

//    @NotBlank
//    @Size(min = 12, max = 12, message = "The number should be in this format : 998*********")
//    private String phoneNumber;
}
