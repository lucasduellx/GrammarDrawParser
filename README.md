Projeto de Gramática da disciplina de Compiladores

Arquivo da gramática: gramatica.txt

Formato:

Σ;ω;pX;δ;n

Σ -> Alfabeto da linguagem 

ω -> Axioma, condição inicial

pX -> Regras de produção da gramática(separados por ,)

δ -> Angulo em graus

n -> Passos, "repetições"

Constantes aceitas: + - [ ] F f

+: gira sentido horário

-: gira sentido anti-horário

[: push

]: pop

F: desenha pra frente

f: vai para frente(sem desenhar)

Exemplo:

Floco de Neve

Σ = C

ω = C-C-C-C-C

pX = C=C-C++C+C-C-C

δ = 72

n = 4

Gramatica txt: C;C-C-C-C-C;C=C-C++C+C-C-C;72;4

Carpete

Σ = A G

ω = A

pX = A=A+A-A-A-G+A+A+A-A,G=GGG

δ = 90

n = 4

Gramatica txt: A G;A;A=A+A-A-A-G+A+A+A-A,G=GGG;90;4

Hilbert curve

Σ = A B

ω = A

pX = A=+BF-AFA-FB+, B=-AF+BFB+FA-

δ = 90

n = 5

Gramatica txt: A B;A;A=+BF-AFA-FB+, B=-AF+BFB+FA-;90;5
