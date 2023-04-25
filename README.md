
# Softreaming REST API

<a href="https://ibb.co/pJbfFNr"><img src="https://i.ibb.co/86NrZG4/softreaming-api1.png" alt="softreaming-api1" border="0"></a>
<a href="https://ibb.co/0Mb5s5P"><img src="https://i.ibb.co/J7JZ3ZX/softreaming-api2.png" alt="softreaming-api2" border="0"></a>
<a href="https://ibb.co/rM8NPNr"><img src="https://i.ibb.co/NpDbhbB/softreaming-api3.png" alt="softreaming-api3" border="0"></a>

O projeto trata-se de uma api inspirada num serviço de streaming, onde é possível realizar o cadastro de usuários, efetuar a autenticação.
Todos os usuários podem criar perfis, podendo ter, no máximo, 4 perfis por conta.
Para os usuários administradores, é disponibilizado a função de criar, editar e excluir filmes. Já para os usuários comuns, é permitido apenas listar e assistir os filmes.

## Stack utilizada

**Java (JDK 17), SpringBoot ˆ3.0.0**

**Database** MySQL

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/jpcchaves/softreaming-api.git
```

Entre no diretório do projeto

```bash
  cd softreaming-api
```

Aguarde o Maven instalar as dependências

Inicie o servidor (se estiver utilizando o IntelliJ, utilize o comando abaixo. Caso não, busque a opção Run Application da sua IDE de preferência)

```bash
  CTRL + SHIFT + F10
```


## Documentação da API

⚙️ In progress...

## Aprendizados

Aprendi a utilizar o framework Spring Boot para desenvolver uma API Rest com os principais endpoints: GET, PUT, PATCH, POST, DELETE.

Também foi de grande valia para aprender mais sobre tratamento de exceções em Java e validação dos dados enviados pelo usuário por meio da validação disponibilizada pelo Spring Boot (Bean Validation with Hibernate validator).

Além disso, implementei HATEOAS (Hypermedia As the Engine Of Application State) e o padrão DTO, buscando deixar a API mais robusta e com padrões de projeto implementados, alcançando o nível REST (Representational State Transfer).
