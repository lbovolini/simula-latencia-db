# demo

latencia padrão de acesso ao banco de dados de 200 ms


## Rota com 3 consultas SQL na mesma requisição
- localhost:8080/jdbc

## Como usar

- Entrar no diretorio do projeto

```shell
cd simula-latencia-db
```

- Realizar o build da aplicação e criação dos containers Docker

```shell
mvn clean package -Dmaven.test.skip=true && docker compose down && docker compose up -d --build
```

- Visualizar logs da aplicacao

```shell
docker logs simula-latencia-db-demo-1 
```