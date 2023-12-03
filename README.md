 <h1 align="center"> Music Player </h1>

<p align="justify"> Este é o projeto final da matéria Linguagem de programação II (IMD0040). O projeto se trata de um tocador de mp3,
onde os `usuário`s podem escutar suas músicas cadastrando-as através de diretórios. Estes diretórios deverão possuir as músicas que o `usuário` deseja escutar.</p>

---

## Usuários ##

Os `usuário`s são dividos em dois tipos, os `usuário`s comuns e os vips. A principal diferença entre eles é a possibilidade
de criar playlists. Essa possibilidade é dada apenas ao `usuário` do tipo vip, que tabmém pode ouvir músicas apenas cadastrando seus
diretórios. Com isso, o `usuário` comum não pode criar playlists, mas ainda pode ouvir suas músicas através dos diretórios.

Um `usuário`  vip apenas poderá ser criado quando o `usuário` `Admin` cadastrá-lo ou quando o `usuário` `Admin` transformar um
`usuário` comum em um `usuário` vip.

## Login ##

Ao iniciar o `Music Player`, a tela inicial que o `usuário` irá encontrar será a tela de login.

<img src="docs\LoginScreen.png">

Na tela de login, temos dois campos para preencher. O primeiro campo, será preenchido com o `email` que o `usuário` ou o 
`usuário` `Admin`, cadastrou. Logo em seguida, temos a `senha`, que deve ser preenchida com a `senha` cadastrada (O registro
por parte do `usuário` será discutido em breve). Caso o email e `senha`, do `usuário` que está tentando entrar, estejam corretos,
basta apertar o botão de `login`. Caso as credenciais estejam incorretas, o `usuário` será informado, assim como mostra a imagem abaixo.

<img src="docs\InvalidLoginScreen.png">

Entretanto, se as credenciais estiverem corretas, o `usuário` irá para a tela principal do `Music Player`.
## Registro ##

Se o `usuário` não possuir cadastro, basta `clicar` no botão de `sign up` e ele será redirecionado para a tele de registro.

<img src="docs\SignUpScreen.png">

Na tela de registro, temos três campos para preencher, sendo o primeiro deles o `username`. Este não é único de um `usuário`, logo, é possível
haver mais de um `usuário`com o mesmo `username`.

O outro campo disponível para preencher é o `email`. Caso o `usuário` tente cadastrar um `email` que já `existe`, o `Music Player`
irá informá-lo. Ou seja, ao contrário do `username`, não pode haver `emails` repetidos.
Se o `usúario` cadastrar um email que já existe, o seguinte aviso será dado:

<img src="docs\InvalidEmail.png">

A `senha` é o terceiro campo, o `usuário` deve estar atento ao cadastrar a `senha`, pois, como já visto, ela será usada para realizar o `login`.
o mesmo vale para o `email`.

Caso o `usuário` não preencha algum desses campos, ele será avisado, assim como mostra a imagem abaixo:

<img src="docs\MissingFields.png">

Entretanto, se tudo for preenchido corretamente, o resgitro será um sucesso e um aviso será dado ao `usuário`, assim como mostra
a imagem abaixo.

<img src="docs\RegistrationSuccessful.png">

## Tela Principal (Usuário Comum) ##

<img src="docs\CommonUserScreen.png">

Na tela principal, o `usuário comum` irá encontrar dois botões principais. Um chamado `Default directory`, que irá pegar
as músicas que estão na pasta `songs`, essa pasta se encontra nesse mesmo projeto. O outro botão é o `Select a directory`, onde o `usuário` poderá
selecionar o diretório onde as músicas, que ele deseja escutar, estão.

## Tela Principal (Usuário Vip)

<img src="docs\VipUserScreen.png">

O `usuário vip` herda as funcionalidades do `usuário comum`. O `usuário vip` terá acesso a um botão chamado
`Create a playlist`, ao clicar nesse botão, uma nova janela (imagem abaixo) irá aparecer, solicitando o nome da `playlist` que o usuário
deseja criar.

<img src="docs\NewPlaylistScreen.png">

Ao inserir o nome da playlist, basta clicar em `create`, a `playlist` aparecerá na lista de `playlists`.

<img src="docs\CreatePlaylistExample.png">
<img src="docs\PlaylistCreated.png">

Ao `clicar` na playlist criada, um novo botão, chamado `Add music to playlist`, irá aparecer. Este botão solicitará que o
`usuário` escolha uma música, em algum diretório, para adicionar a `playlist`. Ao escolher a música, ela aparecerá ao lado
da lista de playlists.

<img src="docs\MusicAddedToAPlaylist.png">

A imagem acima mostra mostra um exemplo de um usuário que adicionou uma nova música à playlist `Pop music 2023`.

## Funcionalidades ##

### Configurações ###

<img src="docs\ConfigButton.png">

Este botão se encontra na tela de login e na tela de registro do `usuário`. Ao clicá-lo, o `usuário` terá acesso a seguinte tela:

<img src="docs\ConfigScreen.png">

Nesta tela, o `usuário` poderá selecionar a linguagem do `Music Player`, podendo escolher entre:
- Inglês

- Português

- Francês

- Japonês

Além disso, o usuário poderá esolhar o tema que deseja usar, podendo escolher entre o tema `escuro` e o tema `claro`.

---

> Desenvolvido por Yuri Filgueira e Renzo Paulo.