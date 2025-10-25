# Sistema Unificado de Mídia Social (Adapter) — Resumo simples

# O que é:
Um programa em Java que publica e agenda posts em várias redes (Twitter, Instagram, LinkedIn e TikTok) usando uma interface única.
Usa os padrões Adapter (principal), Strategy (formatação do texto) e Factory Method (criar o adapter certo).

Versão Java: 17 ou superior.
Dependências: nenhuma externa.


# Objetivo

Ter um só lugar para: publicar, agendar, ver estatísticas e deletar posts.
Não mexer nas “APIs originais” (simuladas no projeto).
Tratar erros de forma clara (ex.: AUTH_FAILED, INVALID_MEDIA).
Ser seguro para uso com múltiplas threads (agendador de posts).


# Como funciona (ideia geral)

Você chama o Gerenciador e diz a plataforma (Twitter/Instagram/LinkedIn/TikTok).
Por baixo, um Adapter traduz para a API daquela plataforma.
O texto pode ter formatação específica por rede (ex.: Twitter corta em 280).
Há um agendador para publicar no futuro.


# O que foi implementado

Interface unificada: GerenciadorMidiaSocial (publicar, agendar, estatísticas, deletar).
Adapters: um por rede (Twitter, Instagram, LinkedIn, TikTok).
Strategy: formatação do texto (Twitter com limite, outras em texto simples).
Factory: cria o adapter certo lendo tokens do ambiente.
Modelos de resposta: Resultado (sucesso/falha), Publicacao, Estatisticas.
Agendamento: publica numa data/hora futura.


# Como rodar

Garanta o Java 17+.
Compile e execute a classe social.Main (pelo VS Code ou pelo terminal).
Se aparecer AUTH_FAILED, faltam tokens de ambiente.

Defina tokens (exemplo rápido no PowerShell do Windows):
$env:TWITTER_TOKEN="demo"
$env:INSTAGRAM_TOKEN="demo"
$env:LINKEDIN_TOKEN="demo"
$env:TIKTOK_TOKEN="demo"
(ou, no código Main, um “fallback” que usa "demo" se não houver variáveis.)


# Como usar (fluxo)

Publicar agora: o Main já mostra um exemplo publicando no Twitter.
Agendar: criamos um conteúdo com data futura e chamamos agendar(...).
Dica: no exemplo, usamos .join() só para garantir que a mensagem do agendamento apareça no console.


# Estrutura (onde estão as coisas)

social.core → interfaces (GerenciadorMidiaSocial, CanalMidiaSocial)
social.impl → implementação do gerenciador (agendamento, cache de canais)
social.adapter → adapters por rede
social.legacy → “APIs originais” simuladas (não altere)
social.strategy → regras de formatação de texto
social.model → Conteudo, Publicacao, Estatisticas
social.resposta → Resultado, Erro
social.factory → cria o adapter certo
social.config → pega variáveis de ambiente
social.Main → exemplo simples rodando tudo


# Erros comuns (e o que significam)

AUTH_FAILED → faltou token daquela rede.
INVALID_MEDIA → faltou/errou a mídia (Instagram/TikTok).
INVALID_TEXT → texto vazio (Twitter).
INVALID_ID → ID no formato errado (ex.: TikTok espera número).
API_ERROR → outro erro da API simulada.


# O que o professor pediu (checklist)

Adapter como padrão principal
Strategy e Factory
Sem alterar APIs originais
Composition over inheritance (adapters compõem clientes)
Tratamento de erro granular
Generics nos tipos de retorno/IDs
Thread-safe (agendador + cache)
Configuração por ambiente
Agendamento funcionando
README + (opção de) diagrama


# Dicas finais

Se o console imprimir algo como Estatisticas@123abc, é normal do Java; já colocamos toString() para ficar legível.
Se trocar nomes de pacotes/arquivos, confira que package bate com a pasta.
Para adicionar outra rede no futuro: criar novo Adapter, registrar na Factory e (se preciso) uma Strategy de formatação.