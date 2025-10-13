package dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String nome;
    private String email;
    private String password;
    private String administrador;
}
