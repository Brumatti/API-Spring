package com.example.api.services.impl;

import com.example.api.domain.Users;
import com.example.api.domain.dto.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.exceptions.DataIntegratyViolationException;
import com.example.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Pedro";
    public static final String EMAIL = "pedro@email.com";
    public static final String PASSWORD = "123";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    public static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado";
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;


    private Users users;
    private UserDTO userDTO;
    private Optional<Users> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();

    }

    @Test
    void whenFindByIdThenReturnAnUsersInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        Users response = service.findById(ID);

        assertNotNull(response); //Assegurar que o response não vai ser nulo, para então verificar o resto
        assertEquals(Users.class, response.getClass()); //Assegurar que a classe vai ser do tipo Users, não DTO
        assertEquals(ID, response.getId()); //assegurar que o id da resposta vai ser igual o ID passado ^
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());

    }

    @Test
    void whenFindByIDThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO)); //Quando chamar o findbyid, lança uma execao de obj nao encontrado

        try{ service.findById(ID);

        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage()); //assegurar que a mensagem é igual
        }

    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(users)); //mock

        List<Users> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());//assegurar que so ira vir 1 usuario
        assertEquals(Users.class, response.get(INDEX).getClass()); //assegura o que o obj no index 0 é do tipo users

        assertEquals(ID, response.get(INDEX).getId()); //assegurando que o objeto que veio no index 0 é o id igual o que foi passado no parametro
        assertEquals(NAME, response.get(INDEX).getName()); //assegurando que o objeto que veio no index 0 é o id igual o que foi passado no parametro
        assertEquals(EMAIL, response.get(INDEX).getEmail()); //assegurando que o objeto que veio no index 0 é o id igual o que foi passado no parametro
        assertEquals(PASSWORD, response.get(INDEX).getPassword()); //assegurando que o objeto que veio no index 0 é o id igual o que foi passado no parametro
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(users); //mock

        Users response = service.create(userDTO);
        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }
    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser); //mock

        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO, ex.getMessage());
        }

    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(users); //mock

        Users response = service.update(userDTO);
        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser); //mock

        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO, ex.getMessage());
        }

    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser); //mockando para nao lancar execao de obj nao encontrado
        doNothing().when(repository).deleteById(anyInt()); //nao fazer nada quando meu repository for chamando no metodo deletebyId passando qualquer valor Inteiro
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
        //Como o metodo nao retorna nada(void), chama o metodo verify para ver
        // quantas vezes o repository foi chamado no metodo deleteById
    }

    private void startUsers(){
        users = new Users(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new Users(ID, NAME, EMAIL, PASSWORD));
    }
}