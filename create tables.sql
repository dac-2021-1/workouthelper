CREATE TABLE `aluno` (
  `id` int NOT NULL,
  `lista_avaliacao` varchar(45) DEFAULT NULL,
  `id_plano` int DEFAULT NULL,
  `ativo` tinyint NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario_idx` (`id_usuario`),
  KEY `id_plano_aluno_idx` (`id_plano`),
  CONSTRAINT `id_plano_aluno` FOREIGN KEY (`id_plano`) REFERENCES `plano` (`id`),
  CONSTRAINT `id_usuario_aluno` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `avaliacao` (
  `id` int NOT NULL,
  `id_aluno` int NOT NULL,
  `id_professor` int NOT NULL,
  `data_avaliacao` varchar(45) NOT NULL,
  `medidas` varchar(45) NOT NULL,
  `peso` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_aluno_idx` (`id_aluno`),
  KEY `fk_professor_idx` (`id_professor`),
  CONSTRAINT `fk_aluno` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id`),
  CONSTRAINT `fk_professor` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exercicio` (
  `id` int NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(45) NOT NULL,
  `link_video` varchar(100) DEFAULT NULL,
  `recomendacao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ficha_treino` (
  `id` int NOT NULL,
  `id_aluno` int NOT NULL,
  `id_professor` int NOT NULL,
  `treino_a` int NOT NULL,
  `treino_b` int NOT NULL,
  `treino_c` int DEFAULT NULL,
  `data_inicio` varchar(45) NOT NULL,
  `data_final` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_aluno_idx` (`id_aluno`),
  KEY `fk_professor_idx` (`id_professor`),
  KEY `fk_treino_a_idx` (`treino_a`),
  KEY `fk_treino_b_idx` (`treino_b`),
  KEY `fk_treino_c_idx` (`treino_c`),
  CONSTRAINT `fk_ficha_aluno` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id`),
  CONSTRAINT `fk_ficha_professor` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id`),
  CONSTRAINT `fk_ficha_treino_a` FOREIGN KEY (`treino_a`) REFERENCES `treino` (`id`),
  CONSTRAINT `fk_ficha_treino_b` FOREIGN KEY (`treino_b`) REFERENCES `treino` (`id`),
  CONSTRAINT `fk_ficha_treino_c` FOREIGN KEY (`treino_c`) REFERENCES `treino` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plano` (
  `id` int NOT NULL,
  `nome` varchar(45) NOT NULL,
  `mensalidade` varchar(45) NOT NULL,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `professor` (
  `id` int NOT NULL,
  `CREF` varchar(45) NOT NULL,
  `salario` varchar(45) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario_idx` (`id_usuario`),
  CONSTRAINT `id_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `rl_treino_exercico` (
  `id_treino` int NOT NULL,
  `id_exercicio` int NOT NULL,
  `serie` varchar(45) NOT NULL,
  `repeticao` varchar(45) NOT NULL,
  `intervalo` varchar(45) NOT NULL,
  `row` varchar(45) NOT NULL,
  PRIMARY KEY (`id_treino`,`id_exercicio`),
  KEY `fk_exercicio_idx` (`id_exercicio`),
  CONSTRAINT `fk_exercicio` FOREIGN KEY (`id_exercicio`) REFERENCES `exercicio` (`id`),
  CONSTRAINT `fk_treino` FOREIGN KEY (`id_treino`) REFERENCES `treino` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `treino` (
  `id` int NOT NULL,
  `grupamento` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuario` (
  `id` int NOT NULL,
  `nome` varchar(45) NOT NULL,
  `data_nasc` varchar(45) NOT NULL,
  `sexo` varchar(45) NOT NULL,
  `cpf` varchar(45) NOT NULL,
  `foto` varchar(1000) DEFAULT NULL,
  `data_inicio` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
