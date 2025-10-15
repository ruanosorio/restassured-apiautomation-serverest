package funcional;

import clients.UserClient;
import dto.UserDTO;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

@Feature("Testes para validar CRUD de Usuários")
public class UserTest {

    private UserClient userClient;
    private String authToken;
    private String userId;

    @BeforeClass
    public void setUp() {
        userClient = new UserClient();
    }

    @Test(priority = 1, description = "Cria um novo usuário com sucesso.")
    public void testCreateUserSuccessfully() {

        UserDTO newUser = new UserDTO("Novo Usuario", "novo@usuario.com", "senha123", "true");
        Response response = userClient.createUser(newUser);

        response.then().statusCode(201).body("message", equalTo("Cadastro realizado com sucesso"));

        userId = response.then().extract().path("_id");
        Assert.assertNotNull(userId, "O ID do usuário não deve ser nulo após o cadastro.");

    }

    @Test(priority = 2, description = "Tenta criar um usuário com email existente.")
    public void testCreateUserWithExistingEmail() {

        UserDTO existingUser = new UserDTO("Outro Usuario", "novo@usuario.com", "outrasenha", "false");
        Response response = userClient.createUser(existingUser);

        response.then().statusCode(400).body("message", equalTo("Este email já está sendo usado"));
    }

    @Test(priority = 3, description = "Realiza o login e obtém o token de autenticação.")
    public void testLoginAndGetToken() {
        Response response = userClient.login("novo@usuario.com", "senha123");

        response.then().statusCode(200).body("message", equalTo("Login realizado com sucesso"));

        authToken = response.then().extract().path("authorization");
        Assert.assertNotNull(authToken, "O token de autenticação não deve ser nulo.");

    }

    @Test(priority = 4, description = "Busca um usuário pelo ID.")
    public void testGetUserById() {

        Response response = userClient.getUserById(userId);

        response.then().statusCode(200).body("nome", equalTo("Novo Usuario"));
    }

    @Test(priority = 5, description = "Lista todos os usuários cadastrados.")
    public void testGetAllUsers() {

        Response response = userClient.getAllUsers();

        response.then().statusCode(200).body("usuarios", not(empty()));
    }

    @Test(priority = 6, description = "Atualiza um usuário existente.")
    public void testUpdateUser() {

        UserDTO updatedUser = new UserDTO("Usuario Atualizado", "usuario.atualizado@test.com", "novaSenha", "false");
        Response response = userClient.updateUser(userId, updatedUser, authToken);

        response.then().statusCode(200).body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test(priority = 7, description = "Deleta um usuário existente.")
    public void testDeleteUser() {

        Response response = userClient.deleteUser(userId, authToken);

        response.then().statusCode(200).body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test(priority = 8, description = "Tenta deletar um usuário que não existe mais.")
    public void testDeleteNonExistentUser() {

        Response response = userClient.deleteUser(userId, authToken);

        response.then().statusCode(200).body("message", equalTo("Nenhum registro excluído"));
    }

}
