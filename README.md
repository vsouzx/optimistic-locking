# Lock Otimista

## O que é Lock Otimista?
O lock otimista é uma técnica de controle de concorrência utilizada em sistemas que necessitam gerenciar o acesso simultâneo a recursos compartilhados. 

Essa técnica é chamada de "otimista" porque assume que múltiplos processos podem frequentemente completar suas transações sem interferir uns nos outros. 

Assim, em vez de bloquear os recursos desde o início, o lock otimista permite que as transações procedam sem restrições iniciais, verificando conflitos apenas no momento da atualização ou commit.

## Exemplo sem Lock Otimista
<p>No exemplo abaixo, quando dois usuários fazem uma requisicao ao mesmo tempo, tentando comprar um produto que tem apenas 1 unidade no estoque, ambos recebem <code>status 200</code> como resposta. Mas apenas um deles deveria conseguir realizar a compra e o outro tomar erro.</p>
<img width="560" alt="Captura de Tela 2024-12-07 às 19 03 57" src="https://github.com/user-attachments/assets/2d321af9-a4d4-4287-93c0-51f71705b5ba">

## Exemplo com Lock Otimista
<p>Agora, a tabela de produtos possui o atributo <code>version</code> que irá ser responsável por validar as transações na hora que elas são commitadas.</p>
<img width="635" alt="Captura de Tela 2024-12-07 às 19 04 29" src="https://github.com/user-attachments/assets/310f4b34-07e1-4a1d-8ac9-cf7d019b7dcc">

<p>Com isso, podemos usar algumas técnicas de retry ou mensagem personalizada pro usuário que tomar <code>LockException</code>, garantindo melhor experiencia pro usuário e consistência dos dados</p>

## Como implementar com Java e Spring

<p>Na entidade da nossa tabela, devemos apenas adicionar um atributo com <code>@Version</code>. Esse atributo pode ser um Integer ou um Timestamp</p>

<img width="414" alt="Captura de Tela 2024-12-07 às 19 20 33" src="https://github.com/user-attachments/assets/1f4dd631-6a60-448f-a783-bda699200354">

E podemos aplicar uma técnica de retry como no exemplo abaixo usando um bloco try-catch para pegar as excecoes do tipo <code>OptimisticLockingFailureException</code> e assim repetir o fluxo usando while.

<img width="915" alt="Captura de Tela 2024-12-07 às 19 25 20" src="https://github.com/user-attachments/assets/cc530893-c380-4b65-8d19-551a9d3057b4">


