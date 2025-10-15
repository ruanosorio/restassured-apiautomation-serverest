package dataprovider;

import dto.UserDTO;
import org.testng.annotations.DataProvider;

public class UserDataProvider {


    @DataProvider(name = "criarNovoUsuario")
    public static Object[][] createNewUser() {
        UserDTO usuario = new UserDTO();
        usuario.setNome("Automation User");
        usuario.setEmail("automation@test.com");
        usuario.setPassword("senha123");
        usuario.setAdministrador("true");
        return new Object[][]{{usuario}};
    }

    @DataProvider(name = "atualizarUsuario")
    public static Object[][] updateUser() {
        UserDTO usuarioAtualizado = new UserDTO();
        usuarioAtualizado.setNome("Updated Automation User");
        usuarioAtualizado.setEmail("updated.automation@test.com");
        usuarioAtualizado.setPassword("novaSenha123");
        usuarioAtualizado.setAdministrador("false");
        return new Object[][]{{usuarioAtualizado}};
    }

}
