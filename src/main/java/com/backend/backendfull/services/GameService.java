package com.backend.backendfull.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backendfull.model.Game;
import com.backend.backendfull.model.exception.ResourceNotFoundException;
import com.backend.backendfull.repository.GameRepository;
import com.backend.backendfull.shared.GameDTO;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<GameDTO> getAll() {

        // Retorna lista
        List<Game> games = gameRepository.findAll();

        return games.stream()
                .map(game -> new ModelMapper().map(game, GameDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<GameDTO> getId(Integer id) {
        // retorna optional por id
        Optional<Game> game = gameRepository.findById(id);
        // se não lança exception
        if (game.isEmpty()) {
            throw new ResourceNotFoundException("Game com id: " + id + "não encontrado");
        }
        // converte optional de game em GameDTO
        GameDTO dto = new ModelMapper().map(game.get(), GameDTO.class);

        // cria e retorna optional gameDTO
        return Optional.of(dto);
    }

    public GameDTO add(GameDTO gameDto) {
        // Remove o id para poder fazer o cadastro
        gameDto.setId(null);

        // cria objeto de mapeamento
        ModelMapper mapper = new ModelMapper();
        // converter gameDTO em um game
        Game game = mapper.map(gameDto, Game.class);
        // salvar o game no BD
        game = gameRepository.save(game);

        gameDto.setId(game.getId());
        // retornar o gameDTO atualizado

        return gameDto;
    }

    public void delete(Integer id) {
        // verifica se produto existe
        Optional<Game> game = gameRepository.findById(id);

        // Se não lança exception
        if (game.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível deletar o game com ID" + id
                    + "Game não existe");
        }
        // Deletar pelo Id
        gameRepository.deleteById(id);

    }

    public GameDTO update(Integer id, GameDTO gameDto) {

        gameDto.setId(id);
        // Cria objeto de mapeamento
        ModelMapper mapper = new ModelMapper();
        // Converte DTO em Game
        Game game = mapper.map(gameDto, Game.class);
        // Atualiza Game no BD
        gameRepository.save(game);
        // Retorna gameDto atualizado
        return gameDto;

    }

}
