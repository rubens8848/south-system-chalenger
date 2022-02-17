# SOUTH SYSTEM CHALLENGE
Sistema para criar pautas, computar e apurar votos de associados de uma cooperativa.

# Recursos
- Cadastro clientes/pautas/votos
- busca de pautas
- finalização automatica de pautas que excederam o tempo limite de votação
- iniciar e finalizar pautas


# Dependencias

- ter o kafka rodando na porta 9092
- criar o tópico agenda
- OBS: sem as duas dependencias acima descritas o projeto ainda sim irá rodar e funcionar, porém a comunicação da mensageria será falaha

# Tecnologias utilizadas
- Spring Framework 
- MySQL
- Hibernate
- Kafka

# Documentação
- a documentação pode ser acessada pelo link abaixo assim que a api estiver em execução:
    http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
 
 - para auxiliar os testes da API, foi criado uma coleção do postman, lembrando que além dos end-poins de cadastro e login o usuário deve estar autenticado, 
   logo, tem que ser colocado o token recebido no endpoint de login em todas as outras requisições.
   
   https://documenter.getpostman.com/view/6528201/UVkiTeP9
   https://www.getpostman.com/collections/d6409ffb23d8f87c0895
   

# Teste inicial

- criar user - > login - > criar pauta - > iniciar pauta - > votar na pauta
