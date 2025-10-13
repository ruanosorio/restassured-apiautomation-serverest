package funcional;

import clients.UserClient;
import dto.UserDTO;
import groovy.util.logging.Slf4j;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

@Slf4j
public class UserTest {

    private final UserClient userClient = new UserClient();
    private String authToken;
    private String userId;

    @Test(priority = 1, description = "Cria um novo usuário com sucesso.")
    public void testCreateUserSuccessfully() {
        log.info("Iniciando teste: Criação de novo usuário");

        UserDTO newUser = new UserDTO("Novo Usuario", "novo@usuario.com", "senha123", "true", null);
        Response response = userClient.createUser(newUser);

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"));

        userId = response.then().extract().path("_id");
        Assert.assertNotNull(userId, "O ID do usuário não deve ser nulo após o cadastro.");
        log.info("Usuário criado com sucesso. ID: {}", userId);
    }

    @Test(priority = 2, description = "Tenta criar um usuário com email existente.")
    public void testCreateUserWithExistingEmail() {
        log.info("Iniciando teste: Criação de usuário com email duplicado");

        UserDTO existingUser = new UserDTO("Outro Usuario", "novo@usuario.com", "outrasenha", "false", null);
        Response response = userClient.createUser(existingUser);

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"));
    }

    @Test(priority = 3, description = "Realiza o login e obtém o token de autenticação.")
    public void testLoginAndGetToken() {
        log.info("Iniciando teste: Login de usuário");

        Response response = userClient.login("novo@usuario.com", "senha123");

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("message", equalTo("Login realizado com sucesso"));

        authToken = response.then().extract().path("authorization");
        Assert.assertNotNull(authToken, "O token de autenticação não deve ser nulo.");
        log.info("Token obtido com sucesso: {}", authToken);
    }

    @Test(priority = 4, description = "Busca um usuário pelo ID.")
    public void testGetUserById() {
        log.info("Iniciando teste: Busca de usuário por ID");

        Response response = userClient.getUserById(userId);

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("nome", equalTo("Novo Usuario"));
    }

    @Test(priority = 5, description = "Lista todos os usuários cadastrados.")
    public void testGetAllUsers() {
        log.info("Iniciando teste: Listagem de usuários");

        Response response = userClient.getAllUsers();

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("usuarios", not(empty()));
    }

    @Test(priority = 6, description = "Atualiza um usuário existente.")
    public void testUpdateUser() {
        log.info("Iniciando teste: Atualização de usuário");

        UserDTO updatedUser = new UserDTO("Usuario Atualizado", "usuario.atualizado@test.com", "novaSenha", "false", null);
        Response response = userClient.updateUser(userId, updatedUser, authToken);

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test(priority = 7, description = "Deleta um usuário existente.")
    public void testDeleteUser() {
        log.info("Iniciando teste: Exclusão de usuário");

        Response response = userClient.deleteUser(userId, authToken);

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test(priority = 8, description = "Tenta deletar um usuário que não existe mais.")
    public void testDeleteNonExistentUser() {
        log.info("Iniciando teste: Exclusão de usuário inexistente");

        Response response = userClient.deleteUser(userId, authToken);

        log.info("Resposta recebida: {}", response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"));
    }

}
