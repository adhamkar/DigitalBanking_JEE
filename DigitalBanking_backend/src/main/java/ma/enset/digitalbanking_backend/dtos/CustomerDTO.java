package ma.enset.digitalbanking_backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
