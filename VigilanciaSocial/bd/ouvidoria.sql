-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 19-Dez-2014 às 21:20
-- Versão do servidor: 5.6.12-log
-- versão do PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `ouvidoria`
--
CREATE DATABASE IF NOT EXISTS `ouvidoria` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ouvidoria`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `acompanhamento`
--

CREATE TABLE IF NOT EXISTS `acompanhamento` (
  `idAcomp` int(11) NOT NULL AUTO_INCREMENT,
  `dtAcomp` date NOT NULL,
  `evolucao` text,
  `idOcorrencia` int(11) NOT NULL,
  PRIMARY KEY (`idAcomp`),
  KEY `idOcorrencia` (`idOcorrencia`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `acompanhamento`
--

INSERT INTO `acompanhamento` (`idAcomp`, `dtAcomp`, `evolucao`, `idOcorrencia`) VALUES
(1, '2014-12-02', 'FAMILIA ACOMPANHADA', 10),
(2, '2014-12-03', 'FAMILIA EM ACOMPANHAMENTO', 12),
(3, '2014-12-03', 'A FAMILIA CONTINUA EM ACOMPANHAMENTO', 10),
(4, '2014-12-02', 'FAMILIA ACOMPANHADA', 17);

-- --------------------------------------------------------

--
-- Estrutura da tabela `agressor`
--

CREATE TABLE IF NOT EXISTS `agressor` (
  `idAgressor` int(11) NOT NULL AUTO_INCREMENT,
  `descParentesco` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idAgressor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Extraindo dados da tabela `agressor`
--

INSERT INTO `agressor` (`idAgressor`, `descParentesco`) VALUES
(1, 'Pai'),
(2, 'Mãe'),
(3, 'CONJUGUE'),
(4, 'FILHO'),
(5, 'FILHA');

-- --------------------------------------------------------

--
-- Estrutura da tabela `avaliacao`
--

CREATE TABLE IF NOT EXISTS `avaliacao` (
  `idAvaliacao` int(11) NOT NULL AUTO_INCREMENT,
  `avaliacao` varchar(30) NOT NULL,
  `descAvaliacao` text NOT NULL,
  `dtAvaliacao` date NOT NULL,
  `idOcorrencia` int(11) NOT NULL,
  PRIMARY KEY (`idAvaliacao`),
  KEY `idOcorrencia` (`idOcorrencia`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Extraindo dados da tabela `avaliacao`
--

INSERT INTO `avaliacao` (`idAvaliacao`, `avaliacao`, `descAvaliacao`, `dtAvaliacao`, `idOcorrencia`) VALUES
(1, 'OTIMO', 'AVALIADO COM OTIMO RESULTADO', '2014-12-10', 14),
(2, 'BOM', 'AVALIACAO POSITIVA', '2014-12-15', 12);

-- --------------------------------------------------------

--
-- Estrutura da tabela `encaminhar`
--

CREATE TABLE IF NOT EXISTS `encaminhar` (
  `idEncaminhamento` int(11) NOT NULL AUTO_INCREMENT,
  `dtEncaminhamento` date NOT NULL,
  `Ocorrencia_idOcorrencia` int(11) NOT NULL,
  `Orgaoresp_idOrgaoresp` int(11) NOT NULL,
  PRIMARY KEY (`idEncaminhamento`),
  KEY `Ocorrencia_idOcorrencia` (`Ocorrencia_idOcorrencia`,`Orgaoresp_idOrgaoresp`),
  KEY `Orgaoresp_idOrgaoresp` (`Orgaoresp_idOrgaoresp`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Extraindo dados da tabela `encaminhar`
--

INSERT INTO `encaminhar` (`idEncaminhamento`, `dtEncaminhamento`, `Ocorrencia_idOcorrencia`, `Orgaoresp_idOrgaoresp`) VALUES
(1, '2014-12-10', 13, 10),
(2, '2014-12-09', 14, 10),
(3, '2014-12-01', 15, 14),
(4, '2014-12-15', 16, 13),
(5, '2014-12-15', 17, 14);

-- --------------------------------------------------------

--
-- Estrutura da tabela `endorgaoresp`
--

CREATE TABLE IF NOT EXISTS `endorgaoresp` (
  `idEndOrgaoResp` int(11) NOT NULL AUTO_INCREMENT,
  `tipoLog` varchar(30) DEFAULT NULL,
  `nomeLog` varchar(45) DEFAULT NULL,
  `nrLog` varchar(15) DEFAULT NULL,
  `bairro` varchar(30) DEFAULT NULL,
  `cep` varchar(9) DEFAULT NULL,
  `cidade` varchar(35) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  `idOrgResp` int(11) NOT NULL,
  PRIMARY KEY (`idEndOrgaoResp`),
  UNIQUE KEY `idOrgResp_2` (`idOrgResp`),
  KEY `idOrgResp` (`idOrgResp`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Extraindo dados da tabela `endorgaoresp`
--

INSERT INTO `endorgaoresp` (`idEndOrgaoResp`, `tipoLog`, `nomeLog`, `nrLog`, `bairro`, `cep`, `cidade`, `uf`, `idOrgResp`) VALUES
(6, 'Av', 'Frederico Ozana', '1110', 'Alto Sumaré', '11111-111', 'Barretos', 'SP', 13),
(7, 'Av', 'PAULO DIOGO VALIN', '1234', 'ZEQUINHA AMENDOLA', '14780-000', 'BARRETOS', 'SP', 14),
(8, 'Rua', 'ARACATUBA', '2345', 'IBIRAPUERA', '14780-000', 'BARRETOS', 'SP', 15);

-- --------------------------------------------------------

--
-- Estrutura da tabela `endvitima`
--

CREATE TABLE IF NOT EXISTS `endvitima` (
  `idEndVitima` int(11) NOT NULL AUTO_INCREMENT,
  `tipoLog` varchar(25) DEFAULT NULL,
  `nomeLog` varchar(50) DEFAULT NULL,
  `nrLog` varchar(15) DEFAULT NULL,
  `bairro` varchar(30) DEFAULT NULL,
  `cep` varchar(9) DEFAULT NULL,
  `cidade` varchar(30) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  `idVitima` int(11) NOT NULL,
  PRIMARY KEY (`idEndVitima`),
  UNIQUE KEY `idVitima` (`idVitima`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Extraindo dados da tabela `endvitima`
--

INSERT INTO `endvitima` (`idEndVitima`, `tipoLog`, `nomeLog`, `nrLog`, `bairro`, `cep`, `cidade`, `uf`, `idVitima`) VALUES
(2, 'Rua', '10', '1212', 'Centro', '11111-111', 'Barretos', 'SP', 3),
(3, 'Rua', '32', '123', 'Centro', '12222-222', 'Barretos', 'SP', 4),
(4, 'Al', 'Teste', '83910', 'Centro', '44444-444', 'Barretos', 'SP', 6),
(5, 'Rua', '20', '123', 'CENTRO', '14780-000', 'BARRETOS', 'SP', 7),
(6, 'Av', '43', '222', 'JARDIM ALVORADA', '14780-000', 'BARRETOS', 'SP', 8),
(8, 'Rua', '10', '198', 'CENTRO', '14780-000', 'BARRETOS', 'SP', 10),
(9, 'Rua', '26', '1909', 'BARONI', '14780-999', 'BARRETOS', 'SP', 11);

-- --------------------------------------------------------

--
-- Estrutura da tabela `ocorrencia`
--

CREATE TABLE IF NOT EXISTS `ocorrencia` (
  `idOcorrencia` int(11) NOT NULL AUTO_INCREMENT,
  `descOcorrencia` text NOT NULL,
  `dtDenuncia` date NOT NULL,
  `horaDenuncia` time DEFAULT NULL,
  `dtOcorrencia` date DEFAULT NULL,
  `Segmento_idSegmento` int(11) DEFAULT NULL,
  `idVitma` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idAgressor` int(11) NOT NULL,
  PRIMARY KEY (`idOcorrencia`),
  KEY `fk_Ocorrencia_Segmento_idx` (`Segmento_idSegmento`),
  KEY `idVitma` (`idVitma`),
  KEY `idUsuario` (`idUsuario`),
  KEY `idAgressor` (`idAgressor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Extraindo dados da tabela `ocorrencia`
--

INSERT INTO `ocorrencia` (`idOcorrencia`, `descOcorrencia`, `dtDenuncia`, `horaDenuncia`, `dtOcorrencia`, `Segmento_idSegmento`, `idVitma`, `idUsuario`, `idAgressor`) VALUES
(10, 'CRIANCA AGREDIDA', '2014-12-01', '10:42:00', '2014-11-07', 2, 7, 1, 2),
(11, 'IDOSO AGREDIDO', '2014-12-01', '05:26:00', '2014-11-06', 1, 8, 1, 1),
(12, 'CRIANCA VIOLENTADA', '2014-12-01', '05:34:00', '2014-12-01', 2, 3, 19, 1),
(13, 'IDOSA VIOLENTADA', '2014-12-10', '10:34:00', '2014-12-01', 1, 10, 1, 4),
(14, 'IDOSA HUMILHADA', '2014-12-10', '08:34:00', '2014-11-07', 1, 10, 1, 4),
(15, 'CRIANCA VIOLENTADA', '2014-12-15', '10:43:00', '2014-12-01', 2, 6, 1, 1),
(16, 'IDOSO AGREDIDO', '2014-12-15', '07:30:00', '2014-12-01', 1, 8, 1, 4),
(17, 'MULHER AGREDIDA', '2014-12-15', '09:37:00', '2014-12-02', 4, 11, 1, 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `orgaoresp`
--

CREATE TABLE IF NOT EXISTS `orgaoresp` (
  `idOrgaoResp` int(11) NOT NULL AUTO_INCREMENT,
  `nomeOrgao` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `codSuas` bigint(20) unsigned DEFAULT NULL,
  `tipoRede` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idOrgaoResp`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Extraindo dados da tabela `orgaoresp`
--

INSERT INTO `orgaoresp` (`idOrgaoResp`, `nomeOrgao`, `email`, `codSuas`, `tipoRede`) VALUES
(10, 'CRAS I', 'cras1@barretos.sp.gov.br', 35055000939, 'Publica'),
(13, 'CRAS II', 'cras2@barretos.sp.gov.br', 35055000941, 'Privada'),
(14, 'CRAS III', 'cras3@barretos.sp.gov.br', 35055000944, 'Publica'),
(15, 'CRAS IV', 'cras4@barretos.com.br', 35055000948, 'Privada');

-- --------------------------------------------------------

--
-- Estrutura da tabela `segmento`
--

CREATE TABLE IF NOT EXISTS `segmento` (
  `idSegmento` int(11) NOT NULL AUTO_INCREMENT,
  `descSegmento` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idSegmento`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `segmento`
--

INSERT INTO `segmento` (`idSegmento`, `descSegmento`) VALUES
(1, 'IDOSO'),
(2, 'CRIANCA'),
(3, 'ADOLESCENTE'),
(4, 'MULHER');

-- --------------------------------------------------------

--
-- Estrutura da tabela `telorgaoresp`
--

CREATE TABLE IF NOT EXISTS `telorgaoresp` (
  `idTel` int(11) NOT NULL AUTO_INCREMENT,
  `dddTel` varchar(4) NOT NULL,
  `telefone` varchar(10) DEFAULT NULL,
  `idOrgResp` int(11) NOT NULL,
  PRIMARY KEY (`idTel`),
  KEY `idOrgResp` (`idOrgResp`),
  KEY `idOrgResp_2` (`idOrgResp`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=34 ;

--
-- Extraindo dados da tabela `telorgaoresp`
--

INSERT INTO `telorgaoresp` (`idTel`, `dddTel`, `telefone`, `idOrgResp`) VALUES
(14, '(17)', '3322-5638', 13),
(15, '(17)', '99999-9999', 13),
(16, '(17)', '3322-5499', 14),
(17, '(17)', '99177-6688', 14),
(18, '(17)', '3322-7626', 15),
(19, '(17)', '99177-8877', 15);

-- --------------------------------------------------------

--
-- Estrutura da tabela `telvitma`
--

CREATE TABLE IF NOT EXISTS `telvitma` (
  `idTelVitma` int(11) NOT NULL AUTO_INCREMENT,
  `ddd` varchar(4) DEFAULT NULL,
  `telVitima` varchar(10) DEFAULT NULL,
  `idVitima` int(11) NOT NULL,
  PRIMARY KEY (`idTelVitma`),
  KEY `idVitima` (`idVitima`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=31 ;

--
-- Extraindo dados da tabela `telvitma`
--

INSERT INTO `telvitma` (`idTelVitma`, `ddd`, `telVitima`, `idVitima`) VALUES
(1, '(17)', '3322-3456', 3),
(2, '(17)', '99123-4567', 3),
(19, '(17)', '3324-5566', 4),
(20, '(17)', '99122-3344', 4),
(21, '(17)', '3333-3333', 6),
(22, '(17)', '99999-9999', 6),
(23, '(17)', '3322-5566', 7),
(24, '(17)', '99999-9999', 7),
(25, '(17)', '3322-4455', 8),
(26, '(17)', '99543-6278', 8),
(28, '(17)', '3322-1212', 10),
(29, '(00)', '00000-0000', 10),
(30, '(17)', '3322-4455', 11);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `email` varchar(30) NOT NULL,
  `senha` varchar(10) NOT NULL,
  `setor` varchar(30) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=40 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nome`, `email`, `senha`, `setor`) VALUES
(1, 'ELTON MARQUES DA SILVA', 'eltonmrq@gmail.com', '1234', 'Ouvidoria Social'),
(2, 'MARIA FERNANDA FERREIRA', 'mariafernanda@hotmail.com', '12345', 'Central de Informações'),
(7, 'ELTON FRANCISCO', 'elton@outlook.com', '1234', 'Vigilância Social'),
(9, 'JOANA DARC', 'joana@barretos.com', 'joana', 'Casa dos Conselhos'),
(10, 'MARIA FLAVIA', 'mariaflavia@gmail.com', 'marioa', 'Vigilância Social'),
(11, 'JOSE FRANCISCO', 'jose@francisco', '1234', 'Ouvidoria Social'),
(19, 'LUIZ RICARDO NETO', 'luiz@gmail.com', 'tes', 'Casa dos Conselhos'),
(24, 'DIEGO DA SILVA ALVES', 'diegoalves_btos@hotmail.com', '1234', 'Central de Informações');

-- --------------------------------------------------------

--
-- Estrutura da tabela `vitima`
--

CREATE TABLE IF NOT EXISTS `vitima` (
  `idVitima` int(11) NOT NULL AUTO_INCREMENT,
  `nomeVitima` varchar(50) DEFAULT NULL,
  `sexo` varchar(1) DEFAULT NULL,
  `dtNasc` date DEFAULT NULL,
  PRIMARY KEY (`idVitima`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Extraindo dados da tabela `vitima`
--

INSERT INTO `vitima` (`idVitima`, `nomeVitima`, `sexo`, `dtNasc`) VALUES
(1, 'Elton Marques da Silva', 'M', '2014-11-12'),
(2, 'Maria Madalena', 'M', '2014-11-20'),
(3, 'Francisca da Silva', 'F', '1994-01-18'),
(4, 'Maria Jose Barbosa', 'F', '1993-01-01'),
(5, 'JOSEFA BENEDITA', 'F', '2011-11-23'),
(6, 'Teste da Silva', 'F', '2004-12-02'),
(7, 'MARIA DE FATIMA', 'F', '2004-12-30'),
(8, 'PAULO DA SILVA', 'M', '1934-01-03'),
(10, 'OLINDA DE ALMEIDA', 'F', '1933-10-12'),
(11, 'JOSELAINE DE SOUZA', 'F', '1988-07-05');

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `acompanhamento`
--
ALTER TABLE `acompanhamento`
  ADD CONSTRAINT `acompanhamento_ibfk_1` FOREIGN KEY (`idOcorrencia`) REFERENCES `ocorrencia` (`idOcorrencia`);

--
-- Limitadores para a tabela `avaliacao`
--
ALTER TABLE `avaliacao`
  ADD CONSTRAINT `avaliacao_ibfk_1` FOREIGN KEY (`idOcorrencia`) REFERENCES `ocorrencia` (`idOcorrencia`);

--
-- Limitadores para a tabela `encaminhar`
--
ALTER TABLE `encaminhar`
  ADD CONSTRAINT `encaminhar_ibfk_1` FOREIGN KEY (`Ocorrencia_idOcorrencia`) REFERENCES `ocorrencia` (`idOcorrencia`),
  ADD CONSTRAINT `encaminhar_ibfk_2` FOREIGN KEY (`Orgaoresp_idOrgaoresp`) REFERENCES `orgaoresp` (`idOrgaoResp`);

--
-- Limitadores para a tabela `endorgaoresp`
--
ALTER TABLE `endorgaoresp`
  ADD CONSTRAINT `endorgaoresp_ibfk_1` FOREIGN KEY (`idOrgResp`) REFERENCES `orgaoresp` (`idOrgaoResp`);

--
-- Limitadores para a tabela `endvitima`
--
ALTER TABLE `endvitima`
  ADD CONSTRAINT `endvitima_ibfk_1` FOREIGN KEY (`idVitima`) REFERENCES `vitima` (`idVitima`);

--
-- Limitadores para a tabela `ocorrencia`
--
ALTER TABLE `ocorrencia`
  ADD CONSTRAINT `fk_Ocorrencia_Segmento` FOREIGN KEY (`Segmento_idSegmento`) REFERENCES `segmento` (`idSegmento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `ocorrencia_ibfk_1` FOREIGN KEY (`idVitma`) REFERENCES `vitima` (`idVitima`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `ocorrencia_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `ocorrencia_ibfk_3` FOREIGN KEY (`idAgressor`) REFERENCES `agressor` (`idAgressor`);

--
-- Limitadores para a tabela `telorgaoresp`
--
ALTER TABLE `telorgaoresp`
  ADD CONSTRAINT `telorgaoresp_ibfk_1` FOREIGN KEY (`idOrgResp`) REFERENCES `orgaoresp` (`idOrgaoResp`);

--
-- Limitadores para a tabela `telvitma`
--
ALTER TABLE `telvitma`
  ADD CONSTRAINT `telvitma_ibfk_1` FOREIGN KEY (`idVitima`) REFERENCES `vitima` (`idVitima`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
