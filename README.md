# Atividade PSoft Testes Automáticos e TDD

Para a atividade em questão você deve implementar por meio da metodologia de TDD (Test Driven Development) as user stories abaixo para um sistema de gestão de eventos. Para tal, você vai produzir duas versões da implementação e submeter ambas via formulário indicando os commits relacionados com cada versão:
1. Versão da implementação dos testes que não passam sem a implementação da lógica;
2. Versão da implementação dos testes que passam com a implementação da lógica.

### US1: Cadastro e Busca de Eventos

Como um organizador de eventos, desejo cadastrar novos eventos com detalhes e ser capaz de pesquisá-los eficientemente.

 - Deve ser possível cadastrar um novo evento com ID, nome, data, local e capacidade máxima.
 - O sistema deve impedir o cadastro de eventos com o mesmo ID.
 - Deve ser possível buscar eventos pelo nome, aceitando buscas parciais.
 - Se nenhum evento for encontrado na busca, uma mensagem apropriada deve ser retornada.

### US2: Inscrição de Participantes e Controle de Vagas

Como um participante, desejo me inscrever em eventos, e como organizador, desejo garantir que a capacidade dos eventos não seja excedida.

 - Deve ser possível inscrever um participante (identificado por CPF e nome) em um evento existente.
 - A inscrição só deve ser permitida se houver vagas disponíveis no evento.
 - O sistema deve impedir a inscrição duplicada do mesmo participante no mesmo evento.
 - Mensagens de erro devem ser exibidas para casos como evento não encontrado ou capacidade máxima atingida.

### US3: Listagem e Gestão de Vagas

Como organizador, desejo visualizar a lista de todos os eventos e monitorar a disponibilidade de vagas.

  - Deve ser possível listar todos os eventos cadastrados no sistema.
  - Para cada evento listado, deve ser exibido o número de vagas disponíveis (capacidade máxima - número de inscritos).
  - Se não houver eventos cadastrados, uma mensagem apropriada deve ser retornada.
